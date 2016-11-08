package ru.drinksit.controllers;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.exception.ExceptionUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.stereotype.Controller;

import ru.drinksit.auxillary.QRCodeGenerator;
import ru.drinksit.auxillary.database.Drink;
import ru.drinksit.auxillary.database.DrinksItDBManager;
import ru.drinksit.auxillary.database.User;

@Controller
public class QRCodeGenControllerSrv {

	private static final Logger logger = 
			LoggerFactory.getLogger("qrCodeGenControllerLogger");
	
	@Autowired
	DrinksItDBManager drinksItDBManager;
	
	@Autowired
    private Environment environment;
	
	@RequestMapping("/QRCodeGenerator")
	public String getQRCodeGenerator(HttpSession session)
	{
		if (session == null || session.getAttribute("loggedInUser") == null) {
			
			return "login";
        }
		
		return "QRCodeGenerator";
	}
	
	@SuppressWarnings("finally")
	@RequestMapping("/generateQRCode")
	public ModelAndView generateQRCode(HttpSession session, @RequestParam String drinkId)
	{
		User user = (User) session.getAttribute("loggedInUser");
		if (session == null || user == null) {
			
			return new ModelAndView("login");
        }
		
		ModelAndView modelAndView = new ModelAndView("QRCodeGenerator");
		
		try
		{
			// Request DB for Drink name, price, size and Bar Id based on drinkId
			Drink drink = drinksItDBManager.getDrinkByUser(Integer.valueOf(drinkId), user.getUserName());
			
			// Generate QR Code
			String serverUrl = environment.getRequiredProperty("server.name");
			String drinkName = drink.getName();
			double drinkPrice = drink.getPrice();
			double drinkSize = drink.getSize();
			int drinkBarId = drink.getBar().getBar_id();

			String codeData = serverUrl + "/user/postOrder?drinkId=" + drinkId + 
											"&barId=" + drinkBarId + 
											"&drinkName=" + drinkName +
											"&drinkPrice=" + drinkPrice +
											"&drinkSize=" + drinkSize;
			
			String format = environment.getRequiredProperty("qrCodeGenerator.format");

			int qrCodeHeight = Integer.valueOf(environment.getRequiredProperty("qrCode.height"));
			int qrCodeWidth = Integer.valueOf(environment.getRequiredProperty("qrCode.width"));

			String qrCodeImage = QRCodeGenerator.generateQRCode(codeData, format, qrCodeHeight, qrCodeWidth);

			logger.info("QR CODE GEN SUCCESS: ( " + codeData + " )");
			
			modelAndView.addObject("qrCodeImage", qrCodeImage);
	
			modelAndView.addObject("codeData", codeData);			
			modelAndView.addObject("serverUrl", serverUrl);
			modelAndView.addObject("drinkPrice", drinkPrice);
			modelAndView.addObject("drinkSize", drinkSize);
			modelAndView.addObject("drinkBarId", drinkBarId);
			modelAndView.addObject("drinkName", drinkName);
			modelAndView.addObject("qrCodeFormat", format);
		}
		catch (IllegalStateException e)
		{
			modelAndView.addObject("message", "Failed to read server config");
			logger.error("QR CODE GEN FAILURE: (drinkId: " + drinkId + ")");
			logger.error(ExceptionUtils.getStackTrace(e));
		}
		catch (Exception e)
		{
			modelAndView.addObject("message", e.getMessage());
			logger.error("QR CODE GEN FAILURE: (drinkId: " + drinkId + ")");
			logger.error(ExceptionUtils.getStackTrace(e));
		}
		finally
		{
			return modelAndView;
		}
	}
}
