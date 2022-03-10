package XML;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadPoolExecutor;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilderFactory;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class EscriureNomina {
	// FORMAT ANY
	public static final Date dateyear = new Date();
	public static final SimpleDateFormat formatteryear = new SimpleDateFormat("yyyy");
	public static String year = "";

	// FORMAT MES
	public static final Date datemonth = new Date();
	public static final SimpleDateFormat formattermonth = new SimpleDateFormat("M");
	public static String month = "";

	// SEMAFOR
	static Semaphore mutex = new Semaphore(1);

	public static void main(String[] args)
			throws JAXBException, ClassNotFoundException, IOException, DocumentException, InterruptedException {

		cridarFils();
		escriureJAXB();
		escriurePDF();

	}

	public static void cridarFils() throws InterruptedException {
		Thread thread1 = new getAny();
		Thread thread2 = new getMes();

		thread1.run();
		thread2.run();

		thread1.join();
		thread2.join();

	}

	// Fil obtenir any
	public static class getAny extends Thread {
		public void run() {
			try {
				mutex.acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			year = formatteryear.format(dateyear);
			mutex.release();
		}
	}

	// Fil obtenir mes
	public static class getMes extends Thread {
		public void run() {
			try {
				mutex.acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			month = formattermonth.format(datemonth);
			mutex.release();
		}
	}

	private static void escriureJAXB() throws JAXBException, IOException, ClassNotFoundException, DocumentException {

		JAXBContext context = JAXBContext.newInstance(LlistaNomines.class);
		Marshaller marshaller = context.createMarshaller();
		LlistaNomines llistaNomines = new LlistaNomines();
		ArrayList<Nomina> nomines = new ArrayList();

		llistaNomines.setNombre("prova");

		Class.forName("org.postgresql.Driver");
		try (Connection connection = DriverManager.getConnection("jdbc:postgresql://192.168.1.203:5432/Deliverass",
				"postgres", "Fat/3232")) {

				String select = "SELECT \r\n" + "	max(\"Id_nomina\") \"Id_nomina\",\r\n" + "	\"DniRep\",\r\n"
					+ "	\"nom\",\r\n" + "	min(\"KmInicials\") \"kmInicials\",\r\n"
					+ "	max(\"KmFinals\") \"kmFinals\",\r\n" + "	min(\"Data\") \"DataInici\",\r\n"
					+ "	max(\"Data\") \"DataFinal\",\r\n" + "	max(\"euroKm\") \"euroKm\"\r\n"
					+ "FROM \"DEL_nomines\"\r\n" + "INNER JOIN \"DEL_repartidors\" ON\r\n"
					+ "\"DEL_nomines\".\"DniRep\" = \"DEL_repartidors\".\"Dni\"\r\n"
					+ "WHERE EXTRACT(MONTH FROM NOW()) = EXTRACT(MONTH FROM \"Data\")\r\n"
					+ "GROUP BY \"DniRep\", \"nom\";";
;

			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery(select);

			while (rs.next()) {
				Nomina nomina1 = new Nomina();
				nomina1.setIDNomina(rs.getString("id_nomina"));
				nomina1.setDNIRep(rs.getString("DniRep"));
				nomina1.setKmInicials(rs.getString("KmInicials"));
				nomina1.setKmFinals(rs.getString("KmFinals"));
				nomina1.setKmDia(rs.getInt("KmFinals")- rs.getInt("KmInicials"));
				
				//nomina1.setDia(rs.getString("Data"));
				nomina1.setSouDia(rs.getInt("euroKm") * nomina1.getKmDia() );
				nomines.add(nomina1);
				llistaNomines.setNomina(nomines);

			}

			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			marshaller.marshal(llistaNomines, System.out);
			marshaller.marshal(llistaNomines, new FileWriter("Nomines.xml"));

		} catch (SQLException e) {

			e.printStackTrace();
		}

	}

	private static void escriurePDF() {
		try {
			JAXBContext context = JAXBContext.newInstance(LlistaNomines.class);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			LlistaNomines llistaN = (LlistaNomines) unmarshaller.unmarshal(new File("Nomines.xml"));

			ArrayList<Nomina> nomines = llistaN.getNomines();

			for (Nomina n : nomines) {

				File ruta = new File(
						File.listRoots()[0] + "/Nomines/" + n.getDNIRep() + "/" + year + "/" + month + "/");
				if (!ruta.exists()) {
					ruta.mkdirs();
				}

				int i = 1;
				File existent = new File(ruta + "/" + n.getDNIRep() + "_" + month + "_" + year + "_" + i + ".pdf");
				if (existent.exists()) {
					i++;

					new File(ruta + "/" + n.getDNIRep() + "_" + month + "_" + year + "_" + i + ".pdf");
				} else {

					new File(ruta + "/" + n.getDNIRep() + "_" + month + "_" + year + "_" + i + ".pdf");
				}

				Document docpdf = new Document();
				PdfWriter pfw = PdfWriter.getInstance(docpdf, new FileOutputStream(existent));

				docpdf.open();

				docpdf.add(new Paragraph("---------------DELIVERASS-------------"));
				docpdf.add(new Paragraph("                                            "));
				docpdf.add(new Paragraph("  ID: " + n.getIDNomina() + "               "));
				docpdf.add(new Paragraph("  Repartidor: " + n.getDNIRep() + "         "));
				docpdf.add(new Paragraph("  KmInici: " + n.getKmInicials() + "         "));
				docpdf.add(new Paragraph("  KmFinals: " + n.getKmFinals() + "           "));
				docpdf.add(new Paragraph("  KmDia: " + n.getKmDia() + "                "));
				docpdf.add(new Paragraph("  Dia: " + n.getDia() + "                     "));
				docpdf.add(new Paragraph("  SouDiari: " + n.getSouDia() + "             "));
				docpdf.add(new Paragraph("                                            "));
				docpdf.add(new Paragraph("------------------------------------------------"));

				docpdf.close();
				pfw.close();

				 System.out.println("Fitxer Creat a la ruta :" + ruta.getAbsolutePath());

			}
		} catch (JAXBException | FileNotFoundException | DocumentException e) {
			e.printStackTrace();
		}
	}

}
