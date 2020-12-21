package Controller;

import java.io.StringWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Bean.O_ATUALIZAVELOZ;
import Bean.STRACORDOSVELOZ;
import Bean.STRTITACORDVELOZ;
import DAO.DSconexao;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;

public class AcordoController {

	public static O_ATUALIZAVELOZ enviosFieb(String filial) throws SQLException {
		O_ATUALIZAVELOZ atualiza = new O_ATUALIZAVELOZ();

		atualiza.setE1_ACORDOS(consultaAcordos(filial));
		atualiza.setE1_TITULOS(consultaTitulos(filial));

		return atualiza;
	}

	public static List<STRACORDOSVELOZ> consultaAcordos(String filial) throws SQLException {
		DSconexao data = new DSconexao();

		String query = " SELECT  NUMACO,DTCACO,TOTACO,TPGPAR,VLRPAR,DTVPAR,QPAACO FROM EJCBACO";
		query += " left join ejcbpar on CREPAR = CREACO AND NACPAR = NUMACO";
		query += " WHERE CREACO = 53 AND DTCACO = '10/20/2020'";
		// query += " AND FILIAL = " + filial;

		PreparedStatement ps = data.getConnection().prepareStatement(query);

		ResultSet result = ps.executeQuery(query);
		ArrayList<STRACORDOSVELOZ> acordos = new ArrayList<STRACORDOSVELOZ>();
		while (result.next()) {
			STRACORDOSVELOZ acordo = new STRACORDOSVELOZ();

			acordo.setZ06_ACORDO(result.getString("NUMACO"));
			acordo.setZ06_DATA(result.getString("DTCACO"));
			acordo.setZ06_TPPGTO(result.getString("TPGPAR"));
			acordo.setZ06_VACORD(result.getFloat("TOTACO"));
			acordo.setZ06_VALOR(result.getFloat("VLRPAR"));
			acordo.setZ06_VENCTO(result.getString("DTVPAR"));

			acordos.add(acordo);
		}

		ps.close();
		data.getConnection().close();
		return acordos;
	}

	public static List<STRTITACORDVELOZ> consultaTitulos(String filial) throws SQLException {
		DSconexao data = new DSconexao();

		String query = "SELECT ";
		query += " CPFTIT,ACOTIT,NUMTIT, AMOCLI,'S',PROCLI  FROM EJCBACO";
		query += " left join ejcbCLI on CONCLI = CONACO AND CRTCLI = CRTACO AND CRECLI = CREACO";
		query += " left join ejcbtit ON ACOTIT = NUMACO AND CRETIT = CREACO";
		query += " WHERE CREACO = 53 AND DTCACO = '10/20/2020'";
		// query += " AND FILIAL = "+filial;
		query += " ORDER BY NUMACO";
		PreparedStatement ps = data.getConnection().prepareStatement(query);

		ResultSet result = ps.executeQuery(query);
		ArrayList<STRTITACORDVELOZ> titulos = new ArrayList<STRTITACORDVELOZ>();
		while (result.next()) {
			STRTITACORDVELOZ titulo = new STRTITACORDVELOZ();

			titulo.setA1_CGC(result.getString("CPFTIT"));
			titulo.setE1_FSACORD(result.getString("ACOTIT"));
			String[] numParc = result.getString("NUMTIT").split("-");
			String numero = numParc[0];
			String parcela = numParc[1];
			titulo.setE1_NUM(numero);
			titulo.setE1_PARCELA(parcela);
			titulo.setE1_PREFIXO(result.getString("AMOCLI"));
			titulo.setE1_STATUS("");// Corrigir o status !!!!!!!!!!!!!!!!
			titulo.setE1_TIPO(result.getString("PROCLI"));

			titulos.add(titulo);
		}

		ps.close();
		data.getConnection().close();
		return titulos;
	}

	public String gerarXML(STRACORDOSVELOZ[] acordos) {
		// Store XML to String

		StringWriter xml = new StringWriter();
		try {
			// Create JAXB Context
			JAXBContext jaxbContext = JAXBContext.newInstance(STRACORDOSVELOZ.class);
			// Create Marshaller
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			// Required formatting??
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			// Writes XML file to file-system

			StringWriter acordoXml = new StringWriter();
			for (STRACORDOSVELOZ acordo : acordos) {
				jaxbMarshaller.marshal(acordo, acordoXml);
				xml.append(acordoXml.toString());
			}

		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return xml.toString();
	}
}
