package com.zjnu.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.zjnu.utils.PropertiesUtils;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;

public class Configuration extends JFrame {

	private JPanel contentPane;
	private JComboBox comboBox;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Configuration frame = new Configuration();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Configuration() {
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		/*得到数据库信息*/
		Map<String,String> message = PropertiesUtils.list("local_message.properties");
		String databasetype = message.get("databasetype")==null?"":message.get("databasetype");
		String databasename = message.get("databasename")==null?"":message.get("databasename");
		String ipaddress = message.get("ipaddress")==null?"":message.get("ipaddress");
		String username = message.get("username")==null?"":message.get("username");
		String password = message.get("password")==null?"":message.get("password");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(590,300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel label = new JLabel("数据库类型：");
		label.setFont(new Font("PingFang HK", Font.PLAIN, 14));
		label.setBounds(23, 85, 109, 16);
		contentPane.add(label);
		
		JLabel lblIp = new JLabel("IP地址      ：");
		lblIp.setFont(new Font("PingFang HK", Font.PLAIN, 14));
		lblIp.setBounds(23, 124, 85, 16);
		contentPane.add(lblIp);
		
		JLabel label_2 = new JLabel("数据库名称：");
		label_2.setFont(new Font("PingFang HK", Font.PLAIN, 14));
		label_2.setBounds(309, 124, 121, 16);
		contentPane.add(label_2);
		
		JLabel label_3 = new JLabel("账号        ：");
		label_3.setFont(new Font("PingFang HK", Font.PLAIN, 14));
		label_3.setBounds(23, 163, 97, 16);
		contentPane.add(label_3);
		
		JLabel label_4 = new JLabel("密码        ：");
		label_4.setFont(new Font("PingFang HK", Font.PLAIN, 14));
		label_4.setBounds(309, 163, 85, 16);
		contentPane.add(label_4);
		
		comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Oracle", "mysql", "sqlserve"}));
		comboBox.setFont(new Font("PingFang HK", Font.PLAIN, 14));
		comboBox.setBounds(109, 80, 172, 27);
		if(databasetype.length()>0) comboBox.setSelectedItem(databasetype);
		contentPane.add(comboBox);
		
		textField = new JTextField();
		textField.setFont(new Font("PingFang HK", Font.PLAIN, 14));
		textField.setColumns(10);
		textField.setBounds(109, 119, 170, 25);
		textField.setText(ipaddress);
		contentPane.add(textField);
		
		textField_1 = new JTextField();
		textField_1.setFont(new Font("PingFang HK", Font.PLAIN, 14));
		textField_1.setColumns(10);
		textField_1.setBounds(395, 119, 170, 25);
		textField_1.setText(databasename);
		contentPane.add(textField_1);
		
		textField_2 = new JTextField();
		textField_2.setFont(new Font("PingFang HK", Font.PLAIN, 14));
		textField_2.setColumns(10);
		textField_2.setBounds(109, 160, 170, 25);
		textField_2.setText(username);
		contentPane.add(textField_2);
		
		passwordField = new JPasswordField();
		passwordField.setFont(new Font("PingFang HK", Font.PLAIN, 14));
		passwordField.setColumns(10);
		passwordField.setBounds(395, 160, 170, 25);
		passwordField.setText(password);
		contentPane.add(passwordField);
		
		JLabel label_Title = new JLabel("配置本机数据库连接参数");
		label_Title.setFont(new Font("PingFang HK", Font.PLAIN, 16));
		label_Title.setBounds(23, 24, 200, 16);
		contentPane.add(label_Title);
		
		JButton button_cancel = new JButton("关闭");
		button_cancel.setFont(new Font("PingFang HK", Font.PLAIN, 14));
		button_cancel.setEnabled(true);
		button_cancel.setBounds(474, 232, 109, 29);
		contentPane.add(button_cancel);
//		button_cancel.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				// TODO Auto-generated method stub
//				dispose();
//			}
//		});
		
		JButton button_OK = new JButton("确定");
		button_OK.setFont(new Font("PingFang HK", Font.PLAIN, 14));
		button_OK.setEnabled(true);
		button_OK.setBounds(353, 232, 109, 29);
		contentPane.add(button_OK);
		
		/*为确定添加事件*/
		button_OK.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Map<String,String> message = new HashMap<String, String>();
				boolean flag = vertify(message);
				if(flag==false) {
					JOptionPane.showMessageDialog(null, "请填写完整的数据库信息");
					return ;
				}
				/*1将数据写到配置文件中*/
				PropertiesUtils.write_file(message, "local_message.properties");
				/*2.显示成功**/
				JOptionPane.showMessageDialog(null, "修改本地数据库信息成功");
			}
		});
		
	}
	
	public boolean vertify(Map<String,String> message) {
		String databasetype = ((String)comboBox.getSelectedItem()).trim();
		String ipaddress = textField.getText().trim();
		String databasename = textField_1.getText().trim();
		String username = textField_2.getText().trim();
		String passString = passwordField.getText().trim();
		if(databasetype==null || databasetype.length()<=0 || ipaddress==null || ipaddress.length()<=0 ||
				databasename==null || databasename.length()<=0 || username==null || username.length()<=0 || 
				passString==null || passString.length()<=0) {
			return false;
		}else {
			message.put("databasetype", databasetype);
			message.put("ipaddress", ipaddress);
			message.put("databasename", databasename);
			message.put("username", username);
			message.put("password", passString);
		}
		
		return true;
	}
}
