package SOAP;

import java.io.StringReader;

import javax.xml.namespace.QName;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPEnvelope;
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
		dispatch.getRequestContext().put(Dispatch.SOAPACTION_USE_PROPERTY, new Boolean(true));
		dispatch.getRequestContext().put(Dispatch.SOAPACTION_URI_PROPERTY, soapActionUri);

		String guiaForCDATA = "";

		guiaForCDATA += "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
				+ "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:SOAP-ENC=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">"
				+ "	<SOAP-ENV:Body>";

		guiaForCDATA += getXml();

		guiaForCDATA += "	</SOAP-ENV:Body> </SOAP-ENV:Envelope>";

		/** Create SOAPMessage request. **/
		// compose a request message
		MessageFactory messageFactory = MessageFactory.newInstance();
		SOAPMessage message = messageFactory.createMessage();

		// Create objects for the message parts
		SOAPPart soapPart = message.getSOAPPart();
		SOAPEnvelope envelope = soapPart.getEnvelope();
		SOAPBody body = envelope.getBody();
		body.setValue(guiaForCDATA);
		// Populate the Message. In here, I populate the message from a xml file
		System.out.println(guiaForCDATA);
		StreamSource preppedMsgSrc = new StreamSource(new StringReader(guiaForCDATA));
		soapPart.setContent(preppedMsgSrc);

		// Save the message
		message.saveChanges();

		System.out.println(message.getSOAPBody().getFirstChild().getTextContent());

		SOAPMessage response = (SOAPMessage) dispatch.invoke(message); // the error is here
		return response;

	}
	
}
