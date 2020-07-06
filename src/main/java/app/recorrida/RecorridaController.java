package app.recorrida;

import app.util.*;
import app.util.StandardResponse.StatusResponse;
import models.config.Recorrida;
import spark.*;
import utils.DAH;

import java.util.*;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class RecorridaController {
	private static final Logger logger = LoggerFactory.getLogger(RecorridaController.class);
	
	/**
	 * metodo que devuelve en formato json los nombres de las recorridas disponibles
	 */
	public static Route getRecorridas =  (Request request, Response response) -> {
	    response.type("application/json");
	    logger.info("devolvieno todas las recorridas");
	   
	    String ret =new Gson().toJson(
	  	      new StandardResponse(StatusResponse.SUCCESS,new Gson()
	  		        .toJsonTree(DAH.getAllRecorridas()))); 
	    logger.info("response "+ret);
	    return ret;
	    //return  DAH.getAllRecorridas().stream().map(Object::toString).collect(Collectors.joining(", "));
	};
	
	public static Route getRecorridaById=(Request request, Response response) -> {
	    response.type("application/json");
	    return new Gson().toJson(
	      new StandardResponse(StatusResponse.SUCCESS,new Gson()
	        .toJsonTree( 
	        		DAH.em().find(Recorrida.class,Long.valueOf(request.params(":id")))
	        		)));
	};
	
	public static Route getRecorridaByName=(Request request, Response response) -> {
	    response.type("application/json");
	    logger.info("devolviendo una recorrida con nombre ",request.params(":name"));
	    try {
	    return new Gson().toJson(
	      new StandardResponse(StatusResponse.SUCCESS,new Gson()
	        .toJsonTree( DAH.getRecorridaByName(request.params(":name")))));
	    }catch(Exception e) {
	    	return new Gson().toJson(
	    		      new StandardResponse(StatusResponse.ERROR,""));
	    }
	};
	
	public static Route editRecorrida =	(Request request, Response response) -> {
	    response.type("application/json");
	    Recorrida toEdit = new Gson().fromJson(request.body(), Recorrida.class);
	    Recorrida editedRecorrida = (Recorrida) DAH.edit(toEdit);//userService.editUser(toEdit);
	             
	    if (editedRecorrida != null) {
	        return new Gson().toJson(
	          new StandardResponse(StatusResponse.SUCCESS,new Gson()
	            .toJsonTree(editedRecorrida)));
	    } else {
	        return new Gson().toJson(
	          new StandardResponse(StatusResponse.ERROR,new Gson()
	            .toJson("Recorrida not found or error in edit")));
	    }
	};
	
//	public static Route getRecorrida=(Request request, Response response) -> {
//		String name = request.params(":name");
//		Recorrida recorrida = DAH.getRecorrida(name);
//		
//		GsonBuilder builder = new GsonBuilder();
//		Gson gson = builder.create();
//		
//		String ret = gson.toJson(recorrida,Recorrida.class);
//		return ret;
//		
//	};

	public static Route insertRecorrida=(Request request, Response response) -> {
		response.type("application/json");
		Recorrida recorrida = new Gson().fromJson(request.body(), Recorrida.class);
		DAH.save(recorrida);
	 
	    return new Gson()
	      .toJson(new StandardResponse(StatusResponse.SUCCESS));
		
		
//		GsonBuilder builder = new GsonBuilder();
//		Gson gson = builder.create();
//		
//		Recorrida recorrida = gson.fromJson(request.body(), Recorrida.class);
//		DAH.save(recorrida);
//		String ret = gson.toJson(recorrida,Recorrida.class);
//		return ret;
	};

	public static Route deleteRecorrida=(Request request, Response response) -> {
		    response.type("application/json");
		    DAH.remove(DAH.em().find(Recorrida.class, request.params(":id")));
		    
		    return new Gson().toJson(
		      new StandardResponse(StatusResponse.SUCCESS, "user deleted"));
	};
	
	public static Route options=(Request request, Response response) -> {
        response.type("application/json");
        return new Gson().toJson(
          new StandardResponse(StatusResponse.SUCCESS, 
            (DAH.recorridaExists( request.params(":id"))) ? "Recorrida exists" : "Recorrida does not exists" ));
    };
}
