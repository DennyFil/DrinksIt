package webservice.auxillary;

import java.awt.Color;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Iterator;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import be.quodlibet.boxable.BaseTable;
import be.quodlibet.boxable.Cell;
import be.quodlibet.boxable.Row;

import webservice.auxillary.DTO.Order;
import webservice.auxillary.DTO.Bar;

public class OrderTable {

	public static void createOrderTable(PDDocument doc, PDPage page, List<Order> orders, String userName, Bar bar, Date startDate, Date endDate, DateFormat df)
		throws IOException
	{
		//Set margins
		float margin = 10;

		//Initialize Document
		float yStartNewPage = page.getMediaBox().getHeight() - (2 * margin);

		//Initialize table
		float tableWidth = page.getMediaBox().getWidth() - (2 * margin);
		boolean drawContent = true;
		float yStart = yStartNewPage;
		float bottomMargin = 70;
		BaseTable table = new BaseTable(yStart, yStartNewPage, bottomMargin, tableWidth, margin, doc, page, true, drawContent);

		//Create Header row
		Row<PDPage> headerRow = table.createRow(15f);
		Cell<PDPage> cell = headerRow.createCell(100, "Orders report for user: " + userName);
		cell.setFont(PDType1Font.HELVETICA_BOLD);
		cell.setFillColor(Color.GREEN);
		cell.setTextColor(Color.BLACK);

		table.addHeaderRow(headerRow);
		
		Row<PDPage> barRow1 = table.createRow(15f);
		cell = barRow1.createCell((100 / 6f), "Bar");
		cell.setFont(PDType1Font.HELVETICA);
		cell.setFillColor(Color.CYAN);
		cell.setTextColor(Color.BLACK);
		
		cell = barRow1.createCell((100 / 6f), "ID");
		cell.setFont(PDType1Font.HELVETICA);
		cell.setFillColor(Color.CYAN);
		cell.setTextColor(Color.BLACK);
		
		cell = barRow1.createCell((100 / 6f), "Name");
		cell.setFont(PDType1Font.HELVETICA);
		cell.setFillColor(Color.CYAN);
		cell.setTextColor(Color.BLACK);
		
		cell = barRow1.createCell((100 / 6f), "Address");
		cell.setFont(PDType1Font.HELVETICA);
		cell.setFillColor(Color.CYAN);
		cell.setTextColor(Color.BLACK);
		
		cell = barRow1.createCell((100 / 6f), "City");
		cell.setFont(PDType1Font.HELVETICA);
		cell.setFillColor(Color.CYAN);
		cell.setTextColor(Color.BLACK);
		
		cell = barRow1.createCell((100 / 6f), "Country");
		cell.setFont(PDType1Font.HELVETICA);
		cell.setFillColor(Color.CYAN);
		cell.setTextColor(Color.BLACK);
		
		Row<PDPage> barRow2 = table.createRow(15f);
		cell = barRow2.createCell((100 / 6f), "");
		cell.setFont(PDType1Font.HELVETICA);
		cell.setFillColor(Color.CYAN);
		cell.setTextColor(Color.BLACK);
		
		cell = barRow2.createCell((100 / 6f), new Integer(bar.getId()).toString());
		cell.setFont(PDType1Font.HELVETICA);
		cell.setFillColor(Color.CYAN);
		cell.setTextColor(Color.BLACK);
		
		cell = barRow2.createCell((100 / 6f), bar.getName());
		cell.setFont(PDType1Font.HELVETICA);
		cell.setFillColor(Color.CYAN);
		cell.setTextColor(Color.BLACK);
		
		cell = barRow2.createCell((100 / 6f), bar.getAddress());
		cell.setFont(PDType1Font.HELVETICA);
		cell.setFillColor(Color.CYAN);
		cell.setTextColor(Color.BLACK);
		
		cell = barRow2.createCell((100 / 6f), bar.getCity());
		cell.setFont(PDType1Font.HELVETICA);
		cell.setFillColor(Color.CYAN);
		cell.setTextColor(Color.BLACK);
		
		cell = barRow2.createCell((100 / 6f), bar.getCountry());
		cell.setFont(PDType1Font.HELVETICA);
		cell.setFillColor(Color.CYAN);
		cell.setTextColor(Color.BLACK);

		//Create 2 column row
		Row<PDPage> row1 = table.createRow(15f);
		cell = row1.createCell(30, "Start date:");
		cell.setFont(PDType1Font.HELVETICA);

		cell = row1.createCell(70, df.format(startDate));
		cell.setFont(PDType1Font.HELVETICA_OBLIQUE);

		//Create 3 column row
		Row<PDPage> row2 = table.createRow(15f);
		cell = row2.createCell(30, "End date:");
		cell.setFont(PDType1Font.HELVETICA);

		cell = row2.createCell(70, df.format(endDate));
		cell.setFont(PDType1Font.HELVETICA_OBLIQUE);

		//Create Fact header row
		Row<PDPage> orderHeaderRow = table.createRow(15f);

		cell = orderHeaderRow.createCell((100 / 6f), "ID");
		cell.setFont(PDType1Font.HELVETICA);
		cell.setFontSize(6);
		cell.setFillColor(Color.ORANGE);

		cell = orderHeaderRow.createCell((100 / 6f), "TS Create");
		cell.setFillColor(Color.ORANGE);
		cell.setFont(PDType1Font.HELVETICA_OBLIQUE);
		cell.setFontSize(6);
		
		cell = orderHeaderRow.createCell((100 / 6f), "TS Update");
		cell.setFillColor(Color.ORANGE);
		cell.setFont(PDType1Font.HELVETICA_OBLIQUE);
		cell.setFontSize(6);
		
		cell = orderHeaderRow.createCell((100 / 6f), "Drink ID");
		cell.setFillColor(Color.ORANGE);
		cell.setFont(PDType1Font.HELVETICA_OBLIQUE);
		cell.setFontSize(6);
		
		cell = orderHeaderRow.createCell((100 / 6f), "Quantity");
		cell.setFillColor(Color.ORANGE);
		cell.setFont(PDType1Font.HELVETICA_OBLIQUE);
		cell.setFontSize(6);
		
		cell = orderHeaderRow.createCell((100 / 6f), "Status");
		cell.setFillColor(Color.ORANGE);
		cell.setFont(PDType1Font.HELVETICA_OBLIQUE);
		cell.setFontSize(6);

		Row<PDPage> row = table.createRow(15f);
		Iterator<Order> it = orders.iterator();
		while (it.hasNext()) {

			Order order = it.next();
			
			row = table.createRow(10f);
			cell = row.createCell((100 / 6f), new Integer(order.getId()).toString());
			cell.setFont(PDType1Font.HELVETICA);
			cell.setFontSize(6);

			cell = row.createCell((100 / 6f), order.getCreationTS().toString());
			cell.setFont(PDType1Font.HELVETICA);
			cell.setFontSize(6);
			
			cell = row.createCell((100 / 6f), order.getUpdateTS().toString());
			cell.setFont(PDType1Font.HELVETICA);
			cell.setFontSize(6);
			
			cell = row.createCell((100 / 6f), new Integer(order.getDrink().getId()).toString());
			cell.setFont(PDType1Font.HELVETICA);
			cell.setFontSize(6);
			
			cell = row.createCell((100 / 6f), new Integer(order.getQuantity()).toString());
			cell.setFont(PDType1Font.HELVETICA);
			cell.setFontSize(6);
			
			cell = row.createCell((100 / 6f), order.getStatus());
			cell.setFont(PDType1Font.HELVETICA);
			cell.setFontSize(6);
		}

		table.draw();
	}
}
