package XML;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "nomina")
@XmlType(propOrder = { "IDNomina", "DNIRep", "nom", "kmInicials", "kmFinals", "kmTotals", "diaInici", "diaFinal", "souDia" })
public class Nomina {

	private String IDNomina;
	private String DNIRep;
	private String nom;
	private int kmInicials;
	private int kmFinals;
	private int kmTotals;
	private String diaInici;
	private String diaFinal;
	private double souDia;

	public Nomina() {
		super();
	}

	@XmlAttribute(name="IDNomina")
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
	
	@XmlElement(name="Nom")	
	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	@XmlElement(name="kmInicials")
	public int getKmInicials() {
		return kmInicials;
	}

	public void setKmInicials(int kmInicials) {
		this.kmInicials = kmInicials;
	}

	@XmlElement(name="kmFinals")
	public int getKmFinals() {
		return kmFinals;
	}

	public void setKmFinals(int kmFinals) {
		this.kmFinals = kmFinals;
	}

	@XmlElement(name="kmTotals")
	public int getKmTotals() {
		return kmTotals;
	}

	public void setKmTotals(int kmTotals) {
		this.kmTotals = kmTotals;
	}

	@XmlElement(name="diaInici")
	public String getDiaInici() {
		return diaInici;
	}

	public void setDiaInici(String diaInici) {
		this.diaInici = diaInici;
	}
	
	@XmlElement(name="diaFinal")
	public String getDiaFinal() {
		return diaFinal;
	}

	public void setDiaFinal(String diaFinal) {
		this.diaFinal = diaFinal;
	}

	@XmlElement(name="souDia")
	public double getSouDia() {
		return souDia;
	}

	public void setSouDia(double d) {
		this.souDia = d;
	}

	
	
}
