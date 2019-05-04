package webservice.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import webservice.auxillary.DTO.GenItem;
import webservice.auxillary.AuthInfo;

public abstract class GenController<T extends GenItem> extends BaseController {
	
	protected abstract String getTypeStr();
	
	protected abstract boolean hasPostRight(AuthInfo userInfo, T newItem);
	
	protected abstract boolean itemExists(T newItem) throws Exception;
	
	protected abstract T createItem(T newItem) throws Exception;
	
	protected abstract T updateItem(T newItem) throws Exception;
	
	protected abstract void deleteItem(int id) throws Exception;

	private String buildLogStr( String action, String itemId, String userName )
	{
		return action + ": " + getTypeStr() + " " + itemId + " by " + userName;
	}
	
	private T handlePostedItem(T newItem,  String userName ) throws Exception
	{
		T item = null;
		String action = "";

		if (itemExists(newItem))
		{
			item = updateItem(newItem);
			action = "UPDATE";
		}
		else
		{
			item = createItem(newItem);
			action = "CREATE";
		}
		
		logger.debug(buildLogStr( action, newItem.getIdStr(), userName ));

		return item;
	}
	
	protected boolean hasDeleteRight(AuthInfo userInfo)
	{
		return arService.checkRight(userInfo, "delete");
	}
	
	@RequestMapping("/delete")
	public ResponseEntity<?> Delete(HttpServletRequest request, @RequestBody Integer id) throws Exception
	{
		try {
			AuthInfo userInfo = authInfoService.getAuthInfo(request);
			
			if ( ! hasDeleteRight(userInfo))
			{
				logger.debug("POST /delete: no delete right for " + userInfo.getUserName());
				return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Not allowed to delete item");
			}
			
			deleteItem(id);
			buildLogStr( "DELETE", id.toString(), userInfo.getUserName() );
			
			return ResponseEntity.ok(id);
		}
		catch (Exception e){
			logger.debug(e.getMessage());
		}
		
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete item");
	}

	@RequestMapping("/post")
	public ResponseEntity<?> Post(HttpServletRequest request, @RequestBody T newItem) throws Exception {

		try {
			AuthInfo userInfo = authInfoService.getAuthInfo(request);
			
			if ( ! hasPostRight(userInfo, newItem))
			{
				logger.debug("POST /post: no post right for " + userInfo.getUserName());
				return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Not allowed to post item");
			}
			
			GenItem item = handlePostedItem(newItem, userInfo.getUserName());
			
			return ResponseEntity.ok(item);
		}
		catch (Exception e){
			logger.debug(e.getMessage());
		}

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to post item");
	}
}
