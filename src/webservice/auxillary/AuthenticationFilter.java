package webservice.auxillary;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.util.UrlPathHelper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Optional;

import webservice.auxillary.DTO.User;
import webservice.auxillary.DTO.UserInfo;
import webservice.auxillary.ServiceDAO.UserService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.stream.Collectors;

public class AuthenticationFilter extends GenericFilterBean {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private AuthInfoService authInfoService;
	
	private AuthenticationManager authenticationManager;

    public AuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        this.authInfoService = new AuthInfoService();
    }
	
	@Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse)response;
        
        try {
        	String resourcePath = new UrlPathHelper().getPathWithinApplication(httpRequest);
        	
        	if (resourcePath.contains("/login") && httpRequest.getMethod().equals("POST")) {
        		
        		ObjectMapper mapper = new ObjectMapper();
        		User userPosted = mapper.readValue(request.getInputStream(), User.class);
    			
        		logger.debug("Trying to login user {} ", userPosted.getUserName());

        		UsernamePasswordAuthenticationToken requestAuthentication = new UsernamePasswordAuthenticationToken(userPosted.getUserName(), userPosted.getPassword());
        		Authentication responseAuthentication = authenticate(requestAuthentication);
        		
        		User authUser = (User)responseAuthentication.getPrincipal();
        		String token = String.valueOf(responseAuthentication.getCredentials());
        		
                httpResponse.setStatus(HttpServletResponse.SC_OK);
                
                String userInfoJsonResponse = new ObjectMapper().writeValueAsString(new UserInfo(authUser, token));
                
                httpResponse.addHeader("Content-Type", "application/json");
                httpResponse.getWriter().print(userInfoJsonResponse);
    			
                return;
        	}
        	else {            
        		AuthInfo userInfo = authInfoService.getAuthInfo(httpRequest);

        		String token = userInfo.getToken();
        		String userName = userInfo.getUserName();
            
                logger.debug("Trying to authenticate user with token: {}");
                
                PreAuthenticatedAuthenticationToken requestAuthentication = new PreAuthenticatedAuthenticationToken(userName, token);        		
                authenticate(requestAuthentication);
            }

            logger.debug("AuthenticationFilter is passing request down the filter chain");
            chain.doFilter(request, response);
			
            return;
            
        } catch (InternalAuthenticationServiceException internalAuthenticationServiceException) {
            SecurityContextHolder.clearContext();
            logger.error("Internal authentication service exception", internalAuthenticationServiceException);
            httpResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (AuthenticationException authenticationException) {
            SecurityContextHolder.clearContext();
            httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, authenticationException.getMessage());
        } catch (Exception e) {
            SecurityContextHolder.clearContext();
            logger.error("Internal authentication service exception", e);
            httpResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } finally {

        }
    }
	
	private Authentication authenticate(Authentication requestAuthentication) {		
				
        Authentication responseAuthentication = authenticationManager.authenticate(requestAuthentication);
        if (responseAuthentication == null || !responseAuthentication.isAuthenticated()) {
            throw new InternalAuthenticationServiceException("Unable to authenticate user with provided credentials");
        }
        
        SecurityContextHolder.getContext().setAuthentication(responseAuthentication);
        
        return responseAuthentication;
	}
}
