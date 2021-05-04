import Bean.O_ATUALIZAVELOZ;
import Controller.AcordoController;
import SOAP.SoapEnviaTitulos;
import SOAP.SoapConsultaTitulos;

public class Teste {

	public static void main(String[] args) throws Exception {
		String[] filiais = { "01BA", "02BA", "03BA", "04BA", "05BA" };

		for (String filial : filiais) {

			new SoapConsultaTitulos().callSoapWebService(filial, "01");
			O_ATUALIZAVELOZ enviosFieb = new O_ATUALIZAVELOZ();

			enviosFieb = AcordoController.enviosFieb(filial);// precisa passar a
																// filial e as
																// datas para o
																// where das
																// consultas

			new SoapEnviaTitulos().callSoapWebService(filial, "01", enviosFieb);
		}

	}

}
