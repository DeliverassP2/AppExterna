package XML;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class NominesFils {
	public static final Date dateyear = new Date();
	public static final SimpleDateFormat formatteryear = new SimpleDateFormat("yyyy");
	public static final String year = formatteryear.format(dateyear);
	public static final Date datemonth = new Date();
	public static final SimpleDateFormat formattermonth = new SimpleDateFormat("M");
	public static final String month = formattermonth.format(datemonth);

	static Semaphore inici = new Semaphore(0);

	public static class CrearXml extends Thread {

		public CrearXml() {
			super();
		}

		public void run() {
			// System.out.println(File.listRoots()[0]);

			try {
				JAXBContext cont = JAXBContext.newInstance(LlistaNomines.class);
				Marshaller marsh = cont.createMarshaller();
				LlistaNomines ln = new LlistaNomines();
				ln.setNombre("Nomina");
				Connection conn = DriverManager.getConnection("jdbc:postgresql://192.168.1.203:5432/Deliverass",
						"postgres", "Fat/3232");

				String select = "SELECT DEL_Nomies_Treballadors()";

				Statement st = conn.createStatement();
				ResultSet rs = st.executeQuery(select);
				ArrayList<Nomina> nomines = new ArrayList();

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

					File ruta = new File(File.listRoots()[0] + "/Nomines/" + rs.getString("DniRep") + "/" + year + "/"
							+ month + "/");
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
					} else {
						marsh.marshal(ln, new File(
								ruta + "/" + rs.getString("DniRep") + "_" + month + "_" + year + "_" + i + ".xml"));
					}

				}

			} catch (Exception e) {
				e.printStackTrace();
			}

			inici.release();

		}

	}

	public static class CrearPdf extends Thread {

		public CrearPdf() {
			super();
		}

		public void run() {
			try {
				inici.acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			try {
				Scanner sc = new Scanner(System.in);
				System.out.println("Insereix el DNI del treballador que vols crear la nomina?");
				
				String dni = sc.next();
				int j = 1;
				JAXBContext context = JAXBContext.newInstance(LlistaNomines.class);
				Unmarshaller unmarshaller = context.createUnmarshaller();
				LlistaNomines llistaN = (LlistaNomines) unmarshaller
						.unmarshal(new File(File.listRoots()[0] + "/Nomines/" + dni + "/" + year + "/" + month
								+ "/" + dni + "_" + month + "_" + year + "_" + j + ".xml"));

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

					System.out.println("Fitxer Creat a la ruta :" + ruta.getAbsolutePath());
					docpdf.open();

					docpdf.add(new Paragraph("---------------DELIVERASS-------------"));
					docpdf.add(new Paragraph("                                        	"));
					docpdf.add(new Paragraph("  ID: " + n.getIDNomina() + "           	"));
					docpdf.add(new Paragraph("  Repartidor: " + n.getDNIRep() + "     	"));
					docpdf.add(new Paragraph("  Nom: " + n.getNom() + "			     	"));
					docpdf.add(new Paragraph("  Km inici: " + n.getKmInicials() + "     "));
					docpdf.add(new Paragraph("  Km finals: " + n.getKmFinals() + "      "));
					docpdf.add(new Paragraph("  Km totals: " + n.getKmTotals() + "      "));
					docpdf.add(new Paragraph("  Primer dia: " + n.getDiaInici() + "     "));
					docpdf.add(new Paragraph("  Ultim dia: " + n.getDiaFinal() + "      "));
					docpdf.add(new Paragraph("  Salari: " + n.getSouDia() + "           "));
					docpdf.add(new Paragraph("                                        	"));
					docpdf.add(new Paragraph("------------------------------------------------"));

					docpdf.close();
					pfw.close();

					System.out.println("Fitxer Creat a la ruta :" + ruta.getAbsolutePath());

				}
			} catch (JAXBException e) {
				e.printStackTrace();
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	public static void main(String[] args) {

		Thread cx = new CrearXml();
		Thread cn = new CrearPdf();

		cx.start();
		cn.start();

	}

}
