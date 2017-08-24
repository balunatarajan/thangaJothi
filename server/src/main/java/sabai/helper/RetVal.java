package sabai.helper;

public class RetVal {
	public RetVal(String s,String m){
		success=s; message=m;
	}

	public String getSuccess() {
		return success;
	}
	public void setSuccess(String success) {
		this.success = success;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	String success;
	String message;
}