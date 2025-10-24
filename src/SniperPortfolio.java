import java.util.List;

public interface SniperPortfolio extends SniperCollector, PortfolioListener {
    // A interface PortfolioListener deve estender EventListener
    public default void addPortfolioListener(PortfolioListener listener) {
        // Lógica de adição de listener (não é necessária aqui para compilar)
    }
}
