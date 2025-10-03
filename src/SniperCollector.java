import java.util.EventListener;

public interface SniperCollector extends EventListener {
    // Adiciona um novo AuctionSniper à coleção/visualização
    void addSniper(AuctionSniper sniper);
}
