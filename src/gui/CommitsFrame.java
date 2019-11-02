package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagLayout;
import java.text.SimpleDateFormat;
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

import base.Commit;
import management.Controller;

public class CommitsFrame {

	private Controller controller;
	private JFrame frame;
	private JTable table;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					CommitsFrame window = new CommitsFrame("dnsjava-2.1.6");
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public CommitsFrame(String name) {
		initialize(name);

	}

	private void initialize(String name) {
		controller = Controller.getInstance();
		controller.readSoftwareSystem(name);

		ImageIcon icon = new ImageIcon("badCodeSmellIco.jpg");
		frame = new JFrame("All commits into the project");
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

		// HashMap<String, Commit> commits =
		// controller.getCommitManagers().get(name).getCommits();

		StringTokenizer stringTokenizer = new StringTokenizer(name, "-");

		String systemName = stringTokenizer.nextToken();
		String systemVersion = stringTokenizer.nextToken();

		HashMap<String, Commit> commits = controller.getDbManager().selectFromCommit(systemName, systemVersion);

		String[] columnNames = { "Id", "Email committer", "Date", "Description" };

		Object[][] data = new Object[commits.size()][4];

		int row = 0;

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		for (Commit c : commits.values()) {

			data[row][0] = c.getId();
			data[row][1] = c.getEmailCommitter();
			data[row][2] = sdf.format(c.getDate());
			data[row][3] = c.getDescription();
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
			public int compare(String id0, String id1) {
				return id0.compareTo(id1);
			}
		});

		sorter.setComparator(1, new Comparator<String>() {

			@Override
			public int compare(String emailCommitter0, String emailCommitter1) {
				return emailCommitter0.compareTo(emailCommitter1);
			}
		});

		sorter.setComparator(2, new Comparator<String>() {

			@Override
			public int compare(String date0, String date1) {
				return date0.compareTo(date1);
			}
		});

		sorter.setComparator(3, new Comparator<String>() {

			@Override
			public int compare(String description0, String description1) {
				return description0.compareTo(description1);
			}
		});

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setPreferredSize(new Dimension(1000, 400));
		table.setFillsViewportHeight(true);

		panel.setLayout(new BorderLayout());
		panel.add(table.getTableHeader(), BorderLayout.PAGE_START);
		panel.add(scrollPane, BorderLayout.CENTER);

		frame.setLocationRelativeTo(null);
		frame.pack();
	}

}
