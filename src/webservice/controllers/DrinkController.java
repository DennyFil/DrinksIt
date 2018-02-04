package webservice.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import webservice.auxillary.AuthInfo;
import webservice.auxillary.DTO.Bar;
import webservice.auxillary.DTO.Drink;
import webservice.auxillary.ServiceDTO.DrinkService;

@RestController
@RequestMapping("/drinks")
public class DrinkController extends GenController<Drink> {

	@Autowired
	DrinkService drinkService;
	
	@RequestMapping("/list")
	public ResponseEntity<List<Drink>> GetBarDrinks(HttpServletRequest request, @RequestParam Integer barId) throws Exception {

		logger.debug("GET /drinks for bar " + barId);

		AuthInfo userInfo = getAuthInfo(request);
		if (! authService.IsAuthorized(userInfo))
		{
			logger.debug("GET /drinks: not authorized for " + userInfo.getUserName());
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}

		try {
			List<Drink> drinks = drinkService.GetDrinks(barId);
			
			return ResponseEntity.ok(drinks);
		}
		catch (Exception e){
			logger.debug(e.getMessage());
		}

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
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
	protected Drink createItem(Drink newDrink) throws Exception
	{
		return drinkService.Create(newDrink);
	}
	
	@Override
	protected String getPostLog(Drink drink)
	{
		return "CREATION drink: " + drink.getName() + ", size: " + drink.getSize() + ", price: " + drink.getPrice() + ", bar: " + drink.getBarId();
	}

	@Override
	protected ResponseEntity<List<Drink>> getListItems(AuthInfo userInfo) throws Exception {
		// Drinks from master bar = all drinks
		return ResponseEntity.ok(drinkService.GetDrinks(0));
	}
}