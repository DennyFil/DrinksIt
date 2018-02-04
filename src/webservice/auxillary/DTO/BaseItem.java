package webservice.auxillary.DTO;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BaseItem {

	public abstract String getIdStr();
}
