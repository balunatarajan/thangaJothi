
//@XmlRootElement
package sabai.transaction;
import java.util.Date;

public class TranData {
	private Date transDate;
	private String description;
	private double amount;
	private String inExp;
	private String headerCode;
	private String voucherNo;
	private String otherInfo;
	private String userName;
	private String tranId;
    
	@Override
	public String toString() {
		return "TranData [transDate=" + transDate + ", description=" + description + ", amount=" + amount + ", inExp="
				+ inExp + ", headerCode=" + headerCode + ", voucherNo=" + voucherNo + ", otherInfo=" + otherInfo
				+ ", userName=" + userName + ", tranId=" + tranId + "]";
	}
	public String getTranId() {
		return tranId;
	}
	public void setTranId(String tranId) {
		this.tranId = tranId;
	}

	public String getUserName() {
		return userName;
	}
	public void setUserName(String name) {
		this.userName = name;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {

		this.amount = amount;
	}	    
	public Date getTransDate() {
		return transDate;
	}
	public void setTransDate(Date transDate) {
		this.transDate = transDate;
	}	    
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}	    
	public String getInExp() {
		return inExp;
	}
	public void setInExp(String inExp) {
		this.inExp = inExp;
	}	    
	public String getHeaderCode() {
		return headerCode;
	}
	public void setHeaderCode(String headerCode) {
		this.headerCode = headerCode;
	}	    
	public String getVoucherNo() {
		return voucherNo;
	}
	public void setVoucherNo(String voucherNo) {
		this.voucherNo = voucherNo;
	}	    
	public String getOtherInfo() {
		return otherInfo;
	}
	public void setOtherInfo(String otherinfo) {
		this.otherInfo = otherinfo;
	}	    


}