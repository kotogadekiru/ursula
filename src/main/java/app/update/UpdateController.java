package app.update;


import app.util.*;
import spark.*;
import spark.template.freemarker.FreeMarkerEngine;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import com.heroku.sdk.jdbc.DatabaseUrl;


public class UpdateController {

	private static final String MSG = "mensaje";
	private static final String LAS_VERSION_URL = "lasVersionURL";
	private static final String LAS_VERSION_NUMBER = "lasVersionNumber";
	public static Route handleUpdateGet = (Request request, Response response) -> {
		insertTick(request);		
		//System.out.println("imprimiendo update.ftl");
		Map<String, Object> model = new HashMap<>();
		model.put(LAS_VERSION_NUMBER,"0.2.22");
		model.put(LAS_VERSION_URL, "http://bit.ly/2vKg0du");//fuente amazon
		model.put(MSG, "descargar msi 0.2.22x64 para actualizar");
		// return ViewUtil.render(request, model, Path.Template.UPDATE);//SEVERE: ResourceManager : unable to find resource 'update.ftl' in any resource loader.

		FreeMarkerEngine fm= new FreeMarkerEngine();

		return fm.render(new ModelAndView(model, Path.Template.UPDATE));
	};
	
	private static void insertTick(Request request) {
		Connection connection = null;
		try {
			connection = DatabaseUrl.extract().getConnection();
			Statement stmt = connection.createStatement();
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS sessiones (tick timestamp, version varchar(255))");
			String version = "unknown";
			String user = "unknown";
			try{
				version = request.queryParams("VERSION");//http://www.ursulagis.com/update?VERSION=0.2.20
				if(version==null)version = "0.2.19?";
				
				user = request.queryParams("USER");//http://www.ursulagis.com/update?VERSION=0.2.20
				if(user==null)user = "unknown";
			} catch(Exception e){
				System.out.println("version unknown");
			}
			stmt.executeUpdate("INSERT INTO sessiones VALUES (now(),'"+version+" / "+user+"')");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (connection != null) try{connection.close();} catch(SQLException e){}
		}
	}
}
