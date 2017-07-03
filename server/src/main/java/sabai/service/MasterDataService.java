

package sabai.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sabai.book.MasterData;
import sabai.book.MasterDataC;
import sabai.repo.MasterDataRepository;

@Service
public class MasterDataService {
	
	@Autowired 
	MasterDataRepository 	mdRepo;
	
	public List<MasterDataC> getAllEntity(String entityName){
		
		List<MasterData> listA = mdRepo.findMasterDataByType(entityName);
		List<MasterDataC> listB = new ArrayList<>();
		
		for (MasterData bt : listA) {
			MasterDataC mdC = new MasterDataC();
			mdC.set(bt.getDesc() ,bt.getDesctamil(), bt.getImageName());			
			listB.add(mdC);
		}
		return listB;
	}
	
}
