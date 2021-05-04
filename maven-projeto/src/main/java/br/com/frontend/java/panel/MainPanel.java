package br.com.frontend.java.panel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import Bean.O_ATUALIZAVELOZ;
import Controller.AcordoController;
import Controller.TituloController;
import SOAP.SoapConsultaTitulos;
import SOAP.SoapEnviaTitulos;
import br.com.frontend.java.model.Cidade;
import br.com.frontend.java.util.DateLabelFormatter;
import br.com.frontend.java.util.Mock;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

public class MainPanel extends JFrame {

	private static final long serialVersionUID = 1L;

	private JButton exportarTituloBtn;
	private JButton exportarAcordosBtn;
	private JButton importarBtn;
	private JComboBox<Cidade> cidadeComboBox;

	private JDatePickerImpl dataInicial;
	private JDatePickerImpl dataFinal;

	// private JTable tabela;
	private JScrollPane barraRolagem;

	public MainPanel() {
		super("Front End");

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		initComponents();
		add(buildPanel());
	}

	private void initComponents() {
		exportarTituloBtn = new JButton("Exportar Titulo");
		exportarTituloBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				exportarTitulo((Cidade)cidadeComboBox.getSelectedItem(), 
						(Date)dataFinal.getModel().getValue(), 
						(Date)dataFinal.getModel().getValue());
			}
		});
		exportarAcordosBtn = new JButton("Exportar Acordo");
		exportarAcordosBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				exportarAcordo((Cidade)cidadeComboBox.getSelectedItem(), 
						(Date)dataFinal.getModel().getValue(), 
						(Date)dataFinal.getModel().getValue());
			}
		});

		importarBtn = new JButton("Importar");

		importarBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				importar();
			}
		});

		UtilDateModel modelInicial = new UtilDateModel();
		modelInicial.setValue(new Date());
		modelInicial.setSelected(true);

		UtilDateModel modelFinal = new UtilDateModel();
		modelFinal.setDate(1990, 8, 24);
		modelFinal.setSelected(true);

		JDatePanelImpl datePanelInicial = new JDatePanelImpl(modelInicial);
		JDatePanelImpl datePanelFinal = new JDatePanelImpl(modelFinal);
		dataInicial = new JDatePickerImpl(datePanelInicial, new DateLabelFormatter());
		dataFinal = new JDatePickerImpl(datePanelFinal, new DateLabelFormatter());

		cidadeComboBox = new JComboBox<>();

		for (Cidade cidade : Mock.getCidades()) {
			cidadeComboBox.addItem(cidade);
		}

		// tabela = new JTable(new ItemTabelaTableModel());
		//
		// barraRolagem = new JScrollPane(tabela);

	}

	private JPanel buildPanel() {
		FormLayout layout = new FormLayout("5dlu, pref:grow, pref, 5dlu", "1dlu, pref, pref, pref, 5dlu");

		CellConstraints constraints = new CellConstraints();
		PanelBuilder builder = new PanelBuilder(layout);

		int r = 2; // --------------------------------
		builder.add(buildFilterPanel(), constraints.xyw(2, r, 2));

		r += 1; // --------------------------------
		builder.add(importarBtn, constraints.xy(3, r));

		// r += 1; // --------------------------------
		// builder.add(barraRolagem, constraints.xyw(2, r,2));

		return builder.getPanel();
	}

	private JPanel buildFilterPanel() {
		FormLayout layout = new FormLayout(
				"5dlu, left:100dlu, 5dlu, left:pref:grow, 5dlu, left:pref:grow, 5dlu, pref, 5dlu",
				"10dlu, pref, 2dlu, pref, 10dlu");

		CellConstraints constraints = new CellConstraints();
		PanelBuilder builder = new PanelBuilder(layout);

		int r = 2; // --------------------------------
		builder.add(new JLabel("Filial"), constraints.xy(2, r));
		 builder.add(new JLabel("Data Inicial"), constraints.xy(4, r));
		 builder.add(new JLabel("Data Final"), constraints.xy(6, r));
		builder.add(exportarTituloBtn, constraints.xy(8, r));

		r += 2; // --------------------------------
		builder.add(cidadeComboBox, constraints.xy(2, r));
		 builder.add(dataInicial, constraints.xy(4, r));
		 builder.add(dataFinal, constraints.xy(6, r));
		builder.add(exportarAcordosBtn, constraints.xy(8, r));
		

		builder.setBorder(BorderFactory.createTitledBorder(" Exportação "));

		return builder.getPanel();

	}

	private void exportarAcordo(Cidade filial, Date dataInicial, Date dataFinal) {
		O_ATUALIZAVELOZ enviosFieb = new O_ATUALIZAVELOZ();

		try {
			enviosFieb = AcordoController.enviosFieb(filial.getDescricao(),  dataInicial, dataFinal);
			// carregar
			// acordos
			// e titulos

			// Metodo que envia os dados de volta(quando for atualizar os dados
			// comentar esse metodo)
			new SoapEnviaTitulos().callSoapWebService(filial.getDescricao(), "01", enviosFieb);
//			Inserir update aqui
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao tentar Exportar Acordo");
		}
	}
	
	private void exportarTitulo(Cidade filial, Date dataInicial, Date dataFinal) {
		O_ATUALIZAVELOZ enviosFieb = new O_ATUALIZAVELOZ();

		try {
			enviosFieb = TituloController.enviosFiebTitulos(filial.getDescricao(),  dataInicial, dataFinal);//
			// carrega so titulo

			// Metodo que envia os dados de volta(quando for atualizar os dados
			// comentar esse metodo)
			new SoapEnviaTitulos().callSoapWebService(filial.getDescricao(), "01", enviosFieb);
//			Inserir update aqui
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao tentar Exportar Titulo");
		}
	}
	
	
	

	private void importar() {
		String[] filiais = { "01BA", "02BA", "03BA", "04BA", "05BA" };		
		try {
			for (String filial : filiais) {
				// Metodo que pega os títulos na FIEB (quando for enviar os
				// dados de
				// volta comentar esse metodo)
				new SoapConsultaTitulos().callSoapWebService(filial, "01");
			}
			JOptionPane.showMessageDialog(null, "Dados importados");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao tentar Importar");
		}
	}
}
