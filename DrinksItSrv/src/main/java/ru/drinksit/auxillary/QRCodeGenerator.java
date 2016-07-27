package ru.drinksit.auxillary;

import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;

import org.springframework.util.Base64Utils;

public class QRCodeGenerator {

	public static String generateQRCode(String codeData, String format, int qrCodeHeight, int qrCodeWidth)
	{
		final byte[] imageBytes = QRCode
	            .from(codeData)
	            .withSize(qrCodeWidth, qrCodeHeight)
	            .to(ImageType.valueOf(format))
	            .stream()
	            .toByteArray();
		
		String encodedImage = "";
		
		encodedImage = Base64Utils.encodeToString(imageBytes);
		return encodedImage;
	}
}
