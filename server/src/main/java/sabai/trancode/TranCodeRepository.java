package sabai.trancode;

import org.springframework.data.repository.CrudRepository;

import sabai.*;

import java.util.List;

// This will be AUTO IMPLEMENTED by Spring into a Bean called TransactionRepository
// CRUD refers Create, Read, Update, Delete

public interface TranCodeRepository extends CrudRepository<TranCodeMaster, Long> {
	public List<TranCodeMaster> findAll();

}
