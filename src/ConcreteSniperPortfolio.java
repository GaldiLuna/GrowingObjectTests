import java.util.ArrayList;
import java.util.List;

public class ConcreteSniperPortfolio implements SniperPortfolio {
    private final List<AuctionSniper> snipers = new ArrayList<>();
    public ConcreteSniperPortfolio() {}
    @Override
    public void addSniper(AuctionSniper sniper) {
        snipers.add(sniper);
        sniperAdded(sniper);
    }
    @Override
    public void sniperAdded(AuctionSniper sniper) {
        // Lógica para notificar os listeners (como o SnipersTableModel)
    }
}
