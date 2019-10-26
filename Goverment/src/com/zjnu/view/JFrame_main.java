package com.zjnu.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.FileReader;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class JFrame_main extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try
	    {
	        //设置本属性将改变窗口边框样式定义
	        BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.translucencyAppleLike;
	        org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
	    }
	    catch(Exception e)
	    {
	        //TODO exception
	    }
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JFrame_main frame = new JFrame_main();
					frame.setLocationRelativeTo(null);
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
	public JFrame_main() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(800, 1250, 800, 650);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu menu_step1 = new JMenu("\u5BFC\u51FA\u672C\u5730\u7ED3\u6784");
		menu_step1.setIcon(new ImageIcon(JFrame_main.class.getResource("/com/zjnu/image/step1_bg.png")));
		menuBar.add(menu_step1);
		
		JMenuItem menuitem_step1 = new JMenuItem("\u5BFC\u51FA\u672C\u5730\u7ED3\u6784");
		//跳转到setp1功能界面
		menuitem_step1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JPanel_step1 step1 = new JPanel_step1();
				contentPane.setVisible(false);
				contentPane.removeAll();
				contentPane.add(step1);
				contentPane.setVisible(true);
			}
		});
		menuitem_step1.setIcon(new ImageIcon(JFrame_main.class.getResource("/com/zjnu/image/step1_small.png")));
		menu_step1.add(menuitem_step1);
		
		JMenu menu_step2 = new JMenu("\u6784\u5EFA\u5BF9\u5E94\u5173\u7CFB");
		menu_step2.setIcon(new ImageIcon(JFrame_main.class.getResource("/com/zjnu/image/step2_bg.png")));
		menuBar.add(menu_step2);
		
		JMenuItem menuitem_step2 = new JMenuItem("\u6784\u5EFA\u5BF9\u5E94\u5173\u7CFB");
		//跳转到step2功能界面
		menuitem_step2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JPanel_step2 step2  = new JPanel_step2();
				contentPane.setVisible(false);
				contentPane.removeAll();
				contentPane.add(step2);
				contentPane.setVisible(true);
			}
		});
		menuitem_step2.setIcon(new ImageIcon(JFrame_main.class.getResource("/com/zjnu/image/step_small.png")));
		menu_step2.add(menuitem_step2);
		
		JMenu menu_step3 = new JMenu("\u5BFC\u5165\u6570\u636E");
		menu_step3.setIcon(new ImageIcon(JFrame_main.class.getResource("/com/zjnu/image/step3_bg.png")));
		menuBar.add(menu_step3);
		
		JMenuItem menuitem_step3 = new JMenuItem("\u5BFC\u5165\u6570\u636E");
		//跳转到step3功能界面
		menuitem_step3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JPanel_step3 step3  = new JPanel_step3();
				contentPane.setVisible(false);
				contentPane.removeAll();
				contentPane.add(step3);
				contentPane.setVisible(true);
			}
		});
		menuitem_step3.setIcon(new ImageIcon(JFrame_main.class.getResource("/com/zjnu/image/step3_small.png")));
		menu_step3.add(menuitem_step3);
		
		JMenu menu_step4 = new JMenu("\u5BFC\u51FA\u6570\u636E");
		menu_step4.setIcon(new ImageIcon(JFrame_main.class.getResource("/com/zjnu/image/step4.png")));
		menuBar.add(menu_step4);
		
		JMenuItem menuitem_step4 = new JMenuItem("\u5BFC\u51FA\u6570\u636E");
		//跳转到step4功能界面
		menuitem_step4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JPanel_step4 step4  = new JPanel_step4();
				contentPane.setVisible(false);
				contentPane.removeAll();
				contentPane.add(step4);
				contentPane.setVisible(true);
			}
		});
		menuitem_step4.setIcon(new ImageIcon(JFrame_main.class.getResource("/com/zjnu/image/step4_small.png")));
		menu_step4.add(menuitem_step4);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
	}

}
