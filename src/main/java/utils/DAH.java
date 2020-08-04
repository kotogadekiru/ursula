package utils;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import models.config.Campania;
import models.config.Cultivo;
import models.config.Empresa;
import models.config.Establecimiento;
import models.config.Lote;
import models.config.Recorrida;

public class DAH {
	private static final Logger logger = LoggerFactory.getLogger(DAH.class);

	private static EntityManager em = null;
	static EntityTransaction transaction;

	public static EntityManager em(){
		if(em == null){
			EntityManagerLoaderListener emll=new EntityManagerLoaderListener();
			emll.contextInitialized(null);
			em = EntityManagerLoaderListener.createEntityManager();
			logger.info("creando el entityManager");
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
	
	public static Recorrida getRecorridaByName(String name) {
		TypedQuery<Recorrida> query = em().createNamedQuery(Recorrida.FIND_NAME, Recorrida.class);
		query.setParameter("name", name);
		
		// You have attempted to set a parameter at position 0 which does not exist in this query string 
		//SELECT o FROM Recorrida o where o.nombre = :name.
		Recorrida recorrida = query.getSingleResult();
		return recorrida;
	}

	
	public static boolean recorridaExists(String id) {
		boolean ret=false;
		Recorrida recorrida = em().find(Recorrida.class, id);
		if(recorrida !=null) {
			ret = true;
		}
		return ret;
	}
	

	
	public static List<Recorrida> getAllRecorridas() {
		return getAllRecorridas(em());
	}
	
	public static List<Recorrida> getAllRecorridas(EntityManager em) {		
		TypedQuery<Recorrida> query = em.createNamedQuery(
				Recorrida.FIND_ALL, Recorrida.class);
		List<Recorrida> results = query.getResultList();
		
		logger.info("recorridas\n" + results.stream().map(Object::toString).collect(Collectors.joining(", ")));
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
	
	
	public static Object edit(Object entidad) {
		Object ret =null;
		EntityManager em = em();
		if(DAH.transaction == null){
			//	DAH.transaction = em.getTransaction();
			em.getTransaction().begin();		
			ret = em.merge(entidad);			
			em.getTransaction().commit();
		} else{
			ret = em.merge(entidad);			
		}
		return ret;
	}
	

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



}
