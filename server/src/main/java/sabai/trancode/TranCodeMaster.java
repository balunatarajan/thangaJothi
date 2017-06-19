package sabai.trancode;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity // This tells Hibernate to make a table out of this class
public class TranCodeMaster {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer trancodeid;
	private String trancode;
	private String trandesc;
	private Date createdttm;
	private Date moddttm;
	private String user;

	public TranCodeMaster(){

	}
	public TranCodeMaster(Integer trancodeid){
		this.trancodeid = trancodeid;
	}
	public void setTranCodeId(Integer trancodeid)   {
		this.trancodeid = trancodeid;   
	}
	public Integer getTranCodeId(){
		return trancodeid;
	}

	public void setTranCode(String trancode)   {
		this.trancode = trancode;   
	}
	public String getTranCode(){
		return trancode;
	}

	public void setTranDesc(String trandesc)   {
		this.trandesc = trandesc;   
	}
	public String getTranDesc(){
		return trandesc;
	}

	public void setUser(String user)   {
		this.user = user;   
	}
	public String getUser(){
		return user;
	}


	public void setCreateDttm(Date createdttm)   {
		this.createdttm = createdttm;   
	}
	public Date getCreateDttm(){
		return createdttm;
	}


	public void setModDttm(Date moddttm)   {
		this.moddttm = moddttm;   
	}
	public Date getModDttm(){
		return moddttm;
	}
}