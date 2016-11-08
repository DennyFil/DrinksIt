package ru.drinksit.clients;

import java.security.GeneralSecurityException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.util.Base64Utils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import ru.drinksit.auxillary.Bar;
import ru.drinksit.auxillary.Drink;
import ru.drinksit.auxillary.Order;
import ru.drinksit.auxillary.Payment;
import ru.drinksit.auxillary.User;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class RESTClientTools {

	private static final Logger logger = LoggerFactory.getLogger("RESTClientToolsLogger");
	
	private static String adminWS = "DrinksItWSAdmin";
	private static String passwordHashWS = "$2a$10$gFK6sBLv21clvPamex.qTuCUQycR.rhojDXQWrwnZvqYUzh4fa3eO";

	private static String calculateHMAC(String secret, String data) {
		try {
			SecretKeySpec signingKey = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
			Mac mac = Mac.getInstance("HmacSHA256");
			mac.init(signingKey);
			byte[] rawHmac = mac.doFinal(data.getBytes());
			String result = new String(Base64Utils.encodeToString(rawHmac));
			return result;
		} catch (GeneralSecurityException e) {
			throw new IllegalArgumentException();
		}
	}

	/*private static HttpEntity<?> createHttpRequestSimple(String userName, String passwordHash)
	{
		HttpHeaders headers = new HttpHeaders();

		String credsStr = userName + ":" + passwordHash;
		byte [] creds = credsStr.getBytes();
		byte [] credsEnc = Base64.getEncoder().encode (creds);

		headers.add("Authorization", "Basic " + credsEnc);


		HttpEntity<?> httpEntity = new HttpEntity<>(headers);

		return httpEntity;		
	}*/

	public static <T> Object handleAdminRequest(String uri,
			String userName,
			String passwordHash,
			MultiValueMap<String, String> body,
			HttpMethod httpMethod,
			Class<T> type)
	{
		HttpHeaders headers = new HttpHeaders();
		
		JSONObject contentToEncodeJson = new JSONObject();
	
		if (body != null)
		{	
			for (Map.Entry<String,List<String>> e : body.entrySet()) {
				contentToEncodeJson.put(e.getKey(), e.getValue().get(0));
		    }
		}
		
		// Always JSON
		MediaType contentType = MediaType.APPLICATION_FORM_URLENCODED; //MediaType.APPLICATION_JSON; // //
		
		headers.setContentType(contentType);
		
		// Sun, 28 Aug 2016 22:15:13 GMT
		DateFormat df = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z");
		df.setTimeZone(TimeZone.getTimeZone("GMT"));
		
		Date date = new Date();

		long dateL = date.getTime();
		headers.setDate(dateL);

		String contentToEncodeStr = contentToEncodeJson.toString();

		// create signature: method + content md5 + content-type + date + uri
		StringBuilder signature = new StringBuilder();
		signature.append(httpMethod.toString()).append("\n")
		.append(contentToEncodeStr).append("\n")
		.append(contentType.toString()).append("\n")
		.append(df.format(date)).append("\n")
		.append(uri);

		System.out.println(signature);

		String authHeader = userName + ":" + calculateHMAC(passwordHash, signature.toString());

		System.out.println(authHeader);

		headers.add(HttpHeaders.AUTHORIZATION, authHeader);
		
		HttpEntity<?> httpEntity = new HttpEntity<Object>(body, headers);
		
		return sendHttpRequest(uri, httpEntity, httpMethod, type);
	}
	
	public static <T> Object handleUserRequest(String uri,
			MultiValueMap<String, String> body,
			HttpMethod httpMethod,
			Class<T> type)
	{
		HttpHeaders headers = new HttpHeaders();
		
		MediaType contentType = MediaType.APPLICATION_FORM_URLENCODED; // //MediaType.APPLICATION_JSON;//	
		headers.setContentType(contentType);
		
		HttpEntity<?> httpEntity = new HttpEntity<Object>(body, headers);
		
		return sendHttpRequest(uri, httpEntity, httpMethod, type);
	}

	private static <T> Object sendHttpRequest(String uri,
			HttpEntity<?> httpEntity,
			HttpMethod httpMethod,
			Class<T> type)
	{
		RestTemplate restTemplate = new RestTemplate();

		try
		{
			ResponseEntity<T> response = restTemplate.exchange(uri, httpMethod, httpEntity, type);

			return response.getBody();
		}
		catch(Exception e)
		{
			logger.error("Failed to send HTTP request: " + uri);
			e.printStackTrace();
			return null;
		}
	}
	
	public static boolean resetDB(String url)
	{
		String uri_resetDB = url + "admin/resetDB";
		return (boolean) handleAdminRequest(uri_resetDB, adminWS, passwordHashWS, null, HttpMethod.POST, boolean.class);
	}

	public static Order createOrder(String url, int drinkId, int quantity)
	{
		MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();     

		body.add("drinkId", new Integer(drinkId).toString());
		body.add("quantity", new Integer(quantity).toString());

		String url_createOrder = url + "user/createOrder";
		Order order = (Order) handleUserRequest(url_createOrder, body, HttpMethod.POST, Order.class);

		return order;
	}

	public static Payment createPayment(String url, int orderId)
	{
		MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();
		body.add("orderId", new Integer(orderId).toString());

		String uri_createPayment = url + "user/createPayment";

		Payment payment = (Payment) handleUserRequest(uri_createPayment, body, HttpMethod.POST, Payment.class);

		return payment;
	}

	public static boolean checkDrink(String url, int barId, int drinkId, String drinkName, double price, double size) {

		MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();
		body.add("drinkId", new Integer(drinkId).toString());
		body.add("drinkName", drinkName);
		body.add("drinkSize", new Double(size).toString());
		body.add("drinkPrice", new Double(price).toString());
		body.add("barId", new Integer(barId).toString());
		
		Bar barRef = new Bar();
		barRef.setBar_id(barId);
		
		Drink drinkRef = new Drink(barRef, drinkName, price, size);
		drinkRef.setDrink_id(drinkId);

		String uri_checkDrink = url + "user/checkDrink";
		Drink drink = (Drink) handleUserRequest(uri_checkDrink, body, HttpMethod.POST, Drink.class);

		return drink != drinkRef;
	}

	public static Bar createBar(String url, String barName, String address, String  city, String country)
	{
		// Create the request body as a MultiValueMap
		MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();

		body.add("barName", barName);
		body.add("address", address);
		body.add("city", city);
		body.add("country", country);

		String uri_createBar = url + "admin/createBar";

		Bar bar = (Bar) handleAdminRequest(uri_createBar, adminWS, passwordHashWS, body, HttpMethod.POST, Bar.class);

		return bar;
	}

	public static User createUser(String url, int barId, String username, String password)
	{
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String passwordHash = passwordEncoder.encode(password);
		
		// Create the request body as a MultiValueMap
		MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();

		body.add("userName", username);
		body.add("passwordHash", passwordHash);
		body.add("barId", new Integer(barId).toString());

		String uri_createUser = url + "admin/createUser";

		User user = (User) handleAdminRequest(uri_createUser, adminWS, passwordHashWS, body, HttpMethod.POST, User.class);

		return user;
	}

	public static Drink createDrink(String url, int barId, String drinkName, double price, double size) {
		
		// Create the request body as a MultiValueMap
		MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();

		body.add("drinkName", drinkName);
		body.add("price", new Double(price).toString());
		body.add("size", new Double(size).toString());
		body.add("barId", new Integer(barId).toString());

		String uri_createDrink = url + "admin/createDrink";
		
		Drink drink = (Drink) handleAdminRequest(uri_createDrink, adminWS, passwordHashWS, body, HttpMethod.POST, Drink.class);

		return drink;
	}

	public static List<Bar> listBars(String url)
	{
		String requestUri = url + "bars";

		@SuppressWarnings("unchecked")
		Bar [] bars = (Bar []) handleAdminRequest(requestUri, adminWS, passwordHashWS, null, HttpMethod.GET, Bar[].class);

		return Arrays.asList(bars);
	}
}
