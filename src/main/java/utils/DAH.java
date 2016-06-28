package utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import models.config.Campania;
import models.config.Cultivo;
import models.config.Empresa;
import models.config.Establecimiento;
import models.config.Lote;

import org.mat.nounou.servlets.EntityManagerLoaderListener;
import org.mat.nounou.util.HerokuURLAnalyser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DAH {
	private static final Logger logger = LoggerFactory.getLogger(DAH.class);

	private static EntityManager em = null;
	static EntityTransaction transaction;

	public static EntityManager em(){
		if(em == null){
			EntityManagerLoaderListener emll=new EntityManagerLoaderListener();
			emll.contextInitialized(null);

			em = EntityManagerLoaderListener.createEntityManager();
		}
		return em;
	}

	
	public static Establecimiento getEstablecimiento(String establecimientoName) throws Exception {
		TypedQuery<Establecimiento> query = em().createNamedQuery(
				Establecimiento.FIND_NAME, Establecimiento.class);
		query.setParameter(0, establecimientoName);
	
		Establecimiento result = null;
		if(query.getResultList().size()>0){
			result = query.getSingleResult();
		} else {
			result = new Establecimiento(establecimientoName);
			DAH.save(result);
		}
		return result;
	}



	public static List<Establecimiento> getAllEstablecimientos() {
		return getAllEstablecimientos(em());
	}
	
	public static List<Establecimiento> getAllEstablecimientos(EntityManager em) {		
		TypedQuery<Establecimiento> query = em.createNamedQuery(
				Establecimiento.FIND_ALL, Establecimiento.class);
		List<Establecimiento> results = query.getResultList();
		return results;
	}

	/**
	 * 
	 * @param periodoName
	 * @return el periodo existente en la base de datos o crea uno nuevo con ese nombre y lo devuelve
	 * @throws Exception 
	 */
	public static Campania getCampania(String periodoName) throws Exception {	
		TypedQuery<Campania> query = em().createNamedQuery(
				Campania.FIND_NAME, Campania.class);
		query.setParameter(0, periodoName);
		Campania result = null;
		if(query.getResultList().size()>0){
			result = query.getSingleResult();
		} else {
			result = new Campania(periodoName);
			DAH.save(result);
		}
		return result;
	}



	/**
	 * 
	 * @param cultivoName
	 * @return el producto existente en la base de datos o crea uno nuevo con ese nombre y lo devuelve
	 */
	public static Cultivo getCultivo(String cultivoName) {	
//		EntityManager em = em();
//		TypedQuery<Producto> query =
//				em.createQuery("SELECT p FROM Producto p where p.nombre like '"+cultivoName+"'", Producto.class);

		TypedQuery<Cultivo> query = em().createNamedQuery(
				Cultivo.FIND_NAME, Cultivo.class);
		query.setParameter(0, cultivoName);
		
		Cultivo result = null;
		if(query.getResultList().size()>0){
			result = query.getSingleResult();
		} else {
			result = new Cultivo(cultivoName);
			DAH.save(result);
		}
		return result;
	}

	public static List<Cultivo> getAllCultivos(EntityManager em) {
		  TypedQuery<Cultivo> query =
				  em().createNamedQuery(Cultivo.FIND_ALL, Cultivo.class);
			  List<Cultivo> results = query.getResultList();
		return results;
	}
	
	public static List<Cultivo> getAllCultivos() {
		return getAllCultivos(em());
	}
	
//	public static void getAllMargenCultivo(ObservableList<MargenCultivo> observable) {
//		DAH.em();
//		
//		Query countQ = em.createQuery("SELECT COUNT(d) FROM MargenCultivo d");
//		long count = (long)countQ.getSingleResult();
//		int first =0;
//		
//		do{
//			////tiene que ser un nuevo query porque sino cuando configuro el first se modifica en todas
//			 TypedQuery<MargenCultivo> query =
//				      em.createNamedQuery(MargenCultivo.FIND_ALL, MargenCultivo.class);
//			 
//
//			query.setMaxResults(10);
//			QueryService<MargenCultivo> service = new QueryService<MargenCultivo>();
//			service.setQuery(query);
//			service.setFirst(first);
//			service.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
//	            @Override
//	            public void handle(WorkerStateEvent t) {	             
//	            	Collection<? extends MargenCultivo>  value = (Collection<? extends MargenCultivo>) t.getSource().getValue();	            	
//	            	observable.addAll( value);	            
//	            }
//	        });
//			service.start();			
//			first+=query.getMaxResults();			
//		}while(first<count);
//	}



	public static void save(Object entidad) {
	
		EntityManager em = em();
		if(DAH.transaction == null){
			//	DAH.transaction = em.getTransaction();
			em.getTransaction().begin();		
			em.persist(entidad);			
			em.getTransaction().commit();
		} else{
			em.persist(entidad);	

		}

	}
	
	public static void remove(Object entidad) {
		EntityManager em = em();
		if(DAH.transaction == null){
			//	DAH.transaction = em.getTransaction();
			em.getTransaction().begin();		
			em.remove(entidad);			
			em.getTransaction().commit();
		} else{
			em.persist(entidad);	

		}

	}



	public static List<Empresa> getAllEmpresas() {
		TypedQuery<Empresa> query = em().createNamedQuery(
				Empresa.FIND_ALL, Empresa.class);
		List<Empresa> results = query.getResultList();
		
		return results;
	}



	public static List<Lote> getAllLotes() {
		TypedQuery<Lote> query = em().createNamedQuery(
				Lote.FIND_ALL, Lote.class);
		List<Lote> results = query.getResultList();
		
		return results;
	}



	public static List<Campania> getAllCampanias() {
		TypedQuery<Campania> query = em().createNamedQuery(
				Campania.FIND_ALL, Campania.class);
		List<Campania> results = query.getResultList();
		
		return results;
	}






	//	public static void main(String[] args) throws Exception {
	//	       // Open a database connection
	//     // (create a new database if it doesn't exist yet):
	//     EntityManagerFactory emf =
	//         Persistence.createEntityManagerFactory("$objectdb/db/monitores.odb");
	//     
	//   
	//     EntityManager em = emf.createEntityManager();
	//
	//     // Store 1000 Point objects in the database:
	//     em.getTransaction().begin();
	//     
	//     for (int i = 0; i < 10; i++) {
	//         Producto p = new Producto("Producto "+i,100.0- i*10, i*10-100.0);
	//         em.persist(p);
	//     }
	// 
	//     
	//     Establecimiento establecimiento = new Establecimiento("La Tablada");
	//     em.persist(establecimiento);
	//     
	//     em.getTransaction().commit();
	//     
	//     //creo un monitor
	//     em.getTransaction().begin();
	//     Periodo periodo = new Periodo("01-07-2014");
	//     em.persist(periodo);
	//     
	//     Monitor mon = new Monitor(establecimiento, periodo);
	//     em.persist(mon);
	//
	//     List<Producto> productos = DAH.getAllProducts(em);
	//     
	//     for(Producto producto : productos){
	//     	Suplementacion suple = new Suplementacion(mon, producto);//, 20.5, null, null, null, null);
	//     	suple.setCantidadEstaca(20.5);
	//     	suple.setCantidadRecria(39700.0);
	//     	suple.setCantidadVo(0.0);
	//     	suple.setCantidadVs(50.3);
	//     	suple.setPrecio(3.25);   
	//     	
	//     	 em.persist(suple);
	//     }
	//     
	//     
	//     em.getTransaction().commit();
	//
	//     // Find the number of Point objects in the database:
	//     Query q1 = em.createQuery("SELECT COUNT(p) FROM Suplementacion p");
	//     //System.out.println("Cantidad de Suplementaciones: " + q1.getSingleResult());
	//
	//     // Find the average X value:
	////     Query q2 = em.createQuery("SELECT AVG(p.x) FROM Establecimiento p");
	////     System.out.println("Average X: " + q2.getSingleResult());
	//
	//     // Retrieve all the Point objects from the database:
	//     TypedQuery<Establecimiento> query =
	//         em.createQuery("SELECT p FROM Establecimiento p", Establecimiento.class);
	//     
	//     List<Establecimiento> results = query.getResultList();
	//     for (Establecimiento p : results) {
	//         System.out.println(p);
	//     }
	//
	//     // Close the database connection:
	//     em.close();
	//     emf.close();
	//
	//	}

	





	

}
