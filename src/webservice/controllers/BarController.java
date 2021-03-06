package webservice.controllers;

import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import webservice.auxillary.AuthInfo;
import webservice.auxillary.DTO.Bar;
import webservice.auxillary.ServiceDAO.BarService;

@RestController
@RequestMapping("/api/bars")
public class BarController extends GenController<Bar> {

	@Autowired
	private BarService barService;

	@RequestMapping("/list")
	public ResponseEntity<?> GetBars(HttpServletRequest request) throws Exception {

		try {
			AuthInfo userInfo = authInfoService.getAuthInfo(request);
			
			// All bars returned if user has list right
			if ( arService.checkRight(userInfo, "list"))
			{
				return ResponseEntity.ok(barService.FindAll());
			}
			else {
				// Only return bar related to current user
				Bar bar = barService.GetBar(userInfo.getUserName());
				return ResponseEntity.ok(new ArrayList<Bar> ( Arrays.asList(bar) ));
			}
		}
		catch (Exception e) {
			loggerConsole.debug(e.getMessage());
		}

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to get bars");
	}

	@Override
	protected boolean hasPostRight(AuthInfo userInfo, Bar newBar) {
		return arService.checkRight(userInfo, "create");
	}

	@Override
	protected boolean itemExists(Bar newBar) throws Exception {
		return barService.Exists(newBar);
	}

	@Override
	protected Bar updateItem(Bar newBar) throws Exception {
		barService.Update(newBar);
		return newBar;
	}
	
	@Override
	protected void deleteItem(int id) throws Exception {
		barService.DeleteById(id);
	}

	@Override
	protected Bar createItem(Bar newBar) throws Exception {
		return barService.Create(newBar);
	}

	@Override
	protected String getTypeStr() {
		return "bar";
	}
}