package sabai.repo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sabai.book.BookTrans;
import sabai.book.BookCount;

import java.util.List;
import java.util.stream.Stream;

// This will be AUTO IMPLEMENTED by Spring into a Bean called TransactionRepository
// CRUD refers Create, Read, Update, Delete

public interface BookTranRepository extends JpaRepository<BookTrans, Long> {
	public List<BookTrans> findAll();
	public Stream<BookTrans> findByBookname(String bookName);
    public Stream<BookTrans> findByOwnedby(String ownedBy);
    public Stream<BookTrans> findByBooknameAndOwnedby(String bookName,String ownedBy);
    
    @Query("select distinct c.ownedby from BookTrans c" )
    public Stream<String> findByBookOwners();
     
    @Query("select new sabai.book.BookCount(bt.bookname, sum(bt.count)) "
    		+ "from BookTrans bt group by bt.bookname ")
    public List<BookCount> findGroupBy();
    
    @Query("select c from BookTrans c where c.bookname = :bookName and c.ownedby = :ownedBy" )
    List<BookTrans> findBookTranByBookOwner(@Param("bookName") String bookName, @Param("ownedBy") String ownedBy);
	
}
