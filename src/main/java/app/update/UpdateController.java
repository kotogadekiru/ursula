package app.update;


import app.util.*;
import spark.*;
import spark.template.freemarker.FreeMarkerEngine;



import java.util.*;


public class UpdateController {

    private static final String MSG = "mensaje";
	private static final String LAS_VERSION_URL = "lasVersionURL";
	private static final String LAS_VERSION_NUMBER = "lasVersionNumber";
	public static Route handleUpdateGet = (Request request, Response response) -> {
		System.out.println("imprimiendo update.ftl");
        Map<String, Object> model = new HashMap<>();
        model.put(LAS_VERSION_NUMBER,"0.2.19");
        model.put(LAS_VERSION_URL, "http://bit.ly/2pGLXSE");
        model.put(MSG, "descargar msi para instalar desde cero");
        
        FreeMarkerEngine fm= new FreeMarkerEngine();
        return fm.render(new ModelAndView(model, Path.Template.UPDATE));
		
    };

}
