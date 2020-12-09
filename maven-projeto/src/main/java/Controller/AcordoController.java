package Controller;

import java.io.StringWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Bean.STRACORDOSVELOZ;
import DAO.DSconexao;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;

public class AcordoController {

	public ArrayList<STRACORDOSVELOZ> consultaAcordos() throws SQLException {
		DSconexao data = new DSconexao();

		String query = " SELECT  NUMACO,DTCACO,TOTACO,TPGPAR,VLRPAR,DTVPAR,QPAACO FROM EJCBACO";
               query = query + " left join ejcbpar on CREPAR = CREACO AND NACPAR = NUMACO";
               query = query + " WHERE CREACO = 53 AND DTCACO = '10/20/2020'";
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
			for(STRACORDOSVELOZ acordo : acordos){
				jaxbMarshaller.marshal(acordo, acordoXml);
				xml.append(acordoXml.toString());
			}	

		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return xml.toString();
	}	
}


