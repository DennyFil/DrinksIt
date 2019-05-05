package webservice.exceptions;
import org.springframework.security.core.AuthenticationException;

public class CustomAuthenticationException extends AuthenticationException {

	private static final long serialVersionUID = -6961942046803444305L;

	public CustomAuthenticationException(String error) {
		super(error);
	}
}
