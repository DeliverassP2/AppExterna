package XML;

import java.io.File;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public class LlegirNomina {
	public static void main(String[] args){
		try{
			JAXBContext context = JAXBContext.newInstance(LlistaNomines.class);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			LlistaNomines llistaN = (LlistaNomines) unmarshaller.unmarshal(new File("Nomines.xml"));
		
			System.out.println(llistaN.getNombre());
			
			ArrayList<Nomina> nomines = llistaN.getNomines();
			
			for(Nomina n: nomines) {
				
				System.out.println("ID:" + n.getIDNomina() + " Repartidor:" + n.getDNIRep() + " Km_Inici:" + n.getKmInicials() +
						" Km_Final:" + n.getKmFinals() + " km_Dia:" + n.getKmDia() + " Dia:" + n.getDia() +
						" Sou_diari:" + n.getSouDia());
			}
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}
}
