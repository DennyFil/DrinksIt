package webservice.auxillary.ServiceDTO;

import java.io.Serializable;
import org.springframework.stereotype.Repository;

@Repository
public class GenDao<T extends Serializable> extends AbstractDao< T > implements IGenDao<T> {
	
}