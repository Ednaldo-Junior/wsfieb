package SOAP;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import Configuracoes.Config;

public class SoapConsultaTitulos {

	public void callSoapWebService(String Filial, String Empresa) throws Exception {
		String soapAction = "http://protheus_soap/mtdTitulosVeloz";
		String soapEndpointUrl = "http://fbh101-011.fieb.org.br:8080/FFIEBW01.apw";
		//na homologaçã anterior não tinha o ?WSDL, eu inserir hoje 
	    
	    //                  	http://fbh101-011.fieb.org.br:8080/FFIEBW01.apw?WSDL

		try {
			// Create SOAP Connection  
			SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
			SOAPConnection soapConnection = soapConnectionFactory.createConnection();
			// Send SOAP Message to SOAP Server
			SOAPMessage soapResponse = soapConnection.call(createSOAPRequest(soapAction, Filial, Empresa),
					soapEndpointUrl);
			// Print the SOAP Response
			soapResponse.getAttachments();
			System.out.println("Response SOAP Message:");
			soapResponse.writeTo(System.out);
			System.out.println();
			respostaConsulta(soapResponse, Filial);
			soapConnection.close();
		} catch (Exception e) {
			System.err.println(
					"\nError occurred while sending SOAP Request to Server!\nMake sure you have the correct endpoint URL and SOAPAction!\n");
			e.printStackTrace();
			throw e;
		}
	}

	private void respostaConsulta(SOAPMessage soapResponse, String Filial)
			throws SOAPException, ParserConfigurationException, SAXException, IOException,
			TransformerFactoryConfigurationError, TransformerException {
		NodeList list = soapResponse.getSOAPBody().getElementsByTagName("MTDTITULOSVELOZRESULT");
		Node item = soapResponse.getSOAPBody().getElementsByTagName("STRTITULOSVELOZ").item(0);
		if (item != null) {
			NodeList cab = item.getChildNodes();
			String result = "";
			for (int i = 0; i < cab.getLength(); i++) {
				result += cab.item(i).getNodeName();
				if (i != cab.getLength() - 1)
					result += ";";
			}
			result += "\n";
			for (int i = 0; i < list.getLength(); i++) {
				Node lin = list.item(i);
				NodeList it = lin.getChildNodes();
				for (int j = 0; j < it.getLength(); j++) {
					Node linha = it.item(j);
					NodeList itens = linha.getChildNodes();
					for (int k = 0; k < itens.getLength(); k++) {
						result += itens.item(k).getTextContent();
						if (k != itens.getLength() - 1)
							result += ";";

					}
					result += "\n";
				}
			}

			String pathname = Config.CAMINHOCSV + "INC_" + Filial + ".csv";
			File file = new File(pathname);
			System.out.print("Arquivo salvo na pasta:"+pathname);

			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
			bw.write(result);
			bw.flush();
			bw.close();
		}
	}

	private static void createSoapEnvelope(SOAPMessage soapMessage, String Filial, String Empresa)
			throws SOAPException {
		SOAPPart soapPart = soapMessage.getSOAPPart();

		String myNamespace = "prot";
		String myNamespaceURI = "http://protheus_soap/";
		// SOAP Envelope
		SOAPEnvelope envelope = soapPart.getEnvelope();
		envelope.addNamespaceDeclaration(myNamespace, myNamespaceURI);

		// SOAP Body
		SOAPBody soapBody = envelope.getBody();
		SOAPElement soapBodyElem0 = soapBody.addChildElement("MTDTITULOSVELOZ", myNamespace);
		SOAPElement soapBodyElem = soapBodyElem0.addChildElement("O_SEGURANCA", myNamespace);
		SOAPElement soapBodyElem1 = soapBodyElem.addChildElement("C_SENHA", myNamespace);
		soapBodyElem1.addTextNode(Config.SENHA);
		SOAPElement soapBodyElem2 = soapBodyElem.addChildElement("C_USUARIO", myNamespace);
		soapBodyElem2.addTextNode(Config.USUARIO);
		SOAPElement soapBodyElem3 = soapBodyElem0.addChildElement("O_EMPRESA", myNamespace);
		SOAPElement soapBodyElem4 = soapBodyElem3.addChildElement("C_EMPRESA", myNamespace);
		soapBodyElem4.addTextNode(Empresa);
		SOAPElement soapBodyElem5 = soapBodyElem3.addChildElement("C_FILIAL", myNamespace);
		soapBodyElem5.addTextNode(Filial);
	}

	private static SOAPMessage createSOAPRequest(String soapAction, String Filial, String Empresa) throws Exception {
		String PREFERRED_PREFIX = "soapenv";

		MessageFactory messageFactory = MessageFactory.newInstance();
		SOAPMessage soapMessage = messageFactory.createMessage();
		SOAPPart soapPart = soapMessage.getSOAPPart();
		SOAPEnvelope envelope = soapPart.getEnvelope();
		SOAPHeader header = soapMessage.getSOAPHeader();
		header.setTextContent("text/xml");
		SOAPBody body = soapMessage.getSOAPBody();
		envelope.removeNamespaceDeclaration(envelope.getPrefix());
		envelope.addNamespaceDeclaration(PREFERRED_PREFIX, "http://schemas.xmlsoap.org/soap/envelope/");
		envelope.setPrefix(PREFERRED_PREFIX);
		header.setPrefix(PREFERRED_PREFIX);
		body.setPrefix(PREFERRED_PREFIX);

		createSoapEnvelope(soapMessage, Filial, Empresa);

		soapMessage.getMimeHeaders().addHeader("SOAPAction", soapAction);

		soapMessage.saveChanges();

		/* Print the request message, just for debugging purposes */
		System.out.println("Request SOAP Message:");
		soapMessage.writeTo(System.out);
		System.out.println("\n");

		return soapMessage;
	}

}
