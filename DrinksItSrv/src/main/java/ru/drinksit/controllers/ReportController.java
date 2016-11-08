package ru.drinksit.controllers;

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

import ru.drinksit.auxillary.HmacAuthenticationFilter;
import ru.drinksit.auxillary.DTO.Bar;
import ru.drinksit.auxillary.DTO.Drink;
import ru.drinksit.auxillary.DTO.Order;
import ru.drinksit.auxillary.ServiceDTO.BarService;
import ru.drinksit.auxillary.ServiceDTO.OrderService;
import ru.drinksit.auxillary.ReportBuilder;

@RestController
public class ReportController {

	private static final Logger logger = 
			LoggerFactory.getLogger("reportControllerLogger");

	@Autowired
	OrderService orderService;
	
	@Autowired
	BarService barService;

	@Autowired
	HmacAuthenticationFilter hmacAuthenticationFilter;

	@RequestMapping("/orderReport")
	public ResponseEntity<byte []> getDateFilteredOrders(HttpServletRequest request, @RequestParam String userName, @RequestParam String startDate, @RequestParam String endDate) throws ParseException {

		logger.debug("GET /orderReport for: " + userName);

		JSONObject contentJson = new JSONObject();
		contentJson.put("userName", userName);
		contentJson.put("startDate", startDate);
		contentJson.put("endDate", endDate);

		if (! hmacAuthenticationFilter.filterRequest(request, contentJson.toString()))
		{
			logger.debug("GET /orderReport: hmac check failed");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}

		List<Order> orderListAll = orderService.getListOfOrders(userName);

		// Tue Mar 22 22:12:34 CET 2016
		DateFormat dfCreationTimeSrv = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.US);

		DateFormat dfCreationTime = new SimpleDateFormat("yyyy-MM-dd");
		
		Date startDateD = dfCreationTime.parse(startDate);
		Date endDateD = dfCreationTime.parse(endDate);

		logger.debug("GET /orderReport: all orders count " + orderListAll.size());
		List<Order> filteredOrders = orderListAll;

		filteredOrders = orderListAll.stream()
				.filter(o -> {
					
					try {					
						return dfCreationTimeSrv.parse(o.getCreationTS()).after(startDateD) && 
								dfCreationTimeSrv.parse(o.getCreationTS()).before(endDateD);
					} catch (ParseException e) {
						logger.error("Failed to filter orders by time" + o.getOrder_id());
						logger.error(ExceptionUtils.getStackTrace(e));
					}
					return false;
				}).collect(Collectors.toList());		

		Bar bar = barService.getBarByUser(userName);
		
		byte[] report = ReportBuilder.buildOrderReport(filteredOrders, bar, startDateD, endDateD, dfCreationTime);
		
		logger.debug("GET /orderReport: filtered orders count " + filteredOrders.size());
		logger.debug("GET /orderReport: returned pdf report containing orders for period from " + startDate.toString() + " till " + endDate.toString());

		String fileName = "Orders_Bar_" + bar.getBar_id() + "_" + startDate.toString() + "_to_" + endDate.toString() + ".pdf";
		
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.setContentDispositionFormData(fileName, fileName);
		responseHeaders.setCacheControl("must-revalidate, post-check=0, pre-check=0");
		responseHeaders.setContentType(MediaType.parseMediaType("application/pdf"));
		
		ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(report, responseHeaders, HttpStatus.OK);
	    
		return response;
	}
}
