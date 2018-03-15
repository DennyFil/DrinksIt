package webservice.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import webservice.auxillary.AccessRightsService;
import webservice.auxillary.AuthInfoService;

public class BaseController {
	
	protected static final Logger logger = LoggerFactory.getLogger("controllersLogger");
	
	@Autowired
	AccessRightsService arService;
	
	@Autowired
	AuthInfoService authInfoService;
}
