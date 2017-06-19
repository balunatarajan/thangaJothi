package sabai.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import sabai.anbar.AnbarData;
import sabai.anbar.AnbarRepository;
import sabai.anbar.Anbargal;
import sabai.helper.Adoptar;
import sabai.helper.RetVal;

@Service
public class AnbarService {

	@Autowired 
	private AnbarRepository anbarRepository;

	@Autowired
	private MiscService miscObj;

	@Autowired 
	private MailService ms;
	
	public RetVal addAnbar(AnbarData data){
		Anbargal newAnbar = new Anbargal();
		//Ignore the userId, otherwise it will update instead of Insert in db
		data.setUserId("");
		Adoptar.copyClientDataToDatabase(data,newAnbar);
		anbarRepository.save(newAnbar);
		RetVal retval = new RetVal("PASS","Saved");

		List<Object> value = new ArrayList<>();
		Adoptar.copyAnbargalToList(newAnbar,value);
		miscObj.connectGoogleSheetAddData(value,1);
		ms.sendAnbarAddMail(data);
		return retval;
	}

	public RetVal modifyAnbar(AnbarData data){
		Anbargal newAnbar = new Anbargal();
		Adoptar.copyClientDataToDatabase(data,newAnbar);
		anbarRepository.save(newAnbar);
		RetVal retval = new RetVal("PASS","Modified");
		return retval;
	}
	
	public String deleteAnbar(Integer userId){
		Anbargal newAnbar = new Anbargal(userId);
		anbarRepository.delete(newAnbar);
		return "Deleted";
	}
	
	public List<AnbarData> getAllUser(){
	
		//Iterable<Anbargal> listA = anbarRepository.findAll();
		List<Anbargal> listA = anbarRepository.findAll();
		List<AnbarData> listB = new ArrayList<>();
		for (Anbargal anb : listA) {
			AnbarData newAnbar = new AnbarData();
			Adoptar.copyDBDataToClient(newAnbar, anb);
			listB.add(newAnbar);
		}
		
		return listB;	
	}
	public List<AnbarData> getAllAnbarbyPage(Pageable pageable){
		// This returns a JSON or XML with the users
		Page <Anbargal> listPage = anbarRepository.findAll(pageable);
		List<Anbargal>  listA = listPage.getContent();

		List<AnbarData> listB = new ArrayList<>();
		for (Anbargal anb : listA) {
			AnbarData newAnbar = new AnbarData();
			Adoptar.copyDBDataToClient(newAnbar, anb);
			listB.add(newAnbar);
		}
		return listB;

	}
	public long getCount(){
		return anbarRepository.count();
	}
}
