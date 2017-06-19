
package sabai.helper;
import java.util.Date;
import java.util.List;

import sabai.anbar.AnbarData;
import sabai.anbar.Anbargal;
import sabai.book.BookData;
import sabai.book.BookTrans;
import sabai.trancode.TranCode;
import sabai.trancode.TranCodeMaster;
import sabai.transaction.TranData;
import sabai.transaction.Transaction;

import java.text.SimpleDateFormat;



public class Adoptar {

	public static void copyClientDataToDatabase(AnbarData adata, Anbargal data)  {

		data.setUserName(adata.getUserName());
		data.setPhoneno(adata.getPhoneNo());
		data.setEmailId(adata.getEmailId());
		data.setAddress(adata.getAddress());
		data.setCity(adata.getCity());
		data.setInitThru(adata.getInitThru());
		data.setDist(adata.getDist());
		data.setLocality(adata.getLocality());
		data.setState(adata.getState());
		data.setCountry(adata.getCountry());
		if(adata.getUserId() != null && adata.getUserId() != "" )  // for update mode its not null, for insert mode its null
			data.setUserId(Integer.parseInt(adata.getUserId()));
		Date date = new Date();
		data.setModDttm(date);
		if(adata.getUserId() == null ||  adata.getUserId() == "")  // only for create mode 
			data.setCreateDttm(date);
		data.setUser("Balu");
	}

	public static void copyDBDataToClient(AnbarData data, Anbargal adata)  {
		data.setUserName(adata.getUserName());
		data.setPhoneNo(adata.getPhoneno());
		data.setEmailId(adata.getEmailId());
		data.setAddress(adata.getAddress());
		data.setCity(adata.getCity());
		data.setInitThru(adata.getInitthru());
		data.setDist(adata.getDist());
		data.setLocality(adata.getLocality());
		data.setState(adata.getState());
		data.setCountry(adata.getCountry());    
		data.setUserId(Integer.toString(adata.getUserId()));
	}

	public static void copyClientDataToDatabase(TranData adata, Transaction data)  {

		data.setTransDate (adata.getTransDate());
		data.setDescription(adata.getDescription());
		data.setAmount(adata.getAmount());
		data.setInExp(adata.getInExp());
		data.setHeaderCode(adata.getHeaderCode());
		data.setVoucherNo(adata.getVoucherNo());
		data.setOtherInfo(adata.getOtherInfo());
		data.setTransDate(adata.getTransDate());
		data.setUser(adata.getUserName());
		Date date = new Date();
		data.setModDttm(date);
		if(adata.getTranId() != null && adata.getTranId() != "" )  // for update mode its not null, for insert mode its null
			data.setTranid(Integer.parseInt(adata.getTranId()));
		else
			data.setCreatedDttm(date);   

	}
	public static void copyDBDataToClient( TranData data, Transaction adata)  {
		data.setTransDate (adata.getTransDate());
		data.setDescription(adata.getDescription());
		data.setAmount(adata.getAmount());
		data.setInExp(adata.getInExp());
		data.setHeaderCode(adata.getHeaderCode());
		data.setVoucherNo(adata.getVoucherNo());
		data.setOtherInfo(adata.getOtherInfo());
		data.setTransDate(adata.getTransDate());
		data.setUserName(adata.getUser());
		data.setTranId(Integer.toString(adata.getTranid()));
	}


	public static void copyAnbargalToList(Anbargal adata,List<Object> value){
		value.add(Integer.toString(adata.getUserId()));
		value.add(adata.getUserName());
		value.add(adata.getPhoneno());
		value.add(adata.getEmailId());
		value.add(adata.getAddress());
		value.add(adata.getCity());
		value.add(adata.getInitthru());
		value.add(adata.getDist());
		value.add(adata.getLocality());
		value.add(adata.getState());
		value.add(adata.getCountry());
		SimpleDateFormat df = new SimpleDateFormat("yyyyy-mm-dd:hh:mm:ss");
		value.add(df.format(adata.getModDttm()));
		value.add(df.format(adata.getCreateDttm()));
		value.add(adata.getUser());
	}

	public static void copyTransactionToList(TranData adata,List<Object> value){

		String DATE_FORMAT_NOW = "yyyy-MM-dd";
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
		String stringDate = sdf.format(adata.getTransDate());

		value.add(adata.getTranId());
		value.add(stringDate);//adata.getTransDate());;
		value.add(adata.getDescription());
		value.add(adata.getAmount());
		value.add(adata.getInExp());
		value.add(adata.getHeaderCode());
		value.add(adata.getOtherInfo());
		value.add(adata.getVoucherNo());
		value.add(adata.getUserName());

	}

	public static void copyDatabaseToClientData(TranCodeMaster adata, TranCode data)  {

		data.setTranCode (adata.getTranCode());
		data.setTranDesc(adata.getTranDesc());
		data.setTranCodeId (adata.getTranCodeId());
	}

	public static void copyTranCodeToList(TranCode data,List<Object> value){
		value.add(data.getTranCode());
		value.add(data.getTranDesc());
	}

	public static void copyClientDataToDatabase(TranCode adata, TranCodeMaster data)  {

		Date date = new Date();
		data.setTranCode (adata.getTranCode());
		data.setTranDesc(adata.getTranDesc());
		if(adata.getTranCodeId() == 0)// insert
		{
			data.setTranCodeId(null);
			data.setCreateDttm(date); 
		}
		else // update needs primary key
		{
			data.setTranCodeId (adata.getTranCodeId());
		}
		data.setModDttm(date);
	}

	

	public static void copyDatabaseToClientData(BookTrans adata, BookData data)  {

		data.setBookName(adata.getBookName());
		data.setOwnedBy(adata.getOwnedBy());
		data.setCount(adata.getCount());
		data.setBookTranId(adata.getBookTranId());
	}

	public static void copyTranCodeToList(BookData data,List<Object> value){
		value.add(data.getBookTranId());
		value.add(data.getBookName());
		value.add(data.getOwnedBy());
		value.add(data.getCount());
	}

	public static void copyClientDataToDatabase(BookData adata, BookTrans data)  {

		data.setBookName(adata.getBookName());
		data.setOwnedBy(adata.getOwnedBy());
		data.setCount(adata.getCount());
		data.setBookTranId(adata.getBookTranId());

		Date date = new Date();
		if(adata.getBookTranId() == 0)// insert
		{
			data.setBookTranId(null);
			data.setCreateDttm(date); 
		}
		else // update needs primary key
		{
			data.setBookTranId (adata.getBookTranId());
		}
		data.setModDttm(date);
	}

	
	
}