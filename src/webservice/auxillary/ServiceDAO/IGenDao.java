package webservice.auxillary.ServiceDAO;

import java.util.List;

import webservice.auxillary.DTO.BaseItem;

public interface IGenDao<T extends BaseItem> {

	List<T> FindAll() throws Exception;
	
	T Create(T newItem) throws Exception;
	
	void Update(T item) throws Exception;
	
	boolean Exists(T item) throws Exception;

	void DeleteById(int entityId) throws Exception;
}