package ru.drinksit.clients;

import ru.drinksit.auxillary.Bar;
import ru.drinksit.auxillary.Drink;
import ru.drinksit.auxillary.Order;
import ru.drinksit.auxillary.Payment;
import ru.drinksit.auxillary.User;
import ru.drinksit.clients.RESTClientTools;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.EncodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

/**
 * Created by dennyfil on 25.06.16.
 */
public class Tester {

    public static void main(String [] args)
    {
        String url = "http://localhost:8080/DrinksItSrv/";

        scanQRCode();
    }

    public static String scanQRCode()
    {
        String qrCodeFilePath = "C:\\Users\\Denny\\DEV\\QR_Code_Test.png";

        String charset = "UTF-8"; // or "ISO-8859-1"
        Map hintMap = new HashMap();
        hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

        try
        {
        	BufferedImage img = null;        	
        	img = ImageIO.read(new File(qrCodeFilePath));
        	
        	LuminanceSource source = new BufferedImageLuminanceSource(img);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
            
            // Scan QR code

            Result data = new QRCodeReader().decode(bitmap, hintMap);
            
            String orderDrinkUrl = data.getText();
            System.out.println(orderDrinkUrl);

            return orderDrinkUrl;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    /*public static boolean sendOrder(int quantity)
    {
        Map<String, String> drinkInfo = new HashMap<String, String>();

        drinkInfo.put("serverUrl", "localhost");
        drinkInfo.put("drinkId", "1");
        drinkInfo.put("drinkName", "Denis's beer");
        drinkInfo.put("drinkSize", "0.5");
        drinkInfo.put("drinkPrice", "5.0");
        drinkInfo.put("barId", "1");

        try
        {
            String serverUrl = drinkInfo.get("serverUrl");
            drinkInfo.remove("serverUrl");

            String url = "http://" + serverUrl + ":8080/DrinksItSrv/";

            boolean drinkStatus = RESTClientTools.checkDrink(url, drinkInfo);

            if (drinkStatus)
            {
                int drinkId = new Integer(drinkInfo.get("drinkId"));
                Order order = RESTClientTools.createOrder(url, drinkId, quantity);
                System.out.println("Order " + order.getOrder_id());
                System.out.println("	" + order.getDrink().getName());
                System.out.println("	" + order.getDrink().getSize());
                System.out.println("	" + order.getDrink().getPrice());
                System.out.println("	" + order.getQuantity());

                /*if (order != null)
                {
                    Payment payment = RESTClientTools.createPayment(url, order.getOrder_id());

                    if (payment != null)
                    {
                        System.out.println("Payment " + payment.getPayment_id());
                        System.out.println("	" + payment.getAmount());
                        return true;
                    }
                    else
                    {
                        System.out.println("Failed to send payment");
                    }
                }
                //else
                //{
                //    System.out.println("Failed to send order");
                //}
            }
            else
            {
                System.out.println("Drink unavailable");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }

        return false;
    }*/
}
