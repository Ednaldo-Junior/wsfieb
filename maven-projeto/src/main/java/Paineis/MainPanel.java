package Paineis;

import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import model.Credor;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;

public class MainPanel extends JFrame{

	private static final long serialVersionUID = 1L;
	
	private JButton pesquisarBtn;
	private JButton exportarAcordosBtn;
	private JComboBox<Credor> cidadeComboBox;
	
	private JDatePickerImpl dataInicial;
	private JDatePickerImpl dataFinal;
	
	private JTable tabela;
	private JScrollPane barraRolagem;
	
	public MainPanel() {
		super("Front End");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
//		initComponents();
//		add(buildPanel());
	}

/*	private void initComponents() {
			pesquisarBtn = new JButton("Pesquisar");
		pesquisarBtn.addActionListener(new ActionListener() {
						@Override
			public void actionPerformed(ActionEvent e) {
				pesquisar(
						(Credor)cidadeComboBox.getSelectedItem(), 
						(Date)dataFinal.getModel().getValue(), 
						(Date)dataFinal.getModel().getValue());
			}
		});
		
		exportarAcordosBtn = new JButton("Exportar");
		exportarAcordosBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				exportar();
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
		/*
		cidadeComboBox = new JComboBox<>();
		for(Credor cidade : Mock.getCidades()) {
			cidadeComboBox.addItem(cidade);
		}
		
		tabela = new JTable(new ItemTabelaTableModel());
		
		barraRolagem = new JScrollPane(tabela);*/
		
//	}
	
	/*	private JPanel buildPanel() {
	    FormLayout layout = new FormLayout(
	    		"5dlu, pref:grow, pref, 5dlu", 
	    		"1dlu, pref, pref, pref, 5dlu");

	    CellConstraints constraints = new CellConstraints();
	    PanelBuilder builder = new PanelBuilder(layout);

	    int r = 2; // --------------------------------
	    builder.add(buildFilterPanel(), constraints.xyw(2, r, 2));
	    
	    r += 1; // --------------------------------
	    builder.add(exportarBtn, constraints.xy(3, r));
	    
	    r += 1; // --------------------------------
	    builder.add(barraRolagem, constraints.xyw(2, r,2));


	    return builder.getPanel();
	}
	
	private JPanel buildFilterPanel() {
		FormLayout layout = new FormLayout(
	    		"5dlu, left:100dlu, 5dlu, left:pref:grow, 5dlu, left:pref:grow, 5dlu, pref, 5dlu", 
	    		"10dlu, pref, 2dlu, pref, 10dlu");

	    CellConstraints constraints = new CellConstraints();
	    PanelBuilder builder = new PanelBuilder(layout);
	    
	    int r = 2; // --------------------------------
	    builder.add(new JLabel("Cidade"), constraints.xy(2, r));
	    builder.add(new JLabel("Data Inicial"), constraints.xy(4, r));
	    builder.add(new JLabel("Data Final"), constraints.xy(6, r));

	    r += 2; // --------------------------------
	    builder.add(cidadeComboBox, constraints.xy(2, r));
	    builder.add(dataInicial, constraints.xy(4, r));
	    builder.add(dataFinal, constraints.xy(6, r));
	    builder.add(pesquisarBtn, constraints.xy(8, r));

	    builder.setBorder(BorderFactory.createTitledBorder(" Filtro "));
	    
	    return builder.getPanel();
		
	}*/

	private void pesquisar(Credor cidade, Date DataInicial, Date dataFinal) {
		
/*		((ItemTabelaTableModel) tabela.getModel()).limpar();
		
		List<ItemTabela> itens = Mock.getItems();
		
		for(ItemTabela item : itens) {
			((ItemTabelaTableModel) tabela.getModel()).adicionarItem(item);	
		}*/
	}

	private void exportar() {
		JOptionPane.showMessageDialog(null, "Funcionalidade não implementada");
		
	}
}
