package webservice.auxillary.ServiceDTO;

import java.util.List;

import webservice.auxillary.DTO.Drink;

public interface IDrinkService extends IGenDao<Drink> {

	boolean CheckDrink(int drinkId, int barId, String drinkName, double drinkSize, double drinkPrice) throws Exception;
	
	List<Drink> GetDrinks(int barId) throws Exception;
	
	Drink GetDrink(int drinkId) throws Exception;
	
	Drink Create(Drink newDrink) throws Exception;
	
	void Update(Drink drink) throws Exception;
	
	boolean Exists(Drink newDrink) throws Exception;
}
