package sabai.book;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity // This tells Hibernate to make a table out of this class
public class BookTrans {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer booktranid;
	private String bookname;
	private String ownedby;
	private Integer count;

	private Date createdttm;
	private Date moddttm;
	private String user;


	public BookTrans(){
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
	public BookTrans(Integer booktranid){
		this.booktranid = booktranid;
	}
	public void setBookTranId(Integer booktranid)   {
		this.booktranid = booktranid;   
	}
	public Integer getBookTranId(){
		return booktranid;
	}

	public void setBookName(String bookname)   {
		this.bookname = bookname;   
	}
	public String getBookName(){
		return bookname;
	}

	public void setOwnedBy(String ownedby)   {
		this.ownedby = ownedby;   
	}
	public String getOwnedBy(){
		return ownedby;
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