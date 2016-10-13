import java.sql.*;
import java.util.HashMap;
import java.util.ArrayList;

import java.util.List;
import java.util.Map;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.nio.file.*;
import java.util.zip.ZipEntry; 
import java.util.zip.ZipFile; 
import java.util.zip.ZipOutputStream; 


import static spark.Spark.*;
import spark.template.freemarker.FreeMarkerEngine;
import spark.ModelAndView;

import spark.Request;
import utils.DAH;

import static spark.Spark.get;

import com.heroku.sdk.jdbc.DatabaseUrl;
import static javax.measure.unit.SI.KILOGRAM;


import javax.measure.quantity.Mass;

import org.jscience.physics.model.RelativisticModel;
import org.jscience.physics.amount.Amount;
import org.postgis.*;

import models.config.*;

public class Main {

	public static void main(String[] args) {

		port(Integer.valueOf(System.getenv("PORT")));
		//port(Integer.valueOf("8080"));
		staticFileLocation("/public");

		//get("/hello", (req, res) -> "Hello World");
		/*

	    get("/hello", (req, res) -> {
      RelativisticModel.select();
      Amount<Mass> m = Amount.valueOf("12 GeV").to(KILOGRAM);
      return "E=mc^2: 12 GeV = " + m.toString();
    });

		 */

		get("/hello", (req, res) -> {
			RelativisticModel.select();

			String energy = System.getenv().get("ENERGY");
			if(energy == null){
				energy = "22 GeV";
			}

			Amount<Mass> m = Amount.valueOf(energy).to(KILOGRAM);
			//return "E=mc^2: " + energy + " = " + m.toString();
			return "hello Ursula GIS actualizado";
		});

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
		+ "    <input type='file' name='uploaded_file' accept='.zip'>" // make sure to call getPart using the same "name" in the post
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

		get("/", (request, response) -> {
			Map<String, Object> attributes = new HashMap<>();
			attributes.put("message", "Ursula GIS!");

			return new ModelAndView(attributes, "index.ftl");
		}, new FreeMarkerEngine());


		get("/harvestShp", (request, response) -> {
			Map<String, Object> attributes = new HashMap<>();
			attributes.put("message", "Ursula !");

			return new ModelAndView(attributes, "index.ftl");
		}, new FreeMarkerEngine());

		get("/jpa", (req, res) -> {
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


				attributes.put("results", output);
				return new ModelAndView(attributes, "db.ftl");
			} catch (Exception e) {
				attributes.put("message", "There was an error: " + e);
				return new ModelAndView(attributes, "error.ftl");
			} finally {
				if (connection != null) try{connection.close();} catch(SQLException e){}
			}
		}, new FreeMarkerEngine());

		get("/jdbc", (req, res) -> {
			Connection connection = null;
			Map<String, Object> attributes = new HashMap<>();
			try {
				connection = DatabaseUrl.extract().getConnection();
				Statement stmt = connection.createStatement();
				stmt.executeUpdate("CREATE TABLE IF NOT EXISTS ticks (tick timestamp)");
				stmt.executeUpdate("INSERT INTO ticks VALUES (now())");
				ResultSet rs = stmt.executeQuery("SELECT tick FROM ticks");
				ArrayList<String> output = new ArrayList<String>();
				while (rs.next()) {
					output.add( "Read from DB: " + rs.getTimestamp("tick"));
				}

				attributes.put("results", output);
				return new ModelAndView(attributes, "db.ftl");
			} catch (Exception e) {
				attributes.put("message", "There was an error: " + e);
				return new ModelAndView(attributes, "error.ftl");
			} finally {
				if (connection != null) try{connection.close();} catch(SQLException e){}
			}
		}, new FreeMarkerEngine());

		get("/gisJdbc", (req, res) -> {
			return gisJdbc();
		}, new FreeMarkerEngine());
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
			((org.postgresql.Connection)conn).addDataType("geometry","org.postgis.PGgeometry");
			((org.postgresql.Connection)conn).addDataType("box3d","org.postgis.PGbox3d");
			/* 
			 * Create a statement and execute a select query. 
			 */ 
			Statement s = conn.createStatement(); 
			ResultSet r = s.executeQuery("select ST_AsText(geom) as geom,id from geomtable"); 
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
