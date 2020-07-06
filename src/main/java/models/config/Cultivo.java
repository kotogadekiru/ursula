package models.config;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
/**
 * clase que representa un cultivo
 * puede ser Trigo, Cebada, Soja P , Soja S, Soja S/C, Maiz P, Maiz S, Maiz Flint, Arveja, Etc..
 * @author tomas
 *
 */
@Getter
@Setter(value = AccessLevel.PUBLIC)
@ToString

@Entity @Access(AccessType.FIELD)
@NamedQueries({
	@NamedQuery(name=Cultivo.FIND_ALL, query="SELECT c FROM Cultivo c") ,
	@NamedQuery(name=Cultivo.FIND_NAME, query="SELECT o FROM Cultivo o where o.nombre = :name") ,
}) 

public class Cultivo implements Comparable<Cultivo>{
	public static final String FIND_ALL="Cultivo.findAll";
	public static final String FIND_NAME = "Cultivo.findName";
	
	@Id @GeneratedValue
	private long id;
	public String nombre=new String();
	
	public Cultivo(){}
	public Cultivo(String cultivoName) {
		this.nombre=(cultivoName);
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

	@Override
	public int compareTo(Cultivo arg0) {
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
