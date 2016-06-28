package models.config;

import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@NamedQueries({
	@NamedQuery(name=Campania.FIND_ALL, query="SELECT o FROM Campania o") ,
	@NamedQuery(name=Campania.FIND_NAME, query="SELECT o FROM Campania o where o.nombre = :name") ,
}) 

public class Campania implements Comparable<Campania>{
	public static final String FIND_ALL="Campania.findAll";
	public static final String FIND_NAME="Campania.findName";
	@Id @GeneratedValue
	private long id;

	public String nombre=new String();
	@Temporal(TemporalType.DATE)
	private Calendar inicio = Calendar.getInstance();
	@Temporal(TemporalType.DATE)
	private Calendar fin=Calendar.getInstance();

	public Campania(){}
	public Campania(String periodoName) {
		this.nombre=(periodoName);
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre=(nombre);
	}

	/**
	 * @return the inicio
	 */
	public Calendar getInicio() {
		return inicio;
	}

	/**
	 * @param inicio the inicio to set
	 */
	public void setInicio(Calendar inicio) {
		this.inicio = inicio;
	}

	/**
	 * @return the fin
	 */
	public Calendar getFin() {
		return fin;
	}

	/**
	 * @param fin the fin to set
	 */
	public void setFin(Calendar fin) {
		this.fin = fin;
	}

	@Override
	public int compareTo(Campania arg0) {
		return this.nombre.compareTo(arg0.nombre);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return nombre ;
	}


}
