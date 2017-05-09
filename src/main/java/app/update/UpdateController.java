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
		//System.out.println("imprimiendo update.ftl");
        Map<String, Object> model = new HashMap<>();
        model.put(LAS_VERSION_NUMBER,"0.2.18");
        model.put(LAS_VERSION_URL, "http://bit.ly/2pGLXSE");//fuente amazon
        model.put(MSG, "descargar jar para actualizar");
        
        FreeMarkerEngine fm= new FreeMarkerEngine();
        return fm.render(new ModelAndView(model, Path.Template.UPDATE));
		
    };

}
