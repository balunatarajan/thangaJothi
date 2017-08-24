package sabai;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.data.domain.Pageable;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import sabai.anbar.AnbarData;
import sabai.anbar.SearchCriteria;
import sabai.book.BookCount;
import sabai.book.BookData;
import sabai.book.BookTransfer;
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
import sabai.transaction.TranSearch;
import sabai.transaction.Transaction;

//@CrossOrigin(origins = "http://localhost:4040")
@Controller    // This means that this class is a Controller
//@RequestMapping(path="/tjgs") // This means URL's start with /demo (after Application path)
@CrossOrigin

public class MainController {

	private final Logger logger = LogManager.getLogger(MainController.class);
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

		logger.info("value pass to MainController.getCount - table "+table);
		
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
		logger.info("value pass to MainController.getCount - count "+count);
		Count nbrOfRows = new Count();
		nbrOfRows.setCount(String.valueOf(count));
		//System.err.println("tjgs/getCount Number of "+ table +" : "+nbrOfRows.getCount());
		return nbrOfRows;
	}
	//	///////////////////////// Anbar ////////////////////////////////////////////
	//@GetMapping(path="/user/add") // Map ONLY GET Requests
	@CrossOrigin
	@RequestMapping(path="/anbar/add", method = RequestMethod.POST ,consumes = "application/json")
	public @ResponseBody RetVal addNewUser (@RequestBody AnbarData data) {
		// @ResponseBody means the returned String is the response, not a view name
		// @RequestParam means it is a parameter from the GET or POST request
		logger.info("rest/tjgs/user/add: Add user the trust .....",data.getUserName());
		return as.addAnbar(data);
	}

	@CrossOrigin
	@RequestMapping(path="/anbar/modify", method = RequestMethod.PUT ,consumes = "application/json")
	public @ResponseBody RetVal modifyUser (@RequestBody AnbarData data) {
		// @ResponseBody means the returned String is the response, not a view name
		// @RequestParam means it is a parameter from the GET or POST request
		logger.info("Modify user in the trust .....");
		return as.modifyAnbar(data);	
	}

	@CrossOrigin
	@RequestMapping(path="/anbar/delete", method = RequestMethod.DELETE )
	public @ResponseBody String deleteUser (@RequestParam("id")  Integer userId) {
		logger.info("Delete user in the trust .....");
		return as.deleteAnbar(userId);
	}

	// This returns a JSON or XML with the users
	// returns all pages without pagination
	@CrossOrigin
	@GetMapping(path="/anbar/all")
	public @ResponseBody List<AnbarData> getAllUsers() {
		logger.info("/rest/tjgs/anbar/all getAllusers");
		return as.getAllUser();
	}

	@CrossOrigin
	@GetMapping(path="/anbar/allbypage")
	public @ResponseBody List<AnbarData> getAllAnbar(Pageable pageable) {

		logger.info("/rest/tjgs/anbar/allbypage/ Get user by page in the trust page# {} pagesize {}.....",pageable.getPageNumber(),pageable.getPageSize());
		logger.info("Logger --> ",logger.getName());
		return as.getAllAnbarbyPage(pageable);
	}

	@CrossOrigin
	@GetMapping(path="/anbar/nameall")
	public @ResponseBody List<String> getAllUserCity() {
		logger.info("/rest/tjgs/anbar/nameall/ getAllUserCity ");
		return as.getAllAnbar();
	}

	@GetMapping(path="/anbar/filterby")
	public @ResponseBody List<AnbarData> 
		getAnbargalByCriteria(@RequestParam("nameLike") String nameLike,
				@RequestParam("initLike") String initLike,
				@RequestParam("cityLike") String cityLike,
				@RequestParam("distLike") String distLike,
				@RequestParam("stateLike") String stateLike,
				@RequestParam("fDate") String fDate,
				@RequestParam("tDate") String tDate) {
		
		logger.info("/rest/tjgs/anbar/filterby/ getAnbargalByCriteria ");
		logger.info("/rest/tjgs/anbar/filterby/ nameLike {} initLike {} distLike {}  stateLike {} fDate {} tDate {} ",
				nameLike,initLike,cityLike,distLike,stateLike,fDate,tDate);
		
		SearchCriteria criteria = new SearchCriteria(nameLike,initLike,cityLike,
													distLike,stateLike,fDate,tDate);
		return as.getAnbargalByCriteria(criteria);
	}

	/////////////////////// Transaction //////////////////////////////////
	@CrossOrigin

	@GetMapping(path="/tran/allbypage")
	public @ResponseBody List<TranData> getAllTran(Pageable pageable) {

		logger.info("/rest/tjgs/tran/allbypage getAllTran page#{} pageSize{}",
				pageable.getPageNumber(),pageable.getPageSize());
		
		return trs.getallTranByPage(pageable);
	}
	
	
	//@RequestParam(value="fromDate")     @DateTimeFormat(pattern="MMddyyyy") Date fromDate,
	@GetMapping(path="/tran/criteria")
	public @ResponseBody List<TranData> getTransactionByCriteria(
			    @RequestParam("tcLike") String tcLike,
				@RequestParam("fDate") String fDate,
				@RequestParam("tDate") String tDate,
				@RequestParam("descLike") String descLike,
				@RequestParam("voucherLike") String voucherLike) {
		
		logger.info("/rest/tjgs/tran/criteria/ getTransactionByCriteria ");
		logger.info("/rest/tjgs/anbar/filterby/ tcLike {} fDate {} tDate {}  descLike {} voucherLike {} ",
				tcLike,fDate,tDate,descLike,descLike);
		
		TranSearch criteria = new TranSearch(tcLike,fDate,tDate,descLike,voucherLike);
		return trs.getAllTransByCriteria(criteria);
	}
	
	@GetMapping(path="/tran/report/monthwise")
	public @ResponseBody List<TranData> getTransactionForReport(@RequestParam("year") String year){
		logger.info("/rest/tjgs/tran/report/monthwise/ getTransactionForReport ");
		
		return trs.getTransForReport( Integer.parseInt(year),1);
	}
	
	@GetMapping(path="/tran/report/headerwise")
	public @ResponseBody List<TranData> getTransactionForReportHeaderWise(@RequestParam("year") String year){

		logger.info("/rest/tjgs/tran/report/headerwise/ getTransactionForReportHeaderWise ",year);

		return trs.getTransForReport( Integer.parseInt(year),2);
	}
	
	
	//@POST
	@RequestMapping(path="/tran/add", method = RequestMethod.POST ,consumes = "application/json") // Map ONLY GET Requests
	//@Consumes(MediaType.APPLICATION_JSON)
	public @ResponseBody TranData addTransaction (@RequestBody TranData tranData) {
		
		logger.info("/rest/tjgs/tran/add/ addTransaction ",tranData.toString());

		return trs.addTransaction(tranData);

	}
	@CrossOrigin

	@RequestMapping(path="/tran/delete", method = RequestMethod.DELETE )
	public @ResponseBody String deleteTransaction (@RequestParam("id")  Integer tranId) {

		logger.info("/rest/tjgs/tran/delete/ deleteTransaction ",tranId);

		trs.deleteTranscation(tranId);
		return "Deleted";
	}
	@CrossOrigin

	@RequestMapping(path="/tran/modify", method = RequestMethod.PUT ,consumes = "application/json")
	public @ResponseBody RetVal modifyUser (@RequestBody TranData data) {
		logger.info("/rest/tjgs/tran/modify/ modifyUser ",data.toString());
		return trs.modifyTransaction(data);
	}

	@CrossOrigin

	@GetMapping(path="/tran/all")
	public @ResponseBody Iterable<Transaction> getAllTransactions() {
		// This returns a JSON or XML with the users
		logger.info("/rest/tjgs/tran/all/ getAllTransactions ");
		return trs.findAll();
	}

	////////////////////// TRAN CODE - Add/delete/modify

	@RequestMapping(path="/tranhead/add", method = RequestMethod.POST ,consumes = "application/json") // Map ONLY GET Requests
	public @ResponseBody  TranCode addTranHeader (@RequestBody TranCode tranCode) {

		logger.info("/rest/tjgs/tranhead/add  Add a tran header in the trust .....",tranCode.toString());	
		return tcs.addTranHeader(tranCode);
	}	

	@RequestMapping(path="/tranhead/delete", method = RequestMethod.DELETE )
	public @ResponseBody String deleteTranHead (@RequestParam("id")  Integer tranCode) {

		logger.info("/rest/tjgs/tranhead/delete Delete a transaction code in the trust .....");
		tcs.deleteTranCode(tranCode);
		return "Deleted";
	}

	@RequestMapping(path="/tranhead/modify", method = RequestMethod.PUT ,consumes = "application/json")
	public @ResponseBody RetVal modifyTC (@RequestBody TranCode data) {
		logger.info("/rest/tjgs/tranhead/modify Modify a transaction code {}",data.toString());	

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
		logger.info("Get all book tran in the trust .....");	
		return mds.getAllEntity("BOOK");
	}

	@GetMapping(path="/guru/all")
	public @ResponseBody Iterable<MasterDataC> getAllGuruNames() {
		// This returns a JSON or XML with the users
		logger.info("Get all book tran in the trust .....");	
		return mds.getAllEntity("GURU");
	}

	/////////////Books ////////////////////////
	
	@GetMapping(path="/booktran/all")
	public @ResponseBody Iterable<BookData> getAllBookTran() {
		// This returns a JSON or XML with the users
		logger.info("/rest/tjgs/booktran/all/ Get all book tran in the trust .....");
		return bs.getAllBookTran();
		
	}

	@GetMapping(path="/booktran/groupby")
	public @ResponseBody Iterable<BookCount> getAllBookTranGroupBy() {
		// This returns a JSON or XML with the users
		logger.info("/rest/tjgs/booktran/groupby .....");
		return bs.getAllBookTranGroupBy();
		
	}
	
	@GetMapping(path="/booktran/title/{title}")
	public @ResponseBody Iterable<BookData> getBookTranByTitle(
			@PathVariable("title") String title ) {
		// This returns a JSON or XML with the users
		logger.info("Get book tran in the trust .....");
		return bs.getBookTranByTitle(title);
		
	}
	@GetMapping(path="/booktran/{title}/{ownedBy}")
	public @ResponseBody Iterable<BookData> getBookTranByOwnerTitle(
			@PathVariable("title") String title, 
			@PathVariable("ownedBy") String owner ) {
		// This returns a JSON or XML with the users
		logger.info("Get book tran in the trust .....");
		return bs.getBookTranByOwnerTitle(title,owner);
		
	}
	
	@GetMapping(path="/booktran/owner/{ownedBy}")
	public @ResponseBody Iterable<BookData> getBookTranByOwner(
			@PathVariable("ownedBy") String owner){
		return bs.getBookTranByOwner(owner);
	}
	
	
	@RequestMapping(path="/booktran/modify", method = RequestMethod.PUT ,consumes = "application/json")
	public @ResponseBody RetVal modifyTC (@RequestBody BookData data) {

		logger.info("Modify a transaction code in the trust .....");	
		return bs.modifyBookTran(data);
	}

	@RequestMapping(path="/booktran/add", method = RequestMethod.POST ,consumes = "application/json") // Map ONLY GET Requests
	public @ResponseBody  RetVal addBookTran (@RequestBody List<BookData> data) {

		logger.info("Add a book tran in the trust .....");	
		return bs.addBookTran(data);
	}	
	
	@RequestMapping(path="/booktran/sell", method = RequestMethod.POST ,consumes = "application/json") // Map ONLY GET Requests
	public @ResponseBody  RetVal sellBooks (@RequestBody List<BookData> data) {

		logger.info("Add a book tran in the trust .....");	
		return bs.sellBooks(data);
	}	
	
	@RequestMapping(path="/booktran/transfer", method = RequestMethod.POST ,consumes = "application/json") // Map ONLY GET Requests
	public @ResponseBody  RetVal transferBooks (@RequestBody List<BookTransfer> data) {

		logger.info("Add a book tran in the trust .....");	
		return bs.transferBook(data);
	}
	
	@GetMapping(path="/booktran/bookowners")
	public @ResponseBody  Stream<String> getBookOwners () {
		logger.info("getBookOwners .....");	
		return bs.getBookOwners();
	}
}