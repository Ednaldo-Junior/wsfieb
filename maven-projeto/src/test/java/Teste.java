import java.sql.SQLException;
import java.util.ArrayList;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPMessage;

import Bean.STRACORDOSVELOZ;
import Controller.AcordoController;
import DAO.O_ATUALIZAVELOZ;
import SOAP.SoapChamadas;

public class Teste {

	public static void main(String[] args) throws SQLException {
		AcordoController acordoController = new AcordoController();
//		O_TITULOSVELOZ tituloVeloz = new O_TITULOSVELOZ();
		
//		O_ATUALIZAVELOZ acordosVeloz = new O_ATUALIZAVELOZ();
//
//		ArrayList<STRACORDOSVELOZ> consultaAcordos = acordoController.consultaAcordos();
//		STRACORDOSVELOZ[] acordos = new STRACORDOSVELOZ[consultaAcordos.size()]	;
//        consultaAcordos.toArray(acordos);
        
		//acordosVeloz.setSTRTITULOSVELOZ(acordos);
		
//		STRTITULOSVELOZ[] titulos = (STRTITULOSVELOZ[]) consultaAcordos().toArray();
//		tituloVeloz.setSTRTITULOSVELOZ(titulos);
		
//		consultaAcordos();

//		String xml = acordoController.gerarXML(acordos);

//		gerarSoap(xml);
//        System.out.print(xml);
//		enviarSoap();
        enviarSoap();	     	     
	}

	private static void enviarSoap() {
		SoapChamadas chamada = new SoapChamadas();
        String targetNameSpace = "http://protheus_soap/";
        String endpointUrl = "http://fbh101-011.fieb.org.br:8080/FFIEBW01.apw?WSDL";
        QName serviceName = new QName(targetNameSpace, "mtdTitulosVeloz");
        QName portName = new QName(targetNameSpace, "mtdTitulosVeloz");
        String SOAPAction = "http://protheus_soap/mtdTitulosVeloz";
   	
        SOAPMessage response;
        
        try {
               response = chamada.invoke(serviceName, portName, endpointUrl, SOAPAction);
               if (response.getSOAPBody().hasFault()) {
                   System.out.println(response.getSOAPBody().getFault());
               } else {
                   System.out.println("ok");
               }
           } catch (Exception ex) {
               ex.printStackTrace();
           }
	}
	
	

}
