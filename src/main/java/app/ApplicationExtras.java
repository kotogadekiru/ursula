package app;
import static spark.Spark.get;
import static spark.Spark.halt;
import static spark.Spark.post;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletException;
import javax.servlet.http.Part;

import org.postgis.Geometry;
import org.postgis.LinearRing;
import org.postgis.PGgeometry;
import org.postgis.Point;
import org.postgis.Polygon;

import com.heroku.sdk.jdbc.DatabaseUrl;

import models.config.Establecimiento;
import models.config.Lote;
import spark.ModelAndView;
import spark.Request;
import spark.template.freemarker.FreeMarkerEngine;
import utils.DAH;

public class ApplicationExtras {
	public static void registerExtras() {
		registerZipServices();
		registerNdviServices();
		
		get("/jpa/", (req, res) -> {
			Connection connection = null;
			Map<String, Object> attributes = new HashMap<>();
			try {

				ArrayList<String> output = new ArrayList<String>();

				Establecimiento e = new Establecimiento("La Ursula");
				DAH.save(e);
				output.add( "establecimiento: " + e);
				Lote l = new Lote(e, "lote1");
				output.add( "lote: " + l);
				DAH.save(l);
				List<Lote> lotes = DAH.getAllLotes();
				output.add( "lotes: " + lotes);
				output.add( "cantidad de lotes: " + lotes.size());
				for(int i =0;i<lotes.size();i++){
					Lote loteFor = lotes.get(i);
					output.add( "lote "+i+": " + loteFor.getNombre());
				}

				attributes.put("message", "everithing ok! ");
				attributes.put("results", output);
				return new ModelAndView(attributes, "db.ftl");
			} catch (Exception e) {
				attributes.put("message", "There was an error: " + e);
				return new ModelAndView(attributes, "error.ftl");
			} finally {
				if (connection != null) try{connection.close();} catch(SQLException e){}
			}
		}, new FreeMarkerEngine());

		get("/sessiones/", (req, res) -> {
			Connection connection = null;
			Map<String, Object> attributes = new HashMap<>();
			try {
				connection = DatabaseUrl.extract().getConnection();
				Statement stmt = connection.createStatement();
				stmt.executeUpdate("CREATE TABLE IF NOT EXISTS sessiones (tick timestamp, version varchar(255))");
				
				SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");//.format(myTimestamp);
				
				ResultSet countRS = stmt.executeQuery("SELECT COUNT(*) FROM sessiones");
				 countRS.next();
				 int size = countRS.getInt(1);
				ResultSet rs = stmt.executeQuery("SELECT tick,version FROM sessiones ORDER BY tick DESC");
				ArrayList<String> output = new ArrayList<String>();
				int i=0;
				Timestamp tick = null;
				int millisIn3H = 3*60*60*1000;
				while (rs.next()) {				
					tick = rs.getTimestamp("tick");
					tick.setTime(tick.getTime()-millisIn3H);
					output.add( size-i+": " + df.format(tick)+" version: "+rs.getString("version"));
					i++;
				}
				attributes.put("message", "everithing ok! ");
				attributes.put("results", output);
				return new ModelAndView(attributes, "db.ftl");
			} catch (Exception e) {
				attributes.put("message", "There was an error: " + e);
				e.printStackTrace();
				return new ModelAndView(attributes, "error.ftl");
			} finally {
				if (connection != null) try{connection.close();} catch(SQLException e){}
			}
		}, new FreeMarkerEngine());

		get("/gisJdbc/", (req, res) -> {
			return gisJdbc();
		}, new FreeMarkerEngine());
	}
	
	private static void registerNdviServices() {
		get("/api/ndvi/v4/SR/", (request, finalrresponse) -> "Pleas make a POST request!");
		post("/api/ndvi/v4/SR/", (request, finalrresponse) -> {
			//request.raw();
			String reqBdy = request.body();
			/*
			 {
    			"polygons": "[[[[-61.9146741,-33.6600295],[-61.9144856,-33.66025186],[-61.9051189,-33.67223672],[-61.9162476,-33.67825261],[-61.9171016,-33.67814203],[-61.9236927,-33.6698014],[-61.9237653,-33.66954039],[-61.9242583,-33.66878465],[-61.9240055,-33.66840019],[-61.9257235,-33.66634525],[-61.9219305,-33.66388454],[-61.9147469,-33.66000658],[-61.9146741,-33.6600295],[-61.9146741,-33.6600295]]]]",
    			"end": "2018-03-29",
    			"begin": "2018-03-01",
    			"token": "pumaToken4D2"
				}
			 */
			//TODO insertar en la base de datos los requests realizados
			
			System.out.println(reqBdy);
			
			System.out.println("redirecting /ndvi_v4_SR to gee-api-helper...");
			
			URL url = new URL("http://gee-api-helper.herokuapp.com/ndvi_v4_SR");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST");
			//con.setRequestMethod("GET");
			con.setRequestProperty("Content-Type", "application/json; utf-8");
			con.setRequestProperty("Accept", "application/json");
			con.setDoOutput(true);
			
			try(OutputStream os = con.getOutputStream()) {
			    byte[] input = reqBdy.getBytes("utf-8");
			    os.write(input, 0, input.length);           
			}
			
			StringBuilder response = new StringBuilder();
			InputStream is = con.getInputStream();//aca se ejecuta el request
			try(BufferedReader br = new BufferedReader(new InputStreamReader(is, "utf-8"))) {
					    
					    String responseLine = null;
					    while ((responseLine = br.readLine()) != null) {
					        response.append(responseLine.trim());
					    }
					    System.out.println(response.toString());
					    br.close();
					    con.disconnect();
					}
		
		
//			BufferedReader in = new BufferedReader(  new InputStreamReader(is));
//			String inputLine;
//			StringBuffer content = new StringBuffer();
//			while ((inputLine = in.readLine()) != null) {
//				content.append(inputLine);
//			}
//			in.close();
//			con.disconnect();
			
			String body = response.toString();
			System.out.println(body);
//			
			//OutputStreamWriter finalWriter = new OutputStreamWriter(finalrresponse.raw().getOutputStream());
		//	finalWriter.write(body);
			
				
			return body;
		});
	}

	private static void registerZipServices() {
		File uploadDir = new File("upload");
		uploadDir.mkdir(); // create the upload directory if it doesn't exist
		//staticFiles.externalLocation("upload");

		post("/uploadZip", (request, response) -> {
			Path tempFile = Files.createTempFile(uploadDir.toPath(), "", ".zip");

			request.attribute("org.eclipse.multipartConfig", new MultipartConfigElement("/temp"));
			//request.raw().setAttribute("org.eclipse.multipartConfig", new MultipartConfigElement("/temp"));
			try (InputStream input = request.raw().getPart("uploaded_file").getInputStream()) { // getPart needs to use same "name" as input field in form
				Files.copy(input, tempFile, StandardCopyOption.REPLACE_EXISTING);
			}

			logInfo(request, tempFile);
			return "<h1>You uploaded this image:<h1><img src='" + tempFile.getFileName() + "'>";

		});



		get("/uploadZip",(request,response)->
		"<form method='post' enctype='multipart/form-data'>" // note the enctype
		+ "    <input type='file' name='uploaded_file' accept='.zip, *.rar'>" // make sure to call getPart using the same "name" in the post
		+ "    <button>Upload picture</button>"
		+ "</form>"
				);
		
		get("/downloadZip",(request, response) -> {
			 File file = new File("MYFILE");
			 response.raw().setContentType("application/octet-stream");
			 response.raw().setHeader("Content-Disposition","attachment; filename="+file.getName()+".zip");
			    try {

			        try(ZipOutputStream zipOutputStream = new ZipOutputStream(new BufferedOutputStream(response.raw().getOutputStream()));
			        BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file)))
			        {
			            ZipEntry zipEntry = new ZipEntry(file.getName());

			            zipOutputStream.putNextEntry(zipEntry);
			            byte[] buffer = new byte[1024];
			            int len;
			            while ((len = bufferedInputStream.read(buffer)) > 0) {
			                zipOutputStream.write(buffer,0,len);
			            }
			        }
			    } catch (Exception e) {
			        halt(405,"server error");
			    }

			    return null;
		});
	}
	
	  // methods used for logging
    private static void logInfo(Request req, Path tempFile) throws IOException, ServletException {
        System.out.println("Uploaded file '" + getFileName(req.raw().getPart("uploaded_file")) + "' saved as '" + tempFile.toAbsolutePath() + "'");
    }

    private static String getFileName(Part part) {
        for (String cd : part.getHeader("content-disposition").split(";")) {
            if (cd.trim().startsWith("filename")) {
                return cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }

	private static ModelAndView gisJdbc() {
		Connection conn = null;
		Map<String, Object> attributes = new HashMap<>();
		try {
			conn = DatabaseUrl.extract().getConnection();

			/* 
			 * Add the geometry types to the connection. Note that you 
			 * must cast the connection to the pgsql-specific connection 
			 * implementation before calling the addDataType() method. 
			 */
		
			((org.postgresql.jdbc4.Jdbc4Connection)conn).addDataType("geometry","org.postgis.PGgeometry");
			((org.postgresql.jdbc4.Jdbc4Connection)conn).addDataType("box3d","org.postgis.PGbox3d");
			/* 
			 * Create a statement and execute a select query. 
			 */ 
			Statement s = conn.createStatement(); 
			ResultSet r = s.executeQuery("select ST_AsText(geom) as geom,id from geomtable"); //geomtable no existe
			ArrayList<String> output = new ArrayList<String>();
			while( r.next() ) { 
				/* 
				 * Retrieve the geometry as an object then cast it to the geometry type. 
				 * Print things out. 
				 */ 
				PGgeometry geom = (PGgeometry)r.getObject(1); 

				int id = r.getInt(2); 
				output.add( "Row " + id + ":"+geom.toString());
				//  PGgeometry geom = (PGgeometry)r.getObject(1); 

				if( geom.getGeoType() == Geometry.POLYGON ) { 
					Polygon pl = (Polygon)geom.getGeometry(); 
					for( int fIndex = 0; fIndex < pl.numRings(); fIndex++) { 
						LinearRing rng = pl.getRing(fIndex); 
						System.out.println("Ring: " + fIndex); 
						for( int p = 0; p < rng.numPoints(); p++ ) { 
							Point pt = rng.getPoint(p); 
							System.out.println("Point: " + p);
							System.out.println(pt.toString()); 
						} 
					} 
				}
			} 
			s.close(); 
			conn.close(); 

			attributes.put("message", "everithing ok!");
			attributes.put("results", output);
			return new ModelAndView(attributes, "db.ftl");
		} catch (Exception e) {
			attributes.put("message", "There was an error: " + e);
			return new ModelAndView(attributes, "error.ftl");
		} finally {
			if (conn != null) try{conn.close();} catch(SQLException e){}
		}
	}




}
