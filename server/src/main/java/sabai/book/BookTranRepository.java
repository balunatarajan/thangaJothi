package sabai.book;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

// This will be AUTO IMPLEMENTED by Spring into a Bean called TransactionRepository
// CRUD refers Create, Read, Update, Delete

public interface BookTranRepository extends CrudRepository<BookTrans, Long> {
	public List<BookTrans> findAll();

}
