package XML;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "nomina")
@XmlType(propOrder = {"IDNomina","DNIRep","kmInicials","kmFinals","kmDia","dia","souDia"})
public class Nomina {
	private String IDNomina;
	private String DNIRep;
	private String kmInicials;
	private String kmFinals;
	private String kmDia;
	private String dia;
	private String souDia;
	
	public Nomina() {
		
	}
	
	@XmlElement(name="IDNomina")
	public String getIDNomina() {
		return IDNomina;
	}

	public void setIDNomina(String iDNomina) {
		IDNomina = iDNomina;
	}
	
	@XmlElement(name="DNIRep")
	public String getDNIRep() {
		return DNIRep;
	}

	public void setDNIRep(String dNIRep) {
		DNIRep = dNIRep;
	}
	
	@XmlElement(name="kmInicials")
	public String getKmInicials() {
		return kmInicials;
	}

	public void setKmInicials(String kmInicials) {
		this.kmInicials = kmInicials;
	}
	
	@XmlElement(name="kmFinals")
	public String getKmFinals() {
		return kmFinals;
	}

	public void setKmFinals(String kmFinals) {
		this.kmFinals = kmFinals;
	}

	@XmlElement(name="kmDia")
	public String getKmDia() {
		return kmDia;
	}

	public void setKmDia(String kmDia) {
		this.kmDia = kmDia;
	}

	@XmlElement(name="dia")
	public String getDia() {
		return dia;
	}

	public void setDia(String dia) {
		this.dia = dia;
	}

	@XmlElement(name="souDia")
	public String getSouDia() {
		return souDia;
	}

	public void setSouDia(String souDia) {
		this.souDia = souDia;
	}
	
	
}
