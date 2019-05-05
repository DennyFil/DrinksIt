package webservice.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import webservice.auxillary.AccessRightsService;
import webservice.auxillary.AuthInfoService;

public class BaseController {
	
	protected static final Logger loggerDB = LoggerFactory.getLogger("DrinksItLogger");
	protected static final Logger loggerConsole = LoggerFactory.getLogger("DrinksItLoggerConsole");
	
	@Autowired
	AccessRightsService arService;
	
	@Autowired
	AuthInfoService authInfoService;
}
