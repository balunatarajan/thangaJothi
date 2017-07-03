package sabai.book;

public class MasterDataC {
String dataName;
String descTamil;
String imageName;

public void set(String dataName,String descTamil,String imageName){
	this.dataName = dataName;
	this.descTamil = descTamil;
	this.imageName = imageName;
}
public String getDataName() {
	return dataName;
}
public void setDataName(String dataName) {
	this.dataName = dataName;
}
public String getDescTamil() {
	return descTamil;
}
public void setDescTamil(String descTamil) {
	this.descTamil = descTamil;
}
public String getImageName() {
	return imageName;
}
public void setImageName(String imageName) {
	this.imageName = imageName;
}

}
