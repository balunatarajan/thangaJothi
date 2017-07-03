package sabai;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List; 
import org.springframework.data.domain.Pageable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sabai.anbar.AnbarData;
import sabai.anbar.SearchCriteria;
import sabai.book.BookData;
import sabai.book.MasterDataC;
import sabai.helper.Count;
import sabai.helper.RetVal;
import sabai.service.AnbarService;
import sabai.service.BookService;
import sabai.service.MasterDataService;
import sabai.service.TranCodeService;
import sabai.service.TransactionService;
import sabai.trancode.TranCode;
import sabai.transaction.TranData;
import sabai.transaction.Transaction;

//@CrossOrigin(origins = "http://localhost:4040")
@Controller    // This means that this class is a Controller
//@RequestMapping(path="/tjgs") // This means URL's start with /demo (after Application path)
@CrossOrigin

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
	@Autowired
	MasterDataService mds;

	// get record count in table 
	@CrossOrigin
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
	//	///////////////////////// Anbar ////////////////////////////////////////////
	//@GetMapping(path="/user/add") // Map ONLY GET Requests
	@CrossOrigin
	@RequestMapping(path="/anbar/add", method = RequestMethod.POST ,consumes = "application/json")
	public @ResponseBody RetVal addNewUser (@RequestBody AnbarData data) {
		// @ResponseBody means the returned String is the response, not a view name
		// @RequestParam means it is a parameter from the GET or POST request
		logger.error("/user/add: Add user the trust .....",data.getUserName());
		return as.addAnbar(data);
	}

	@CrossOrigin
	@RequestMapping(path="/anbar/modify", method = RequestMethod.PUT ,consumes = "application/json")
	public @ResponseBody RetVal modifyUser (@RequestBody AnbarData data) {
		// @ResponseBody means the returned String is the response, not a view name
		// @RequestParam means it is a parameter from the GET or POST request
		logger.error("Modify user in the trust .....");
		return as.modifyAnbar(data);	
	}

	@CrossOrigin
	@RequestMapping(path="/anbar/delete", method = RequestMethod.DELETE )
	public @ResponseBody String deleteUser (@RequestParam("id")  Integer userId) {
		logger.error("Delete user in the trust .....");
		return as.deleteAnbar(userId);
	}

	// This returns a JSON or XML with the users
	// returns all pages without pagination
	@CrossOrigin
	@GetMapping(path="/anbar/all")
	public @ResponseBody List<AnbarData> getAllUsers() {
		return as.getAllUser();
	}

	@CrossOrigin
	@GetMapping(path="/anbar/allbypage")
	public @ResponseBody List<AnbarData> getAllAnbar(Pageable pageable) {

		logger.error("Get user by page in the trust .....");
		return as.getAllAnbarbyPage(pageable);
	}

	@CrossOrigin
	@GetMapping(path="/anbar/namecityall")
	public @ResponseBody List<String> getAllUserCity() {
		return as.getAllAnbarCity();
	}

	@GetMapping(path="/anbar/filterby")
	public @ResponseBody List<AnbarData> 
		getAnbargalByCriteria(@RequestParam("nameLike") String nameLike,
				@RequestParam("initLike") String initLike,
				@RequestParam("cityLike") String cityLike,
				@RequestParam("distLike") String distLike,
				@RequestParam("stateLike") String stateLike) {
		SearchCriteria criteria = new SearchCriteria(nameLike,initLike,cityLike,distLike,stateLike);
		return as.getAnbargalByCriteria(criteria);
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

	/////////////////Master data ///////////////
	@GetMapping(path="/book/all")
	public @ResponseBody Iterable<MasterDataC> getAllBooks() {
		// This returns a JSON or XML with the users
		logger.error("Get all book tran in the trust .....");	
		return mds.getAllEntity("BOOK");
	}

	@GetMapping(path="/guru/all")
	public @ResponseBody Iterable<MasterDataC> getAllGuruNames() {
		// This returns a JSON or XML with the users
		logger.error("Get all book tran in the trust .....");	
		return mds.getAllEntity("GURU");
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