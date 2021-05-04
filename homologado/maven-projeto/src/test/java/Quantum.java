import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import Bean.O_ATUALIZAVELOZ;
import Controller.AcordoController;
import Controller.TituloController;
import Paineis.MainPanel;
import SOAP.SoapConsultaTitulos;
import SOAP.SoapEnviaTitulos;

public class Quantum {

	public static void main(String[] args) throws Exception {
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} 
		catch (UnsupportedLookAndFeelException e) {
		}
		catch (ClassNotFoundException e) {
		}
		catch (InstantiationException e) {
		}
		catch (IllegalAccessException e) {
			
			//
		}

	/*	MainPanel panel = new MainPanel();
		//panel.setSize(800, 600);
		panel.pack();
		panel.setVisible(true);
		panel.setLocationRelativeTo(null);*/
		
//---------------- Inicio original -----------	
//		String[] filiais = { "01BA", "02BA", "03BA", "04BA", "05BA" };
 		String[] filiais = { "02BA"};

 		
 		//Primeiro vc atualiza os dados para processar no sistema Veloz
 		//Depois vc envia os dados já processardo
		for (String filial : filiais) {
			
			//Metodo que pega os títulos na FIEB (quando for enviar os dados de volta comentar esse metodo)
//			new SoapConsultaTitulos().callSoapWebService(filial, "01");
			
			O_ATUALIZAVELOZ enviosFieb = new O_ATUALIZAVELOZ();

			enviosFieb = AcordoController.enviosFieb(filial);// carregar acordos e titulos
			
//			enviosFieb = TituloController.enviosFiebTitulos(filial);// carrega so titulo
			
			//Metodo que envia os dados de volta(quando for atualizar os dados comentar esse metodo)
			new SoapEnviaTitulos().callSoapWebService(filial, "01", enviosFieb);

//---------------- Fim original -----------	

		}

	}

}
