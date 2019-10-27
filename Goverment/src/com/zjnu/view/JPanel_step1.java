package com.zjnu.view;

import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.SpringLayout;

import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;

import java.awt.Font;

public class JPanel_step1 extends JPanel {

	GridBagLayout gridBagLayout = new GridBagLayout();
	GridBagConstraints c;

	/**
	 * Create the panel.
	 */
	public JPanel_step1() {
		setSize(1000, 1000);

		gridBagLayout.columnWeights = new double[] { 0.7, 0.5, 0.5, 1 };
		gridBagLayout.rowWeights = new double[] { 1, 1, 1, 1, 1, 1 ,1, 1};
		this.setLayout(gridBagLayout);

		// ����һ��
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.fill = GridBagConstraints.BOTH;
		JLabel pass = new JLabel();
		this.add(pass, c);

		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 0;
		c.gridwidth = 2;
		c.insets = new Insets(10, 0, 0, 0);
		c.fill = GridBagConstraints.BOTH;
		JLabel pass1 = new JLabel("�������ؽṹ",JLabel.CENTER);
		pass1.setFont(new Font("΢���ź�",1,16));
		this.add(pass1, c);



		c = new GridBagConstraints();
		c.gridx = 3;
		c.gridy = 0;
		c.fill = GridBagConstraints.BOTH;
		JLabel pass3 = new JLabel();
		this.add(pass3, c);

		


		



		/* ���� */
		//ѡ�����ݿ�����
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(30, 30, 20, 20);
		JLabel label1 = new JLabel("ѡ�񱾵����ݿ�����:",JLabel.RIGHT);
		this.add(label1, c);

		c = new GridBagConstraints();
		c.gridx = 2;
		c.gridy = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(30, 30, 20, 20);
		String[] types = { "Oracle", "mysql", "sqlserver" };
		JComboBox<String> tyep_comBox = new JComboBox<String>(types);
		this.add(tyep_comBox, c);

		// ѡ�����ݿ�ip��ַ
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 2;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(30, 30, 20, 20);
		JLabel label2 = new JLabel("���ݿ�ip��ַ:",JLabel.RIGHT);
		this.add(label2, c);

		c = new GridBagConstraints();
		c.gridx = 2;
		c.gridy = 2;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(30, 30, 20, 20);
		JTextField ipAddress = new JTextField();

		this.add(ipAddress, c);

		//�������ݿ�����
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 3;
		c.fill = GridBagConstraints.HORIZONTAL;
		JLabel label3 = new JLabel("���ݿ�����:",JLabel.RIGHT);
		c.insets = new Insets(30, 30, 20, 20);
		this.add(label3, c);

		c = new GridBagConstraints();
		c.gridx = 2;
		c.gridy = 3;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(30, 30, 20, 20);
		JTextField dataBaseName = new JTextField();

		this.add(dataBaseName, c);
		
		//�˺�
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 4;
		c.fill = GridBagConstraints.HORIZONTAL;
		JLabel label4 = new JLabel("�˺�:",JLabel.RIGHT);
		c.insets = new Insets(30, 30, 20, 20);
		this.add(label4, c);

		c = new GridBagConstraints();
		c.gridx = 2;
		c.gridy = 4;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(30, 30, 20, 20);
		JTextField userName = new JTextField();

		this.add(userName, c);
		
		//����
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 5;
		c.fill = GridBagConstraints.HORIZONTAL;
		JLabel label5 = new JLabel("����:",JLabel.RIGHT);
		c.insets = new Insets(30, 30, 20, 20);
		this.add(label5, c);

		c = new GridBagConstraints();
		c.gridx = 2;
		c.gridy = 5;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(30, 30, 20, 20);
		JTextField passWord = new JTextField();

		this.add(passWord, c);

		//ѡ��EXCEL�ļ�
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 6;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(50,60,50,0);
		JButton button = new JButton("ѡ���ļ�");
		button.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.green));
		this.add(button, c);

		c = new GridBagConstraints();
		c.gridx = 2;
		c.gridy = 6;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(50,40,50,50);
		JTextField fileName = new JTextField("�ļ�����");
		fileName.setEditable(false);
		this.add(fileName, c);

		/* ������ť */
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 7;
		c.gridwidth = 2;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(40,40,50,50);
		JButton button2 = new JButton("�����ṹ");
		button2.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.lightBlue));
		this.add(button2, c);
	}
}
