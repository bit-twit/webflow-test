package org.bittwit.webflow.controller;
 
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
 
@Controller
public class HomeController {

    private static Map<String, String> urls = new HashMap<String, String>();

    static {
        urls.put("/rms-campaign", "/rms/rms-campaign");
        urls.put("/gws-campaign", "/gws/gws-campaign");
    }

    @RequestMapping(value="/", method = RequestMethod.GET)
    public String campaign(ModelMap model, HttpServletRequest request) {
 
        //Spring uses InternalResourceViewResolver and return back index.jsp
        return "index";
    }

    @RequestMapping(value="/welcome", method = RequestMethod.GET)
    public String welcome(ModelMap model) {
 
        model.addAttribute("message", "Maven Web Project + Spring 3 MVC - welcome()");
 
        //Spring uses InternalResourceViewResolver and return back index.jsp
        return "index";
 
    }
 
    @RequestMapping(value="/welcome/{name}", method = RequestMethod.GET)
    public String welcomeName(@PathVariable String name, ModelMap model) {
 
        model.addAttribute("message", "Maven Web Project + Spring Webflow - " + name);
        return "index";
 
    }
 
}