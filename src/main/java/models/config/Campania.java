package models.config;

import java.util.Calendar;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter(value = AccessLevel.PUBLIC)


@Entity @Access(AccessType.FIELD)
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
