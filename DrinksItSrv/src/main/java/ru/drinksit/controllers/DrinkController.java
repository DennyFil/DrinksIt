package ru.drinksit.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ru.drinksit.auxillary.HmacAuthenticationFilter;
import ru.drinksit.auxillary.DTO.Drink;
import ru.drinksit.auxillary.ServiceDTO.DrinkService;

@RestController
public class DrinkController {

	private static final Logger logger = 
			LoggerFactory.getLogger("drinkControllerLogger");
	
	@Autowired
	DrinkService drinkService;
	
	@Autowired
	HmacAuthenticationFilter hmacAuthenticationFilter;
	
	@RequestMapping("/drinks")
	public ResponseEntity<List<Drink>> getDrinks(HttpServletRequest request, @RequestParam Integer barId) {

		logger.debug("GET /drinks for bar " + barId);
		
		JSONObject contentJson = new JSONObject();
		contentJson.put("barId", new Integer(barId).toString());

		if (! hmacAuthenticationFilter.filterRequest(request, contentJson.toString()))
		{
			logger.debug("GET /drinks: hmac check failed");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}

		List<Drink> drinks = drinkService.getListOfDrinks(barId);
		
		return ResponseEntity.ok(drinks);
	}

	@RequestMapping("/postDrink")
	public ResponseEntity<Drink> createDrink(HttpServletRequest request, @RequestParam String drinkName, @RequestParam double price, @RequestParam double size, @RequestParam int barId) {

		logger.debug("POST /postDrink " + drinkName + " to bar " + barId);
		
		JSONObject contentJson = new JSONObject();
		contentJson.put("drinkName", drinkName);
		contentJson.put("price", new Double(price).toString());
		contentJson.put("size", new Double(size).toString());
		contentJson.put("barId", new Integer(barId).toString());

		if (! hmacAuthenticationFilter.filterRequest(request, contentJson.toString()))
		{
			logger.debug("POST /postDrink: hmac check failed");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}

		Drink drink = drinkService.createDrink(drinkName, price, size, barId);
		
		return ResponseEntity.ok(drink);
	}
}