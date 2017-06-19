package sabai.helper;

public class RetVal {
	public RetVal(String s,String m){
		success=s; message=m;}
	public String getSuccess() { 
		return success;
	}
	public String getMessage() { 
		return message;
	}
	String success;
	String message;
}