package sabai.service;

//import static org.junit.Assert.assertArrayEquals;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.apache.poi.ss.formula.functions.Code;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;

import sabai.helper.Adoptar;
import sabai.helper.RetVal;
import sabai.repo.TransactionRepository;
import sabai.trancode.TranCode;
import sabai.transaction.QTransaction;
import sabai.transaction.TranData;
import sabai.transaction.TranSearch;
import sabai.transaction.Transaction;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.mysema.query.group.GroupBy;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.jpa.sql.JPASQLQuery;
import com.mysema.query.types.expr.BooleanExpression;
//import com.mysema.query.sql.MySQLTemplates;
//import com.mysema.query.sql.SQLTemplates;

//@Value("${userBucket.path}")
//private String userBucketPath;

@Service
public class TransactionService {

	@Autowired 
	private TransactionRepository transactionRepository;
	private MiscService miscObj = new MiscService();
	
	@Autowired
	private Environment env;
	
	@Autowired
	EntityManager em;
	
	@Autowired
	TranCodeService tcs;

	public List<TranData> getallTranByPage(Pageable pageable)
	{
		Page <Transaction> listPage = transactionRepository.findAll(pageable);
		List<Transaction>  listA = listPage.getContent();
		List<TranData> listB = new ArrayList<>();
		for (Transaction tran : listA) {
			TranData newTran = new TranData();
			Adoptar.copyDBDataToClient(newTran, tran);
			listB.add(newTran);
		}
		return listB;
	}
	public List<TranData> getAllTransByCriteria(TranSearch ts){
		String tranCode = ts.getTcLike();
		String fDate = ts.getfDate();
		String tDate = ts.gettDate();
		String desc = ts.getDescLike();
		String voucher = ts.getVoucherLike();
		
		QTransaction qTrans = QTransaction.transaction;
		JPAQuery query = new JPAQuery(em).from(qTrans);
		BooleanExpression whereClause = null;
		boolean flag = false;
		
		if(tranCode != null && !tranCode.equals("") && !tranCode.equals("*"))
		{
			whereClause = qTrans.headercode.likeIgnoreCase("%"+tranCode+"%");
			flag = true;
		}
		if(fDate != null && !fDate.equals("") && !fDate.equals("*"))
		{
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd"); 
			Date startDate = null;
			try {
				startDate = df.parse(fDate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			BooleanExpression be = qTrans.transdate.goe(startDate);
			if(flag)
				whereClause = whereClause.and(be);
			else
				whereClause = be;
			flag = true;
		}
		if(tDate != null && !tDate.equals("") && !tDate.equals("*"))
		{
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd"); 
			Date toDate = null;
			try {
				toDate = df.parse(tDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			BooleanExpression be  = qTrans.transdate.loe(toDate);
			if(flag)
				whereClause = whereClause.and(be);
			else
				whereClause = be;
			flag = true;
		}
		if(desc != null && !desc.equals("")  && !desc.equals("*"))
		{
			
			BooleanExpression be  = qTrans.description.likeIgnoreCase("%"+desc+"%");
			if(flag)
				whereClause = 	whereClause.and(be);
			else
				whereClause = be;
			
			flag = true;
		}
		if(voucher != null &&  !voucher.equals("")  && !voucher.equals("*") )
		{
			BooleanExpression be  = qTrans.voucherno.likeIgnoreCase("%"+voucher+"%");
			if(flag)
				whereClause = whereClause.and(be);
			else
				whereClause = be;

			flag = true;
		}
		
		BooleanExpression be  = qTrans.amount.gt(0.0);
		if(flag){
			whereClause = whereClause.and(be);

		}
		else{
			whereClause = whereClause.and(be);
		}
		
		query.where(whereClause)
			 .orderBy(QTransaction.transaction.transdate.asc())
			 .orderBy(QTransaction.transaction.headercode.asc());
		
		List<Transaction> listA =  query.list(qTrans);
		
		List<TranData> listB = new ArrayList<>();
		for (Transaction tran : listA) {
			TranData newTran = new TranData();
			Adoptar.copyDBDataToClient(newTran, tran);
			listB.add(newTran);
		}
		return listB;
	}

	//JPAQuery is for JPQL queries and JPASQLQuery is for JPA native (SQL) queries.
	//	SQLTemplates templates  = MySQLTemplates.DEFAULT; 
	//	//Object templates;
	//	JPASQLQuery query = new JPASQLQuery(em, templates);
	//	List<String> names = query.from(qTrans).list(cat.name);

	public List<TranData> getTransForReport(int year, int type){
		
		double closingBalance = calculateClosingBalance(year);
		QTransaction qTrans = QTransaction.transaction;
		JPAQuery query = new JPAQuery(em).from(qTrans);
		BooleanExpression whereClause = null;
		boolean flag = false;
		
		// Get report for year ending
		
		String dfStr="01/04/"+(year-1);  
		Date df = null;
		String dtStr="31/03/"+year;  
		Date dt = null;
        
		try {
			
			df = new SimpleDateFormat("dd/MM/yyyy").parse(dfStr);
			dt = new SimpleDateFormat("dd/MM/yyyy").parse(dtStr);
			
		} catch (ParseException e) {
			e.printStackTrace();
		}  
		
		whereClause = qTrans.transdate.between(df, dt);

		BooleanExpression be = qTrans.amount.gt(0.0);
		whereClause = whereClause.and(be);

		if(type == 1){
			query.where(whereClause)
			.orderBy(QTransaction.transaction.transdate.asc())
			.orderBy(QTransaction.transaction.headercode.asc());
		}
		else if(type == 2){
			query.where(whereClause)
			.orderBy(QTransaction.transaction.headercode.asc())
			.orderBy(QTransaction.transaction.transdate.asc());
		}
		
		List<Transaction> listA =  query.list(qTrans);
		
		List<TranData> listB = new ArrayList<>();
		// Opening balance is the first record  - needed in report 
		TranData newTran = new TranData();
		newTran.setAmount(closingBalance);
		newTran.setDescription("Opening balance");
		newTran.setHeaderCode("OB");
		listB.add(newTran);
		
		// later change to join
		List<TranCode> tc = tcs.getAllCodeList();
		
		for (Transaction tran : listA) {
			newTran = new TranData();
			Adoptar.copyDBDataToClient(newTran, tran);
			for(TranCode c: tc) // later remove this after join
			{
				if(newTran.getHeaderCode().equals(c.getTranCode()))
				{
					newTran.setHeaderCode(c.getTranDesc());
					break;
				}
			}
			listB.add(newTran);
		}
		
	
		
		return listB;

	}
	
	private double calculateClosingBalance(int year){

		//select sum(amount) from transaction group by inexp  
		//where transdate > x and transd
		// select inexp,sum(amount) from transaction  where transdate >= '2015/04/01' and 
		// transdate <= '2016/03/31' group by inexp 

		
		QTransaction qTrans = QTransaction.transaction;
		JPAQuery query = new JPAQuery(em).from(qTrans);
		BooleanExpression whereClause = null;
		boolean flag = false;
		
		// Get report for year ending
		
		String dfStr="01/04/2013";  
		String dtStr="31/03/"+(year-1);  
		
		Date dt = null;
		Date df = null;
		try {
			
			df = new SimpleDateFormat("dd/MM/yyyy").parse(dfStr);
			dt = new SimpleDateFormat("dd/MM/yyyy").parse(dtStr);
			
		} catch (ParseException e) {
			e.printStackTrace();
		}  
		
		whereClause = qTrans.transdate.between(df, dt);
		query.where(whereClause); //.groupBy(qTrans.inexp)
			 
		
		//double sumCr = query.from(qTrans).list(qTrans.amount.sum()).get(0);
		
		
		Map<String, Double> results = 
		query.transform(
		      GroupBy.groupBy(qTrans.inexp).as(GroupBy.sum(qTrans.amount)));
		
		double balance = 0; 
		if(results.containsKey("i") && results.containsKey("e")){
			System.out.println("Opening balance sum of income : >" + results.get("i"));
			System.out.println("Opening balance sum of expense : >" + results.get("e"));
			System.out.println("Opening balance  : >" + (results.get("i") - results.get("e")));
			balance = results.get("i") - results.get("e");
		}
		
		return balance;
		 //http://www.baeldung.com/querydsl-with-jpa-tutorial
	}
	public long getCount(){
		return transactionRepository.count();
	}
		
	public TranData addTransaction(TranData tranData){
		Transaction trans = new Transaction();
		Adoptar.copyClientDataToDatabase(tranData,trans);

		transactionRepository.save(trans);
		
		String congoogle = env.getProperty("sabai.connectgoogle");
		 
		List<Object> value = new ArrayList<>();
		
		try{
		if(congoogle.equals("1")){
			Adoptar.copyTransactionToList(tranData,value);
			miscObj.connectGoogleSheetAddData(value,2);
		}
			
		}
		catch(Exception ex){
			System.out.println("----Ignored Google Error ----");
		}
		return tranData;
	}
	
	public void deleteTranscation(Integer tranId){
		Transaction trans = new Transaction(tranId);
		transactionRepository.delete(trans);
	}
	
	public RetVal modifyTransaction(TranData data){
		Transaction trans = new Transaction();
		Adoptar.copyClientDataToDatabase(data,trans);
		transactionRepository.save(trans);
		RetVal retval = new RetVal("PASS","Modified");
		return retval;
	}
	
	public Iterable<Transaction>  findAll(){
		return transactionRepository.findAll();
	}
}
