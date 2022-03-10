package M9;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="llistaPaquets")
//@XmlType(propOrder = {"nombre,","paquets","paquet"})
public class llistaPaquets {
	private String nombre;
	private ArrayList <paquet> paquets;
	
	public llistaPaquets() {
		super();
	}
	
	@XmlElement (name="nombre")
		public String getNombre() {
		return nombre;
	}
		
		
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	@XmlElementWrapper(name = "paquets")
	@XmlElement(name = "paquet")
	public ArrayList<paquet> getPaquets() {
		return paquets;
	}
	
	public void setPaquets(ArrayList<paquet> paquets) {
		this.paquets = paquets;
	}
		

	
}
