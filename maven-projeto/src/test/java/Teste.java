import Controller.AcordoController;
import SOAP.SoapConsultaTitulos;

public class Teste {

	public static void main(String[] args) throws Exception {
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
		new SoapConsultaTitulos().callSoapWebService("03BA", "01");
	}


}
