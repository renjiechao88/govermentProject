package com.zjnu.view;

import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;

public class JPanel_step3 extends JPanel {

	GridBagLayout gridBagLayout = new GridBagLayout();
	GridBagConstraints c;

	/**
	 * Create the panel.
	 */
	public JPanel_step3() {
		/* ��Ϊ2��2�У��·����ڷ��ð�ť */
		gridBagLayout.columnWeights = new double[] { 1, 1 };
		gridBagLayout.rowWeights = new double[] { 1, 3, 1 };
		this.setLayout(gridBagLayout);

		/*������ʾ*/
		JPanel title_panel = new JPanel();
		title_panel.setLayout(new BorderLayout());
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth  = 2;
		c.fill = GridBagConstraints.BOTH;
		JLabel label = new JLabel("��������", JLabel.CENTER);
		label.setFont(new Font("΢���ź�", 1, 16));
		title_panel.add(label);
		this.add(title_panel,c);
		
		
		/* �������ݿ���Ϣ��д */
		JPanel local_panel = new JPanel();
//		local_panel.setBackground(Color.green);
		local_panel.setBorder(BorderFactory.createLineBorder(Color.green));
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 1;
		c.fill = GridBagConstraints.BOTH;
		this.add(local_panel, c);

		/* �ⲿ���ݿ���Ϣ��д */
		JPanel inter_panel = new JPanel();
//		inter_panel.setBackground(Color.red);
		inter_panel.setBorder(BorderFactory.createLineBorder(Color.green));
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 1;
		c.fill = GridBagConstraints.BOTH;
		this.add(inter_panel, c);

		/* ��ť��ʾ���� */
		JPanel button_panel = new JPanel();
//		button_panel.setBackground(Color.BLUE);
		button_panel.setBorder(BorderFactory.createLineBorder(Color.green));
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 2;
		c.fill = GridBagConstraints.BOTH;
		this.add(button_panel, c);

		/* Ϊ�������ݿ���Ϣ������ */
		paint(local_panel, "�������ݿ���Ϣ");

		/* Ϊ�ⲿ���ݿ������� */
		paint(inter_panel, "�ⲿ���ݿ���Ϣ");

		/* ����button_panel */
		paint_button(button_panel);
	}

	public void paint_button(JPanel panel) {
		GridBagLayout gridBagLayout = new GridBagLayout();
		GridBagConstraints c;

		gridBagLayout.columnWeights = new double[] { 1, 0.5, 0.5, 1 };
		gridBagLayout.rowWeights = new double[] { 0.5, 1, 1, 0.5 };
		panel.setLayout(gridBagLayout);

		// ѡ��EXCEL�ļ�
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(50, 60, 50, 0);
		JButton button = new JButton("ѡ���ļ�");
		button.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.green));
		panel.add(button, c);

		c = new GridBagConstraints();
		c.gridx = 2;
		c.gridy = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(50, 40, 50, 50);
		JTextField fileName = new JTextField("�ļ�����");
		fileName.setEditable(false);
		panel.add(fileName, c);

		/* ������ť */
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 2;
		c.gridwidth = 2;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(40, 40, 50, 50);
		JButton button2 = new JButton("��������");
		button2.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.lightBlue));
		panel.add(button2, c);
		// ���
		c = new GridBagConstraints();
		c.gridx = 3;
		c.gridy = 0;
		c.fill = GridBagConstraints.BOTH;
		JLabel pass = new JLabel();
		panel.add(pass, c);
	}

	public void paint(JPanel panel, String name) {

		GridBagLayout gridBagLayout = new GridBagLayout();
		GridBagConstraints c;

		gridBagLayout.columnWeights = new double[] { 0.2, 0.5, 0.5, 0.2 };
		gridBagLayout.rowWeights = new double[] { 1, 1, 1, 1, 1, 1, 1, 1 };
		panel.setLayout(gridBagLayout);

		// ����һ��
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.fill = GridBagConstraints.BOTH;
		JLabel pass = new JLabel();
		panel.add(pass, c);

		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 0;
		c.gridwidth = 2;
		c.insets = new Insets(10, 0, 0, 0);
		c.fill = GridBagConstraints.BOTH;
		JLabel pass1 = new JLabel(name, JLabel.CENTER);
		pass1.setFont(new Font("΢���ź�", 1, 16));
		panel.add(pass1, c);

		c = new GridBagConstraints();
		c.gridx = 3;
		c.gridy = 0;
		c.fill = GridBagConstraints.BOTH;
		JLabel pass3 = new JLabel();
		panel.add(pass3, c);

		/* ���� */
		// ѡ�����ݿ�����
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(30, 30, 20, 20);
		JLabel label1 = new JLabel("ѡ�����ݿ�����:", JLabel.RIGHT);
		panel.add(label1, c);

		c = new GridBagConstraints();
		c.gridx = 2;
		c.gridy = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(30, 30, 20, 20);
		String[] types = { "Oracle", "mysql", "sqlserver" };
		JComboBox<String> tyep_comBox = new JComboBox<String>(types);
		panel.add(tyep_comBox, c);

		// ѡ�����ݿ�ip��ַ
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 2;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(30, 30, 20, 20);
		JLabel label2 = new JLabel("���ݿ�ip��ַ:", JLabel.RIGHT);
		panel.add(label2, c);

		c = new GridBagConstraints();
		c.gridx = 2;
		c.gridy = 2;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(30, 30, 20, 20);
		JTextField ipAddress = new JTextField();

		panel.add(ipAddress, c);

		// �������ݿ�����
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 3;
		c.fill = GridBagConstraints.HORIZONTAL;
		JLabel label3 = new JLabel("���ݿ�����:", JLabel.RIGHT);
		c.insets = new Insets(30, 30, 20, 20);
		panel.add(label3, c);

		c = new GridBagConstraints();
		c.gridx = 2;
		c.gridy = 3;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(30, 30, 20, 20);
		JTextField dataBaseName = new JTextField();

		panel.add(dataBaseName, c);

		// �˺�
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 4;
		c.fill = GridBagConstraints.HORIZONTAL;
		JLabel label4 = new JLabel("�˺�:", JLabel.RIGHT);
		c.insets = new Insets(30, 30, 20, 20);
		panel.add(label4, c);

		c = new GridBagConstraints();
		c.gridx = 2;
		c.gridy = 4;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(30, 30, 20, 20);
		JTextField userName = new JTextField();

		panel.add(userName, c);

		// ����
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 5;
		c.fill = GridBagConstraints.HORIZONTAL;
		JLabel label5 = new JLabel("����:", JLabel.RIGHT);
		c.insets = new Insets(30, 30, 20, 20);
		panel.add(label5, c);

		c = new GridBagConstraints();
		c.gridx = 2;
		c.gridy = 5;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(30, 30, 20, 20);
		JTextField passWord = new JTextField();

		panel.add(passWord, c);

	}

}
