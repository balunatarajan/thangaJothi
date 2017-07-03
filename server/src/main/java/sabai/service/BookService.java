package sabai.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sabai.book.BookData;
import sabai.book.BookTrans;
import sabai.helper.Adoptar;
import sabai.helper.RetVal;
import sabai.repo.BookTranRepository;

@Service
public class BookService {
	
	@Autowired
	BookTranRepository bookTranRepository;
	
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
	
	public RetVal modifyBookTran(BookData data){
		BookTrans bt = new BookTrans();
		Adoptar.copyClientDataToDatabase(data,bt);
		bookTranRepository.save(bt);
		RetVal retval = new RetVal("PASS","Modified");
		return retval;
	}
	
	public BookData addBookTran(BookData data){
		BookTrans bt = new BookTrans();
		Adoptar.copyClientDataToDatabase(data,bt);
		bookTranRepository.save(bt);

		List<Object> value = new ArrayList<>();
		Adoptar.copyTranCodeToList(data,value);
		misc.connectGoogleSheetAddData(value,3);

		return data;
	}
	
}
