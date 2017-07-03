package sabai.repo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import sabai.book.BookTrans;

import java.util.List;
import java.util.stream.Stream;

// This will be AUTO IMPLEMENTED by Spring into a Bean called TransactionRepository
// CRUD refers Create, Read, Update, Delete

public interface BookTranRepository extends CrudRepository<BookTrans, Long> {
	public List<BookTrans> findAll();
	
}
