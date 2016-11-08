package ru.drinksit.views;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.cert.CRLException;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

import ru.drinksit.auxillary.OrderTable;
import ru.drinksit.auxillary.DTO.Bar;
import ru.drinksit.auxillary.DTO.Order;

public class PdfOrderReportView extends AbstractPdfBoxView
{
	@Override
	protected void buildPdfDocument(Map<String, Object> model, PDDocument document,
			HttpServletRequest request, HttpServletResponse response)
					throws IOException, SignatureException, CRLException, NoSuchAlgorithmException
	{
		Date startDateD = (Date) model.get("startDate");
		Date endDateD = (Date) model.get("endDate");
		DateFormat df = (DateFormat) model.get("dateFormat");
		Bar bar = (Bar) model.get("bar");

		List<Order> orderList = (List<Order>) model.get("orders");

		PDPage page = new PDPage();
		document.addPage( page );

		OrderTable.createOrderTable(document, page, orderList, bar, startDateD, endDateD, df);
	}
}
