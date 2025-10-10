import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;

import org.hamcrest.Matcher;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.anything;

import static org.junit.Assert.assertEquals;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

// Importações auxiliares do Hamcrest (Assume a existência de uma classe utilitária)
// Nota: samePropertyValuesAs não é um matcher padrão do Hamcrest,
// é geralmente fornecido por uma biblioteca ou utilitário do projeto (como o jMock Extensions).
// Vamos importá-lo como se fosse um metodo estático de Hamcrest:
import static org.hamcrest.beans.SamePropertyValuesAs.samePropertyValuesAs;
// Ou de um utilitário do seu projeto para matchers personalizados.

@RunWith(JMock.class)
public class SnipersTableModelTest {
    private final Mockery context = new Mockery();
    private TableModelListener listener = context.mock(TableModelListener.class);
    private final SnipersTableModel model = new SnipersTableModel();
    @Before
    public void attachModelListener() {
        model.addTableModelListener(listener);
    }

    @Test
    public void hasEnoughColumns() {
        assertThat(model.getColumnCount(), equalTo(Column.values().length));
    }
    @Test
    public void setsSniperValuesInColumns() {
        SniperSnapshot joining = SniperSnapshot.joining("item id");
        SniperSnapshot bidding = joining.bidding(555, 666);
        AuctionSniper sniper = auctionSniperFor(joining);
        context.checking(new Expectations() {{
            allowing(listener).tableChanged(with(anyInsertionEvent()));
            oneOf(listener).tableChanged(with(aChangeInRow(0)));
        }});
        model.addSniper(sniper);
        model.sniperStateChanged(bidding);
        assertRowMatchesSnapshot(0, bidding);
    }
    private void assertRowMatchesSnapshot(int row, SniperSnapshot snapshot) {
        assertColumnEquals(Column.ITEM_IDENTIFIER, snapshot.itemId);
        assertColumnEquals(Column.LAST_PRICE, snapshot.lastPrice);
        assertColumnEquals(Column.LAST_BID, snapshot.lastBid);
        // Note: A asserção do status aqui é mais complexa e depende de SnipersTableModel.textFor()
    }
    private void assertColumnEquals(Column column, Object expected) {
        final int rowIndex = 0;
        final int columnIndex = column.ordinal();
        assertEquals(expected, model.getValueAt(rowIndex, columnIndex));
    }
    private Matcher<TableModelEvent> aRowChangedEvent() {
        return samePropertyValuesAs(new TableModelEvent(model, 0));
    }

    @Test
    public void setsUpColumnHeadings() {
        for (Column column: Column.values()) {
            assertEquals(column.name, model.getColumnName(column.ordinal()));
        }
    }

    @Test
    public void notifiesListenersWhenAddingASniper() {
        SniperSnapshot joining = SniperSnapshot.joining("item123");
        context.checking(new Expectations() {{
            oneOf(listener).tableChanged(with(anInsertionAtRow(0)));
        }});
        assertEquals(0, model.getRowCount());
        model.addSniper(joining);
        assertEquals(1, model.getRowCount());
        assertRowMatchesSnapshot(0, joining);
    }
    private Matcher<TableModelEvent> aChangeInRow(int row) {
        return samePropertyValuesAs(new TableModelEvent(model, row));
    }
    private Matcher<TableModelEvent> anyInsertionEvent() {
        return samePropertyValuesAs(new TableModelEvent(model, 0, 0, TableModelEvent.INSERT));
    }
    private Matcher<TableModelEvent> anInsertionAtRow(int row) {
        return samePropertyValuesAs(new TableModelEvent(model, row, row, TableModelEvent.INSERT));
    }

    private AuctionSniper auctionSniperFor(SniperSnapshot snapshot) {
        AuctionSniper sniper = context.mock(AuctionSniper.class, "sniper-" + snapshot.itemId);
        // allowing(sniper).getSnapshot(); will(returnValue(snapshot));
        return sniper;
    }

    @Test
    public void holdsSnipersInAdditionOrder() {
        context.checking(new Expectations() {{
            ignoring(listener);
        }});
        model.addSniper(SniperSnapshot.joining("item 0"));
        model.addSniper(SniperSnapshot.joining("item 1"));
        assertEquals("item 0", cellValue(0, Column.ITEM_IDENTIFIER));
        assertEquals("item 1", cellValue(1, Column.ITEM_IDENTIFIER));
    }
    private Object cellValue(int row, Column column) {
        return model.getValueAt(row, column.ordinal());
    }
    public void updatesCorrectRowForSniper() {  }
    public void throwsDefectIfNoExistingSniperForAnUpdate() {  }
}