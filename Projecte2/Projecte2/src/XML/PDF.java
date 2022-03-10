package XML;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilderFactory;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class PDF {

	public static final Date dateyear = new Date();
	public static final SimpleDateFormat formatteryear = new SimpleDateFormat("yyyy");
	public static final String year = formatteryear.format(dateyear);
	public static final Date datemonth = new Date();
	public static final SimpleDateFormat formattermonth = new SimpleDateFormat("M");
	public static final String month = formattermonth.format(datemonth);

	public static void main(String[] args) throws DocumentException, FileNotFoundException {

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

				System.out.println("Fitxer Creat a la ruta :" + ruta.getAbsolutePath());
				docpdf.open();

				docpdf.add(new Paragraph("---------------DELIVERASS-------------"));
				docpdf.add(new Paragraph("                                            "));
				docpdf.add(new Paragraph("  ID: " + n.getIDNomina() + "               "));
				docpdf.add(new Paragraph("  Repartidor: " + n.getDNIRep() + "         "));
				docpdf.add(new Paragraph("  KmInici: " + n.getKmInicials()+ "         "));
				docpdf.add(new Paragraph("  KmFinals: " + n.getKmFinals()+"           ")  );
				docpdf.add(new Paragraph("  KmDia: " + n.getKmDia() +"                "));
				docpdf.add(new Paragraph("  Dia: " + n.getDia()+"                     "));
				docpdf.add(new Paragraph("  SouDiari: " + n.getSouDia()+"             "));
				docpdf.add(new Paragraph("                                            "));
				docpdf.add(new Paragraph("------------------------------------------------"));

				docpdf.close();
				pfw.close();

				System.out.println("Fitxer Creat a la ruta :" + ruta.getAbsolutePath());

			}
		} catch (JAXBException e) {
			e.printStackTrace();
		}

	}

}
