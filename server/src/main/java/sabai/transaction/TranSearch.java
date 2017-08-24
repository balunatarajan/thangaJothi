package sabai.transaction;

import java.util.Date;

public class TranSearch {
	String	  tcLike ;
	String	  fDate;
	String	  tDate;
	String	  descLike;
	String	  voucherLike;
	
	public TranSearch(String tcLike, String fDate, String tDate, String descLike, String voucherLike) {
		this.tcLike = tcLike;
		this.fDate = fDate;
		this.tDate = tDate;
		this.descLike = descLike;
		this.voucherLike = voucherLike;
	}
	public String getTcLike() {
		return tcLike;
	}
	public void setTcLike(String tcLike) {
		this.tcLike = tcLike;
	}
	public String getfDate() {
		return fDate;
	}
	public void setfDate(String fDate) {
		this.fDate = fDate;
	}
	public String gettDate() {
		return tDate;
	}
	public void settDate(String tDate) {
		this.tDate = tDate;
	}
	public String getDescLike() {
		return descLike;
	}
	public void setDescLike(String descLike) {
		this.descLike = descLike;
	}
	public String getVoucherLike() {
		return voucherLike;
	}
	public void setVoucherLike(String voucherLike) {
		this.voucherLike = voucherLike;
	}
	         
}
