import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;

public class JPATransactor {
    private final EntityManager entityManager;
    public JPATransactor(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    public void perform(UnitOfWork unitOfWork) throws Exception {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        try {
            unitOfWork.work();
            transaction.commit();
        }
        catch (PersistenceException e) {
            transaction.rollback();
            throw e;
        }
        catch (Exception e) {
            transaction.rollback();
            throw e;
        }
    }
    public Object performQuery(QueryUnitOfWork unitOfWork) throws Exception {
        Object result = null;
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        try {
            result = unitOfWork.query(); // Executa o trabalho e captura o resultado
            transaction.commit();
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
