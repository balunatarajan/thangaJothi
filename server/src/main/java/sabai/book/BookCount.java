package sabai.book;

public class BookCount {
	String bookName;
	long count;

	public BookCount(String bookName, long count) {
		super();
		this.bookName = bookName;
		this.count = count;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

}
