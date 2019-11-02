package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagLayout;
import java.util.Comparator;
import java.util.HashMap;
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

import base.Committer;
import management.Controller;

public class AllCommittersFrame {

	private Controller controller;
	private JFrame frame;
	private JTable table;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					AllCommittersFrame window = new AllCommittersFrame("dnsjava-2.1.6");
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public AllCommittersFrame(String name) {
		initialize(name);

	}

	private void initialize(String name) {
		controller = Controller.getInstance();
		controller.readSoftwareSystem(name);

		ImageIcon icon = new ImageIcon("badCodeSmellIco.jpg");
		frame = new JFrame("All committers into the project");
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

		// HashMap<String, Committer> committers =
		// controller.getCommitManagers().get(name).getCommitters();

		StringTokenizer stringTokenizer = new StringTokenizer(name, "-");

		String systemName = stringTokenizer.nextToken();
		String systemVersion = stringTokenizer.nextToken();

		HashMap<String, Committer> committers = controller.getDbManager().selectFromCommitters(systemName,
				systemVersion);

		String[] columnNames1 = { "Name", "Email" };

		Object[][] data1 = new Object[committers.size()][2];

		int row1 = 0;

		for (Committer c : committers.values()) {
			data1[row1][0] = c.getName();
			data1[row1][1] = c.getEmail();
			row1++;
		}

		DefaultTableModel model_1 = new DefaultTableModel(data1, columnNames1) {

			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		table = new JTable();

		table.setModel(model_1);

		TableRowSorter<TableModel> sorter_1 = new TableRowSorter<TableModel>(model_1);

		table.setRowSorter(sorter_1);

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

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setPreferredSize(new Dimension(600, 300));
		table.setFillsViewportHeight(true);

		panel.setLayout(new BorderLayout());
		panel.add(table.getTableHeader(), BorderLayout.PAGE_START);
		panel.add(scrollPane, BorderLayout.CENTER);

		frame.setLocationRelativeTo(null);
		frame.pack();
	}

}
