package sabai.repo;

//import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import sabai.transaction.Transaction;

import java.util.List;

// This will be AUTO IMPLEMENTED by Spring into a Bean called TransactionRepository
// CRUD refers Create, Read, Update, Delete

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
	public List<Transaction> findAll();
}
