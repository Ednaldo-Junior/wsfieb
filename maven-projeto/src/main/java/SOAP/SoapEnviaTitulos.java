package SOAP;

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

import Bean.O_ATUALIZAVELOZ;
import Bean.STRACORDOSVELOZ;
import Bean.STRTITACORDVELOZ;
import Configuracoes.Config;

public class SoapEnviaTitulos {

	public void callSoapWebService(String Filial, String Empresa, O_ATUALIZAVELOZ atualizacao) {
		String soapAction = "http://protheus_soap/mtdAtualizaVeloz";
		String soapEndpointUrl = "http://fbh101-011.fieb.org.br:8080/FFIEBW01.apw";
		try {
			// Create SOAP Connection
			SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
			SOAPConnection soapConnection = soapConnectionFactory.createConnection();
			// Send SOAP Message to SOAP Server
			SOAPMessage soapResponse = soapConnection.call(createSOAPRequest(soapAction, Filial, Empresa, atualizacao),
					soapEndpointUrl);
			// Print the SOAP Response
			soapResponse.getAttachments();
			System.out.println("Response SOAP Message:");
			soapResponse.writeTo(System.out);
			System.out.println();
			soapResponse.getSOAPBody().getElementsByTagName("faultstring").item(0).getTextContent();
			soapConnection.close();
		} catch (Exception e) {
			System.err.println(
					"\nError occurred while sending SOAP Request to Server!\nMake sure you have the correct endpoint URL and SOAPAction!\n");
			e.printStackTrace();
		}
	}

	private static void createSoapEnvelope(SOAPMessage soapMessage, String Filial, String Empresa,
			O_ATUALIZAVELOZ atualizacao) throws SOAPException {
		SOAPPart soapPart = soapMessage.getSOAPPart();

		String myNamespace = "prot";
		String myNamespaceURI = "http://protheus_soap/";
		// SOAP Envelope
		SOAPEnvelope envelope = soapPart.getEnvelope();
		envelope.addNamespaceDeclaration(myNamespace, myNamespaceURI);

		// SOAP Body
		// Cabeçalho
		SOAPBody soapBody = envelope.getBody();
		SOAPElement soapBodyMetodo = soapBody.addChildElement("MTDATUALIZAVELOZ", myNamespace);
		SOAPElement soapBodySeguranca = soapBodyMetodo.addChildElement("O_SEGURANCA", myNamespace);
		SOAPElement soapBodySenha = soapBodySeguranca.addChildElement("C_SENHA", myNamespace);
		soapBodySenha.addTextNode(Config.SENHA);
		SOAPElement soapBodyUsuario = soapBodySeguranca.addChildElement("C_USUARIO", myNamespace);
		soapBodyUsuario.addTextNode(Config.USUARIO);
		SOAPElement soapBodyEmpresa = soapBodyMetodo.addChildElement("O_EMPRESA", myNamespace);
		SOAPElement soapBodyCdEmpresa = soapBodyEmpresa.addChildElement("C_EMPRESA", myNamespace);
		soapBodyCdEmpresa.addTextNode(Empresa);
		SOAPElement soapBodyFilial = soapBodyEmpresa.addChildElement("C_FILIAL", myNamespace);
		soapBodyFilial.addTextNode(Filial);
		// Atualizações
		SOAPElement soapBodyAtualizacao = soapBodyMetodo.addChildElement("O_ATUALIZAVELOZ", myNamespace);
		SOAPElement soapBodyAcordos = soapBodyAtualizacao.addChildElement("E1_ACORDOS", myNamespace);
		for (STRACORDOSVELOZ acordo : atualizacao.getE1_ACORDOS()) {
			SOAPElement soapBodyZ06_ACORDO = soapBodyAcordos.addChildElement("Z06_ACORDO", myNamespace);
			soapBodyZ06_ACORDO.addTextNode(acordo.getZ06_ACORDO());
			SOAPElement soapBodyZ06_DATA = soapBodyAcordos.addChildElement("Z06_DATA", myNamespace);
			soapBodyZ06_DATA.addTextNode(acordo.getZ06_DATA());
			SOAPElement soapBodyZ06_TPPGTO = soapBodyAcordos.addChildElement("Z06_TPPGTO", myNamespace);
			soapBodyZ06_TPPGTO.addTextNode(acordo.getZ06_TPPGTO());
			SOAPElement soapBodyZ06_VACORD = soapBodyAcordos.addChildElement("Z06_VACORD", myNamespace);
			soapBodyZ06_VACORD.addTextNode(acordo.getZ06_VACORD().toString());
			SOAPElement soapBodyZ06_VALOR = soapBodyAcordos.addChildElement("Z06_VALOR", myNamespace);
			soapBodyZ06_VALOR.addTextNode(acordo.getZ06_VALOR().toString());
			SOAPElement soapBodyZ06_VENCTO = soapBodyAcordos.addChildElement("Z06_VENCTO", myNamespace);
			soapBodyZ06_VENCTO.addTextNode(acordo.getZ06_VENCTO());
		}
		SOAPElement soapBodyTitAcordo = soapBodyAtualizacao.addChildElement("STRTITACORDVELOZ", myNamespace);
		for (STRTITACORDVELOZ titulo : atualizacao.getE1_TITULOS()) {
			SOAPElement soapBodyA1_CGC = soapBodyTitAcordo.addChildElement("A1_CGC", myNamespace);
			soapBodyA1_CGC.addTextNode(titulo.getA1_CGC());
			SOAPElement soapBodyE1_FSACORD = soapBodyTitAcordo.addChildElement("E1_FSACORD", myNamespace);
			soapBodyE1_FSACORD.addTextNode(titulo.getE1_FSACORD());
			SOAPElement soapBodyE1_NUM = soapBodyTitAcordo.addChildElement("E1_NUM", myNamespace);
			soapBodyE1_NUM.addTextNode(titulo.getE1_NUM());
			SOAPElement soapBodyE1_PARCELA = soapBodyTitAcordo.addChildElement("E1_PARCELA", myNamespace);
			soapBodyE1_PARCELA.addTextNode(titulo.getE1_PARCELA());
			SOAPElement soapBodyE1_PREFIXO = soapBodyTitAcordo.addChildElement("E1_PREFIXO", myNamespace);
			soapBodyE1_PREFIXO.addTextNode(titulo.getE1_PREFIXO());
			SOAPElement soapBodyE1_STATUS = soapBodyTitAcordo.addChildElement("E1_STATUS", myNamespace);
			soapBodyE1_STATUS.addTextNode(titulo.getE1_STATUS());
			SOAPElement soapBodyE1_TIPO = soapBodyTitAcordo.addChildElement("E1_TIPO", myNamespace);
			soapBodyE1_TIPO.addTextNode(titulo.getE1_TIPO());
		}
	}

	private static SOAPMessage createSOAPRequest(String soapAction, String Filial, String Empresa,
			O_ATUALIZAVELOZ atualizacao) throws Exception {
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

		createSoapEnvelope(soapMessage, Filial, Empresa, atualizacao);

		soapMessage.getMimeHeaders().addHeader("SOAPAction", soapAction);

		soapMessage.saveChanges();

		/* Print the request message, just for debugging purposes */
		System.out.println("Request SOAP Message:");
		soapMessage.writeTo(System.out);
		System.out.println("\n");

		return soapMessage;
	}

}
