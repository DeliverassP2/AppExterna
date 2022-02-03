package XML;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;



public class EscriureNomina {
	public static void main(String[] args) throws JAXBException, ClassNotFoundException, IOException {
		JAXBContext context = JAXBContext.newInstance(LlistaNomines.class);
		Marshaller marshaller = context.createMarshaller();
		LlistaNomines LlistaNomines = new LlistaNomines();
		
		ArrayList <Nomina> nomines = new ArrayList();
		
		Nomina nomina = new Nomina();
		nomina.setIDNomina("id1");
		nomina.setDNIRep("x8922312");
		nomina.setKmInicials("111992");
		nomina.setKmFinals("112500");
		nomina.setDia("21/02/2022");
		nomina.setSouDia("50");
		LlistaNomines.setNomina(nomines);
		/*
		Class.forName("org.postgresql.Driver");
		try (Connection connection = DriverManager.getConnection("jdbc:postgresql://192.168.1.203:5432/Deliverass",
					"postgres", "Fat/3232")){
			
			
			String select = "SELECT * from \"DEL_nomines\";";
			
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery(select);
			
			while(rs.next()) {
				Nomina nomina1 = new Nomina();
				nomina1.setIDNomina(rs.getString("id_nomina"));
				nomina1.setDNIRep(rs.getString("DniRep"));
				nomina1.setKmInicials(rs.getString("KmInicials"));
				nomina1.setKmFinals(rs.getString("KmFinals"));
				nomina1.setDia(rs.getString("KmDia"));
				nomina1.setDia(rs.getString("Data"));
				nomina1.setSouDia(rs.getString("SouDia"));
				
				LlistaNomines.setNomina(nomines);
				
				
			}
			*/
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			marshaller.marshal(LlistaNomines, System.out);
			marshaller.marshal(LlistaNomines, new FileWriter("Nomines.xml"));
			
	//	}catch(SQLException e) {
			
	//		e.printStackTrace();
		}
		
		
		
		
	}
//}
