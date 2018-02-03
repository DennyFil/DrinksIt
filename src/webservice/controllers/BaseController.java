package webservice.controllers;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;

import webservice.auxillary.AuthInfo;
import webservice.auxillary.AuthenticationService;
import webservice.auxillary.HashComputor;

public class BaseController {
	
	protected static final Logger logger = LoggerFactory.getLogger("controllersLogger");

	@Autowired
	AuthenticationService authService;
	
	protected AuthInfo getAuthInfo(HttpServletRequest request) throws Exception {

		String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

		if (authHeader == null || authHeader == "") {
			throw new Exception("Empty authentication header");
		}

		try {
			int idxSeparator = authHeader.indexOf(":");

			String userName = authHeader.substring(0, idxSeparator);
			String receivedPassword = authHeader.substring(idxSeparator + 1, authHeader.length());
			String receivedPasswordHash = HashComputor.ComputeSHA256(receivedPassword);

			return new AuthInfo(userName, receivedPasswordHash);
		}
		catch (Exception e)
		{
			throw new Exception("Failed to parse authentication header");
		}
	}
}
