package webservice.controllers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import webservice.auxillary.DTO.Bar;
import webservice.auxillary.DTO.Order;
import webservice.auxillary.ServiceDAO.IBarService;
import webservice.auxillary.ServiceDAO.IOrderService;
import webservice.auxillary.ReportBuilder;
import webservice.auxillary.AuthInfo;

@RestController
@RequestMapping("/api")
public class ReportController extends BaseController {

	@Autowired
	IOrderService orderService;

	@Autowired
	IBarService barService;

	@RequestMapping("/ordersReport")
	public ResponseEntity<?> ExportOrders(HttpServletRequest request, @RequestParam String startDate, @RequestParam String endDate) throws Exception {

		try {
			AuthInfo userInfo = authInfoService.getAuthInfo(request);
			String userName = userInfo.getUserName();
			
			List<Order> orderListAll = orderService.GetOrders(userName);

			// Tue Mar 22 22:12:34 CET 2016
			//DateFormat dfCreationTimeSrv = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.US);

			DateFormat dfCreationTime = new SimpleDateFormat("yyyy-MM-dd");

			Date startDateD = dfCreationTime.parse(startDate);
			Date endDateD = dfCreationTime.parse(endDate);

			List<Order> filteredOrders = orderListAll;

			filteredOrders = orderListAll.stream()
					.filter(o -> {

						return o.getCreationTS().after(startDateD) && 
								o.getCreationTS().before(endDateD);
					}).collect(Collectors.toList());		

			Bar bar = barService.GetBar(userName);

			byte[] report = ReportBuilder.buildOrderReport(filteredOrders, userName, bar, startDateD, endDateD, dfCreationTime);

			String reportFileName = "Orders_Bar_" + bar.getId() + "_" + startDate.toString() + "_to_" + endDate.toString() + ".pdf";

			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.setContentDispositionFormData(reportFileName, reportFileName);
			responseHeaders.setCacheControl("must-revalidate, post-check=0, pre-check=0");
			responseHeaders.setContentType(MediaType.parseMediaType("application/pdf"));
			responseHeaders.set("filename", reportFileName);

			ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(report, responseHeaders, HttpStatus.OK);

			loggerDB.debug("GET /orderReport: returned pdf report containing orders for period from " + startDate.toString() + " till " + endDate.toString());

			return response;
		}
		catch (Exception e) {
			loggerConsole.debug(e.getMessage());
		}

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to generate report");
	}
}
