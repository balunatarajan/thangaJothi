package sabai.book;

public class BookTransfer {
	  public String getFromOwner() {
		return fromOwner;
	}
	public void setFromOwner(String fromOwner) {
		this.fromOwner = fromOwner;
	}
	public String getToOwner() {
		return toOwner;
	}
	public void setToOwner(String toOwner) {
		this.toOwner = toOwner;
	}
	public String getBookName() {
		return bookName;
	}
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	String fromOwner;
	  String toOwner;
	  String bookName;
	  Integer count;
}
