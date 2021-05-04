package br.com.frontend.java.util;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import br.com.frontend.java.model.ItemTabela;

public class ItemTabelaTableModel extends DefaultTableModel{

	private static final long serialVersionUID = 1L;

	private static List<String> cabecalho = Arrays.asList("Codigo", "Descricao");
	private List<ItemTabela> list;
	
	@Override
	public String getColumnName(int columnIndex) {
		return cabecalho.get(columnIndex);
	}
	
	@Override
	public int getColumnCount() {
		return cabecalho.size();
	}
	
	@Override
	public int getRowCount() {
		return getList().size();
	}
	
	public List<ItemTabela> getList() {
		if(list == null) {
			list = new ArrayList<ItemTabela>();
		}
		return list;
	}
	
	public ItemTabela getFromModel(int rowIndex) {
		if(list == null || list.size() == 0){
			return null;
		}
		return this.list.get(rowIndex);
	}
	
	@Override
	public String getValueAt(int rowIndex, int columnIndex) {

		ItemTabela item = (ItemTabela) this.getFromModel(rowIndex);

		switch (columnIndex) {
		case 0:
			return String.valueOf(item.getCodigo());
		case 1:    	
			return item.getDescricao();
		}
		return null;
	}
	
	public void adicionarItem(ItemTabela item) {
		
		getList().add(item);
		this.fireTableDataChanged();
		
	}

	public void limpar() {
		list = null;
	}
}
