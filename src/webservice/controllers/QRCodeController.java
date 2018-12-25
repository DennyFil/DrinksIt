package webservice.controllers;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.exception.ExceptionUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import webservice.auxillary.QRCodeGenerator;
import webservice.auxillary.QrCode;
import webservice.auxillary.AuthInfo;
import webservice.auxillary.DTO.Drink;
import webservice.auxillary.ServiceDAO.IDrinkService;

@RestController
@RequestMapping("/api")
public class QRCodeController extends BaseController {

	@Autowired
	IDrinkService drinkService;

	@Autowired
	private Environment environment;

	@RequestMapping("/qrcode")
	public ResponseEntity GetQRCode(HttpServletRequest request, @RequestBody Integer drinkId) throws Exception
	{
		logger.debug("GET /qrcode for drink " + drinkId);
		
		try
		{
			AuthInfo userInfo = authInfoService.getAuthInfo(request);
			
			// Request DB for Drink name, price, size and Bar Id based on drinkId
			Drink drink = drinkService.GetDrink(drinkId);
						
			if (! arService.isBarAdmin(userInfo, drink.getBarId()))
			{
				logger.debug("GET /qrcode: no right for " + userInfo.getUserName());
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not allowed to generate QR code for this drink");
			}

			// Generate QR Code
			String serverUrl = environment.getRequiredProperty("server.url");
			String drinkName = drink.getName();
			double drinkPrice = drink.getPrice();
			double drinkSize = drink.getSize();
			int drinkBarId = drink.getBarId();

			String qrCodeContent = serverUrl + "/api/postOrder?drinkId=" + drinkId + 
					"&barId=" + drinkBarId + 
					"&drinkName=" + drinkName +
					"&drinkPrice=" + drinkPrice +
					"&drinkSize=" + drinkSize;

			String format = environment.getRequiredProperty("qrCodeGenerator.format");

			int qrCodeHeight = Integer.valueOf(environment.getRequiredProperty("qrCode.height"));
			int qrCodeWidth = Integer.valueOf(environment.getRequiredProperty("qrCode.width"));

			String qrCodeImage = QRCodeGenerator.generateQRCode(qrCodeContent, format, qrCodeHeight, qrCodeWidth);

			QrCode qrCode = new QrCode(qrCodeImage, qrCodeContent);

			return ResponseEntity.ok(qrCode);
		}
		catch (Exception e)
		{
			logger.error(ExceptionUtils.getStackTrace(e));
		}

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to generate QR code (drinkId: " + drinkId + ")");
	}
}
