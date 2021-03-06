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

	private static final String LAS_VERSION_URL_VALUE ="https://bit.ly/3nm5d49";//<-0.2.23x32  || 0.2.22x64-> "http://bit.ly/2vKg0du";
	private static final String LAST_VERSION_NUMBER_VALUE = "0.2.26";
	private static final String USER_PARAM = "USER";
	private static final String VERSION_PARAM = "VERSION";
	private static final String MSG_PARAM = "mensaje";
	private static final String LAS_VERSION_URL_PARAM = "lasVersionURL";
	private static final String LAS_VERSION_NUMBER_PARAM = "lasVersionNumber";
	
	private static final String header = "<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js\"></script>\n" + 
			"<script>\n" + 
			"  (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){\n" + 
			"  (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),\n" + 
			"  m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)\n" + 
			"  })(window,document,'script','https://www.google-analytics.com/analytics.js','ga');\n" + 
			"\n" + 
			"  ga('create', 'UA-96140168-1', 'auto');\n" + 
			"  ga('send', 'pageview');\n" + 
			"\n" + 
			"</script>\n" + 
			"<!--cript type='text/javascript'>\n" + 
			"window.__lo_site_id = 82140;\n" + 
			"\n" + 
			"	(function() {\n" + 
			"		var wa = document.createElement('script'); wa.type = 'text/javascript'; wa.async = true;\n" + 
			"		wa.src = 'https://d10lpsik1i8c69.cloudfront.net/w.js';\n" + 
			"		var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(wa, s);\n" + 
			"	  })();\n" + 
			"	</script>\n" + 
			"<script type=\"text/javascript\" src=\"//maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js\">\n" + 
			"</script-->";
	public static Route handleUpdateGet = (Request request, Response response) -> {
		insertTick(request);		
		//System.out.println("imprimiendo update.ftl");
		
		
		Map<String, Object> model = new HashMap<>();
		model.put(LAS_VERSION_NUMBER_PARAM,LAST_VERSION_NUMBER_VALUE);
		model.put(LAS_VERSION_URL_PARAM, LAS_VERSION_URL_VALUE);//fuente amazon
		model.put(MSG_PARAM, "Ud ya tiene la ultima versi&oacute;n instalada disponible");
		// return ViewUtil.render(request, model, Path.Template.UPDATE);//SEVERE: ResourceManager : unable to find resource 'update.ftl' in any resource loader.

		String userVersion = request.queryParams(VERSION_PARAM);
		if(userVersion!=null) {
			Double ver=versionToDouble(userVersion);
			//TODO controlar si la version del usuario es de 32 o 64bites
			if(versionToDouble(LAST_VERSION_NUMBER_VALUE).equals(ver)) {
				model.put(MSG_PARAM, "<HTML>"
						+ "<b>Aviso:</b>"
						+ "<br></b>"
						+"<ul>"
						+"<li>Ud esta ejecutando la ultima version disponible de UrsulaGIS</li>"  
						+"</ul>"        
						+ " </HTML>");//XXX va a webView.getEngine().loadContent(message);
			}else if(ver>= 0.223) {
				model.put(MSG_PARAM, "<HTML>"
						+ "<b>Aviso:</b>"
						+ "<br>Ya esta disponible para descargar la version 0.2.26 de UrsulaGIS que permite abrir las imagenes ndvi en el nuevo formato</b>"
						+"<br>visite <a href=\"www.ursulagis.com/\" rel=\"nofollow\" target=\"_blank\" title=\"W3C HTML validator\">www.ursulagis.com</a> para mas informaci&oacute;n"
						+ " </HTML>");//XXX va a webView.getEngine().loadContent(message);
			} else {//antes de la 0.223 no traia el parametro de la version
				model.put(MSG_PARAM, "Hay una nueva versi&oacute;n disponible para actualizar "+LAST_VERSION_NUMBER_VALUE);
			}
		}
		
		FreeMarkerEngine fm= new FreeMarkerEngine();

		return fm.render(new ModelAndView(model, Path.Template.UPDATE));
	};
	
	public static Double versionToDouble(String ver){
		ver= ver.replace(" dev", "");
		String[] v =ver.split("\\.");
		String ret = v[0]+".";
		for(int i=1;i<v.length;i++){
			ret=ret.concat(v[i]);
		}
		try{
			return Double.parseDouble(ret);
		}catch(Exception e){
			e.printStackTrace();
			return -1.0;
		}
	}
	
	private static void insertTick(Request request) {
		Connection connection = null;
		try {
			connection = DatabaseUrl.extract().getConnection();
			Statement stmt = connection.createStatement();
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS sessiones (tick timestamp, version varchar(255))");
			String version = "unknown";
			String user = "unknown";
			String ip = "unknown";
			try{
				version = request.queryParams(VERSION_PARAM);//http://www.ursulagis.com/update?VERSION=0.2.20
				if(version==null)version = "0.2.19?";
				
				user = request.queryParams(USER_PARAM);//http://www.ursulagis.com/update?VERSION=0.2.20
				if(user==null)user = "unknown";
				
				ip = request.headers("X-FORWARDED-FOR");  
			//	if (ipAddress == null) {  ("X-Forwarded-For");//request.queryParams("IP");//http://www.ursulagis.com/update?VERSION=0.2.20
				if(ip==null){
				//	ip = request.queryParams("fwd");
					//if(ip==null)
						ip = "unknown";
				}
			} catch(Exception e){
				System.out.println("ip unknown");
			}
			stmt.executeUpdate("INSERT INTO sessiones VALUES (now(),'"+version+" / "+user+" @ "+ip+"')");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (connection != null) try{connection.close();} catch(SQLException e){}
		}
	}
}
