package webservice.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

		if (! authService.IsAuthorized(getAuthInfo(request)))
		{
			logger.debug("GET /drinks: not logged in");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}

		List<Drink> drinks = drinkService.GetDrinks(barId);
		
		return ResponseEntity.ok(drinks);
	}

	@RequestMapping("/postDrink")
	public ResponseEntity<Drink> PostDrink(HttpServletRequest request, @RequestParam String drinkName, @RequestParam double price, @RequestParam double size, @RequestParam int barId) throws Exception {

		logger.debug("POST /postDrink " + drinkName + " to bar " + barId);

		AuthInfo userInfo = getAuthInfo(request);
		if (! authService.IsAuthorized(userInfo))
		{
			logger.debug("POST /postDrink: not logged in");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
		
		if ( ! arService.checkRight(userInfo, "create"))
		{
			logger.debug("POST /postDrink: no create right");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}

		Drink drink = drinkService.CreateDrink(drinkName, price, size, barId);
		
		return drink != null? ResponseEntity.ok(drink) : ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	}
}