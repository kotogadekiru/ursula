package models.config;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * clase que representa una observacion
 * @author quero
 *
 */

@Getter
@Setter(value = AccessLevel.PUBLIC)

@Entity @Access(AccessType.FIELD)
@NamedQueries({
	@NamedQuery(name=Muestra.FIND_ALL, query="SELECT o FROM Muestra o"),
	@NamedQuery(name=Muestra.FIND_NAME, query="SELECT o FROM Muestra o where o.nombre = :name") ,
}) 

public class Muestra {
	public static final String FIND_ALL = "Muestra.findAll";
	public static final String FIND_NAME = "Muestra.findName";
	
	@Id @GeneratedValue//(strategy = GenerationType.IDENTITY)
	private long id;
	
	public String nombre=new String();
	public String observacion=new String();
	public String posicion=new String();//json {long,lat}
	public Double latitude= new Double(0.0);
	public Double longitude=new Double(0.0);
	
	@ManyToOne
	private Recorrida recorrida=null;
	
	public Muestra() {
		
	}

	@Override
	public String toString() {
		return nombre;
	}
}
