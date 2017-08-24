package sabai.anbar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity // This tells Hibernate to make a table out of this class
public class Anbargal {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer userid;
	private String username;
	private String emailid;
	private String phoneno;
	private String address;
	private String city;
	private String initthru;
	private String dist;
	private String locality;
	private String state;
	private String country;
	private Date createdttm;
	private Date moddttm;
	private String user;
	private Date initdate;
	public String toString(){
		return Integer.toString(userid)+":"+username+":"+emailid+":"+phoneno+":"
				+address+":"+city+":"+initthru+":"+dist+":"+locality+":"+state
				+":"+country+":"+createdttm+":"+moddttm+":"+user;

	}
	public Anbargal() {}
	public Anbargal (Integer id) {
		this.userid = id;
	}
	public Date getCreateDttm() {
		return createdttm;
	}
	public void setCreateDttm(Date createdttm) {
		this.createdttm = createdttm;
	}

	public Date getModDttm() {
		return moddttm;
	}
	public void setModDttm(Date moddtm) {
		this.moddttm = moddtm;
	}

	public String 	 getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}

	public Integer getUserId() {
		return userid;
	}
	public void setUserId(Integer id) {
		this.userid = id;
	}


	public String getUserName() {
		return username;
	}
	public void setUserName(String name) {
		this.username = name;
	}
	public String getEmailId() {
		return emailid;
	}
	public void setEmailId(String email) {
		this.emailid = email;
	}
	public void setPhoneno(String phoneno){
		this.phoneno = phoneno;
	}
	public String getPhoneno( )	{
		return phoneno;
	}
	public void setAddress(String address){
		this.address = address;
	}
	public String getAddress( )	{
		return this.address;
	}
	public String getCity( )	{
		return this.city;
	}
	public void setCity(String city)	{
		this.city = city;
	}
	public void setInitThru(String initthru){
		this.initthru = initthru;
	}
	public String getInitthru( )	{
		return this.initthru;
	}
	public void setDist(String dist) {
		this.dist = dist;
	}
	public String getDist() {
		return this.dist;
	}
	public void setLocality(String locality) {
		this.locality = locality;
	}
	public String getLocality() {
		return this.locality;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getState() {
		return this.state;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getCountry() {
		return this.country;
	}
	public Date getInitdate() {
		return initdate;
	}
	public void setInitdate(Date initdate) {
		this.initdate = initdate;
	}

}

