package org.mat.nounou.servlets;

import org.eclipse.persistence.config.PersistenceUnitProperties;
import org.mat.nounou.util.HerokuURLAnalyser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.heroku.sdk.jdbc.DatabaseUrl;

import javax.naming.Context;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import java.util.HashMap;
import java.util.Map;

/**
 * Initialize EntityManager Factory
 * For Heroku, try to find the environment property DATABASE_URL, and transform
 * it into a valid jdbc URL to initialize properly the DB.
 *
 * @author mlecoutre
 */

/**
  private static final Logger logger = LoggerFactory.getLogger(AppointmentService.class);

    @GET
    public List<AppointmentVO> get() {
        logger.debug("Appointment service");
        EntityManager em = EntityManagerLoaderListener.createEntityManager();
        TypedQuery<Appointment> query = em.createQuery("FROM Appointment", Appointment.class);
        List<AppointmentVO> apps = new ArrayList<AppointmentVO>();
        try {
            query.setMaxResults(Constants.MAX_RESULT);
            List<Appointment> appointments = query.getResultList();
            for (Appointment a : appointments) {
                AppointmentVO appVo = populateAppointmentVO(a);
                apps.add(appVo);
            }
        } catch (NoResultException nre) {
            logger.error("No appointment at this time");
        } finally {
            em.close();
        }
        return apps;
    }

 */
@WebListener
public class EntityManagerLoaderListener implements ServletContextListener {
	private static final Logger logger = LoggerFactory.getLogger(EntityManagerLoaderListener.class);

	private String DEFAULT_DB_URL = "jdbc:h2:~/test.db";
	private static EntityManagerFactory emf;
	private boolean pushAdditionalProperties = true;

	public EntityManagerLoaderListener() {
	}

	public EntityManagerLoaderListener(boolean pushAdditionalProperties) {
		this.pushAdditionalProperties = pushAdditionalProperties;
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		System.out.println("EntityManagerLoaderListener.contextInitialized()");
		logger.debug("WebListener start entity manager");
		String databaseUrl = System.getenv("DATABASE_URL");
		//postgres://{user}:{password}@{hostname}:{port}/{database-name}
		//set DATABASE_URL=postgres://postgres:admin@localhost:5432/ursulaGIS

		if (databaseUrl == null && pushAdditionalProperties) {
			logger.debug("Use default config in persistence.xml with " + DEFAULT_DB_URL);
			databaseUrl = DEFAULT_DB_URL;
		}
		Map<String, String> properties = new HashMap<String, String>();
		if (pushAdditionalProperties) {
		//	HerokuURLAnalyser analyser = new HerokuURLAnalyser(databaseUrl);
			try{
				DatabaseUrl ext = DatabaseUrl.extract();
				//.jdbcUrl()

				//logger.debug("SET JDBC URL TO " + analyser.getJdbcURL());

				properties.put(PersistenceUnitProperties.JDBC_URL, ext.jdbcUrl());//analyser.getJdbcURL());
				properties.put(PersistenceUnitProperties.JDBC_USER, ext.username());//analyser.getUserName());
				properties.put(PersistenceUnitProperties.JDBC_PASSWORD, ext.password());//analyser.getPassword());

				properties.put(PersistenceUnitProperties.JDBC_DRIVER, "org.postgresql.Driver");
				//properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
				//properties.put(PersistenceUnitProperties.DDL_GENERATION, PersistenceUnitProperties.DROP_AND_CREATE);
				//properties.put(PersistenceUnitProperties.DDL_GENERATION_MODE, PersistenceUnitProperties.DDL_DATABASE_GENERATION);
			
				/*
     <property name="eclipselink.ddl-generation" value="create-tables" />
     <property name="eclipselink.composite-unit.member" value="true"/>
     <property name="eclipselink.target-database"      value="org.eclipse.persistence.platform.database.H2Platform"/>
     <property name="eclipselink.ddl-generation.output-mode" value="database" />
     <property name="eclipselink.create-ddl-jdbc-file-name" value="create.sql"/>
				 */
				

			}catch(Exception e){e.printStackTrace();}
		}
		emf = Persistence.createEntityManagerFactory("ursulaGIS", properties);
		System.out.println("Persistence.createEntityManagerFactory(\"default\", properties)->"+emf);


	}

	/**
	 * Close the entity manager
	 *
	 * @param event ServletContextEvent not used
	 */
	@Override
	public void contextDestroyed(ServletContextEvent event) {
		emf.close();
	}

	/**
	 * Create the EntityManager
	 *
	 * @return EntityManager
	 */
	public static EntityManager createEntityManager() {
		if (emf == null) {

			throw new IllegalStateException("Context is not initialized yet.");
		}

		return emf.createEntityManager();
	}


	public boolean isPushAdditionalProperties() {
		return pushAdditionalProperties;
	}

	public void setPushAdditionalProperties(boolean pushAdditionalProperties) {
		this.pushAdditionalProperties = pushAdditionalProperties;
	}
}