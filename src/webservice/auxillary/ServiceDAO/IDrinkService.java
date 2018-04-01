package webservice.auxillary.ServiceDAO;

import java.util.List;

import webservice.auxillary.DTO.Drink;

public interface IDrinkService extends IGenDao<Drink> {

	boolean CheckDrink(int drinkId, int barId, String drinkName, double drinkSize, double drinkPrice) throws Exception;
	
	List<Drink> GetDrinks(int barId) throws Exception;
	
	Drink GetDrink(int drinkId) throws Exception;
}
