package com.epam.pwt.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MyErrorController implements ErrorController{
	
	
	@RequestMapping(value = "/error", method = RequestMethod.GET)
    public ModelAndView renderErrorPage(HttpServletRequest httpRequest) {
        
        ModelAndView errorPage = new ModelAndView("errorPage");
        String errorMsg = "";
        int httpErrorCode = getErrorCode(httpRequest);

        switch (httpErrorCode) {
            case 400: {
                errorMsg = "Http Error Code: 400. Bad Request";
                break;
            }
            case 401: {
                errorMsg = "Http Error Code: 401. Unauthorized";
                break;
            }
            case 404: {
                errorMsg = "Http Error Code: 404. Resource not found";
                break;
            }
            case 405:{
            	errorMsg = "Http Error Code: 405. Method Type Not Available";
            	break;
            }
            case 500: {
                errorMsg = "Http Error Code: 500. Internal Server Error";
                break;
            }
        }
        errorPage.addObject("errorMessage", errorMsg);
        return errorPage;
    }
    

	private int getErrorCode(HttpServletRequest httpRequest) {
		
		return (Integer) httpRequest
		          .getAttribute("javax.servlet.error.status_code");
		
	}

}
