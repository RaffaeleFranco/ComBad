package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagLayout;
import java.util.Comparator;
import java.util.HashSet;
import java.util.StringTokenizer;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import base.BadCodeSmell;
import base.DeadCode;
import base.DuplicatedCode;
import base.LargeClass;
import base.LongMethod;
import base.LongParameterList;
import management.Controller;

public class RelatedBadCodeSmellsFrame {

	private Controller controller;
	private JFrame frame;
	private JTable table;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					RelatedBadCodeSmellsFrame window = new RelatedBadCodeSmellsFrame(null, null);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public RelatedBadCodeSmellsFrame(String name, String committerEmail) {
		initialize(name, committerEmail);

	}

	private void initialize(String name, String committerEmail) {
		controller = Controller.getInstance();
		controller.readSoftwareSystem(name);

		ImageIcon icon = new ImageIcon("badCodeSmellIco.jpg");
		frame = new JFrame("Related bad code smells");
		frame.setVisible(true);
		frame.setBounds(100, 100, 750, 500);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setIconImage(icon.getImage());

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
		frame.getContentPane().setLayout(gridBagLayout);

		JPanel panel = new JPanel();
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 0, 0 };
		gbl_panel.rowHeights = new int[] { 0, 0 };
		gbl_panel.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);
		frame.getContentPane().add(panel);

		/*
		 * HashSet<BadCodeSmell> badCodeSmells =
		 * controller.getAssociationManagers().get(name).getCommitters()
		 * .get(committerEmail).getBadCodeSmells();
		 */

		StringTokenizer stringTokenizer = new StringTokenizer(name, "-");

		String systemName = stringTokenizer.nextToken();
		String systemVersion = stringTokenizer.nextToken();

		HashSet<BadCodeSmell> badCodeSmells = controller.getDbManager()
				.selectFromBadCodeSmellsJoinAssociations(systemName, systemVersion, committerEmail);

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
		scrollPane.setPreferredSize(new Dimension(700, 500));
		table.setFillsViewportHeight(true);

		panel.setLayout(new BorderLayout());
		panel.add(table.getTableHeader(), BorderLayout.PAGE_START);
		panel.add(scrollPane, BorderLayout.CENTER);

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
