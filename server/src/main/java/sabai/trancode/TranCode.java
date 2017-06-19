package sabai.trancode;

public class TranCode {
	private String tranCode;
	private String tranDesc;
	private Integer tranCodeId;

	public void setTranCodeId(Integer tranCodeId)   {
		this.tranCodeId = tranCodeId;   
	}
	public Integer getTranCodeId(){
		return tranCodeId;
	}

	public void setTranCode(String tranCode)   {
		this.tranCode = tranCode;   
	}
	public String getTranCode(){
		return tranCode;
	}

	public void setTranDesc(String tranDesc)   {
		this.tranDesc = tranDesc;   
	}
	public String getTranDesc(){
		return tranDesc;
	}
}