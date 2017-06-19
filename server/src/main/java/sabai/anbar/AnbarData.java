//@XmlRootElement
package sabai.anbar;

public class AnbarData {
	private  String userName;
	private  String phoneNo;
	private  String emailId;
	private  String address;
	private String city;
	private String initThru;
	private String dist;
	private String locality;
	private String state;
	private String country;
	private String userId;

	public String getUserId() {
		return userId;
	}
	public void setUserId(String id) {
		this.userId = id;
	}

	public String getUserName() {
		return userName;
	}
	public void setUserName(String name) {
		this.userName = name;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getInitThru() {
		return initThru;
	}
	public void setInitThru(String initThru) {
		this.initThru = initThru;
	}
	public String getDist() {
		return dist;
	}
	public void setDist(String dist) {
		this.dist = dist;
	}
	public String getLocality() {
		return locality;
	}
	public void setLocality(String locality) {
		this.locality = locality;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}



}