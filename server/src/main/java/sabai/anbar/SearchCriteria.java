package sabai.anbar;

public class SearchCriteria {
	String nameLike;
	String initLike;
	String cityLike;
	String distLike;
	String stateLike;
	String fDate;
	String tDate;
	
	public String getfDate() {
		return fDate;
	}
	public void setfDate(String fDate) {
		this.fDate = fDate;
	}
	public String gettDate() {
		return tDate;
	}
	public void settDate(String tDate) {
		this.tDate = tDate;
	}
	
	
	public SearchCriteria(String nameLike,String initLike,String cityLike,
							String distLike,String stateLike,String fDate, String tDate){
			this.cityLike = cityLike;
			this.distLike = distLike;
			this.initLike = initLike;
			this.stateLike = stateLike;
			this.nameLike = nameLike;
			this.fDate = fDate;
			this.tDate = tDate;
	} 
	public String getNameLike() {
		return nameLike;
	}
	public void setNameLike(String nameLike) {
		this.nameLike = nameLike;
	}
	public String getInitLike() {
		return initLike;
	}
	public void setInitLike(String initLike) {
		this.initLike = initLike;
	}
	public String getCityLike() {
		return cityLike;
	}
	public void setCityLike(String cityLike) {
		this.cityLike = cityLike;
	}
	public String getDistLike() {
		return distLike;
	}
	public void setDistLike(String distLike) {
		this.distLike = distLike;
	}
	public String getStateLike() {
		return stateLike;
	}
	public void setStateLike(String stateLike) {
		this.stateLike = stateLike;
	}
}