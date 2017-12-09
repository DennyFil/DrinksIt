package webservice.controllers;

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

import webservice.auxillary.AuthenticationService;
import webservice.auxillary.UserInfo;
import webservice.auxillary.DTO.Bar;
import webservice.auxillary.ServiceDTO.BarService;

@RestController
public class BarController extends GenController {

	private static final Logger logger = 
			LoggerFactory.getLogger("barControllerLogger");
	
	@Autowired
	BarService barService;
	
	@Autowired
	AuthenticationService authService;

	@RequestMapping("/barsByUser")
	public ResponseEntity<Bar> GetBarsByUser(HttpServletRequest request, @RequestParam String userName) throws Exception {
		
		logger.debug("GET /barsByUser for " + userName);
		
		if (! authService.IsAuthorized(getUserInfo(request)))
		{
			logger.debug("GET /barsByUser: not logged in");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
		
		Bar bar = barService.getBarByUser(userName);
			
		return ResponseEntity.ok(bar);
	}

	@RequestMapping("/bars")
	public ResponseEntity<List<Bar>> GetBars(HttpServletRequest request) throws Exception {

		logger.debug("GET /bars");
		if (! authService.IsAuthorized(getUserInfo(request)))
		{
			logger.debug("GET /bars: not logged in");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}

		List<Bar> bars = barService.getListOfBars();
			
		return ResponseEntity.ok(bars);
	}
	
	@RequestMapping("/postBar")
	public ResponseEntity<Bar> PostBar(HttpServletRequest request, @RequestParam String barName, @RequestParam String address, @RequestParam String city, @RequestParam String country) throws Exception {
		
		logger.debug("POST /postBar");
		JSONObject contentJson = new JSONObject();
		contentJson.put("barName", barName);
		contentJson.put("address", address);
		contentJson.put("city", city);
		contentJson.put("country", country);

		UserInfo userInfo = getUserInfo(request);
		if (! authService.IsAuthorized(userInfo))
		{
			logger.debug("POST /postBar: not logged in");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
		
		if ( ! arService.checkRight(userInfo, "create"))
		{
			logger.debug("POST /postBar: no create right");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
		
		Bar bar = barService.createBar(barName, address, city, country);
			
		return ResponseEntity.ok(bar);
	}
}