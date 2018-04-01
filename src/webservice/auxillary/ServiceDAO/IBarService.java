package webservice.auxillary.ServiceDAO;

import webservice.auxillary.DTO.Bar;

public interface IBarService extends IGenDao<Bar> {

	Bar GetBar(String userName) throws Exception;
}
