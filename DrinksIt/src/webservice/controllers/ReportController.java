package webservice.controllers;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import webservice.auxillary.AuthenticationService;
import webservice.auxillary.DTO.Bar;
import webservice.auxillary.DTO.Drink;
import webservice.auxillary.DTO.Order;
import webservice.auxillary.ServiceDTO.BarService;
import webservice.auxillary.ServiceDTO.OrderService;
import webservice.auxillary.ReportBuilder;
import webservice.auxillary.UserInfo;

@RestController
public class ReportController extends GenController {

	private static final Logger logger = 
			LoggerFactory.getLogger("reportControllerLogger");

	@Autowired
	OrderService orderService;
	
	@Autowired
	BarService barService;

	@Autowired
	AuthenticationService authService;

	@RequestMapping("/ordersReport")
	public ResponseEntity<byte []> ExportOrders(HttpServletRequest request, @RequestParam String startDate, @RequestParam String endDate) throws Exception {

		UserInfo userInfo = getUserInfo(request);
		logger.debug("GET /orderReport for: " + userInfo.getUserName());

		if (! authService.IsAuthorized(userInfo))
		{
			logger.debug("GET /orderReport: not logged in");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}

		String userName = userInfo.getUserName();
		List<Order> orderListAll = orderService.GetOrders(userName);

		// Tue Mar 22 22:12:34 CET 2016
		//DateFormat dfCreationTimeSrv = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.US);

		DateFormat dfCreationTime = new SimpleDateFormat("yyyy-MM-dd");
		
		Date startDateD = dfCreationTime.parse(startDate);
		Date endDateD = dfCreationTime.parse(endDate);

		logger.debug("GET /orderReport: all orders count " + orderListAll.size());
		List<Order> filteredOrders = orderListAll;

		filteredOrders = orderListAll.stream()
				.filter(o -> {
					
					return o.getCreationTS().after(startDateD) && 
								o.getCreationTS().before(endDateD);
				}).collect(Collectors.toList());		

		Bar bar = barService.GetBar(userName);
		
		if (bar != null) {
		
			byte[] report = ReportBuilder.buildOrderReport(filteredOrders, bar, startDateD, endDateD, dfCreationTime);
			
			logger.debug("GET /orderReport: filtered orders count " + filteredOrders.size());
			logger.debug("GET /orderReport: returned pdf report containing orders for period from " + startDate.toString() + " till " + endDate.toString());
	
			String reportFileName = "Orders_Bar_" + bar.getId() + "_" + startDate.toString() + "_to_" + endDate.toString() + ".pdf";
			
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.setContentDispositionFormData(reportFileName, reportFileName);
			responseHeaders.setCacheControl("must-revalidate, post-check=0, pre-check=0");
			responseHeaders.setContentType(MediaType.parseMediaType("application/pdf"));
			responseHeaders.set("filename", reportFileName);
			
			ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(report, responseHeaders, HttpStatus.OK);
		    
			return response;
		}

		logger.debug("Bar not available for user: " + userName);
		return new ResponseEntity<byte[]>(HttpStatus.NOT_FOUND);
	}
}