package sabai.book;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity // This tells Hibernate to make a table out of this class
public class MasterData {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer masterdataid;
	private String type;
	private String desc;
	private String desctamil;

	private Date createdttm;
	private Date moddttm;
	private String user;

	@Column(name="image_name")
	private String imageName;

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}


	public MasterData(){
	}

	
	public Integer getMasterdataid() {
		return masterdataid;
	}


	public void setMasterdataid(Integer masterdataid) {
		this.masterdataid = masterdataid;
	}


	public String getDesc() {
		return desc;
	}


	public void setDesc(String desc) {
		this.desc = desc;
	}


	public String getDesctamil() {
		return desctamil;
	}


	public void setDesctamil(String desctamil) {
		this.desctamil = desctamil;
	}


	public Date getCreatedttm() {
		return createdttm;
	}


	public void setCreatedttm(Date createdttm) {
		this.createdttm = createdttm;
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


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}
}