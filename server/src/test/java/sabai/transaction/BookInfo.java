package sabai.transaction;

import java.io.Serializable;
//dataName":"KanmaniMaalai","descTamil":null,"imageName":"B1.jpg"
public class BookInfo implements Serializable {
String dataName;
String descTamil;
String imageName;
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
