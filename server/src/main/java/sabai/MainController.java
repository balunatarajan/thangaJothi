package sabai;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.*;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List; 
import java.util.ArrayList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sabai.*;
import sabai.anbar.AnbarData;
import sabai.anbar.AnbarRepository;
import sabai.anbar.Anbargal;
import sabai.book.BookData;
import sabai.book.BookTrans;
import sabai.helper.Adoptar;
import sabai.helper.Count;
import sabai.helper.GoogleDriveSheet;
import sabai.helper.RetVal;
import sabai.service.AnbarService;
import sabai.service.BookService;
import sabai.service.TranCodeService;
import sabai.service.TransactionService;
import sabai.trancode.TranCode;
import sabai.trancode.TranCodeMaster;
import sabai.trancode.TranCodeRepository;
import sabai.transaction.TranData;
import sabai.transaction.Transaction;
import sabai.transaction.TransactionRepository;

//@CrossOrigin(origins = "http://localhost:4040")
@Controller    // This means that this class is a Controller
@RequestMapping(path="/tjgs") // This means URL's start with /demo (after Application path)
public class MainController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	AnbarService as;// = new AnbarService();
	@Autowired
	TransactionService trs;// = new AnbarService();
	@Autowired
	TranCodeService tcs;// = new AnbarService();
	@Autowired
	BookService bs;

	// get record count in table 
	@GetMapping(path="/getCount")
	public @ResponseBody Count getCount (@RequestParam("table")  String table) {

		long count = 0;
		if(table.equals("anbar")) {
			count = as.getCount() ; 
		} 
		else if(table.equals("trans")) {
			count = trs.getCount();
		}

		else if(table.equals("tcode")) {
			count = tcs.getCount();
		}
		Count nbrOfRows = new Count();
		nbrOfRows.setCount(String.valueOf(count));
		System.err.println("tjgs/getCount Number of "+ table +" : "+nbrOfRows.getCount());
		return nbrOfRows;
	}
/////////////////////////// Anbar ////////////////////////////////////////////
	//@GetMapping(path="/user/add") // Map ONLY GET Requests
	@RequestMapping(path="/anbar/add", method = RequestMethod.POST ,consumes = "application/json")
	public @ResponseBody RetVal addNewUser (@RequestBody AnbarData data) {
		// @ResponseBody means the returned String is the response, not a view name
		// @RequestParam means it is a parameter from the GET or POST request
		logger.error("/user/add: Add user the trust .....",data.getUserName());
		return as.addAnbar(data);
	}

	@RequestMapping(path="/anbar/modify", method = RequestMethod.PUT ,consumes = "application/json")
	public @ResponseBody RetVal modifyUser (@RequestBody AnbarData data) {
		// @ResponseBody means the returned String is the response, not a view name
		// @RequestParam means it is a parameter from the GET or POST request
		logger.error("Modify user in the trust .....");
		return as.modifyAnbar(data);	
	}
	
	@RequestMapping(path="/anbar/delete", method = RequestMethod.DELETE )
	public @ResponseBody String deleteUser (@RequestParam("id")  Integer userId) {
		logger.error("Delete user in the trust .....");
		return as.deleteAnbar(userId);
	}

	// This returns a JSON or XML with the users
	// returns all pages without pagination
	@GetMapping(path="/anbar/all")
	public @ResponseBody List<AnbarData> getAllUsers() {
		return as.getAllUser();
	}
	@GetMapping(path="/anbar/allbypage")
	public @ResponseBody List<AnbarData> getAllAnbar(Pageable pageable) {

		logger.error("Get user by page in the trust .....");
		return as.getAllAnbarbyPage(pageable);
	}

/////////////////////// Transaction //////////////////////////////////
	@GetMapping(path="/tran/allbypage")
	public @ResponseBody List<TranData> getAllTran(Pageable pageable) {
		
		logger.error("Add a  transaction codes in the trust .....");
		return trs.getallTranByPage(pageable);
	}

	//@POST
	@RequestMapping(path="/tran/add", method = RequestMethod.POST ,consumes = "application/json") // Map ONLY GET Requests
	//@Consumes(MediaType.APPLICATION_JSON)
	public @ResponseBody TranData addTransaction (@RequestBody TranData tranData) {
		logger.error("Add a cash transaction in the trust .....");

		return trs.addTransaction(tranData);
		
	}

	@RequestMapping(path="/tran/delete", method = RequestMethod.DELETE )
	public @ResponseBody String deleteTransaction (@RequestParam("id")  Integer tranId) {

		trs.deleteTranscation(tranId);
		return "Deleted";
	}

	@RequestMapping(path="/tran/modify", method = RequestMethod.PUT ,consumes = "application/json")
	public @ResponseBody RetVal modifyUser (@RequestBody TranData data) {
		logger.error("Modify a cash transaction in the trust .....");
		return trs.modifyTransaction(data);
	}


	@GetMapping(path="/tran/all")
	public @ResponseBody Iterable<Transaction> getAllTransactions() {
		// This returns a JSON or XML with the users
		return trs.findAll();
	}

////////////////////// TRAN CODE - Add/delete/modify

	@RequestMapping(path="/tranhead/add", method = RequestMethod.POST ,consumes = "application/json") // Map ONLY GET Requests
	public @ResponseBody  TranCode addTranHeader (@RequestBody TranCode tranCode) {

		logger.error("Add a transaction code in the trust .....");	
		return tcs.addTranHeader(tranCode);
	}	

	@RequestMapping(path="/tranhead/delete", method = RequestMethod.DELETE )
	public @ResponseBody String deleteTranHead (@RequestParam("id")  Integer tranCode) {
		
		logger.error("Delete a transaction code in the trust .....");
		tcs.deleteTranCode(tranCode);
		return "Deleted";
	}

	@RequestMapping(path="/tranhead/modify", method = RequestMethod.PUT ,consumes = "application/json")
	public @ResponseBody RetVal modifyTC (@RequestBody TranCode data) {
		logger.error("Modify a transaction code in the trust .....");	
		
		return tcs.modifyTransaction(data);
	
	}

	@GetMapping(path="/tranhead/all")
	public @ResponseBody Iterable<TranCode> getAllTranCodes() {
		// This returns a JSON or XML with the users
				return tcs.getAllCode();
			}

	
/////////////Books ////////////////////////

@GetMapping(path="/booktran/all")
public @ResponseBody Iterable<BookData> getAllBookTran() {
		// This returns a JSON or XML with the users
		logger.error("Get all book tran in the trust .....");	
		return bs.getAllBookTran();
		
	}

@RequestMapping(path="/booktran/modify", method = RequestMethod.PUT ,consumes = "application/json")
	public @ResponseBody RetVal modifyTC (@RequestBody BookData data) {
		
		logger.error("Modify a transaction code in the trust .....");	
		return bs.modifyBookTran(data);
	}

	@RequestMapping(path="/booktran/add", method = RequestMethod.POST ,consumes = "application/json") // Map ONLY GET Requests
	public @ResponseBody  BookData addTranHeader (@RequestBody BookData data) {

		logger.error("Add a book tran in the trust .....");	
		return bs.addBookTran(data);
	}	


}