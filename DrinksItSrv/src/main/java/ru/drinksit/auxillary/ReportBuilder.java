package ru.drinksit.auxillary;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

import ru.drinksit.auxillary.OrderTable;
import ru.drinksit.auxillary.DTO.Bar;
import ru.drinksit.auxillary.DTO.Order;

public class ReportBuilder {

	public static byte[] buildOrderReport(List<Order> orders, Bar bar, Date startDate, Date endDate, DateFormat dateFormat) {
	
		try {
			// IE workaround: write into byte array first.
			ByteArrayOutputStream baos = new ByteArrayOutputStream();

			// Apply preferences and build metadata.
			PDDocument document = new PDDocument();
			
			// Build PDF document
			PDPage page = new PDPage();
			document.addPage( page );

			OrderTable.createOrderTable(document, page, orders, bar, startDate, endDate, dateFormat);		
			
			document.save(baos);
			document.close();
			
			return baos.toByteArray();
		}
		catch (Exception e)
		{
			// Failed to generate pdf
			return null;
		}
	}
}