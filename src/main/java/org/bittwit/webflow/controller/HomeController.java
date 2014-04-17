package org.bittwit.webflow.controller;
 
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
 
@Controller
public class HomeController {

    @RequestMapping(value="/", method = RequestMethod.GET)
    public String campaign(ModelMap model, HttpServletRequest request) {
 
        return "index";
    }
 
}