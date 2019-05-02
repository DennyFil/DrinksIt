package webservice.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import webservice.auxillary.AuthInfo;
import webservice.auxillary.DTO.Drink;
import webservice.auxillary.ServiceDAO.IDrinkService;

@RestController
@RequestMapping("/api/drinks")
public class DrinkController extends GenController<Drink> {

	@Autowired
	IDrinkService drinkService;
	
	@RequestMapping("/list")
	public ResponseEntity<?> GetBarDrinks(HttpServletRequest request, @RequestParam Integer barId) throws Exception {

		try {
			AuthInfo userInfo = authInfoService.getAuthInfo(request);
			
			if (! arService.isBarAdmin(userInfo, barId))
			{
				logger.debug("GET /drinks: no right for " + userInfo.getUserName());
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not allowed to get drinks for bar: " + barId);
			}
			
			return ResponseEntity.ok(drinkService.GetDrinks(barId));
		}
		catch (Exception e){
			logger.debug(e.getMessage());
		}

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to get drinks");
	}

	@Override
	protected boolean hasPostRight(AuthInfo userInfo, Drink newDrink) {
		return arService.isBarAdmin(userInfo, newDrink.getBarId());
	}
	
	@Override
	protected boolean itemExists(Drink newDrink) throws Exception {
		return drinkService.Exists(newDrink);
	}

	@Override
	protected Drink updateItem(Drink newDrink) throws Exception {
		drinkService.Update(newDrink);
		return newDrink;
	}
	
	@Override
	protected void deleteItem(int id) throws Exception {
		drinkService.DeleteById(id);
	}
	
	@Override
	protected Drink createItem(Drink newDrink) throws Exception
	{
		return drinkService.Create(newDrink);
	}
	
	@Override
	protected String getTypeStr()
	{
		return "drink";
	}
}