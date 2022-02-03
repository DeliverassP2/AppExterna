package XML;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="llistaNomines")
@XmlType(propOrder = {"nombre","nomines"})
public class LlistaNomines {
	private String nombre;
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
