package webservice.auxillary.ServiceDAO;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import webservice.auxillary.DTO.GenItem;

@Repository
@Transactional
public abstract class GenDao<T extends GenItem> implements IGenDao<T> {
	
	private Class<T> genericType;

	@Autowired
	public SessionFactory sessionFactory;

	public final void setGenericType( Class< T > typeToSet ){
		this.genericType = typeToSet;
	}
	
	public boolean Exists(T newItem) throws Exception
	{
		Session session = sessionFactory.getCurrentSession();

		T item = session.get(genericType, newItem.getId());

		return item != null;
	}

	@SuppressWarnings("unchecked")
	public List<T> FindAll() throws Exception{
		Session session = sessionFactory.getCurrentSession();
		return session.createQuery( "from " + this.genericType.getSimpleName() ).list();
	}

	public T Create(T newItem) throws Exception
	{
		Session session = sessionFactory.getCurrentSession();

		session.save(newItem);

		return newItem;
	}
	
	public void Update(T item) throws Exception
	{
		Session session = sessionFactory.getCurrentSession();

		session.update(item);
	}
	
	public void DeleteById(int entityId) throws Exception
	{
		Session session = sessionFactory.getCurrentSession();

		T item = session.get(genericType, entityId);

		if (item != null) {

			session.delete(item);
		}
		else {
			throw new Exception("DELETE item failed");
		}
	}
}