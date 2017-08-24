package sabai.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.expr.BooleanExpression;

import sabai.anbar.AnbarData;
import sabai.anbar.Anbargal;
import sabai.anbar.QAnbargal;
import sabai.anbar.SearchCriteria;
import sabai.helper.Adoptar;
import sabai.helper.RetVal;
import sabai.repo.AnbarRepository;

@Service
public class AnbarService {

	@Autowired 
	private AnbarRepository anbarRepository;

	@Autowired
	private MiscService miscObj;

	@Autowired 
	private MailService ms;
	//private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private final Logger logger = LogManager.getLogger(AnbarService.class);
   
	
	@Autowired
	EntityManager em;
	
	public RetVal addAnbar(AnbarData data){
		Anbargal newAnbar = new Anbargal();
		//Ignore the userId, otherwise it will update instead of Insert in db
		logger.debug("Add new anbar {} to the system",data.getUserName());
		data.setUserId("");
		Adoptar.copyClientDataToDatabase(data,newAnbar);
		anbarRepository.save(newAnbar);
		RetVal retval = new RetVal("PASS","Saved");

		logger.debug("Sending to google sheet calling connectGoogleSheetAddData",data.getUserName());
		List<Object> value = new ArrayList<>();
		Adoptar.copyAnbargalToList(newAnbar,value);
		miscObj.connectGoogleSheetAddData(value,1);
		ms.sendAnbarAddMail(data);
		logger.debug("Retrured from  Google sheet calling connectGoogleSheetAddData",data.getUserName());
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
	
	public List<String> getAllAnbar(){
		return anbarRepository.findAllUser();
	}
	
	public List<AnbarData> getAnbargalByCriteria(SearchCriteria criteria)
	{
		
		String nameLike = criteria.getNameLike();
		String initThruLike = criteria.getInitLike();
		String cityLike = criteria.getCityLike();
		String distLike = criteria.getDistLike();
		String stateLike = criteria.getStateLike();
		String fDate = criteria.getfDate();
		String tDate = criteria.gettDate();
		logger.error("getAnbargalByCriteria .....+"+cityLike);
		
		//Predicate anbarSearchPredicate = (Predicate) QAnbargal.anbargal.username.eq(nameLike);
		//QAnbargal.anbargal.username.like(nameLike);
		//Iterable<T> findAll(Predicate predicate)
		//Anbargal am = anbarRepository.findOne(anbarSearchPredicate);
		//PathBuilder<Anbargal> entityPath = new PathBuilder<>(Anbargal.class, "user");
		//.and(QTodo.todo.description.eq("Bar"));
		
       // List<Anbargal> listA = anbarRepository.findAll(QAnbargal.anbargal.username.contains(nameLike));
        
        
		/*QAnbargal anbargal = QAnbargal.anbargal;
		Predicate nPrd = (Predicate) anbargal.username.contains(nameLike);
		Iterable<Anbargal> ads = anbarRepository.findAll(nPrd);
		for(Anbargal e : ads){
			System.out.println("By Employee Name Containing :: Employee Inquired :: "
		     +e.getUserName()+" :: "+e.getCity());
		}
			
		List<AnbarData> listB = new ArrayList<>();
		for (Anbargal anb : ads) {
			AnbarData newAnbar = new AnbarData();
			Adoptar.copyDBDataToClient(newAnbar, anb);
			listB.add(newAnbar);
		}*/
		
		///public List<Person> select(String firstname, String lastname) {
       
		/*
		 * 
		 *  JdbcTemplate select = new JdbcTemplate(dataSource);
        return select
                .query(
                        "select  FIRSTNAME, LASTNAME from PERSON where FIRSTNAME = ? AND LASTNAME= ?",
                        new Object[] { firstname, lastname },
                        new PersonRowMapper());
		}*/
		//JPQLQuery 
		QAnbargal qanbar = QAnbargal.anbargal;
		JPAQuery query = new JPAQuery(em).from(qanbar);
		BooleanExpression whereClause = null;
		boolean flag = false;
		if(nameLike != null && !nameLike.equals("") && !nameLike.equals("*"))
		{
			whereClause = qanbar.username.likeIgnoreCase("%"+nameLike+"%");
			flag = true;
		}
		if(initThruLike != null && !initThruLike.equals("") && !initThruLike.equals("*"))
		{
			BooleanExpression be = qanbar.initthru.likeIgnoreCase("%"+initThruLike+"%");
			if(flag)
				whereClause = whereClause.and(be);
			else
				whereClause = be;
			
		}
		if(cityLike != null && !cityLike.equals("") && !cityLike.equals("*"))
		{
			BooleanExpression be  = qanbar.city.likeIgnoreCase("%"+cityLike+"%");
			if(flag)
				whereClause = whereClause.and(be);
			else
				whereClause = be;
		
		}
		if(distLike != null && !distLike.equals("")  && !distLike.equals("*"))
		{
			BooleanExpression be  = qanbar.dist.likeIgnoreCase("%"+distLike+"%");
			if(flag)
				whereClause = 	whereClause.and(be);
			else
				whereClause = be;
		}
		if(stateLike != null &&  !stateLike.equals("")  && !stateLike.equals("*") )
		{
			BooleanExpression be  = qanbar.state.likeIgnoreCase("%"+stateLike+"%");
			if(flag)
				whereClause = whereClause.and(be);
			else
				whereClause = be;
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
			
			BooleanExpression be = qanbar.initdate.goe(startDate);
			if(flag)
				whereClause = whereClause.and(be);
			else
				whereClause = be;
			
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
		
			BooleanExpression be  = qanbar.initdate.loe(toDate);
			if(flag)
				whereClause = whereClause.and(be);
			else
				whereClause = be;
		
		}
		query.where(whereClause)
			 .orderBy(qanbar.username.asc());
			 
		List<Anbargal> listA =  query.list(qanbar);
			
		//for(Anbargal a: listA) System.out.println(a.getUserName()+a.getCity());
		
		
		List<AnbarData> listB = new ArrayList<>();
		for (Anbargal anb : listA) {
			AnbarData newAnbar = new AnbarData();
			Adoptar.copyDBDataToClient(newAnbar, anb);
			listB.add(newAnbar);
		}
		return listB;
	}
	
	

}

