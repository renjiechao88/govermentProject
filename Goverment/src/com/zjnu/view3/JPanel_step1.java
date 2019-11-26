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
import javax.swing.SpringLayout;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;

import com.zjnu.mapper.read_local_datastructMapper;
import com.zjnu.service.read_ex_struct_service;
import com.zjnu.service.read_local_struct_service;

import java.awt.Font;

public class JPanel_step1 extends JPanel {

	GridBagLayout gridBagLayout = new GridBagLayout();
	GridBagConstraints c;
	String filePath = null;

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
		JLabel pass1 = new JLabel("生成EXCEL",JLabel.CENTER);
		pass1.setFont(new Font("微软雅黑",1,16));
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
		JLabel label1 = new JLabel("数据库类型:",JLabel.RIGHT);
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
		JLabel label2 = new JLabel("ip地址:",JLabel.RIGHT);
		this.add(label2, c);

		c = new GridBagConstraints();
		c.gridx = 2;
		c.gridy = 2;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(30, 30, 20, 20);
		JTextField ipAddress = new JTextField("localhost");

		this.add(ipAddress, c);

		//�������ݿ�����
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 3;
		c.fill = GridBagConstraints.HORIZONTAL;
		JLabel label3 = new JLabel("数据库名称:",JLabel.RIGHT);
		c.insets = new Insets(30, 30, 20, 20);
		this.add(label3, c);

		c = new GridBagConstraints();
		c.gridx = 2;
		c.gridy = 3;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(30, 30, 20, 20);
		JTextField dataBaseName = new JTextField("test");

		this.add(dataBaseName, c);
		
		//�˺�
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 4;
		c.fill = GridBagConstraints.HORIZONTAL;
		JLabel label4 = new JLabel("用户名:",JLabel.RIGHT);
		c.insets = new Insets(30, 30, 20, 20);
		this.add(label4, c);

		c = new GridBagConstraints();
		c.gridx = 2;
		c.gridy = 4;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(30, 30, 20, 20);
		JTextField userName = new JTextField("root");

		this.add(userName, c);
		
		//����
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 5;
		c.fill = GridBagConstraints.HORIZONTAL;
		JLabel label5 = new JLabel("密码:",JLabel.RIGHT);
		c.insets = new Insets(30, 30, 20, 20);
		this.add(label5, c);

		c = new GridBagConstraints();
		c.gridx = 2;
		c.gridy = 5;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(30, 30, 20, 20);
		JTextField passWord = new JTextField("123");

		this.add(passWord, c);

		//ѡ��EXCEL�ļ�
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 6;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(50,60,50,0);
		JButton button = new JButton("选择文件");
		button.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.green));
		this.add(button, c);

		c = new GridBagConstraints();
		c.gridx = 2;
		c.gridy = 6;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(50,40,50,50);
		JTextField fileName = new JTextField("文件名称");
		fileName.setEditable(false);
		this.add(fileName, c);

		/* ������ť */
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 7;
		c.gridwidth = 2;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(40,40,50,50);
		JButton button2 = new JButton("导出");
		button2.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.lightBlue));
		this.add(button2, c);
		
		
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
				Map<String,String> message = new TreeMap<String, String>();
				message.put("username", userName.getText());
				message.put("password", passWord.getText());
				message.put("databasetype", (String)tyep_comBox.getSelectedItem());
				message.put("databasename", dataBaseName.getText());
				message.put("ipaddress", ipAddress.getText());
				message.put("filepath", filePath);
				read_local_struct_service service = new read_local_struct_service();
				try {
					service.read_struct(message);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
	}
}
