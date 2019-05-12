package webservice.controllers;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import webservice.auxillary.AccessRightsService;
import webservice.auxillary.AuthInfoService;
import webservice.auxillary.DTO.Log;
import webservice.auxillary.DTO.LogAction;
import webservice.auxillary.ServiceDAO.LogService;

public class BaseController {
	
	protected static final Logger loggerConsole = LoggerFactory.getLogger("DrinksItLoggerConsole");
	
	@Autowired
	protected AccessRightsService arService;
	
	@Autowired
	protected AuthInfoService authInfoService;

	@Autowired
	protected LogService logService;

	public void AddLog(int loggerId, LogAction action, String message) {

		Log newLog = new Log(new Date(), loggerId, action, message);
		try {
			logService.Create(newLog);
		} catch (Exception e) {
			loggerConsole.debug(e.getMessage());
		}
	}
}
