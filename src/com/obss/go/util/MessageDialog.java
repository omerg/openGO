package com.obss.go.util;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.obss.go.api.Constants;

public class MessageDialog {

	JFrame frame;

	public MessageDialog(Constants.MESSAGE_TYPE messageType, String message) {
		JOptionPane.showMessageDialog(frame,
			    "Eggs are not supposed to be green.", 
			    "Message", 0);
	}
}
