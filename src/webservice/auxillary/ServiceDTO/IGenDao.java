package webservice.auxillary.ServiceDTO;

import java.io.Serializable;
import java.util.List;

public interface IGenDao<T extends Serializable> {

	List<T> FindAll();

	void DeleteById(int entityId) throws Exception;
}