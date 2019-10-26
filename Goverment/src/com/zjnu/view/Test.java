package com.zjnu.view;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Test {

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setSize(800, 1000);
		frame.setLocationRelativeTo(null);
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createLineBorder(Color.red));
		BorderLayout borderLayout = new BorderLayout();
//		borderLayout.
		frame.setLayout(borderLayout);
		frame.add(panel,BorderLayout.CENTER);
		frame.setVisible(true);
	}
	
}
