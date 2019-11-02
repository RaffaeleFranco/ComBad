package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.StringTokenizer;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import base.BadCodeSmell;
import base.Committer;
import base.DeadCode;
import base.DuplicatedCode;
import base.LargeClass;
import base.LongMethod;
import base.LongParameterList;
import management.Controller;

public class CommittersBadCodeSmellsFrame {

	private Controller controller;
	private JFrame frame;
	private JTable table;
	private JTable table_1;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					CommittersBadCodeSmellsFrame window = new CommittersBadCodeSmellsFrame(null);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public CommittersBadCodeSmellsFrame(String name) {
		initialize(name);

	}

	private void initialize(String name) {
		controller = Controller.getInstance();
		controller.readSoftwareSystem(name);

		ImageIcon icon = new ImageIcon("badCodeSmellIco.jpg");
		frame = new JFrame("Bad code smells & Committers");
		frame.setVisible(true);
		frame.setBounds(100, 100, 750, 500);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setIconImage(icon.getImage());

		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		JMenu mnNewMenu = new JMenu("Visualize");
		menuBar.add(mnNewMenu);

		JMenuItem mntmNewMenuItem = new JMenuItem("All bad code smells");
		mnNewMenu.add(mntmNewMenuItem);

		JMenuItem mntmNewMenuItem_1 = new JMenuItem("All committers");
		mnNewMenu.add(mntmNewMenuItem_1);

		JMenuItem mntmNewMenuItem_2 = new JMenuItem("Commits");
		mnNewMenu.add(mntmNewMenuItem_2);

		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.visualizeAllBadCodeSmells(name);
			}
		});

		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.visualizeAllCommitters(name);
			}
		});

		mntmNewMenuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.visualizeCommits(name);
			}
		});

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
		frame.getContentPane().setLayout(gridBagLayout);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		GridBagConstraints gbc_tabbedPane = new GridBagConstraints();
		gbc_tabbedPane.gridwidth = 2;
		gbc_tabbedPane.gridheight = 2;
		gbc_tabbedPane.insets = new Insets(0, 0, 5, 5);
		gbc_tabbedPane.fill = GridBagConstraints.BOTH;
		gbc_tabbedPane.gridx = 0;
		gbc_tabbedPane.gridy = 0;
		frame.getContentPane().add(tabbedPane, gbc_tabbedPane);

		JPanel panel = new JPanel();
		tabbedPane.addTab("Bad code smells", null, panel, null);

		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 0, 0 };
		gbl_panel.rowHeights = new int[] { 0, 0 };
		gbl_panel.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		// HashSet<BadCodeSmell> badCodeSmells =
		// controller.getAssociationManagers().get(name).getBadCodeSmells();

		StringTokenizer stringTokenizer = new StringTokenizer(name, "-");

		String systemName = stringTokenizer.nextToken();
		String systemVersion = stringTokenizer.nextToken();

		HashSet<BadCodeSmell> badCodeSmells = controller.getDbManager()
				.selectFromBadCodeSmellsJoinAssociations(systemName, systemVersion);

		String[] columnNames = { "Type", "Class name", "Package", "Start line", "End line" };

		Object[][] data = new Object[badCodeSmells.size()][5];

		int row = 0;

		for (BadCodeSmell bcs : badCodeSmells) {

			data[row][0] = getTypeBadCodeSmell(bcs);
			data[row][1] = bcs.getClassName();
			data[row][2] = bcs.getPackageName();
			data[row][3] = bcs.getStartRow();
			data[row][4] = bcs.getEndRow();
			row++;
		}

		DefaultTableModel model = new DefaultTableModel(data, columnNames) {

			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		table = new JTable();

		table.setModel(model);

		TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model);

		table.setRowSorter(sorter);

		sorter.setComparator(0, new Comparator<String>() {

			@Override
			public int compare(String type0, String type1) {
				return type0.compareTo(type1);
			}
		});

		sorter.setComparator(1, new Comparator<String>() {

			@Override
			public int compare(String className0, String className1) {
				return className0.compareTo(className1);
			}
		});

		sorter.setComparator(2, new Comparator<String>() {

			@Override
			public int compare(String package0, String package1) {
				return package0.compareTo(package1);
			}
		});

		sorter.setComparator(3, new Comparator<Integer>() {

			@Override
			public int compare(Integer startLine0, Integer startLine1) {
				return startLine0.compareTo(startLine1);
			}
		});

		sorter.setComparator(4, new Comparator<Integer>() {

			@Override
			public int compare(Integer endLine0, Integer endLine1) {
				return endLine0.compareTo(endLine1);
			}
		});

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setPreferredSize(new Dimension(800, 600));

		table.setFillsViewportHeight(true);

		panel.setLayout(new BorderLayout());
		panel.add(table.getTableHeader(), BorderLayout.PAGE_START);
		panel.add(scrollPane, BorderLayout.CENTER);

		table.addMouseListener(new MouseListener() {

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {

					String type = table.getValueAt(table.getSelectedRow(), 0).toString();
					String className = (String) table.getValueAt(table.getSelectedRow(), 1).toString();
					String packageName = (String) table.getValueAt(table.getSelectedRow(), 2).toString();
					String startRow = (String) table.getValueAt(table.getSelectedRow(), 3).toString();
					String endRow = (String) table.getValueAt(table.getSelectedRow(), 4).toString();

					controller.visualizeRelatedCommitters(name, type, className, packageName, startRow, endRow);
				}

			}
		});

		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Committers", null, panel_1, null);

		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[] { 0, 0 };
		gbl_panel_1.rowHeights = new int[] { 0, 0 };
		gbl_panel_1.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_panel_1.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		panel_1.setLayout(gbl_panel);

		// HashMap<String, Committer> committers =
		// controller.getAssociationManagers().get(name).getCommitters();

		HashMap<String, Committer> committers = controller.getDbManager()
				.selectFromCommittersJoinAssociations(systemName, systemVersion);

		String[] columnNames1 = { "Name", "Email", "Code Smells", "Dead Codes", "Duplicated Codes", "Large Classes",
				"Long Methods", "Long Parameter Lists" };

		Object[][] data1 = new Object[committers.size()][8];

		int row1 = 0;

		for (Committer c : committers.values()) {
			data1[row1][0] = c.getName();
			data1[row1][1] = c.getEmail();
			data1[row1][2] = c.getBadCodeSmells().size();

			int deadCodes = 0;
			int duplicatedCodes = 0;
			int largeClasses = 0;
			int longMethod = 0;
			int longParameterList = 0;

			for (BadCodeSmell b : c.getBadCodeSmells()) {
				if (b instanceof DeadCode)
					deadCodes++;
				else if (b instanceof DuplicatedCode)
					duplicatedCodes++;
				else if (b instanceof LargeClass)
					largeClasses++;
				else if (b instanceof LongMethod)
					longMethod++;
				else if (b instanceof LongParameterList)
					longParameterList++;
			}

			data1[row1][3] = deadCodes;
			data1[row1][4] = duplicatedCodes;
			data1[row1][5] = largeClasses;
			data1[row1][6] = longMethod;
			data1[row1][7] = longParameterList;
			row1++;
		}

		DefaultTableModel model_1 = new DefaultTableModel(data1, columnNames1) {

			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		table_1 = new JTable();

		table_1.setModel(model_1);

		TableRowSorter<TableModel> sorter_1 = new TableRowSorter<TableModel>(model_1);

		table_1.setRowSorter(sorter_1);

		sorter_1.setComparator(0, new Comparator<String>() {

			@Override
			public int compare(String name0, String name1) {
				return name0.compareTo(name1);
			}
		});

		sorter_1.setComparator(1, new Comparator<String>() {

			@Override
			public int compare(String email0, String email1) {
				return email0.compareTo(email1);
			}
		});

		JScrollPane scrollPane_1 = new JScrollPane(table_1);
		table_1.setFillsViewportHeight(true);

		panel_1.setLayout(new BorderLayout());
		panel_1.add(table_1.getTableHeader(), BorderLayout.PAGE_START);
		panel_1.add(scrollPane_1, BorderLayout.CENTER);

		table_1.addMouseListener(new MouseListener() {

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					controller.visualizeRelatedBadCodeSmells(name,
							table_1.getValueAt(table_1.getSelectedRow(), 1).toString());
				}

			}
		});

		frame.setLocationRelativeTo(null);
		frame.pack();
	}

	private String getTypeBadCodeSmell(BadCodeSmell badCodeSmell) {

		if (badCodeSmell instanceof DeadCode)
			return ((DeadCode) badCodeSmell).getType();
		else if (badCodeSmell instanceof DuplicatedCode)
			return "Duplicated Code";
		else if (badCodeSmell instanceof LargeClass)
			return "Large Class";
		else if (badCodeSmell instanceof LongMethod)
			return "Long Method";
		else if (badCodeSmell instanceof LongParameterList)
			return "Long Parameter List";
		else
			return "Undefined";
	}

}
