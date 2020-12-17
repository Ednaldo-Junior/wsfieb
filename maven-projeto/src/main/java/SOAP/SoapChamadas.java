package SOAP;

import java.io.StringReader;

import javax.xml.namespace.QName;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.transform.stream.StreamSource;
import javax.xml.ws.Dispatch;
import javax.xml.ws.Service;
import javax.xml.ws.soap.SOAPBinding;

public class SoapChamadas {

	public SOAPMessage invoke(QName serviceName, QName portName, String endpointUrl, String soapActionUri)
			throws Exception {
		/** Create a service and add at least one port to it. **/
		Service service = Service.create(serviceName);
		service.addPort(portName, SOAPBinding.SOAP11HTTP_BINDING, endpointUrl);

		/** Create a Dispatch instance from a service. **/
		Dispatch dispatch = service.createDispatch(portName, SOAPMessage.class, Service.Mode.MESSAGE);

		// The soapActionUri is set here. otherwise we get a error on .net based
		// services.
		dispatch.getRequestContext().put(Dispatch.SOAPACTION_USE_PROPERTY, new Boolean(false));
		dispatch.getRequestContext().put(Dispatch.SOAPACTION_URI_PROPERTY, soapActionUri);

		String guiaForCDATA = "";

		guiaForCDATA += "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
				+ "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:SOAP-ENC=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">"
				+ "	<SOAP-ENV:Body>";

		guiaForCDATA += "<O_SEGURANCA>\\r\\n\"\r\n"
				+ "				+ \" <C_SENHA>totvs@123</C_SENHA>\\r\\n\"\r\n"
				+ "				+ \" <C_USUARIO>totvs_fieb</C_USUARIO>\\r\\n\"\r\n"
				+ "				+ \" </O_SEGURANCA>\\r\\n\"\r\n"
				+ "				+ \" <O_EMPRESA>\\r\\n\"\r\n"
				+ "				+ \" <C_EMPRESA>01</C_EMPRESA>\\r\\n\"\r\n"
				+ "				+ \" <C_FILIAL>02BA</C_FILIAL>\\r\\n\"\r\n"
				+ "				+ \" </O_EMPRESA>";

		guiaForCDATA += "	</SOAP-ENV:Body> </SOAP-ENV:Envelope>";

		/** Create SOAPMessage request. **/
		// compose a request message
		MessageFactory messageFactory = MessageFactory.newInstance();
		SOAPMessage message = messageFactory.createMessage();

		// Create objects for the message parts
		StreamSource preppedMsgSrc = new StreamSource(new StringReader(guiaForCDATA));
		message.getSOAPPart().setValue(guiaForCDATA);
		message.saveChanges();

		System.out.println(message.getSOAPBody().getFirstChild().getTextContent());

		SOAPMessage response = (SOAPMessage) dispatch.invoke(message); // the error is here
		return response;

	}
	
	
	
	
	 public static void main(String args[]) {
	        /*
	            The example below requests from the Web Service at:
	             http://www.webservicex.net/uszip.asmx?op=GetInfoByCity


	            To call other WS, change the parameters below, which are:
	             - the SOAP Endpoint URL (that is, where the service is responding from)
	             - the SOAP Action

	            Also change the contents of the method createSoapEnvelope() in this class. It constructs
	             the inner part of the SOAP envelope that is actually sent.
	         */
	        String soapEndpointUrl = "http://fbh101-011.fieb.org.br:8080/FFIEBW01.apw";
	        String soapAction = "http://protheus_soap/mtdTitulosVeloz";

	        callSoapWebService(soapEndpointUrl, soapAction);
	    }

	    private static void createSoapEnvelope(SOAPMessage soapMessage) throws SOAPException {
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
	        soapBodyElem1.addTextNode("totvs@123");
	        SOAPElement soapBodyElem2 = soapBodyElem.addChildElement("C_USUARIO", myNamespace);
	        soapBodyElem2.addTextNode("totvs_fieb");
	        SOAPElement soapBodyElem3 = soapBodyElem0.addChildElement("O_EMPRESA", myNamespace);
	        SOAPElement soapBodyElem4 = soapBodyElem3.addChildElement("C_EMPRESA", myNamespace);
	        soapBodyElem4.addTextNode("01");
	        SOAPElement soapBodyElem5 = soapBodyElem3.addChildElement("C_FILIAL", myNamespace);
	        soapBodyElem5.addTextNode("02BA");
	    }

	    private static void callSoapWebService(String soapEndpointUrl, String soapAction) {
	        try {
	            // Create SOAP Connection
	            SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
	            SOAPConnection soapConnection = soapConnectionFactory.createConnection();
	            // Send SOAP Message to SOAP Server
	            SOAPMessage soapResponse = soapConnection.call(createSOAPRequest(soapAction), soapEndpointUrl);
	            // Print the SOAP Response
	            soapResponse.getAttachments();
	            System.out.println("Response SOAP Message:");
	            soapResponse.writeTo(System.out);
	            System.out.println();

	            soapConnection.close();
	        } catch (Exception e) {
	            System.err.println("\nError occurred while sending SOAP Request to Server!\nMake sure you have the correct endpoint URL and SOAPAction!\n");
	            e.printStackTrace();
	        }
	    }

	    private static SOAPMessage createSOAPRequest(String soapAction) throws Exception {
	    	String PREFERRED_PREFIX = "soapenv";
	    	
	        MessageFactory messageFactory = MessageFactory.newInstance();
	        SOAPMessage soapMessage = messageFactory.createMessage();
	        SOAPPart soapPart = soapMessage.getSOAPPart();
	        SOAPEnvelope envelope = soapPart.getEnvelope();
	        SOAPHeader header = soapMessage.getSOAPHeader();
	        SOAPBody body = soapMessage.getSOAPBody();
	        envelope.removeNamespaceDeclaration(envelope.getPrefix());
	        envelope.addNamespaceDeclaration(PREFERRED_PREFIX, "http://schemas.xmlsoap.org/soap/envelope/");
	        envelope.setPrefix(PREFERRED_PREFIX);
	        header.setPrefix(PREFERRED_PREFIX);
	        body.setPrefix(PREFERRED_PREFIX);

	        createSoapEnvelope(soapMessage);

	        soapMessage.getMimeHeaders().addHeader("SOAPAction", soapAction);
	        
	        soapMessage.saveChanges();

	        /* Print the request message, just for debugging purposes */
	        System.out.println("Request SOAP Message:");
	        soapMessage.writeTo(System.out);
	        System.out.println("\n");

	        return soapMessage;
	    }
	
	
	
	
}
