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
import webservice.auxillary.AutoInfo;
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
	public ResponseEntity<Bar> GetBarsByUser(HttpServletRequest request) throws Exception {
		
		AutoInfo userInfo = getAuthInfo(request);
		logger.debug("GET /barsByUser for " + userInfo.getUserName());
		
		if (! authService.IsAuthorized(getAuthInfo(request)))
		{
			logger.debug("GET /barsByUser: not logged in");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
		
		Bar bar = barService.GetBar(userInfo.getUserName());

		return bar != null? ResponseEntity.ok(bar) : ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	}

	@RequestMapping("/bars")
	public ResponseEntity<List<Bar>> GetBars(HttpServletRequest request) throws Exception {

		logger.debug("GET /bars");
		if (! authService.IsAuthorized(getAuthInfo(request)))
		{
			logger.debug("GET /bars: not logged in");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}

		List<Bar> bars = barService.GetBars();
			
		return ResponseEntity.ok(bars);
	}
	
	@RequestMapping("/postBar")
	public ResponseEntity<Bar> PostBar(HttpServletRequest request, @RequestParam String barName, @RequestParam String address, @RequestParam String city, @RequestParam String country) throws Exception {
		
		logger.debug("POST /postBar");

		AutoInfo userInfo = getAuthInfo(request);
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
		
		Bar bar = barService.CreateBar(barName, address, city, country);
			
		return bar != null? ResponseEntity.ok(bar) : ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	}
}