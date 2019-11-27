package com.zjnu.view;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.JToggleButton;
import javax.swing.UIManager;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;
import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

import com.mybatis.dao.IUserDao;
import com.zjnu.service.read_ex_struct_service;
import com.zjnu.service.read_local_struct_service;
import com.zjnu.service.transfer_data_service;
import com.zjnu.utils.Mybatistest;
import com.zjnu.utils.PropertiesUtils;
import com.zjnu.utils.exceltosqlTool;

import java.awt.Color;
import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import javax.swing.JProgressBar;
import javax.swing.JCheckBox;
import java.awt.Toolkit;
import java.awt.FlowLayout;
import javax.swing.SpringLayout;

public class JFrameMain extends JFrame {

	private JPanel contentPane=new JPanel();;
	private JToggleButton tglbtnStep_1=new JToggleButton("<html>　  阶段一<br>同步所需字段</html>");
	private JToggleButton tglbtnStep_2=new JToggleButton("<html>　  阶段二<br>构建对应关系</html>");
	private JToggleButton tglbtnStep_3=new JToggleButton("<html>　  阶段三<br>导回到数据库</html>");
	private JPanel panel_content = new JPanel();
	private JPanel panel_picture = new JPanel();
	private JTextField textField;
	private String filePath = "";
	private File file;

	public static void main(String[] args) {
		try{
	        BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.osLookAndFeelDecorated;
	        UIManager.put("RootPane.setupButtonVisible",false);
	        org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
	    }
	    catch(Exception e) {
	        //TODO exception
	    }
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JFrameMain frame = new JFrameMain();
					frame.setLocationRelativeTo(null);//居中显示
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public JFrameMain() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(JFrameMain.class.getResource("/com/zjnu/image/tools.png")));
		setMainJFrame();	//设置一些主窗口属性，并加入右侧的panel将其定位
		addThreeJToggleButtons();	//加入左侧三个切换按钮
		tglbtnStep_1.setSelected(true);	//默认选定标签1按钮
		addContent1();	//默认添加阶段一内容
		setJToggleButtonActions();	//设置三个切换按钮的动作
	}
	
	public void setMainJFrame() {
		setResizable(false);
		setTitle("金华市审计局编目批量管理工具");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1000, 300+235+20+10);	//图片的宽度为760
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{1, 0, 0, 0, 1};
		gbl_contentPane.columnWeights = new double[]{1.0, 1, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.001, 0.001, 0.001, 3.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		//初始化右侧图片模块
		FlowLayout fl_panel_picture = (FlowLayout) panel_picture.getLayout();
		GridBagConstraints gbc_panel_picture = new GridBagConstraints();
		gbc_panel_picture.insets = new Insets(4, -1, 5, 4);
		panel_picture.setBorder(new LineBorder(new Color(128, 128, 128)));	//设置panel
		gbc_panel_picture.gridheight = 3;
		gbc_panel_picture.fill = GridBagConstraints.BOTH;
		gbc_panel_picture.gridx = 1;
		gbc_panel_picture.gridy = 0;
		contentPane.add(panel_picture, gbc_panel_picture);
		
		//左下角的配置、帮助按钮对应的panel
		JPanel panel_SettingAndHelp = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(6, -9, 4, 5);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 3;
		contentPane.add(panel_SettingAndHelp, gbc_panel);
		SpringLayout sl_panel = new SpringLayout();
		panel_SettingAndHelp.setLayout(sl_panel);
		
		JButton button_Setting = new JButton("");
		button_Setting.setIcon(new ImageIcon(JFrameMain.class.getResource("/com/zjnu/image/settings_16px.png")));
		sl_panel.putConstraint(SpringLayout.WEST, button_Setting, 10, SpringLayout.WEST, panel_SettingAndHelp);
		sl_panel.putConstraint(SpringLayout.SOUTH, button_Setting, 0, SpringLayout.SOUTH, panel_SettingAndHelp);
		panel_SettingAndHelp.add(button_Setting);
		button_Setting.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Configuration cfg=new Configuration();
				cfg.setLocationRelativeTo(null);//居中显示
				cfg.setVisible(true);
			}
		});
		
		JButton button_Help = new JButton("");
		sl_panel.putConstraint(SpringLayout.WEST, button_Help, 6, SpringLayout.EAST, button_Setting);
		sl_panel.putConstraint(SpringLayout.SOUTH, button_Help, 0, SpringLayout.SOUTH, button_Setting);
		button_Help.setIcon(new ImageIcon(JFrameMain.class.getResource("/com/zjnu/image/question_16px.png")));
		panel_SettingAndHelp.add(button_Help);
		
		//初始化右侧内容模块
		panel_content.setBorder(new LineBorder(new Color(128, 128, 128)));	//设置panel
		panel_content.setLayout(null);
		GridBagConstraints gbc_panel2 = new GridBagConstraints();
		gbc_panel2.insets = new Insets(6, -1, 4, 4);
		gbc_panel2.gridheight = 3;
		gbc_panel2.fill = GridBagConstraints.BOTH;
		gbc_panel2.gridx = 1;
		gbc_panel2.gridy = 3;
		contentPane.add(panel_content, gbc_panel2);
	}
	
	public void addThreeJToggleButtons() {
		JPanel panel_step1 = new JPanel();
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.insets = new Insets(-1, -9, 5, 5);
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 0;
		contentPane.add(panel_step1, gbc_panel_1);
		
		tglbtnStep_1.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		tglbtnStep_1.setIcon(new ImageIcon(JFrameMain.class.getResource("/com/zjnu/image/1.png")));
		panel_step1.add(tglbtnStep_1);
		
		JPanel panel_step2 = new JPanel();
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.insets = new Insets(0, -9, 5, 5);
		gbc_panel_2.fill = GridBagConstraints.BOTH;
		gbc_panel_2.gridx = 0;
		gbc_panel_2.gridy = 1;
		contentPane.add(panel_step2, gbc_panel_2);
		
		tglbtnStep_2.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		tglbtnStep_2.setIcon(new ImageIcon(JFrameMain.class.getResource("/com/zjnu/image/2.png")));
		panel_step2.add(tglbtnStep_2);
		
		JPanel panel_step3 = new JPanel();
		GridBagConstraints gbc_panel_3 = new GridBagConstraints();
		gbc_panel_3.insets = new Insets(0, -9, 5, 5);
		gbc_panel_3.fill = GridBagConstraints.BOTH;
		gbc_panel_3.gridx = 0;
		gbc_panel_3.gridy = 2;
		contentPane.add(panel_step3, gbc_panel_3);
		
		tglbtnStep_3.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		tglbtnStep_3.setIcon(new ImageIcon(JFrameMain.class.getResource("/com/zjnu/image/3.png")));
		panel_step3.add(tglbtnStep_3);
	}

	public void setJToggleButtonActions() {
		tglbtnStep_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				tglbtnStep_1.setSelected(true);
				tglbtnStep_2.setSelected(false);
				tglbtnStep_3.setSelected(false);
				
				contentPane.setVisible(false);
				removeContent();
				addContent1();
				contentPane.setVisible(true);
			}
		});
		
		tglbtnStep_2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				tglbtnStep_1.setSelected(false);
				tglbtnStep_2.setSelected(true);
				tglbtnStep_3.setSelected(false);
				
				contentPane.setVisible(false);
				removeContent();
				addContent2();
				contentPane.setVisible(true);
			}
		});
		
		tglbtnStep_3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				tglbtnStep_1.setSelected(false);
				tglbtnStep_2.setSelected(false);
				tglbtnStep_3.setSelected(true);
				
				contentPane.setVisible(false);
				removeContent();
				addContent3();
				contentPane.setVisible(true);
			}
		});
	}
	
	public void removeContent() {
		panel_picture.removeAll();
		panel_content.removeAll();
	}
	
	public void addContent1() {
		filePath = "";
		//上侧图片
		JLabel label_picture = new JLabel("");
		label_picture.setIcon(new ImageIcon(JFrameMain.class.getResource("/com/zjnu/image/Step1.png")));
		panel_picture.add(label_picture);
		
		//下侧内容
		JLabel label_DatabaseType = new JLabel("数据库类型：");
		label_DatabaseType.setFont(new Font("PingFang HK", Font.PLAIN, 14));
		label_DatabaseType.setBounds(25, 25, 109, 16);
		panel_content.add(label_DatabaseType);
		
		JLabel label_IpAddress = new JLabel("IP地址：");
		label_IpAddress.setFont(new Font("PingFang HK", Font.PLAIN, 14));
		label_IpAddress.setBounds(266, 25, 68, 16);
		panel_content.add(label_IpAddress);
		
		JLabel label_DatabaseName = new JLabel("数据库名称：");
		label_DatabaseName.setFont(new Font("PingFang HK", Font.PLAIN, 14));
		label_DatabaseName.setBounds(505, 25, 121, 16);
		panel_content.add(label_DatabaseName);
		
		JLabel label_Account = new JLabel("账号：");
		label_Account.setFont(new Font("PingFang HK", Font.PLAIN, 14));
		label_Account.setBounds(25, 63, 68, 16);
		panel_content.add(label_Account);
		
		JLabel label_Password = new JLabel("密码：");
		label_Password.setIcon(null);
		label_Password.setFont(new Font("PingFang HK", Font.PLAIN, 14));
		label_Password.setBounds(282, 63, 85, 16);
		panel_content.add(label_Password);
		
		JComboBox comboBox_DatabaseType = new JComboBox();
		comboBox_DatabaseType.setFont(new Font("PingFang HK", Font.PLAIN, 14));
		comboBox_DatabaseType.setModel(new DefaultComboBoxModel(new String[] {"Oracle", "mysql","sqlserve"}));
		comboBox_DatabaseType.setBounds(122, 20, 129, 27);
		panel_content.add(comboBox_DatabaseType);
		
		JTextField textField_IpAddress = new JTextField();
		textField_IpAddress.setFont(new Font("PingFang HK", Font.PLAIN, 14));
		textField_IpAddress.setBounds(323, 20, 170, 25);
		panel_content.add(textField_IpAddress);
		textField_IpAddress.setColumns(10);
		
		JTextField textField_DatabaseName = new JTextField();
		textField_DatabaseName.setFont(new Font("PingFang HK", Font.PLAIN, 14));
		textField_DatabaseName.setColumns(10);
		textField_DatabaseName.setBounds(591, 19, 170, 27);
		panel_content.add(textField_DatabaseName);
		
		JTextField textField_Account = new JTextField();
		textField_Account.setFont(new Font("PingFang HK", Font.PLAIN, 14));
		textField_Account.setColumns(10);
		textField_Account.setBounds(80, 60, 180, 25);
		panel_content.add(textField_Account);
		
		JPasswordField passwordField_Password = new JPasswordField();
		passwordField_Password.setFont(new Font("PingFang HK", Font.PLAIN, 14));
		passwordField_Password.setColumns(10);
		passwordField_Password.setBounds(334, 60, 180, 25);
		panel_content.add(passwordField_Password);
		
		JTextField textField_SelectFile = new JTextField();
		textField_SelectFile.setEditable(false);
		textField_SelectFile.setEnabled(false);
		textField_SelectFile.setFont(new Font("PingFang HK", Font.PLAIN, 14));
		textField_SelectFile.setColumns(10);
		textField_SelectFile.setBounds(201, 98, 439, 25);
		panel_content.add(textField_SelectFile);
		
		JProgressBar progressBar = new JProgressBar();
		progressBar.setFont(new Font("PingFang HK", Font.PLAIN, 14));
		progressBar.setBounds(25, 187, 736, 20);
		panel_content.add(progressBar);
		
		JLabel label_Status = new JLabel("当前状态：数据库连接成功，正在导出......");
		label_Status.setForeground(Color.DARK_GRAY);
		label_Status.setFont(new Font("PingFang HK", Font.PLAIN, 14));
		label_Status.setBounds(25, 225, 318, 16);
		panel_content.add(label_Status);
		
		JButton button_Browse = new JButton("浏览");
		button_Browse.setEnabled(false);
		button_Browse.setFont(new Font("PingFang HK", Font.PLAIN, 14));
		button_Browse.setBounds(652, 96, 109, 29);
		panel_content.add(button_Browse);
		
		JButton button_ExportToFile = new JButton("同步文件和数据库");
		button_ExportToFile.setToolTipText("默认导出路径为桌面");
		button_ExportToFile.setFont(new Font("PingFang HK", Font.PLAIN, 14));
		button_ExportToFile.setBounds(629, 220, 132, 29);
		panel_content.add(button_ExportToFile);
		
		JCheckBox checkBox = new JCheckBox("从文件同步到数据库：");
		checkBox.setFont(new Font("PingFang HK", Font.PLAIN, 14));
		checkBox.setBounds(19, 98, 179, 23);
		panel_content.add(checkBox);
		
		textField_IpAddress.setText("localhost");
		textField_Account.setText("root");
		textField_DatabaseName.setText("test2");
		passwordField_Password.setText("123");
		
		checkBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (checkBox.isSelected()) {
					textField_SelectFile.setEnabled(true);
					button_Browse.setEnabled(true);
				}
				else {
					textField_SelectFile.setEnabled(false);
					button_Browse.setEnabled(false);
				}
			}
		});
		
		
		/*为游览按钮添加事件*/
		button_Browse.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				FileNameExtensionFilter filter = new FileNameExtensionFilter("表格文件(xls,xlsx)", "xls","xlsx");
				fileChooser.setFileFilter(filter);
				fileChooser.showDialog(null, "选择excel文件");
				file= fileChooser.getSelectedFile();
				if(file==null) return;
				/*创建时要以xls或者xlsx结尾*/
				filePath = file.getAbsolutePath();
				if(!filePath.endsWith(".xls") && !filePath.endsWith(".xlsx")) {
					filePath = filePath+".xlsx";
				}
				file = new File(filePath);
				/*同时将文件名字显示*/
				textField_SelectFile.setText(file.getName());
			}
		});
		
		
		/*添加点击事件*/
		button_ExportToFile.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				/*1.选中表示从excel创建数据库*/
				if(checkBox.isSelected()) {
					Map<String,String> message = new HashMap<String, String>();
					/*1.1校验数据库信息*/
					boolean flag = vertify(message);
					if(flag==false) {
						JOptionPane.showMessageDialog(null, "请填写完整的数据库信息");
						return;
					}
					/*1.2还需要校验是否有文件*/
					if(filePath.trim().length()<=0) {
						JOptionPane.showMessageDialog(null, "从文件创建数据库需要选择文件，请选择正确的文件");
						return;
					}
					/*1.3通过校验之后将数据打包好*/
//					message.put("filepath", filePath);
					//1.4调用接口
					try {
						exceltosqlTool.exceltosql(message, file);
						JOptionPane.showMessageDialog(null, "导入成功");
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(null, "导入失败"+e1.getMessage());
					}
					
				}
				
				/*2.从数据库创建excel*/
				else {
					Map<String,String> message = new HashMap<String, String>();
					/*1.1校验数据库信息*/
					boolean flag = vertify(message);
					if(flag==false) {
						JOptionPane.showMessageDialog(null, "请填写完整的数据库信息");
						return;
					}
					/*1.2默认将excle保存在桌面上*/
					File desktopDir = FileSystemView.getFileSystemView() .getHomeDirectory();
					String desktopPath = desktopDir.getAbsolutePath();

					SimpleDateFormat formatter= new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
					Date date = new Date(System.currentTimeMillis());
					/*使用局部变量，不要影响游览选择器的选择*/
					String filePath = desktopPath+"\\数据库结构文件"+formatter.format(date)+".xlsx";
					message.put("filepath", filePath);
					System.out.println(message);
					/*1。3调用api*/
					read_local_struct_service service = new read_local_struct_service();
					try {
						service.read_struct(message);
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(null, "导出数据库结构失败,确认选择了正确的数据库类型");
						e1.printStackTrace();
						return;
					}
				}
			}
		});
	}
	
	public void addContent2() {
		filePath = "";
		//上侧图片
		JLabel label_picture = new JLabel("");
		label_picture.setIcon(new ImageIcon(JFrameMain.class.getResource("/com/zjnu/image/Step2.png")));
		panel_picture.add(label_picture);
				
		//下侧内容
		JLabel label_DatabaseType = new JLabel("数据库类型：");
		label_DatabaseType.setFont(new Font("PingFang HK", Font.PLAIN, 14));
		label_DatabaseType.setBounds(25, 25, 109, 16);
		panel_content.add(label_DatabaseType);
		
		JLabel label_IpAddress = new JLabel("IP地址：");
		label_IpAddress.setFont(new Font("PingFang HK", Font.PLAIN, 14));
		label_IpAddress.setBounds(266, 25, 68, 16);
		panel_content.add(label_IpAddress);
		
		JLabel label_DatabaseName = new JLabel("数据库名称：");
		label_DatabaseName.setFont(new Font("PingFang HK", Font.PLAIN, 14));
		label_DatabaseName.setBounds(505, 25, 121, 16);
		panel_content.add(label_DatabaseName);
		
		JLabel label_Account = new JLabel("账号：");
		label_Account.setFont(new Font("PingFang HK", Font.PLAIN, 14));
		label_Account.setBounds(25, 63, 68, 16);
		panel_content.add(label_Account);
		
		JLabel label_Password = new JLabel("密码：");
		label_Password.setIcon(null);
		label_Password.setFont(new Font("PingFang HK", Font.PLAIN, 14));
		label_Password.setBounds(282, 63, 85, 16);
		panel_content.add(label_Password);
		

		
		JComboBox comboBox_DatabaseType = new JComboBox();
		comboBox_DatabaseType.setFont(new Font("PingFang HK", Font.PLAIN, 14));
		comboBox_DatabaseType.setModel(new DefaultComboBoxModel(new String[] {"Oracle", "mysql","sqlserve"}));
		comboBox_DatabaseType.setBounds(122, 20, 129, 27);
		panel_content.add(comboBox_DatabaseType);
		
		JTextField textField_IpAddress = new JTextField();
		textField_IpAddress.setFont(new Font("PingFang HK", Font.PLAIN, 14));
		textField_IpAddress.setBounds(323, 20, 170, 25);
		panel_content.add(textField_IpAddress);
		textField_IpAddress.setColumns(10);
		
		JTextField textField_DatabaseName = new JTextField();
		textField_DatabaseName.setFont(new Font("PingFang HK", Font.PLAIN, 14));
		textField_DatabaseName.setColumns(10);
		textField_DatabaseName.setBounds(591, 19, 170, 27);
		panel_content.add(textField_DatabaseName);
		
		JTextField textField_Account = new JTextField();
		textField_Account.setFont(new Font("PingFang HK", Font.PLAIN, 14));
		textField_Account.setColumns(10);
		textField_Account.setBounds(80, 60, 180, 25);
		panel_content.add(textField_Account);
		
		JPasswordField passwordField_Password = new JPasswordField();
		passwordField_Password.setFont(new Font("PingFang HK", Font.PLAIN, 14));
		passwordField_Password.setColumns(10);
		passwordField_Password.setBounds(334, 60, 180, 25);
		panel_content.add(passwordField_Password);
		
		/*默认信息*/
		textField_IpAddress.setText("localhost");
		textField_Account.setText("root");
		textField_DatabaseName.setText("test2");
		passwordField_Password.setText("123");
		
		
		JLabel label_SelectFile = new JLabel("选择所需字段文件：");
		label_SelectFile.setFont(new Font("PingFang HK", Font.PLAIN, 14));
		label_SelectFile.setBounds(25, 101, 130, 16);
		panel_content.add(label_SelectFile);
		
		JTextField textField_SelectFile = new JTextField();
		textField_SelectFile.setEditable(false);
		textField_SelectFile.setFont(new Font("PingFang HK", Font.PLAIN, 14));
		textField_SelectFile.setColumns(10);
		textField_SelectFile.setBounds(161, 98, 479, 25);
		panel_content.add(textField_SelectFile);
		
		
		JProgressBar progressBar = new JProgressBar();
		progressBar.setFont(new Font("PingFang HK", Font.PLAIN, 14));
		progressBar.setBounds(25, 187, 736, 20);
		panel_content.add(progressBar);
		
		JLabel label_Status = new JLabel("当前状态：数据库连接成功，正在写入文件......");
		label_Status.setForeground(Color.DARK_GRAY);
		label_Status.setFont(new Font("PingFang HK", Font.PLAIN, 14));
		label_Status.setBounds(25, 225, 318, 16);
		panel_content.add(label_Status);
		
		JButton button_Browse = new JButton("浏览");
		button_Browse.setFont(new Font("PingFang HK", Font.PLAIN, 14));
		button_Browse.setBounds(652, 96, 109, 29);
		panel_content.add(button_Browse);
		
		/*添加数据库结构文件游览*/
		button_Browse.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				FileNameExtensionFilter filter = new FileNameExtensionFilter("表格文件(xls,xlsx)", "xls","xlsx");
				fileChooser.setFileFilter(filter);
				fileChooser.showDialog(null, "选择或创建excel文件");
				File file = fileChooser.getSelectedFile();
				if(file==null) return;
				/*创建时要以xls或者xlsx结尾*/
				filePath = file.getAbsolutePath();
				if(!filePath.endsWith(".xls") && !filePath.endsWith(".xlsx")) {
					filePath = filePath+".xlsx";
				}
				file = new File(filePath);
				/*同时将文件名字显示*/
				textField_SelectFile.setText(file.getName());
				textField.setText("");
			}
		});
		
		JButton button_WriteToFile = new JButton("导到本机数据库");
		button_WriteToFile.setFont(new Font("PingFang HK", Font.PLAIN, 14));
		button_WriteToFile.setBounds(652, 220, 109, 29);
		panel_content.add(button_WriteToFile);
		
		/*导数据到本地数据库*/
		button_WriteToFile.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Map<String,String> message_ex = new HashMap<String, String>();
				/*1.1校验外部数据库信息*/
				boolean flag = vertify(message_ex);
				if(flag==false) {
					JOptionPane.showMessageDialog(null, "请填写完整的外部数据库信息");
					return;
				}
				if(filePath.trim().length()<=0) {
					JOptionPane.showMessageDialog(null, "导信息到本地数据库需要excel文件，请选择正确的文件");
					return;
				}
				/*1.2得到本地数据库的信息*/
				Map<String,String> message_local = PropertiesUtils.list("local_message.properties");
				
				/*1.3调用api*/
				transfer_data_service service = new transfer_data_service();
			
				service.transfer_data(message_local, message_ex, filePath);
			
			}
		});
		
		JLabel label = new JLabel("选择对应关系文件：");
		label.setFont(new Font("PingFang HK", Font.PLAIN, 14));
		label.setBounds(25, 139, 130, 16);
		panel_content.add(label);
		
		textField = new JTextField();
		textField.setEditable(false);
		textField.setFont(new Font("PingFang HK", Font.PLAIN, 14));
		textField.setColumns(10);
		textField.setBounds(161, 136, 479, 25);
		panel_content.add(textField);
		
		JButton button = new JButton("浏览");
		button.setFont(new Font("PingFang HK", Font.PLAIN, 14));
		button.setBounds(652, 134, 109, 29);
		panel_content.add(button);
		
		/*添加导数据库文件游览*/
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				FileNameExtensionFilter filter = new FileNameExtensionFilter("表格文件(xls,xlsx)", "xls","xlsx");
				fileChooser.setFileFilter(filter);
				fileChooser.showDialog(null, "选择或创建excel文件");
				File file = fileChooser.getSelectedFile();
				if(file==null) return;
				/*创建时要以xls或者xlsx结尾*/
				filePath = file.getAbsolutePath();
				if(!filePath.endsWith(".xls") && !filePath.endsWith(".xlsx")) {
					filePath = filePath+".xlsx";
				}
				file = new File(filePath);
				/*同时将文件名字显示*/
				textField.setText(file.getName());
				textField_SelectFile.setText("");
			}
		});
		
		JButton button_WriteToExcel = new JButton("写入到文件");
		button_WriteToExcel.setFont(new Font("PingFang HK", Font.PLAIN, 14));
		button_WriteToExcel.setBounds(531, 220, 109, 29);
		panel_content.add(button_WriteToExcel);
		
		
		/*为写入到文件添加事件*/
		button_WriteToExcel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Map<String,String> message = new HashMap<String, String>();
				/*1.1校验数据库信息*/
				boolean flag = vertify(message);
				if(flag==false) {
					JOptionPane.showMessageDialog(null, "请填写完整的外部数据库信息");
					return;
				}
				/*1.2通过校验之后将数据打包好*/
				if(filePath.trim().length()<=0) {
					JOptionPane.showMessageDialog(null, "导出外部数据库信息到excel文件需要选择excel，请选择正确的文件");
					return;
				}
				message.put("filepath", filePath);
				/*1.3调用api*/
				read_ex_struct_service service = new read_ex_struct_service();
				try {
					service.read_struct(message);
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, "导出外部数据库信息失败");
					e1.printStackTrace();
					return;
				}
				
			}
		});
		
	}
	
	public void addContent3() {
		filePath = "";
		//上侧图片
		JLabel label_picture = new JLabel("");
		label_picture.setIcon(new ImageIcon(JFrameMain.class.getResource("/com/zjnu/image/Step3.png")));
		panel_picture.add(label_picture);
				
		//下侧内容
		JLabel label_DatabaseType = new JLabel("数据库类型：");
		label_DatabaseType.setFont(new Font("PingFang HK", Font.PLAIN, 14));
		label_DatabaseType.setBounds(25, 25, 109, 16);
		panel_content.add(label_DatabaseType);
		
		JLabel label_IpAddress = new JLabel("IP地址：");
		label_IpAddress.setFont(new Font("PingFang HK", Font.PLAIN, 14));
		label_IpAddress.setBounds(266, 25, 68, 16);
		panel_content.add(label_IpAddress);
		
		JLabel label_DatabaseName = new JLabel("数据库名称：");
		label_DatabaseName.setFont(new Font("PingFang HK", Font.PLAIN, 14));
		label_DatabaseName.setBounds(505, 25, 121, 16);
		panel_content.add(label_DatabaseName);
		
		JLabel label_Account = new JLabel("账号：");
		label_Account.setFont(new Font("PingFang HK", Font.PLAIN, 14));
		label_Account.setBounds(25, 63, 68, 16);
		panel_content.add(label_Account);
		
		JLabel label_Password = new JLabel("密码：");
		label_Password.setIcon(null);
		label_Password.setFont(new Font("PingFang HK", Font.PLAIN, 14));
		label_Password.setBounds(282, 63, 85, 16);
		panel_content.add(label_Password);
		
		JComboBox comboBox_DatabaseType = new JComboBox();
		comboBox_DatabaseType.setFont(new Font("PingFang HK", Font.PLAIN, 14));
		comboBox_DatabaseType.setModel(new DefaultComboBoxModel(new String[] {"Oracle", "mysql","sqlserve"}));
		comboBox_DatabaseType.setBounds(122, 20, 129, 27);
		panel_content.add(comboBox_DatabaseType);
		
		JTextField textField_IpAddress = new JTextField();
		textField_IpAddress.setFont(new Font("PingFang HK", Font.PLAIN, 14));
		textField_IpAddress.setBounds(323, 20, 170, 25);
		panel_content.add(textField_IpAddress);
		textField_IpAddress.setColumns(10);
		
		JTextField textField_DatabaseName = new JTextField();
		textField_DatabaseName.setFont(new Font("PingFang HK", Font.PLAIN, 14));
		textField_DatabaseName.setColumns(10);
		textField_DatabaseName.setBounds(591, 19, 170, 27);
		panel_content.add(textField_DatabaseName);
		
		JTextField textField_Account = new JTextField();
		textField_Account.setFont(new Font("PingFang HK", Font.PLAIN, 14));
		textField_Account.setColumns(10);
		textField_Account.setBounds(80, 60, 180, 25);
		panel_content.add(textField_Account);
		
		JPasswordField passwordField_Password = new JPasswordField();
		passwordField_Password.setFont(new Font("PingFang HK", Font.PLAIN, 14));
		passwordField_Password.setColumns(10);
		passwordField_Password.setBounds(334, 60, 180, 25);
		panel_content.add(passwordField_Password);
		
		JProgressBar progressBar = new JProgressBar();
		progressBar.setFont(new Font("PingFang HK", Font.PLAIN, 14));
		progressBar.setBounds(25, 187, 736, 20);
		panel_content.add(progressBar);
		
		JLabel label_Status = new JLabel("当前状态：数据库连接成功，正在导入数据库......");
		label_Status.setForeground(Color.DARK_GRAY);
		label_Status.setFont(new Font("PingFang HK", Font.PLAIN, 14));
		label_Status.setBounds(25, 225, 318, 16);
		panel_content.add(label_Status);
		
		JButton button_WriteToFile = new JButton("导入数据库");
		button_WriteToFile.setFont(new Font("PingFang HK", Font.PLAIN, 14));
		button_WriteToFile.setBounds(652, 220, 109, 29);
		panel_content.add(button_WriteToFile);
		
		/*导数据库*/
		button_WriteToFile.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Map<String,String> message_ex = new HashMap<String, String>();
				/*1.1校验外部数据库信息*/
				boolean flag = vertify(message_ex);
				if(flag==false) {
					JOptionPane.showMessageDialog(null, "请填写完整的外部数据库信息");
					return;
				}
				//不需要excel文件
//				if(filePath.trim().length()<=0) {
//					JOptionPane.showMessageDialog(null, "导信息到数据库需要excel文件，请选择正确的文件");
//					return;
//				}
				/*1.2得到本地数据库的信息*/
//				System.out.println("外部数据库"+message_ex);
				Map<String,String> message_local = PropertiesUtils.list("local_message.properties");
//				System.out.println("内部数据库："+message_local);
				/*1.3调用api*/
				try {
					Mybatistest.sqltosql(message_local, message_ex);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
	}
	
	/*校验panelcontent中的数据*/
	public boolean vertify(Map<String,String> message) {
		String[] name = {"databasetype", "ipaddress", "databasename", "username", "password"};
		Component[] components = panel_content.getComponents();
		for(int i=5;i<=9;i++) {
			if(i==5) {
				JComboBox<String> databasetype = (JComboBox<String>)components[i];
				String datatypeString = (String)databasetype.getSelectedItem();
				
				if( datatypeString==null || datatypeString.trim().length()<=0 ) return false;
				else message.put(name[i-5], (String)databasetype.getSelectedItem());
			}
			else {
				JTextField textField = (JTextField)components[i];
				String text = textField.getText();
				if(text==null || text.trim().length()<=0) return false;
				else message.put(name[i-5], text);
			}
	
		}
		
		return true;
	}
	
	
	
}
