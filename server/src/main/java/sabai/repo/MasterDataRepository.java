package sabai.repo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sabai.book.MasterData;

import java.util.List;

// This will be AUTO IMPLEMENTED by Spring into a Bean called TransactionRepository
// CRUD refers Create, Read, Update, Delete

public interface MasterDataRepository extends JpaRepository<MasterData, Long> {
	// custom query example and return a stream
    @Query("select c from MasterData c where c.type = :type")
    List<MasterData> findMasterDataByType(@Param("type") String type);

}
