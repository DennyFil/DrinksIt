package ru.drinksit.auxillary;

public class QRCodeGenerationException extends Exception {

	public QRCodeGenerationException() {
		super("Failed to generate QR code");
	}

}
