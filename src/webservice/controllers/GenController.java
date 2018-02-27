package webservice.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import webservice.auxillary.AccessRightsService;
import webservice.auxillary.DTO.BaseItem;
import webservice.auxillary.AuthInfo;

public abstract class GenController<T extends BaseItem> extends BaseController {
	
	protected abstract String getPostLog(T item);
	
	protected abstract boolean hasPostRight(AuthInfo userInfo, T newItem);
	
	protected abstract boolean itemExists(T newItem) throws Exception;
	
	protected abstract T createItem(T newItem) throws Exception;
	
	protected abstract T updateItem(T newItem) throws Exception;
	
	protected abstract ResponseEntity<List<T>> getListItems(AuthInfo userInfo) throws Exception;
	
	private T handlePostedItem(T newItem) throws Exception
	{
		if (itemExists(newItem))
		{
			return updateItem(newItem);
		}
		
		return createItem(newItem);
	}

	@RequestMapping("/post")
	public ResponseEntity Post(HttpServletRequest request, @RequestBody T newItem) throws Exception {

		AuthInfo userInfo = getAuthInfo(request);
		if (! authService.IsAuthorized(userInfo))
		{
			logger.debug("POST /post: not authorized for " + userInfo.getUserName());
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not authorized");
		}
		
		if ( ! hasPostRight(userInfo, newItem))
		{
			logger.debug("POST /post: no post right for " + userInfo.getUserName());
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Not allowed post item");
		}
		
		try {
			BaseItem item = handlePostedItem(newItem);
			
			logger.debug("POST /post " + newItem.getIdStr());
			
			String logStr = getPostLog(newItem);
			logger.debug(logStr);
			
			return ResponseEntity.ok(item);
		}
		catch (Exception e){
			logger.debug(e.getMessage());
		}

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to post item");
	}
	
	protected ResponseEntity list(HttpServletRequest request) throws Exception {

		AuthInfo userInfo = getAuthInfo(request);
		if (! authService.IsAuthorized(userInfo))
		{
			logger.debug("GET /list: not authorized for " + userInfo.getUserName());
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not authorized");
		}

		try {			
			return getListItems(userInfo);
		}
		catch (Exception e){
			logger.debug(e.getMessage());
		}

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to list items");
	}
}
