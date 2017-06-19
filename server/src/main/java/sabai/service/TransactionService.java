package sabai.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import sabai.helper.Adoptar;
import sabai.helper.RetVal;
import sabai.transaction.TranData;
import sabai.transaction.Transaction;
import sabai.transaction.TransactionRepository;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class TransactionService {

	@Autowired 
	private TransactionRepository transactionRepository;
	private MiscService miscObj = new MiscService();

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
	
	public long getCount(){
		return transactionRepository.count();
	}
		
	public TranData addTransaction(TranData tranData){
		Transaction trans = new Transaction();
		Adoptar.copyClientDataToDatabase(tranData,trans);

		transactionRepository.save(trans);

		List<Object> value = new ArrayList<>();
		Adoptar.copyTransactionToList(tranData,value);
		miscObj.connectGoogleSheetAddData(value,2);

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
