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
import Bean.ResultadoAtualizacao;
import Bean.STRACORDOSVELOZ;
import Bean.STRTITACORDVELOZ;
import Configuracoes.Config;

public class SoapEnviaTitulos {

	public ResultadoAtualizacao callSoapWebService(String Filial, String Empresa, O_ATUALIZAVELOZ atualizacao) {
		ResultadoAtualizacao result = new ResultadoAtualizacao();
		String soapAction = "http://protheus_soap/mtdAtualizaVeloz";
		String soapEndpointUrl = "http://fbh101-011.fieb.org.br:8080/FFIEBW01.apw";
		//http://fbh101-011.fieb.org.br:8080/FFIEBW01.apw?WSDL
		try {
			// Create SOAP Connection
			SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
			SOAPConnection soapConnection = soapConnectionFactory.createConnection();
			// Send SOAP Message to SOAP Server
//			createSOAPRequest(soapAction, Filial, Empresa, atualizacao);
			SOAPMessage soapResponse = soapConnection.call(createSOAPRequest(soapAction, Filial, Empresa, atualizacao),
					soapEndpointUrl);
			// Print the SOAP Response
			soapResponse.getAttachments();
			System.out.println("Response SOAP Message:");
			soapResponse.writeTo(System.out);
			System.out.println();
			try {
			result.setL_STATUS(soapResponse.getSOAPBody().getElementsByTagName("L_STATUS").item(0).getTextContent());
			result.setC_MENSAGEM(soapResponse.getSOAPBody().getElementsByTagName("C_MENSAGEM").item(0).getTextContent());
			System.out.println(result.getC_MENSAGEM());
			soapConnection.close();
			} catch (Exception e) {
				result.setL_STATUS("Erro no servidor");
				result.setC_MENSAGEM(soapResponse.getSOAPBody().getElementsByTagName("faultstring").item(0).getTextContent());
				throw new Exception("Erro interno no webservice: "+ result.getC_MENSAGEM()); // Erro interno no webservice
				
				//será qe teria que colocar o ?wsdl
			}
		} catch (Exception e) {
			System.err.println(
					"\nError occurred while sending SOAP Request to Server!\nMake sure you have the correct endpoint URL and SOAPAction!\n");
			e.printStackTrace();
		}
		return result;
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
			SOAPElement soapBodySTRACORDOSVELOZ = soapBodyAcordos.addChildElement("STRACORDOSVELOZ", myNamespace);
			SOAPElement soapBodyZ06_ACORDO = soapBodySTRACORDOSVELOZ.addChildElement("Z06_ACORDO", myNamespace);
			soapBodyZ06_ACORDO.addTextNode(acordo.getZ06_ACORDO());
			SOAPElement soapBodyZ06_FILIAL = soapBodySTRACORDOSVELOZ.addChildElement("Z06_FILIAL", myNamespace);
			soapBodyZ06_FILIAL.addTextNode(acordo.getZ06_FILIAL());			
			SOAPElement soapBodyZ06_DATA = soapBodySTRACORDOSVELOZ.addChildElement("Z06_DATA", myNamespace);
			soapBodyZ06_DATA.addTextNode(acordo.getZ06_DATA());
			SOAPElement soapBodyZ06_TPPGTO = soapBodySTRACORDOSVELOZ.addChildElement("Z06_TPPGTO", myNamespace);
			soapBodyZ06_TPPGTO.addTextNode(acordo.getZ06_TPPGTO());
			SOAPElement soapBodyZ06_VACORD = soapBodySTRACORDOSVELOZ.addChildElement("Z06_VACORD", myNamespace);
			soapBodyZ06_VACORD.addTextNode(acordo.getZ06_VACORD().toString());
			SOAPElement soapBodyZ06_VALOR = soapBodySTRACORDOSVELOZ.addChildElement("Z06_VALOR", myNamespace);
			soapBodyZ06_VALOR.addTextNode(acordo.getZ06_VALOR().toString());
			SOAPElement soapBodyZ06_VENCTO = soapBodySTRACORDOSVELOZ.addChildElement("Z06_VENCTO", myNamespace);
			soapBodyZ06_VENCTO.addTextNode(acordo.getZ06_VENCTO());
		}
		SOAPElement soapBodyE1_TITULOS = soapBodyAtualizacao.addChildElement("E1_TITULOS", myNamespace);
		for (STRTITACORDVELOZ titulo : atualizacao.getE1_TITULOS()) {
			SOAPElement soapBodyTitAcordo = soapBodyE1_TITULOS.addChildElement("STRTITACORDVELOZ", myNamespace);
			SOAPElement soapBodyA1_CGC = soapBodyTitAcordo.addChildElement("A1_CGC", myNamespace);
			soapBodyA1_CGC.addTextNode(titulo.getA1_CGC());
			SOAPElement soapBodyE1_FSACORD = soapBodyTitAcordo.addChildElement("E1_FSACORD", myNamespace);
			soapBodyE1_FSACORD.addTextNode(titulo.getE1_FSACORD());
			SOAPElement soapBodyE1_FILIAL = soapBodyTitAcordo.addChildElement("E1_FILIAL", myNamespace);
			soapBodyE1_FILIAL.addTextNode(titulo.getE1_FILIAL());
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
