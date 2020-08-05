package utils;

import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.eclipse.persistence.config.PersistenceUnitProperties;
import org.eclipse.persistence.internal.jpa.deployment.JPAInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.eclipse.persistence.jpa.PersistenceProvider;
import com.heroku.sdk.jdbc.DatabaseUrl;

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

	//private String DEFAULT_DB_URL = "postgres://postgres:postgres@localhost:5432/ursulaGIS";//"jdbc:h2:~/test.db";
	                              //"postgres://ursulaUser:ursulaPass@localhost:5432/UrsulaGIS";
	private String DEFAULT_DB_URL = "postgres://ursulaUser:ursulaPass@localhost:5432/UrsulaGIS";
	private static EntityManagerFactory emf;
	private boolean pushAdditionalProperties = true;

	public EntityManagerLoaderListener() {
	}

	public EntityManagerLoaderListener(boolean pushAdditionalProperties) {
		this.pushAdditionalProperties = pushAdditionalProperties;
	}
	
	/**
	 * metodo que crea emf al inicializarse el context
	 */
	//@Override
	public void contextInitializedNew(ServletContextEvent event) {	
		logger.info("EntityManagerLoaderListener.contextInitialized()");
		
		String databaseUrl = System.getenv("DATABASE_URL");
		StringTokenizer st = new StringTokenizer(databaseUrl, ":@/");
		String dbVendor = st.nextToken(); //if DATABASE_URL is set
		String userName = st.nextToken();
		String password = st.nextToken();
		String host = st.nextToken();
		String port = st.nextToken();
		String databaseName = st.nextToken();
		String jdbcUrl = String.format("jdbc:postgresql://%s:%s/%s?ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory", host, port, databaseName);
		Map<String, String> properties = new HashMap<String, String>();
		properties.put("javax.persistence.jdbc.url", jdbcUrl );// cambiar databaseUrl por jdbcUrl? ;databaseUrl
		properties.put("javax.persistence.jdbc.user", userName );
		properties.put("javax.persistence.jdbc.password", password );
		properties.put("javax.persistence.jdbc.driver", "org.postgresql.Driver");
	//	properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
		//PersistenceProvider prov = new PersistenceProvider();
		//JPAInitializer initializer = prov.getInitializer("ursulaGIS", properties);
		
		emf = Persistence.createEntityManagerFactory("ursulaGIS", properties);
		
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		//contextInitializedOld(event);
		//contextInitializedNew(event);
		contextInitializedRemote(event);
	}
	
	//esto funciona bien en local pero no en remoto porque da error de string decription
	public void contextInitializedTest(ServletContextEvent event) {
		String databaseUrl = System.getenv("DATABASE_URL");
		HerokuURLAnalyser analyser = new HerokuURLAnalyser(databaseUrl);
		Map<String, String> properties = new HashMap<String, String>();
		logger.info("jdbcURL: "+ analyser.getJdbcURL());
		properties.put("javax.persistence.jdbc.url", analyser.getJdbcURL());//"jdbc:postgresql://localhost:5432/UrsulaGIS2" );// cambiar databaseUrl por jdbcUrl? ;databaseUrl
		properties.put("javax.persistence.jdbc.user", analyser.getUserName() );
		properties.put("javax.persistence.jdbc.password", analyser.getPassword() );
		emf = Persistence.createEntityManagerFactory("ursulaGIS",properties);
		
	}
	
	public void contextInitializedRemote(ServletContextEvent event) {
		
		try {
			DatabaseUrl databaseUrl = DatabaseUrl.extract(false);
			//databaseUrl.
			
		//	String databaseUrl = System.getenv("DATABASE_URL");
			//HerokuURLAnalyser analyser = new HerokuURLAnalyser(databaseUrl);
			Map<String, String> properties = new HashMap<String, String>();
			logger.info("jdbcURL: "+ databaseUrl.jdbcUrl());
			properties.put("javax.persistence.jdbc.url", "jdbc:postgresql://ec2-54-235-193-0.compute-1.amazonaws.com:5432/d18da30584sone?ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory");//"jdbc:postgresql://localhost:5432/UrsulaGIS2" );// cambiar databaseUrl por jdbcUrl? ;databaseUrl
			properties.put("javax.persistence.jdbc.user","pajqghckztncso");
			properties.put("javax.persistence.jdbc.password", "912701de8594f74c1001d4d1c3c32bdcd03079dd563b2eec37a12c17a4d86dab");
			emf = Persistence.createEntityManagerFactory("ursulaGIS",properties);
			
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
	public void contextInitializedOld(ServletContextEvent event) {
		logger.info("EntityManagerLoaderListener.contextInitialized()");
		
		String databaseUrl = System.getenv("DATABASE_URL");
		//logger.info("system database url ="+databaseUrl);
		//System.out.println("database url ="+databaseUrl);
		//postgres://{user}:{password}@{hostname}:{port}/{database-name}
		//set DATABASE_URL=postgres://postgres:admin@localhost:5432/ursulaGIS
		
		//DatabaseUrl.extract().getConnection();

		if (databaseUrl == null && pushAdditionalProperties) {
			logger.info("Use default config in persistence.xml with " + DEFAULT_DB_URL);
			databaseUrl = DEFAULT_DB_URL;
		}
		Map<String, String> properties = new HashMap<String, String>();
		if (pushAdditionalProperties) {
			HerokuURLAnalyser analyser = new HerokuURLAnalyser(databaseUrl);
			try{
//				DatabaseUrl ext = DatabaseUrl.extract();
//				properties.put(PersistenceUnitProperties.JDBC_URL,ext.jdbcUrl());// ext.jdbcUrl());//
//				properties.put(PersistenceUnitProperties.JDBC_USER, ext.username());//ext.username());//
//				properties.put(PersistenceUnitProperties.JDBC_PASSWORD,ext.password());// ext.password());
//				
				
				
				//properties.put("sslmode", "prefer");
				properties.put(PersistenceUnitProperties.JDBC_URL,analyser.getJdbcURL());// ext.jdbcUrl());//
				properties.put(PersistenceUnitProperties.JDBC_USER, analyser.getUserName());//ext.username());//
				properties.put(PersistenceUnitProperties.JDBC_PASSWORD,analyser.getPassword());// ext.password());
				
				//logger.info("PersistenceUnitProperties.VALIDATOR_FACTORY = "+PersistenceUnitProperties.VALIDATOR_FACTORY);
				//javax.persistence.validation.factory
				//properties.put("sslfactory","org.postgresql.ssl.NonValidatingFactory");
				properties.put(PersistenceUnitProperties.JDBC_DRIVER, "org.postgresql.Driver");
				
				
				
				//properties.put("eclipselink.tenant-id", "HTHL");//property para soportar multi tenancy
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
				

			}catch(Exception e){e.printStackTrace();
			logger.error(e.toString());}
		}
		emf = Persistence.createEntityManagerFactory("ursulaGIS", properties);
		logger.info("Creatin emf with->"+emf.getProperties());
		//Persistence.createEntityManagerFactory("ursulaGIS", properties)->
		//{javax.persistence.jdbc.url=jdbc:postgresql://localhost:5432/ursulaGIS, javax.persistence.jdbc.user=postgres, javax.persistence.jdbc.password=postgres, javax.persistence.jdbc.driver=org.postgresql.Driver}
//		logger.info("Persistence.createEntityManagerFactory(\"default\", properties)->"+emf);
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