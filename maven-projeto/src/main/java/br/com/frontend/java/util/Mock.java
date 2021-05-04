package br.com.frontend.java.util;

import java.util.ArrayList;
import java.util.List;

import br.com.frontend.java.model.Cidade;
import br.com.frontend.java.model.ItemTabela;

public class Mock {

	public static List<ItemTabela> getItems(){
		List<ItemTabela> result = new ArrayList<>();
		result.add(new ItemTabela(1, "Fusca"));
		result.add(new ItemTabela(2, "Brasília"));
		result.add(new ItemTabela(3, "Parati"));
		result.add(new ItemTabela(4, "Kombi"));
		result.add(new ItemTabela(5, "Gol"));
		
		return result;
	}
	
	public static List<Cidade> getCidades(){
		List<Cidade> result = new ArrayList<>();
		result.add(new Cidade(1, "01"));
		result.add(new Cidade(2, "02"));
		result.add(new Cidade(3, "03"));
		result.add(new Cidade(4, "04"));
		result.add(new Cidade(5, "05"));
		
		return result;
	}
}
