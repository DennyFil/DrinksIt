package webservice.auxillary.ServiceDTO;

import webservice.auxillary.DTO.Bar;

public interface IBarService extends IGenDao<Bar> {

	Bar GetBar(String userName) throws Exception;
	
	Bar Create(Bar newBar) throws Exception;
	
	void Update(Bar bar) throws Exception;
	
	boolean Exists(Bar newBar) throws Exception;
}
