package br.com.frontend.java;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import br.com.frontend.java.panel.MainPanel;

public class Main {

	public static void main(String[] args) {

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
		}

		MainPanel panel = new MainPanel();
		//panel.setSize(800, 600);
		panel.pack();
		panel.setVisible(true);
		panel.setLocationRelativeTo(null);


	}
}
