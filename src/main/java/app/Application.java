package app;

import app.index.IndexController;
//import app.book.*;
//import app.index.*;
import app.login.*;
import app.update.UpdateController;
import app.user.*;
import app.util.*;
import static spark.Spark.*;
import static spark.debug.DebugScreen.enableDebugScreen;
//import static spark.debug.DebugScreen.*;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import app.user.UserDao;
import app.util.Filters;
import spark.ModelAndView;
import spark.template.freemarker.FreeMarkerEngine;

public class Application {

    // Declare dependencies
  //  public static BookDao bookDao;
    public static UserDao userDao;

    public static void main(String[] args) {
    	System.out.println("ejecutando Application.main()");
    	String port = System.getenv("PORT");
    	if(port==null){
    		System.out.println("PORT enviroment variable not set. Defaulting to 5000");
    		port = "5000";
    	}
    	port(Integer.valueOf(port));
        // Instantiate your dependencies
//        bookDao = new BookDao();
        userDao = new UserDao();

        // Configure Spark
        //port(4567);
        staticFiles.location("/public");
        staticFiles.expireTime(600L);
        //enableDebugScreen();

        // Set up before-filters (called before each get/post)
        before("*",                  Filters.addTrailingSlashes);
        before("*",                  Filters.handleLocaleChange);
        
		get("/", (request, response) -> {
			Map<String, Object> attributes = new HashMap<>();
			attributes.put("title", "Ursula GIS");

			return new ModelAndView(attributes, "index.ftl");
		}, new FreeMarkerEngine());
       
       get(Path.Web.INDEX,          IndexController.serveIndexPage);

        get(Path.Web.LOGIN,          LoginController.serveLoginPage);
        post(Path.Web.LOGIN,         LoginController.handleLoginPost);
        post(Path.Web.LOGOUT,        LoginController.handleLogoutPost);
        
        get(Path.Web.UPDATE,        UpdateController.handleUpdateGet);
        ApplicationExtras.registerExtras();
        get("*",                     ViewUtil.notFound);

        //Set up after-filters (called after each get/post)
        after("*",                   Filters.addGzipHeader);

    }

}