package M9;

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

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Main {

	public static void main(String[] args) {
		try {
			escriurePaquets();
			escriurePaquetsClients();
		} catch (ClassNotFoundException | JAXBException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void escriurePaquets() throws ClassNotFoundException, JAXBException, SQLException {
		JAXBContext context = JAXBContext.newInstance(llistaPaquets.class);
		Marshaller marshaller = context.createMarshaller();
		llistaPaquets llistaPaquets = new llistaPaquets();
		ArrayList<paquet> pedidos = new ArrayList();

		llistaPaquets.setNombre("prova");

		Class.forName("org.postgresql.Driver");
		try (Connection connection = DriverManager.getConnection("jdbc:postgresql://192.168.1.203:5432/Deliverass",
				"postgres", "Fat/3232")) {

			String select = "SELECT * from \"DEL_paquets\" WHERE estat = 'false' ORDER BY intents Desc limit 10;";

			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery(select);

			while (rs.next()) {
				paquet paquet1 = new paquet();
				paquet1.setIdEnviament(rs.getInt("id_enviament"));
				paquet1.setLatitud(rs.getString("latitud"));
				paquet1.setLongitud(rs.getString("longitud"));
				paquet1.setPes(rs.getString("pes"));
				paquet1.setCp(rs.getInt("cp"));
				paquet1.setData(rs.getDate("data"));
				paquet1.setEstat(rs.getString("estat"));
				paquet1.setDataEntrega(rs.getString("data_entrega"));
				paquet1.setDireccio(rs.getString("direccio"));
				paquet1.setDataIntent1(rs.getDate("dataintent1"));
				paquet1.setDataIntent2(rs.getDate("dataintent2"));
				paquet1.setDniRep(rs.getString("dnirep"));
				paquet1.setDniCli(rs.getString("dnicli"));
				paquet1.setIntents(rs.getInt("intents"));
				pedidos.add(paquet1);
				llistaPaquets.setPaquets(pedidos);
			}

			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			marshaller.marshal(llistaPaquets, System.out);
			marshaller.marshal(llistaPaquets, new FileWriter("paquets.xml"));
		} catch (JAXBException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void escriurePaquetsClients() {
		// COnnexio a la base de dades
		try (Connection connection = DriverManager.getConnection("jdbc:postgresql://192.168.1.203:5432/Deliverass",
				"postgres", "Fat/3232")) {

			Statement stCli = connection.createStatement();
			String selectCli = "SELECT * FROM \"DEL_usuaris\" WHERE tipo = 1";
			ResultSet rsCli = stCli.executeQuery(selectCli);

			while (rsCli.next()) {
				String Client = rsCli.getString("dni");
				Statement st = connection.createStatement();

				String select = "SELECT * FROM \"DEL_paquets\" WHERE estat=\'false\' AND dnicli = '" + Client + "';";
				ResultSet rs = st.executeQuery(select);

				// Creacio del document xml
				DocumentBuilderFactory df = DocumentBuilderFactory.newInstance();
				DocumentBuilder db = df.newDocumentBuilder();
				Document document = db.newDocument();

				// Arrel
				Element arrel = document.createElement("deliverass");
				document.appendChild(arrel);

				// El query de la seleccio de dades

				while (rs.next()) {
					boolean estat1 = rs.getBoolean("estat");
					String DniCli = rs.getString("dnicli");
					String DniRep = rs.getString("dnirep");
					int idEnv = rs.getInt("id_enviament");
					int x = 0;
					String xmlFilePath = (DniCli + ".xml");

					if (estat1 == true) {
						String entregat = "Entregat";

						// Si no esta entregat, crear el xml del pedido
					} else {
						String entregat = "No entregat";

						// Pare
						Element pare = document.createElement("pedido");
						arrel.appendChild(pare);

						// Declarar id a l'element pare

						Attr id = document.createAttribute("id");
						id.setValue(rs.getString("id_enviament"));
						pare.setAttributeNode(id);

						// id_enviament/entrega
						Element idEnviament = document.createElement("id_enviament");
						idEnviament.appendChild(document.createTextNode(rs.getString("id_enviament")));
						pare.appendChild(idEnviament);

						// id_destinatari
						Element idDestinatari = document.createElement("id_destinatari");
						idDestinatari.appendChild(document.createTextNode(DniCli));
						pare.appendChild(idDestinatari);

						// id_treballador
						Element idTreballador = document.createElement("id_treballador");
						idTreballador.appendChild(document.createTextNode(DniRep));
						pare.appendChild(idTreballador);

						// direccio
						Element dir = document.createElement("direccio");
						dir.appendChild(document.createTextNode(rs.getString("direccio")));
						pare.appendChild(dir);

						// latitud
						Element latitud = document.createElement("latitud");
						latitud.appendChild(document.createTextNode(rs.getString("latitud")));
						pare.appendChild(latitud);

						// longitud
						Element longitud = document.createElement("longitud");
						longitud.appendChild(document.createTextNode(rs.getString("longitud")));
						pare.appendChild(longitud);

						// pes
						Element pes = document.createElement("pes");
						pes.appendChild(document.createTextNode(rs.getString("pes")));
						pare.appendChild(pes);

						// cp
						Element cp = document.createElement("cp");
						cp.appendChild(document.createTextNode(rs.getString("cp")));
						pare.appendChild(cp);

						// data enviament
						Element dataEnv = document.createElement("dataEnv");
						dataEnv.appendChild(document.createTextNode(rs.getString("data")));
						pare.appendChild(dataEnv);

						// estat
						Element estat = document.createElement("estat");
						estat.appendChild(document.createTextNode(entregat));
						pare.appendChild(estat);

						// data entrega
						Element dataEnt = document.createElement("dataEnt");
						dataEnt.appendChild(document.createTextNode(rs.getString("data_entrega")));
						pare.appendChild(dataEnt);

						// data entrega
						Element intents = document.createElement("intents");
						intents.appendChild(document.createTextNode(rs.getString("Intents")));
						pare.appendChild(intents);

						// data entrega
						Element dataI1 = document.createElement("dataI1");
						dataI1.appendChild(document.createTextNode(rs.getString("dataintent1")));
						pare.appendChild(dataI1);

						// data entrega
						Element dataI2 = document.createElement("dataI2");
						dataI2.appendChild(document.createTextNode(rs.getString("dataintent2")));
						pare.appendChild(dataI2);

						TransformerFactory tf = TransformerFactory.newInstance();
						Transformer t = tf.newTransformer();
						DOMSource ds = new DOMSource(document);
						StreamResult sr = new StreamResult(new File( "C:\\prova\\"+xmlFilePath));

						t.transform(ds, sr);

					}
					// String xmlFilePath = (DniCli + ".xml");

					// crear l'arxiu XML
					// transformar l'objecte DOM a XML

				}
			}

			// Transformacio de les dades i document

		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException te) {
			te.printStackTrace();
		} catch (SQLException e) {
			System.out.println("Error de connexio.");
			e.printStackTrace();
		}
	}

}
