package webservice.auxillary;

public class QRCodeGenerationException extends Exception {

	private static final long serialVersionUID = 1L;

	public QRCodeGenerationException() {
		super("Failed to generate QR code");
	}

}
