package webservice.controllers;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.http.HttpHeaders;

import webservice.auxillary.AccessRightsService;
import webservice.auxillary.HashComputor;
import webservice.auxillary.AutoInfo;

public class GenController {

	protected AccessRightsService arService = new AccessRightsService();
	
	protected AutoInfo getAuthInfo(HttpServletRequest request) throws Exception {

		String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

		if (authHeader == null || authHeader == "") {
			throw new Exception("Empty authentication header");
		}

		try {
			int idxSeparator = authHeader.indexOf(":");

			String userName = authHeader.substring(0, idxSeparator);
			String receivedPassword = authHeader.substring(idxSeparator + 1, authHeader.length());
			String receivedPasswordHash = HashComputor.ComputeSHA256(receivedPassword);

			return new AutoInfo(userName, receivedPasswordHash);
		}
		catch (Exception e)
		{
			throw new Exception("Failed to parse authentication header");
		}
	}
}
