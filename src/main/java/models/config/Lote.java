package models.config;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;



@Entity
@NamedQueries({
	@NamedQuery(name=Lote.FIND_ALL, query="SELECT o FROM Lote o"),
	@NamedQuery(name=Lote.FIND_NAME, query="SELECT o FROM Lote o where o.nombre = :name") ,
}) 
public class Lote implements Comparable<Lote> {
	public static final String FIND_ALL = "Lote.findAll";
	public static final String FIND_NAME = "Lote.findNombre";

	@Id @GeneratedValue
	private long id;

	public String nombre= new String();

	public Double superficie=new Double(0.0);
	
	@ManyToOne
	private Establecimiento establecimiento=null;

	public Lote(Establecimiento e, String n){
		this.establecimiento=e;
		this.nombre=n;
	}

	public Lote(String n) {
	this.nombre=n;
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
	 * @return the establecimiento
	 */
	public Establecimiento getEstablecimiento() {
		return establecimiento;
	}

	/**
	 * @param establecimiento the establecimiento to set
	 */
	public void setEstablecimiento(Establecimiento establecimiento) {
		this.establecimiento = establecimiento;
	}
	 
	public String getNombre(){
		return this.nombre;
	}
	
	public void setNombre(String n){
		this.nombre=n;
	}
	
	
	public Double getSuperficie(){
		return this.superficie;
	}
	public void setSuperficie(Double s){
		this.superficie=s;
	}

	@Override
	public int compareTo(Lote arg0) {
		return this.nombre.compareTo(arg0.nombre);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return nombre;
	}
}
