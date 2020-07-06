package models.config;

import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter(value = AccessLevel.PUBLIC)

@Entity @Access(AccessType.FIELD)
@NamedQueries({
	@NamedQuery(name=Establecimiento.FIND_ALL, query="SELECT o FROM Establecimiento o"),
	@NamedQuery(name=Establecimiento.FIND_NAME, query="SELECT o FROM Establecimiento o where o.nombre = :name") ,
}) 

public class Establecimiento implements Comparable<Establecimiento> {
	public static final String FIND_ALL = "Establecimiento.findAll";
	public static final String FIND_NAME = "Establecimiento.findName";
	
	@Id @GeneratedValue//(strategy = GenerationType.IDENTITY)
	private long id;
	
	public String nombre=new String();
	@ManyToOne
	private Empresa empresa;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="establecimiento")
	private List<Lote> lotes;
	
	public Establecimiento(){
		
	}
	
	 public Establecimiento(String establecimientoName) {
			nombre= establecimientoName;
	}

	@Override
	public String toString() {
		return nombre;
	}

	@Override
	public int compareTo(Establecimiento arg0) {
		if (this.nombre==null){
			return -1;
		}
		if (arg0==null){
			return 1;
		}
	return this.nombre.compareTo(arg0.nombre);
	}
	
	
	 
}
