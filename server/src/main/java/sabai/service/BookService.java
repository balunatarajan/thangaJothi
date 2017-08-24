package sabai.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sabai.book.BookCount;
import sabai.book.BookData;
import sabai.book.BookTrans;
import sabai.book.BookTransfer;
import sabai.helper.Adoptar;
import sabai.helper.RetVal;
import sabai.messageQ.Listener;
import sabai.repo.BookTranRepository;

@Service
public class BookService {
	
	@Autowired
	BookTranRepository bookTranRepository;
	
	@Autowired 
	Listener mAQ;
	
	MiscService misc = new MiscService();
	
	public Iterable<BookData> getAllBookTran() {
		List<BookTrans> listA = bookTranRepository.findAll();
		List<BookData> listB = new ArrayList<>();
		for (BookTrans bt : listA) {
			BookData bd = new BookData();
			Adoptar.copyDatabaseToClientData(bt, bd);
			listB.add(bd);
		}
		return listB;
	}
	
	public Iterable<BookCount> getAllBookTranGroupBy() {
		List<BookCount> listA = bookTranRepository.findGroupBy();		
		return listA;
	}
	
	public RetVal modifyBookTran(BookData data){
		BookTrans bt = new BookTrans();
		Adoptar.copyClientDataToDatabase(data,bt);
		bookTranRepository.save(bt);
		RetVal retval = new RetVal("PASS","Modified");
		return retval;
	}
	
	
	//List<Email> findByEmailIdInAndPincodeIn(List<String> emails, List<String> pinCodes);
	//select email_id,name from email_details where eamil_id in('mike@gmail.com','ram@gmail.com') and pin_code in('633677','733877')
	public RetVal addBookTran(List<BookData> dataList){
		// DONT CONSOLIDATE ,, we need to track each additon.
		//
		RetVal retval = new RetVal("PASS","Saved");

		dataList.forEach((data) -> {
				if(!updateBookTrans(data,true))
				{
					BookTrans bt = new BookTrans();
					Adoptar.copyClientDataToDatabase(data,bt);
					bookTranRepository.save(bt);			
				}
		});
		return retval;
	}
	@Transactional
	public Iterable<BookData> getBookTranByTitle(String title){
		Stream<BookTrans> dataList = bookTranRepository.findByBookname(title);
		List<BookData> listB = new ArrayList<>();
		
		dataList.forEach((data)->{BookData bt = new BookData();
		Adoptar.copyDatabaseToClientData(data,bt);
		listB.add(bt);});
		
		
		/*for(BookTrans data:dataList) {
			BookData bt = new BookData();
			Adoptar.copyDatabaseToClientData(data,bt);
			listB.add(bt);
		}*/
		return listB;
	}
	
	@Transactional
	public Iterable<BookData> getBookTranByOwner(String owner){
		Stream<BookTrans> dataList = bookTranRepository.findByOwnedby(owner);
		List<BookData> listB = new ArrayList<>();
		
		dataList.forEach((data)->{BookData bt = new BookData();
		Adoptar.copyDatabaseToClientData(data,bt);listB.add(bt);});
		
		return listB;
	}
	@Transactional
	public Stream<String> getBookOwners(){
		return bookTranRepository.findByBookOwners();
	}
	
	@Transactional
	public Iterable<BookData> getBookTranByOwnerTitle(String owner, String title){
		Stream<BookTrans> dataList = bookTranRepository.findByBooknameAndOwnedby(owner,title);
		List<BookData> listB = new ArrayList<>();
		
		dataList.forEach((data)->{BookData bt = new BookData();
		Adoptar.copyDatabaseToClientData(data,bt);listB.add(bt);});

		return listB;
	}

	public RetVal sellBooks(List<BookData> dataList){

		RetVal retval = new RetVal("Update","Passed");
		dataList.forEach((bt) -> updateBookTrans(bt,false));
		return retval;
	}
	@Transactional 
	public RetVal transferBook(List<BookTransfer> dataList){
		// (-) bt.getFromOwner() (+)  bt.getToOwner() bt.getBookName()  bt.getCount()
		RetVal retval = new RetVal("Transfer","Passed");
		
		List<BookData> addList = new ArrayList<>();
		dataList.forEach((bt) -> {
			BookData fbd = new BookData();
			fbd.setBookName(bt.getBookName());
			fbd.setCount(bt.getCount());
			fbd.setOwnedBy(bt.getFromOwner());
			updateBookTrans(fbd,false);
			
			BookData tbd = new BookData();
			tbd.setBookName(bt.getBookName());
			tbd.setCount(bt.getCount());
			tbd.setOwnedBy(bt.getToOwner());
			addList.add(tbd);
		});
		this.addBookTran(addList);
		
		return retval;
		
	}
	
	private  boolean updateBookTrans(BookData bt, boolean addBook){

 		 boolean updated = false; 
		// get the trans by book name and owner 
		// Reduce the qty 
			List<BookTrans> btsList = 
						bookTranRepository.findBookTranByBookOwner(bt.getBookName(),bt.getOwnedBy());
	
			if(btsList.size() > 0)
			{
				BookTrans btd = btsList.get(0);
				if(addBook)
					btd.setCount(btd.getCount() + bt.getCount());
				else
					btd.setCount(btd.getCount() - bt.getCount());
				updated = true;
				bookTranRepository.save(btd);
			}
		
		return updated;
	}

}
