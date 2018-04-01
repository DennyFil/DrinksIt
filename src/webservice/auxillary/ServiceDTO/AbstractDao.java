package webservice.auxillary.ServiceDTO;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class AbstractDao< T extends Serializable > {

	private Class<T> genericType;

	@Autowired
	public SessionFactory sessionFactory;

	public final void setGenericType( Class< T > typeToSet ){
		this.genericType = typeToSet;
	}

	@SuppressWarnings("unchecked")
	public List<T> FindAll(){
		Session session = sessionFactory.getCurrentSession();
		return session.createQuery( "from " + this.genericType.getSimpleName() ).list();
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
