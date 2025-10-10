public interface TotalSalesReport {
    void displaysTotalSalesFor(String product, Object matcher);
    void containsTotalSalesFor(String product, Object matcher);
}
