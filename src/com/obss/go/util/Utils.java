package com.obss.go.util;

import javax.swing.JComponent;
import javax.swing.JOptionPane;

public class Utils {

	public static void messageHandler(JComponent component, String message) {
		JOptionPane.showMessageDialog(component, message);
	}
}
