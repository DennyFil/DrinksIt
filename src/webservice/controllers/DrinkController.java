package webservice.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import webservice.auxillary.AuthInfo;
import webservice.auxillary.DTO.Drink;
import webservice.auxillary.ServiceDTO.DrinkService;

@RestController
public class DrinkController extends GenController {

	private static final Logger logger = 
			LoggerFactory.getLogger("drinkControllerLogger");
	
	@Autowired
	DrinkService drinkService;
	
	@RequestMapping("/drinks")
	public ResponseEntity<List<Drink>> GetDrinks(HttpServletRequest request, @RequestParam Integer barId) throws Exception {

		logger.debug("GET /drinks for bar " + barId);

		AuthInfo userInfo = getAuthInfo(request);
		if (! authService.IsAuthorized(userInfo))
		{
			logger.debug("GET /drinks: not authorized for " + userInfo.getUserName());
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}

		try {
			List<Drink> drinks = drinkService.GetDrinks(barId);
			
			return ResponseEntity.ok(drinks);
		}
		catch (Exception e){
			logger.debug(e.getMessage());
		}

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	}

	@RequestMapping("/postDrink")
	public ResponseEntity<Drink> PostDrink(HttpServletRequest request, @RequestBody Drink newDrink) throws Exception {

		logger.debug("POST /postDrink " + newDrink.getName() + " to bar " + newDrink.getBarId());

		AuthInfo userInfo = getAuthInfo(request);
		if (! authService.IsAuthorized(userInfo))
		{
			logger.debug("POST /postDrink: not authorized for " + userInfo.getUserName());
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
		
		if ( ! arService.isBarAdmin(userInfo, newDrink.getBarId()))
		{
			logger.debug("POST /postDrink: no create right for " + userInfo.getUserName());
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
		}
		
		try {

			Drink drink = drinkService.CreateDrink(newDrink);
			
			logger.debug("CREATION drink: " + newDrink.getName() + ", size: " + newDrink.getSize() + ", price: " + newDrink.getPrice() + ", bar: " + newDrink.getBarId());
            
			return ResponseEntity.ok(drink);
		}
		catch (Exception e){
			logger.debug(e.getMessage());
		}

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	}
}