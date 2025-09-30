public interface UnitOfWork {
    transactor.perform(new UnitOfWork() {
        public void work() throws Exception {
            customers.addCustomer(aNewCustomer());
        }
    });
}
