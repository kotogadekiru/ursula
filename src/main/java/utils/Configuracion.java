package utils;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;

/**
 * clase util para acceder al archivo de configuracion persistente en el sistema.
 * el usuario debe construir la instancia leer, modificar y guardar los cambios realizados. 
 * si quere volver a leer puede actualizar la instancia llamando a loadProperties()
 * no asegura que este acutalizado contra el archivo config.properties
 * @author tomas
 *
 */
public class Configuracion{
	private static final String DEFAULT_CONFIG_PROPERTIES = "/utils/config.properties";
	private static final String FILE_CONFIG_PROPERTIES = "config.properties";
	private final Properties configProp = new Properties();
	
	private String propertiesFileUrl = FILE_CONFIG_PROPERTIES;
	//public static SimpleBooleanProperty modified = new SimpleBooleanProperty();

	//private static final String GENERAR_MAPA_RENTABILIDAD_FROM_SHP = "generarMapaRentabilidadFromShp";
	public static final String LAST_FILE = "LAST_FILE";
	
	//private Property<Boolean> generarMapaRentabilidadFromShpProperty = new SimpleBooleanProperty();

	public Configuracion(String propertiesUrl) {		
		this.propertiesFileUrl=propertiesUrl;
		loadProperties();
	}

	public Configuracion() {		
		loadProperties();
	
//		generarMapaRentabilidadFromShpProperty.setValue(
//				"true".equalsIgnoreCase(configProp
//						.getProperty(GENERAR_MAPA_RENTABILIDAD_FROM_SHP)));
//		
//		generarMapaRentabilidadFromShpProperty
//				.addListener(( ov, o,  n) ->{
//					this.setProperty(GENERAR_MAPA_RENTABILIDAD_FROM_SHP, n.toString());					
//				});		
		
		System.out.println("finished loading config");
	//	modified();
	}

	public void loadProperties() {
		System.out.println("loading properties");
		
		boolean success = false;
		try {
			FileReader reader = new FileReader(propertiesFileUrl);
			configProp.load(reader);
			success = true;
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		if (!success) {
			InputStream in = this.getClass().getResourceAsStream(
					DEFAULT_CONFIG_PROPERTIES);
			propertiesFileUrl=DEFAULT_CONFIG_PROPERTIES;
			System.out.println("Read all properties from file");
			try {
				configProp.load(in);
				System.out.println("finished loading defaults");
			} catch (IOException e) {
				System.out.println("failed to load default configuration");
				e.printStackTrace();
			}
		}
	}
	
//	public void modified() {
//		modified.setValue(!modified.getValue());
//	}

	// Bill Pugh Solution for singleton pattern
//	private static class LazyHolder {
//		public static Configuracion INSTANCE = new Configuracion();
//	}
//
//	public static Configuracion getInstance() {
//		return LazyHolder.INSTANCE;
//	}



	
	public String getPropertyOrDefault(String key,String def) {
		String ret = configProp.getProperty(key);
		if(ret == null){
			setProperty(key,def);
			ret = def;
		}
		return ret;
	}

	public Set<String> getAllPropertyNames() {
	//	loadProperties();
		return configProp.stringPropertyNames();
	}

	public boolean containsKey(String key) {
	//	loadProperties();
		return configProp.containsKey(key);
	}

	public void setProperty(String key, String value) {
		//loadProperties();
		configProp.setProperty(key, value);
	///	save();
	}


	public void save() {
		try {
			configProp.store(new FileWriter(propertiesFileUrl),"MarginMapGenerator");//Calendar.getInstance().getTime().toString());
		
		} catch (IOException e) {
			e.printStackTrace();
		}
		
//		modified();
	}
	

}
