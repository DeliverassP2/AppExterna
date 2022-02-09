package XML;

import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

public class EscriureNomina {
	public static final Date dateyear = new Date();
	public static final SimpleDateFormat formatteryear = new SimpleDateFormat("yyyy");
	public static final String year = formatteryear.format(dateyear);
	public static final Date datemonth = new Date();
	public static final SimpleDateFormat formattermonth = new SimpleDateFormat("M");
	public static final String month = formattermonth.format(datemonth);

	public static void main(String[] args) {

		System.out.println(File.listRoots()[0]);

		try {
			JAXBContext cont = JAXBContext.newInstance(LlistaNomines.class);
			Marshaller marsh = cont.createMarshaller();
			LlistaNomines ln = new LlistaNomines();
			ln.setNombre("Nomina");

			ArrayList<Nomina> nomines = new ArrayList();
			Connection conn = DriverManager.getConnection("jdbc:postgresql://192.168.1.203:5432/Deliverass", "postgres",
					"Fat/3232");

			String select = "SELECT \r\n" + "	max(\"Id_nomina\") \"Id_nomina\",\r\n" + "	\"DniRep\",\r\n"
					+ "	\"nom\",\r\n" + "	min(\"KmInicials\") \"kmInicials\",\r\n"
					+ "	max(\"KmFinals\") \"kmFinals\",\r\n" + "	min(\"Data\") \"DataInici\",\r\n"
					+ "	max(\"Data\") \"DataFinal\",\r\n" + "	max(\"euroKm\") \"euroKm\"\r\n"
					+ "FROM \"DEL_nomines\"\r\n" + "INNER JOIN \"DEL_repartidors\" ON\r\n"
					+ "\"DEL_nomines\".\"DniRep\" = \"DEL_repartidors\".\"Dni\"\r\n"
					+ "WHERE EXTRACT(MONTH FROM NOW()) = EXTRACT(MONTH FROM \"Data\")\r\n"
					+ "GROUP BY \"DniRep\", \"nom\";";

			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(select);
			while (rs.next()) {
				Nomina nomina = new Nomina();

				int kmTotals = rs.getInt("KmFinals") - rs.getInt("KmInicials");

				nomina.setIDNomina(rs.getString("Id_nomina"));
				nomina.setDNIRep(rs.getString("DniRep"));
				nomina.setNom(rs.getString("nom"));
				nomina.setKmInicials(rs.getInt("kmInicials"));
				nomina.setKmFinals(rs.getInt("kmFinals"));
				nomina.setKmTotals(kmTotals);
				nomina.setDiaInici(rs.getString("DataInici"));
				nomina.setDiaFinal(rs.getString("DataFinal"));
				nomina.setSouDia(rs.getDouble("euroKm") * kmTotals);
				nomines.add(nomina);

				ln.setNomines(nomines);

				File ruta = new File(
						File.listRoots()[0] + "/Nomines/" + rs.getString("DniRep") + "/" + year + "/" + month + "/");
				if (!ruta.exists()) {
					ruta.mkdirs();
				}

				marsh.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
				int i = 1;
				File existent = new File(
						ruta + "/" + rs.getString("DniRep") + "_" + month + "_" + year + "_" + i + ".xml");
				if (existent.exists()) {
					i++;
					marsh.marshal(ln, new File(
							ruta + "/" + rs.getString("DniRep") + "_" + month + "_" + year + "_" + i + ".xml"));
				}else {
					marsh.marshal(ln, new File(
							ruta + "/" + rs.getString("DniRep") + "_" + month + "_" + year + "_" + i + ".xml"));
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
