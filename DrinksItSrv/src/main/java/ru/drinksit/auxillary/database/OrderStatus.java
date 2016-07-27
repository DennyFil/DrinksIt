package ru.drinksit.auxillary.database;

public enum OrderStatus {
	NOT_ACCEPTED ("NOT_ACCEPTED"),
	ACCEPTED ("ACCEPTED"),
	DELIVERED ("DELIVERED");
	
	private final String status;

	OrderStatus(String status) {
        this.status = status;
    }
	
	public String getStatus()
	{
		return status;
	}
}
