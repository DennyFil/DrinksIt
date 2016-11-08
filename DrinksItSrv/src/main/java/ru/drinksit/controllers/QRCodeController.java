package ru.drinksit.controllers;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ru.drinksit.auxillary.HmacAuthenticationFilter;
import ru.drinksit.auxillary.QRCodeGenerator;
import ru.drinksit.auxillary.QrCode;
import ru.drinksit.auxillary.DTO.Drink;
import ru.drinksit.auxillary.ServiceDTO.DrinkService;

@RestController
public class QRCodeController {

	private static final Logger logger = 
			LoggerFactory.getLogger("qrCodeGenControllerLogger");
	
	@Autowired
	DrinkService drinkService;
	
	@Autowired
    private Environment environment;
	
	@Autowired
	HmacAuthenticationFilter hmacAuthenticationFilter;
	
	@RequestMapping("/qrcode")
	public ResponseEntity<QrCode> getQRCode(HttpServletRequest request, @RequestParam String userName, @RequestParam String drinkId)
	{
		logger.debug("GET /qrcode for drink " + drinkId);
		JSONObject contentJson = new JSONObject();
		contentJson.put("userName", userName);
		contentJson.put("drinkId", drinkId);
		
		if (! hmacAuthenticationFilter.filterRequest(request, contentJson.toString()))
		{
			logger.debug("GET /qrcode: hmac check failed");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
		
		try
		{
			// Request DB for Drink name, price, size and Bar Id based on drinkId
			Drink drink = drinkService.getDrinkByUser(Integer.valueOf(drinkId), userName);
			
			// Generate QR Code
			String serverUrl = environment.getRequiredProperty("server.url");
			String drinkName = drink.getName();
			double drinkPrice = drink.getPrice();
			double drinkSize = drink.getSize();
			int drinkBarId = drink.getBar().getBar_id();

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
		catch (IllegalStateException e)
		{
			logger.error("QR CODE GEN FAILURE: (drinkId: " + drinkId + ")");
			logger.error(ExceptionUtils.getStackTrace(e));
		}
		catch (Exception e)
		{
			logger.error("QR CODE GEN FAILURE: (drinkId: " + drinkId + ")");
			logger.error(ExceptionUtils.getStackTrace(e));
		}
		finally
		{
			
		}
		
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
}
