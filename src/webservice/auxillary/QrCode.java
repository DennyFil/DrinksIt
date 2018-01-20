package webservice.auxillary;

public class QrCode {

	String image;
	String content;
	
	public QrCode (String image, String content) {
		this.image = image;
		this.content = content;
	}
	
	public String getImage() {
		return image;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setImage(String image) {
		this.image = image;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
}
