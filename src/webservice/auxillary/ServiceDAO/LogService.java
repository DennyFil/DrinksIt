package webservice.auxillary.ServiceDAO;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import webservice.auxillary.DTO.Log;

@Service("LogService")
@Transactional
public class LogService extends GenDao<Log> {

	public LogService() {
		super(Log.class);
	}
}