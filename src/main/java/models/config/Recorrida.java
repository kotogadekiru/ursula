package models.config;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

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
	@NamedQuery(name=Recorrida.FIND_ALL, query="SELECT o FROM Recorrida o"),
	@NamedQuery(name=Recorrida.FIND_NAME, query="SELECT o FROM Recorrida o where o.nombre = :name") ,
	@NamedQuery(name=Recorrida.FIND_ID, query="SELECT o FROM Recorrida o where o.id = :id") ,
}) 

public class Recorrida {
	public static final String FIND_ALL = "Recorrida.findAll";
	public static final String FIND_NAME = "Recorrida.findName";
	public static final String FIND_ID = "Recorrida.findID";
	
	@Id @GeneratedValue//(strategy = GenerationType.IDENTITY)
	private long id;
	
	public String nombre=new String();
	public String observacion=new String();
	
	public String posicion=new String();//json {long,lat}
	public Double latitude= new Double(0.0);
	public Double longitude=new Double(0.0);
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="recorrida")
	public List<Muestra> muestras =new ArrayList<Muestra>();
	
	public Recorrida() {
		
	}
	
	@Override
	public String toString() {
		return nombre;
	}
}
