package sabai.repo;

//import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;

//import com.querydsl.core.types.dsl.BooleanExpression;

import sabai.anbar.Anbargal;
import java.util.List;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

@Repository 
public interface AnbarRepository extends JpaRepository<Anbargal, Long>{ //, QueryDslPredicateExecutor<Anbargal>{
	
	public List<Anbargal> findAll();
	//public List<Anbargal> findAll(BooleanExpression be);
	
	@Query("select concat(c.username,c.city) from Anbargal c")
	List<String> findMasterDataByType();
	
	//@Query("select c from Customer c where c.email = :email")
    //Stream<Customer> findByEmailReturnStream(@Param("email") String email);
	
	//@Query("select c from Anbargal c where :nameLike ")
	//List<Anbargal> getAnbargalByCriteria(@Param("nameLike") String whereClause);
	
}
