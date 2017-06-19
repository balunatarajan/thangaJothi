package sabai.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sabai.helper.Adoptar;
import sabai.helper.RetVal;
import sabai.trancode.TranCode;
import sabai.trancode.TranCodeMaster;
import sabai.trancode.TranCodeRepository;

@Service
public class TranCodeService {

	@Autowired 
	private TranCodeRepository tranCodeRepository;
	MiscService misc = new MiscService();
	
	public TranCode addTranHeader(TranCode tranCode){
		TranCodeMaster trans = new TranCodeMaster();
		Adoptar.copyClientDataToDatabase(tranCode,trans);
		tranCodeRepository.save(trans);

		List<Object> value = new ArrayList<>();
		Adoptar.copyTranCodeToList(tranCode,value);
		misc.connectGoogleSheetAddData(value,3);

		return tranCode;
	}
	
	public void deleteTranCode(Integer tranCode){
		TranCodeMaster trans = new TranCodeMaster(tranCode);
		tranCodeRepository.delete(trans);
	}
	
	public RetVal modifyTransaction( TranCode data){
		TranCodeMaster trans = new TranCodeMaster();
		Adoptar.copyClientDataToDatabase(data,trans);
		tranCodeRepository.save(trans);
		RetVal retval = new RetVal("PASS","Modified");
		return retval;
	}
	
	public Iterable<TranCode> getAllCode(){
		List<TranCodeMaster> listA = tranCodeRepository.findAll();
		List<TranCode> listB = new ArrayList<>();
		for (TranCodeMaster tcm : listA) {
			System.err.println("getAllTranCodes---- :"+ tcm.getTranDesc());
			TranCode tc = new TranCode();
			Adoptar.copyDatabaseToClientData(tcm, tc);
			listB.add(tc);
		}
		return listB;
	}
	
	public long getCount(){
		return tranCodeRepository.count();
	}
	
}
