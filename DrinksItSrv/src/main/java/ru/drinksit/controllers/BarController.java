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
import ru.drinksit.auxillary.DTO.Bar;
import ru.drinksit.auxillary.ServiceDTO.BarService;

@RestController
public class BarController {

	private static final Logger logger = 
			LoggerFactory.getLogger("barControllerLogger");
	
	@Autowired
	BarService barService;
	
	@Autowired
	HmacAuthenticationFilter hmacAuthenticationFilter;

	@RequestMapping("/barsByUser")
	public ResponseEntity<Bar> getBarsByUser(HttpServletRequest request, @RequestParam String userName) {
		
		logger.debug("GET /barsByUser for " + userName);
		
		JSONObject contentJson = new JSONObject();
		contentJson.put("userName", userName);
		
		if (! hmacAuthenticationFilter.filterRequest(request, contentJson.toString()))
		{
			logger.debug("GET /barsByUser: hmac check failed");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
		
		Bar bar = barService.getBarByUser(userName);
			
		return ResponseEntity.ok(bar);
	}

	@RequestMapping("/bars")
	public ResponseEntity<List<Bar>> getBars(HttpServletRequest request) {

		logger.debug("GET /bars");
		if (! hmacAuthenticationFilter.filterRequest(request, "{}"))
		{
			logger.debug("GET /bars: hmac check failed");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}

		List<Bar> bars = barService.getListOfBars();
			
		return ResponseEntity.ok(bars);
	}
	
	@RequestMapping("/postBar")
	public ResponseEntity<Bar> createBar(HttpServletRequest request, @RequestParam String barName, @RequestParam String address, @RequestParam String city, @RequestParam String country) {
		
		logger.debug("POST /postBar");
		JSONObject contentJson = new JSONObject();
		contentJson.put("barName", barName);
		contentJson.put("address", address);
		contentJson.put("city", city);
		contentJson.put("country", country);

		if (! hmacAuthenticationFilter.filterRequest(request, contentJson.toString()))
		{
			logger.debug("POST /postBar: hmac check failed");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
		Bar bar = barService.createBar(barName, address, city, country);
			
		return ResponseEntity.ok(bar);
	}
}