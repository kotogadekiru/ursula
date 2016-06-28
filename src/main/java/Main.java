import java.sql.*;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.net.URI;
import java.net.URISyntaxException;

import static spark.Spark.*;
import spark.template.freemarker.FreeMarkerEngine;
import spark.ModelAndView;
import utils.DAH;
import static spark.Spark.get;

import com.heroku.sdk.jdbc.DatabaseUrl;

import static javax.measure.unit.SI.KILOGRAM;

import javax.measure.quantity.Mass;

import org.jscience.physics.model.RelativisticModel;
import org.jscience.physics.amount.Amount;

import models.config.*;

public class Main {

  public static void main(String[] args) {

    port(Integer.valueOf(System.getenv("PORT")));
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
      return "hello Ursula GIS";
    });

    get("/", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("message", "Ursula GIS!");

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

  }

}
