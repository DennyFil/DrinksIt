package ru.drinksit.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ru.drinksit.auxillary.database.DrinksItDBManager;
import ru.drinksit.auxillary.database.User;

import org.apache.commons.lang.exception.ExceptionUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class LoginControllerSrv {

	private static final Logger logger = 
			LoggerFactory.getLogger("loginLogger");
	
	@Autowired
	DrinksItDBManager drinksItDBManager;

	@RequestMapping("/logout")
	public String logout(HttpSession session)
	{
		logger.info("LOGOUT: user: " + session.getAttribute("loggedInUser"));
		session.invalidate();
		return "redirect:/login";
	}
	
	@RequestMapping("/")
	public String startupPage(HttpSession session)
	{
		if (session == null || session.getAttribute("loggedInUser") == null) {
			
			return "login";
        }

		return "redirect:/login";
	}
	
	@RequestMapping("/DrinksItDefault")
	public String drinksItDefault(HttpSession session)
	{
		if (session == null || session.getAttribute("loggedInUser") == null) {
			
			return "login";
        }

		return "DrinksItDefault";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(HttpSession session)
	{
		if (session == null || session.getAttribute("loggedInUser") == null) {
			
			return "login";
        }

		return "redirect:/DrinksItDefault";
	}

	@SuppressWarnings("finally")
	@RequestMapping("/checkCreds")
	public ModelAndView checkCredentials(HttpSession session, @RequestBody String body, @RequestParam String userName, @RequestParam String password)
	{
		ModelAndView modelAndViewLogin = new ModelAndView("login");
		
		if (userName == null || userName == "" || password == null || password == "")
		{
			modelAndViewLogin.addObject("message", "Username or password is empty.");
			logger.error("LOGIN: Username or password is empty");
			return modelAndViewLogin;
		}
		
		try
		{
			String passwordHash = drinksItDBManager.getUserPasswordHash(userName);
			
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			
			if (! passwordEncoder.matches(password, passwordHash))
			{
				modelAndViewLogin.addObject("message", "Password is false.");
				logger.error("LOGIN: Password is false for user: " + userName);
				return modelAndViewLogin;
			}
			
			User user = drinksItDBManager.getUserByUsername(userName);
			
			session.setAttribute("loggedInUser", user);
			
			logger.info("LOGIN: successful for user: " + userName);
			return new ModelAndView("DrinksItDefault");
		}
		catch(Exception e)
		{
			modelAndViewLogin.addObject("message", e.getMessage());
			logger.error(ExceptionUtils.getStackTrace(e));
			
			return modelAndViewLogin;
		}
		finally
		{
			
		}
	}
}
