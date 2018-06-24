package webservice.configuration;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import webservice.auxillary.AuthenticationFilter;
import webservice.auxillary.TokenAuthenticationProvider;
import webservice.auxillary.UserNamePasswordAuthenticationProvider;

@Configuration
@EnableWebSecurity
@ComponentScan
public class SecurityConfig extends WebSecurityConfigurerAdapter {
  
    @Autowired
    private TokenAuthenticationProvider tokenAuthenticationProvider;
    
    @Autowired
    private UserNamePasswordAuthenticationProvider userNamePasswordAuthenticationProvider;
 
    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    
    @Autowired
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
  
        auth.authenticationProvider(tokenAuthenticationProvider)
        .authenticationProvider(userNamePasswordAuthenticationProvider);
    }
 
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/api/postOrder");
        web.ignoring().antMatchers("/index.html", "/", "/login", "/*.js", "/*.css");
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	
    	http
    		.httpBasic()
    		.and()
            .authorizeRequests()
            .anyRequest().authenticated()
            .and().csrf().disable();
    	
        http.addFilterBefore(new AuthenticationFilter(authenticationManager()), BasicAuthenticationFilter.class);
    }

    @Bean
    public AuthenticationEntryPoint unauthorizedEntryPoint() {
        return (request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
    }
}