package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import management.Controller;

public class WelcomeFrame {

	private Controller controller;
	private JFrame frame;
	private DefaultListModel<String> defaultListModel;
	private DefaultListModel<String> defaultListModel_1;
	private DefaultListModel<String> defaultListModel_2;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					WelcomeFrame window = new WelcomeFrame();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public WelcomeFrame() {
		initialize();

	}

	private void initialize() {
		controller = Controller.getInstance();
		frame = new JFrame("ComBad v1.0");
		frame.setVisible(true);

		ImageIcon icon = new ImageIcon("badCodeSmellIco.jpg");
		frame.setBounds(100, 100, 750, 500);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setIconImage(icon.getImage());

		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		JMenu mnNewMenu = new JMenu("Program");
		menuBar.add(mnNewMenu);

		JMenuItem mntmNewMenuItem = new JMenuItem("About ComBad");
		mnNewMenu.add(mntmNewMenuItem);

		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null,
						"ComBad stands for Committers - Bad Code Smells. This software associates bad code smells present in GitHub software projects to their own committers.",
						"About ComBad", JOptionPane.INFORMATION_MESSAGE);
			}
		});

		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Exit");
		mnNewMenu.add(mntmNewMenuItem_1);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));

		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 0, 0, 0 };
		gbl_panel.rowHeights = new int[] { 0, 0, 60, 0, 0, 0, 0, 0, 0 };
		gbl_panel.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel); 

		JPanel panel_5 = new JPanel();
		GridBagConstraints gbc_panel_5 = new GridBagConstraints();
		gbc_panel_5.insets = new Insets(0, 0, 5, 0);
		gbc_panel_5.fill = GridBagConstraints.BOTH;
		gbc_panel_5.gridx = 1;
		gbc_panel_5.gridy = 0;
		panel.add(panel_5, gbc_panel_5);

		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon("badCodeSmell.png"));
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.fill = GridBagConstraints.BOTH;
		gbc_label.insets = new Insets(0, 0, 5, 0);
		gbc_label.gridx = 1;
		gbc_label.gridy = 1;
		panel.add(label, gbc_label);

		JPanel panel_3 = new JPanel();
		GridBagConstraints gbc_panel_3 = new GridBagConstraints();
		gbc_panel_3.insets = new Insets(0, 0, 5, 0);
		gbc_panel_3.fill = GridBagConstraints.BOTH;
		gbc_panel_3.gridx = 1;
		gbc_panel_3.gridy = 2;
		panel.add(panel_3, gbc_panel_3);

		JPanel panel_4 = new JPanel();
		GridBagConstraints gbc_panel_4 = new GridBagConstraints();
		gbc_panel_4.gridheight = 6;
		gbc_panel_4.insets = new Insets(0, 0, 5, 5);
		gbc_panel_4.fill = GridBagConstraints.BOTH;
		gbc_panel_4.gridx = 0;
		gbc_panel_4.gridy = 1;
		panel.add(panel_4, gbc_panel_4);

		JLabel lblCommittersBad = new JLabel("Committers - Bad Code Smells");
		lblCommittersBad.setFont(new Font("SansSerif", Font.BOLD, 12));
		GridBagConstraints gbc_lblCommittersBad = new GridBagConstraints();
		gbc_lblCommittersBad.anchor = GridBagConstraints.WEST;
		gbc_lblCommittersBad.insets = new Insets(0, 0, 5, 0);
		gbc_lblCommittersBad.gridx = 1;
		gbc_lblCommittersBad.gridy = 3;
		panel.add(lblCommittersBad, gbc_lblCommittersBad);

		JLabel lblVersion = new JLabel("Version 1.0");
		lblVersion.setFont(new Font("SansSerif", Font.BOLD, 12));
		GridBagConstraints gbc_lblVersion = new GridBagConstraints();
		gbc_lblVersion.anchor = GridBagConstraints.WEST;
		gbc_lblVersion.insets = new Insets(0, 0, 5, 0);
		gbc_lblVersion.gridx = 1;
		gbc_lblVersion.gridy = 4;
		panel.add(lblVersion, gbc_lblVersion);

		JLabel lblUniversityOfSannio = new JLabel("University of Sannio");
		lblUniversityOfSannio.setFont(new Font("SansSerif", Font.BOLD, 12));
		GridBagConstraints gbc_lblUniversityOfSannio = new GridBagConstraints();
		gbc_lblUniversityOfSannio.anchor = GridBagConstraints.WEST;
		gbc_lblUniversityOfSannio.insets = new Insets(0, 0, 5, 0);
		gbc_lblUniversityOfSannio.gridx = 1;
		gbc_lblUniversityOfSannio.gridy = 5;
		panel.add(lblUniversityOfSannio, gbc_lblUniversityOfSannio);

		JLabel lblDepartmentOfEngineering = new JLabel("Department of Engineering");
		lblDepartmentOfEngineering.setFont(new Font("SansSerif", Font.BOLD, 12));
		GridBagConstraints gbc_lblDepartmentOfEngineering = new GridBagConstraints();
		gbc_lblDepartmentOfEngineering.insets = new Insets(0, 0, 5, 0);
		gbc_lblDepartmentOfEngineering.anchor = GridBagConstraints.WEST;
		gbc_lblDepartmentOfEngineering.gridx = 1;
		gbc_lblDepartmentOfEngineering.gridy = 6;
		panel.add(lblDepartmentOfEngineering, gbc_lblDepartmentOfEngineering);

		JPanel panel_6 = new JPanel();
		GridBagConstraints gbc_panel_6 = new GridBagConstraints();
		gbc_panel_6.fill = GridBagConstraints.BOTH;
		gbc_panel_6.gridx = 1;
		gbc_panel_6.gridy = 7;
		panel.add(panel_6, gbc_panel_6);

		JPanel panel_1 = new JPanel();
		frame.getContentPane().add(panel_1, BorderLayout.EAST);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[] { 172, 0, 0 };
		gbl_panel_1.rowHeights = new int[] { 8, 0, 0, 0, 0 };
		gbl_panel_1.columnWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		gbl_panel_1.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panel_1.setLayout(gbl_panel_1);
		
		JPanel panel_7 = new JPanel();
		GridBagConstraints gbc_panel_7 = new GridBagConstraints();
		gbc_panel_7.insets = new Insets(0, 0, 5, 5);
		gbc_panel_7.fill = GridBagConstraints.BOTH;
		gbc_panel_7.gridx = 0;
		gbc_panel_7.gridy = 0;
		panel_1.add(panel_7, gbc_panel_7);

		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(null, "Software systems", TitledBorder.LEADING, TitledBorder.TOP,
				new Font("SansSerif", Font.BOLD, 12), null));
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.insets = new Insets(0, 0, 5, 5);
		gbc_panel_2.fill = GridBagConstraints.BOTH;
		gbc_panel_2.gridx = 0;
		gbc_panel_2.gridy = 1;
		panel_1.add(panel_2, gbc_panel_2);
		GridBagLayout gbl_panel_2 = new GridBagLayout();
		gbl_panel_2.columnWidths = new int[] { 134, 0 };
		gbl_panel_2.rowHeights = new int[] { 81, 0, 0, 0, 0 };
		gbl_panel_2.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_panel_2.rowWeights = new double[] { 1.0, 1.0, 0.0, 1.0, Double.MIN_VALUE };
		panel_2.setLayout(gbl_panel_2);

		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.HORIZONTAL;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 0;
		panel_2.add(scrollPane, gbc_scrollPane);

		defaultListModel = new DefaultListModel<String>();

		defaultListModel.addElement("dnsjava-2.1.9");

		@SuppressWarnings({ "unchecked", "rawtypes" })
		JList list = new JList(defaultListModel);

		scrollPane.setViewportView(list);
		scrollPane.setPreferredSize(
				new Dimension(scrollPane.getPreferredSize().width, scrollPane.getPreferredSize().width));

		JScrollPane scrollPane_1 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
		gbc_scrollPane_1.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_1.gridx = 0;
		gbc_scrollPane_1.gridy = 1;
		panel_2.add(scrollPane_1, gbc_scrollPane_1);

		defaultListModel_1 = new DefaultListModel<String>();

		defaultListModel_1.addElement("tika-1.21");

		@SuppressWarnings({ "rawtypes", "unchecked" })
		JList list_1 = new JList(defaultListModel_1);
		scrollPane_1.setViewportView(list_1);
		scrollPane_1.setPreferredSize(
				new Dimension(scrollPane.getPreferredSize().width, scrollPane.getPreferredSize().width));

		JScrollPane scrollPane_2 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_2 = new GridBagConstraints();
		gbc_scrollPane_2.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane_2.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_2.gridx = 0;
		gbc_scrollPane_2.gridy = 2;
		panel_2.add(scrollPane_2, gbc_scrollPane_2);

		defaultListModel_2 = new DefaultListModel<String>();

		defaultListModel_2.addElement("guava-27.0");
		defaultListModel_2.addElement("guava-27.1");
		defaultListModel_2.addElement("guava-28.0");

		@SuppressWarnings({ "rawtypes", "unchecked" })
		JList list_2 = new JList(defaultListModel_2);
		scrollPane_2.setViewportView(list_2);
		scrollPane_2.setPreferredSize(
				new Dimension(scrollPane.getPreferredSize().width, scrollPane.getPreferredSize().width));

		JButton btnNext = new JButton("Next");

		GridBagConstraints gbc_btnNext = new GridBagConstraints();
		gbc_btnNext.insets = new Insets(0, 0, 5, 5);
		gbc_btnNext.gridx = 0;
		gbc_btnNext.gridy = 2;
		panel_1.add(btnNext, gbc_btnNext);

		list.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent lse) {
				list_1.clearSelection();
				list_2.clearSelection();

			}
		});

		list_1.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent lse) {
				list.clearSelection();
				list_2.clearSelection();

			}
		});

		list_2.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent lse) {
				list.clearSelection();
				list_1.clearSelection();

			}
		});

		btnNext.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				if (list.getSelectedValue() != null) {
					controller.visualizeSystem(list.getSelectedValue().toString());
				} else if (list_1.getSelectedValue() != null) {
					controller.visualizeSystem(list_1.getSelectedValue().toString());
				} else if (list_2.getSelectedValue() != null) {
					controller.visualizeSystem(list_2.getSelectedValue().toString());
				} else {
					JOptionPane.showMessageDialog(null, "You have to select an element in the lists!", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		frame.setLocationRelativeTo(null);
		frame.pack();
	}

}
