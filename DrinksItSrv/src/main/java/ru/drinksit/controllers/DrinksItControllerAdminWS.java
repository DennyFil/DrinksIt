/*
    Author: Denis Filimonov
    Date: 28.08.16
    Class: DrinksItControllerAdminWS
*/

package ru.drinksit.controllers;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ru.drinksit.auxillary.HmacAuthenticationFilter;
import ru.drinksit.auxillary.database.DatabaseManager;

@RestController
public class DrinksItControllerAdminWS {

	private static final Logger logger = LoggerFactory.getLogger("wsControllerLogger");

	@Autowired
	DatabaseManager databaseManager;

	@Autowired
	HmacAuthenticationFilter hmacAuthenticationFilter;

	@RequestMapping("/resetDB")
	public ResponseEntity<Boolean> resetDB(HttpServletRequest request) {
		
		logger.debug("POST /resetDB");
		if (! hmacAuthenticationFilter.filterRequest(request, "{}"))
		{
			logger.debug("POST /resetDB: hmac check failed");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}

		boolean dbResetted = databaseManager.truncateTable("Payment") &&
							databaseManager.truncateTable("Order") &&
							databaseManager.truncateTable("Drink") &&
							databaseManager.truncateTable("User") &&
							databaseManager.truncateTable("Bar") &&
							databaseManager.resetAutoIncr("bars") &&
							databaseManager.resetAutoIncr("orders") &&
							databaseManager.resetAutoIncr("drinks") &&
							databaseManager.resetAutoIncr("payments");

		if (dbResetted) 
		{
			logger.debug("DB resetted");
			return ResponseEntity.ok(dbResetted);
		}
		
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
}
