package webservice.exceptions;
import org.springframework.security.core.AuthenticationException;

public class CustomAuthenticationException extends AuthenticationException {

	public CustomAuthenticationException(String error){
		super(error);
	}
}
