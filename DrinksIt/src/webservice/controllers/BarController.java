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
import org.springframework.web.bind.annotation.RestController;

import webservice.auxillary.AuthInfo;
import webservice.auxillary.DTO.Bar;
import webservice.auxillary.ServiceDTO.BarService;

@RestController
public class BarController extends GenController {

	private static final Logger logger = 
			LoggerFactory.getLogger("barControllerLogger");
	
	@Autowired
	BarService barService;

	@RequestMapping("/bars")
	public ResponseEntity<List<Bar>> GetBars(HttpServletRequest request) throws Exception {

		logger.debug("GET /bars");
		AuthInfo userInfo = getAuthInfo(request);
		if (! authService.IsAuthorized(userInfo))
		{
			logger.debug("GET /bars: not authorized for " + userInfo.getUserName());
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
		
		if ( ! arService.checkRight(userInfo, "list"))
		{
			logger.debug("GET /bars: no list right for " + userInfo.getUserName());
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
		}
		
		try {
			List<Bar> bars = barService.GetBars();
			
			return ResponseEntity.ok(bars);
		}
		catch (Exception e){
			logger.debug(e.getMessage());
		}
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	}
	
	@RequestMapping("/postBar")
	public ResponseEntity<Bar> PostBar(HttpServletRequest request, @RequestBody Bar newBar) throws Exception {
		
		logger.debug("POST /postBar");

		AuthInfo userInfo = getAuthInfo(request);
		if (! authService.IsAuthorized(userInfo))
		{
			logger.debug("POST /postBar: not authorized for " + userInfo.getUserName());
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
		
		if ( ! arService.checkRight(userInfo, "create"))
		{
			logger.debug("POST /postBar: no create right for " + userInfo.getUserName());
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
		}
		
		try {
			Bar bar = barService.CreateBar(newBar);

			logger.debug("CREATION: bar " + bar.getName());
			
			return ResponseEntity.ok(bar);
		}
		catch (Exception e){
			logger.debug(e.getMessage());
		}

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	}
}