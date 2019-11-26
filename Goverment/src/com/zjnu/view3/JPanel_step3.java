package com.zjnu.view;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;

import com.zjnu.service.read_ex_struct_service;
import com.zjnu.service.transfer_data_service;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;

public class JPanel_step3 extends JPanel {

	GridBagLayout gridBagLayout = new GridBagLayout();
	GridBagConstraints c;
	String filePath = "";
	JPanel local_panel;
	JPanel inter_panel;
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
		JLabel label = new JLabel("导入数据", JLabel.CENTER);
		label.setFont(new Font("微软雅黑", 1, 16));
		title_panel.add(label);
		this.add(title_panel,c);
		
		
		/* �������ݿ���Ϣ��д */
		local_panel = new JPanel();
//		local_panel.setBackground(Color.green);
		local_panel.setBorder(BorderFactory.createLineBorder(Color.green));
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 1;
		c.fill = GridBagConstraints.BOTH;
		this.add(local_panel, c);

		/* �ⲿ���ݿ���Ϣ��д */
		inter_panel = new JPanel();
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
		paint(local_panel, "本地数据库");

		/* Ϊ�ⲿ���ݿ������� */
		paint(inter_panel, "外部数据库");

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
		JButton button = new JButton("选择excel文件");
		button.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.green));
		panel.add(button, c);

		c = new GridBagConstraints();
		c.gridx = 2;
		c.gridy = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(50, 40, 50, 50);
		JTextField fileName = new JTextField("文件名");
		fileName.setEditable(false);
		panel.add(fileName, c);

		/* ������ť */
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 2;
		c.gridwidth = 2;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(40, 40, 50, 50);
		JButton button2 = new JButton("根据结构传输数据");
		button2.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.lightBlue));
		panel.add(button2, c);
		// ���
		c = new GridBagConstraints();
		c.gridx = 3;
		c.gridy = 0;
		c.fill = GridBagConstraints.BOTH;
		JLabel pass = new JLabel();
		panel.add(pass, c);
		
		/*选择文件按钮添加事件*/
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				FileNameExtensionFilter filter = new FileNameExtensionFilter("表格文件(xls,xlsx)", "xls","xlsx");
				fileChooser.setFileFilter(filter);
				fileChooser.showDialog(null, "选择或创建excel导出文件");
				File file = fileChooser.getSelectedFile();
				filePath = file.getAbsolutePath();
			}
		});
		
		/*为导出按钮添加事件*/
		button2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Map<String,String> message_local = new TreeMap<String, String>();
				Map<String,String> message_ex = new TreeMap<String, String>();
				/*获取本地数据库*/
				getmessage(local_panel, message_local);
				getmessage(inter_panel, message_ex);
				
				transfer_data_service service = new transfer_data_service();
				service.transfer_data(message_local, message_ex, filePath);
			
			}
		});
	}
	

	/*用来获取panel中的数据*/
	public void getmessage(JPanel panel, Map<String,String> map) {
		String[] names = {"databasetype","ipaddress","databasename","username","password"};
		Component[] components = panel.getComponents();
		//0，1，2,3跳过
		for(int i=4;i<components.length;i+=2) {
			
			//为数据库类型时特判
			if(i==4) {
				JComboBox<String> type = (JComboBox<String>)components[i];
				map.put(names[i-4], (String)type.getSelectedItem());
			}
			//其他直接扔进去
			else{
				JTextField textField = (JTextField)components[i];
				map.put(names[(i-4)/2], textField.getText());
			}
			if(i==components.length-1)break;
		}
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
		pass1.setFont(new Font("微软雅黑", 1, 16));
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
		JLabel label1 = new JLabel("数据库类型:", JLabel.RIGHT);
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
		JLabel label2 = new JLabel("ip地址:", JLabel.RIGHT);
		panel.add(label2, c);

		c = new GridBagConstraints();
		c.gridx = 2;
		c.gridy = 2;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(30, 30, 20, 20);
		JTextField ipAddress = new JTextField("localhost");

		panel.add(ipAddress, c);

		// �������ݿ�����
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 3;
		c.fill = GridBagConstraints.HORIZONTAL;
		JLabel label3 = new JLabel("数据库名称:", JLabel.RIGHT);
		c.insets = new Insets(30, 30, 20, 20);
		panel.add(label3, c);

		c = new GridBagConstraints();
		c.gridx = 2;
		c.gridy = 3;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(30, 30, 20, 20);
		JTextField dataBaseName = new JTextField("test");

		panel.add(dataBaseName, c);

		// �˺�
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 4;
		c.fill = GridBagConstraints.HORIZONTAL;
		JLabel label4 = new JLabel("用户名:", JLabel.RIGHT);
		c.insets = new Insets(30, 30, 20, 20);
		panel.add(label4, c);

		c = new GridBagConstraints();
		c.gridx = 2;
		c.gridy = 4;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(30, 30, 20, 20);
		JTextField userName = new JTextField("root");

		panel.add(userName, c);

		// ����
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 5;
		c.fill = GridBagConstraints.HORIZONTAL;
		JLabel label5 = new JLabel("密码:", JLabel.RIGHT);
		c.insets = new Insets(30, 30, 20, 20);
		panel.add(label5, c);

		c = new GridBagConstraints();
		c.gridx = 2;
		c.gridy = 5;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(30, 30, 20, 20);
		JTextField passWord = new JTextField("123");

		panel.add(passWord, c);

	}

}
