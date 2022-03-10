package XML;

import java.io.File;
import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="llistaNomines")
@XmlType(propOrder = {"nombre","nomines","nomina"})
public class LlistaNomines {
	private String nombre;
	private String nomina;
	
	public String getNomina() {
		return nomina;
	}


	public void setNomina(String nomina) {
		this.nomina = nomina;
	}


	public void setNomines(ArrayList<Nomina> nomines) {
		this.nomines = nomines;
	}
	private ArrayList <Nomina> nomines = new ArrayList<Nomina>();
	
	public LlistaNomines() {
		
	}
	

	@XmlElement (name ="nombre")
	public String getNombre() {
		return nombre;

	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	
	@XmlElementWrapper(name="nomines")
	@XmlElement (name = "nomina")
	public ArrayList<Nomina> getNomines(){
		return nomines;
	}
	public void setNomina(ArrayList<Nomina> nomines) {
		this.nomines = nomines;
	}
	
}
