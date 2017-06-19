package sabai.book;

public class BookData {
	private String bookName;
	private String ownedBy;
	private Integer count;
	private Integer bookTranId;

	public void setBookName(String bookName)   {
		this.bookName = bookName;   
	}
	public String getBookName(){
		return bookName;
	}

	public void setOwnedBy(String ownedBy)   {
		this.ownedBy = ownedBy;   
	}
	public String getOwnedBy(){
		return ownedBy;
	}

	public void setCount(Integer count)   {
		this.count = count;   
	}
	public Integer getCount(){
		return this.count;
	}

	public void setBookTranId(Integer bookTranId)   {
		this.bookTranId = bookTranId;   
	}
	public Integer getBookTranId(){
		return bookTranId;
	}


}