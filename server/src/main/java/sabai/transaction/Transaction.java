package sabai.transaction;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import java.util.Date;
@Entity // This tells Hibernate to make a table out of this class
public class Transaction {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer tranid;
	private String description;
	private double amount;
	private  String inexp;
	private  String headercode;
	private String voucherno;
	private String otherinfo;
	private Date createdttm;
	private Date transdate;
	private Date moddttm;
	private String user;	

	public Transaction(){

	}
	public Transaction(Integer tranid) {
		this.tranid = tranid;
	}
	public String getHeaderCode() {
		return headercode;
	}
	public void setHeaderCode(String headercode) {
		this.headercode = headercode;
	}

	public Integer getTranid() {
		return tranid;
	}
	public void setTranid(Integer tranid) {
		this.tranid = tranid;
	}
	public String getVoucherNo() {
		return voucherno;
	}
	public void setVoucherNo(String voucherno) {
		this.voucherno = voucherno;
	}

	public String getOtherInfo() {
		return otherinfo;
	}
	public void setOtherInfo(String otherinfo) {
		this.otherinfo = otherinfo;
	}
	public Date getCreatedDttm() {
		return createdttm;
	}
	public void setCreatedDttm(Date createdttm) {
		this.createdttm = createdttm;
	}
	public Date getTransDate() {
		return transdate;
	}
	public void setTransDate(Date transdate) {
		this.transdate = transdate;
	}
	public Date getModDttm() {
		return moddttm;
	}
	public void setModDttm(Date moddttm) {
		this.moddttm = moddttm;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String name) {
		this.user = name;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}	
	public String getInExp() {
		return inexp;
	}
	public void setInExp(String inexp) {
		this.inexp = inexp;
	}	


}

