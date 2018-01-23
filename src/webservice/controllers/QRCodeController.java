package webservice.controllers;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import webservice.auxillary.QRCodeGenerator;
import webservice.auxillary.QrCode;
import webservice.auxillary.AuthInfo;
import webservice.auxillary.DTO.Drink;
import webservice.auxillary.ServiceDTO.DrinkService;

@RestController
public class QRCodeController extends GenController {

	private static final Logger logger = 
			LoggerFactory.getLogger("qrCodeGenControllerLogger");

	@Autowired
	DrinkService drinkService;

	@Autowired
	private Environment environment;

	@RequestMapping("/qrcode")
	public ResponseEntity GetQRCode(HttpServletRequest request, @RequestParam String drinkId) throws Exception
	{
		logger.debug("GET /qrcode for drink " + drinkId);

		AuthInfo userInfo = getAuthInfo(request);	
		if (! authService.IsAuthorized(userInfo))
		{
			logger.debug("GET /qrcode: not authorized for " + userInfo.getUserName());
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}

		try
		{
			// Request DB for Drink name, price, size and Bar Id based on drinkId
			Drink drink = drinkService.GetDrink(Integer.valueOf(drinkId), userInfo.getUserName());

			// Generate QR Code
			String serverUrl = environment.getRequiredProperty("server.url");
			String drinkName = drink.getName();
			double drinkPrice = drink.getPrice();
			double drinkSize = drink.getSize();
			int drinkBarId = drink.getBarId();

			String qrCodeContent = serverUrl + "/postOrder?drinkId=" + drinkId + 
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
			logger.error("GEN QR CODE FAILURE: (drinkId: " + drinkId + ")");
			logger.error(ExceptionUtils.getStackTrace(e));
		}
		finally
		{

		}

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("GEN QR CODE FAILURE: (drinkId: " + drinkId + ")");
	}
}
