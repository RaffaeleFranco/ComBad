package management;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

import base.BadCodeSmell;
import base.Change;
import base.Commit;
import base.Committer;
import base.CommitterBadCodeSmell;
import base.DeadCode;
import base.DuplicatedCode;
import base.LargeClass;
import base.LongMethod;
import base.LongParameterList;
import base.Range;

public class DBManager {

	private static DBManager instance;

	/**
	 * Singleton design pattern
	 * 
	 * @return A single instance of DBManager
	 * @throws IOException
	 */
	public static DBManager getInstance() throws IOException {
		if (instance == null)
			instance = new DBManager();
		return instance;
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

	private Connection connection;

	/**
	 * This class implements the Pure Fabrication design pattern (GRASP), In
	 * particular, the responsibilities about the access (so read and write) into
	 * MySQL database are managed here
	 * 
	 * @throws IOException
	 */
	private DBManager() throws IOException {

		BufferedReader br = new BufferedReader(new FileReader("src/main/resources/config.db"));

		String localhost = br.readLine();
		String port = br.readLine();
		String name = br.readLine();
		String password = br.readLine();

		br.close();

		connectDB(localhost, port, name, password);
		createDB();
		createTableSoftwareSystems();
		createTableCommitters();
		createTableCommits();
		createTableChanges();
		createTableRanges();
		createTableBadCodeSmells();
		createTableAssociations();
	}

	/**
	 * @param host localhost
	 * @param port port
	 * @param user username
	 * @param pass password
	 * @return True if database is connected
	 */
	public boolean connectDB(String host, String port, String user, String pass) {

		String url = "jdbc:mysql://" + host + ":" + port;
		try {
			this.connection = DriverManager.getConnection(url, user, pass);
		} catch (SQLException e) {
			this.connection = null;
			System.err.println(e.getMessage());
			return false;
		}
		return true;
	}

	public void createDB() {
		try {
			Statement stmt = connection.createStatement();

			String sql = "create database if not exists ComBad";

			stmt.executeUpdate(sql);

		} catch (SQLException e) {
			System.err.println(e.getMessage());

		}
	}

	public void createTableSoftwareSystems() {
		try {
			Statement stmt = connection.createStatement();

			String sql = "use ComBad";

			String sql1 = "create table if not exists software_systems ( name varchar(20) not null, version varchar(20) not null, primary key(name, version));";

			stmt.executeUpdate(sql);
			stmt.executeUpdate(sql1);

		} catch (SQLException e) {
			System.err.println("software systems: " + e.getMessage());
		}
	}

	public void createTableCommitters() {
		try {
			Statement stmt = connection.createStatement();

			String sql = "use ComBad";

			String sql1 = "create table if not exists committers ( name varchar(20) not null, email varchar(50) not null primary key);";

			stmt.executeUpdate(sql);
			stmt.executeUpdate(sql1);

		} catch (SQLException e) {
			System.err.println("committers: " + e.getMessage());
		}
	}

	public void createTableCommits() {
		try {
			Statement stmt = connection.createStatement();

			String sql = "use ComBad";

			String sql1 = "create table if not exists commits ( " + "commit_id varchar(50) not null primary key,"
					+ "committer_email varchar(50) not null references committers(email)," + "date_commit datetime,"
					+ "description varchar(20) not null," + "software_system_name varchar(20) not null,"
					+ "software_system_version varchar(20) not null,"
					+ "foreign key(software_system_name, software_system_version) references software_systems(name, version));";

			stmt.executeUpdate(sql);
			stmt.executeUpdate(sql1);

		} catch (SQLException e) {
			System.err.println("commits: " + e.getMessage());
		}
	}

	public void createTableChanges() {
		try {
			Statement stmt = connection.createStatement();

			String sql = "use ComBad";

			String sql1 = "create table if not exists changes ( change_id varchar(100) not null primary key, commit_id varchar(50) not null references commits(commit_id), file varchar(50) not null);";

			stmt.executeUpdate(sql);
			stmt.executeUpdate(sql1);

		} catch (SQLException e) {
			System.err.println("changes: " + e.getMessage());
		}
	}

	public void createTableRanges() {
		try {
			Statement stmt = connection.createStatement();

			String sql = "use ComBad";

			String sql1 = "create table if not exists ranges ( range_id varchar(150) not null primary key, change_id varchar(100) not null references changes(change_id), start_row int not null null, interval_range int not null);";

			stmt.executeUpdate(sql);
			stmt.executeUpdate(sql1);

		} catch (SQLException e) {
			System.err.println("ranges: " + e.getMessage());
		}
	}

	public void createTableBadCodeSmells() {
		try {
			Statement stmt = connection.createStatement();

			String sql = "use ComBad";

			String sql1 = "create table if not exists badcodesmells ( \n"
					+ "badcodesmell_id varchar(100) not null primary key, \n" + "class_name varchar(50) not null, \n"
					+ "method_name varchar(50), \n" + "package_name varchar(200) not null, \n"
					+ "file varchar(50) not null, \n" + "type varchar(50) not null,\n" + "variable varchar(10),\n"
					+ "start_row int not null, \n" + "end_row int not null, \n"
					+ "software_system_name varchar(20) not null, \n"
					+ "software_system_version varchar(20) not null, \n"
					+ "foreign key(software_system_name, software_system_version) references software_systems(name, version));";

			stmt.executeUpdate(sql);
			stmt.executeUpdate(sql1);

		} catch (SQLException e) {
			System.err.println("bad code smells: " + e.getMessage());
		}
	}

	public void createTableAssociations() {
		try {
			Statement stmt = connection.createStatement();

			String sql = "use ComBad";

			String sql1 = "create table if not exists associations ( committer_email varchar(50) not null references committers(email), badcodesmell_id varchar(100) not null references badcodesmells(badcodesmell_id), primary key(committer_email, badcodesmell_id));";

			stmt.executeUpdate(sql);
			stmt.executeUpdate(sql1);

		} catch (SQLException e) {
			System.err.println("associations: " + e.getMessage());
		}
	}

	public void insertIntoSoftwareSystems(String name, String version) {
		try {
			Statement stmt = connection.createStatement();

			String sql = "insert ignore into software_systems values('" + name + "', '" + version + "');";

			stmt.executeUpdate(sql);

		} catch (SQLException e) {
			System.err.println("software systems: " + e.getMessage());
		}
	}

	public void insertIntoCommitters(Committer committer) {
		try {
			Statement stmt = connection.createStatement();

			String sql = "insert ignore into committers values('" + committer.getName() + "', '" + committer.getEmail()
					+ "');";

			stmt.executeUpdate(sql);

		} catch (SQLException e) {
			System.err.println("committers: " + e.getMessage());
		}
	}

	public void insertIntoCommitters(HashMap<String, Committer> committers) {
		for (Committer c : committers.values())
			insertIntoCommitters(c);
	}

	public void insertIntoCommits(Commit commit, String name, String version) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Statement stmt = connection.createStatement();

			String sql = "insert ignore into commits values ( '" + commit.getId() + "', '" + commit.getEmailCommitter()
					+ "', '" + sdf.format(commit.getDate()) + "', '" + commit.getDescription() + "', '" + name + "', '"
					+ version + "');";

			stmt.executeUpdate(sql);

		} catch (SQLException e) {
			System.err.println("commits: " + e.getMessage());
		}
	}

	public void insertIntoCommits(HashMap<String, Commit> commits, String name, String version) {
		for (Commit c : commits.values())
			insertIntoCommits(c, name, version);
	}

	public void insertIntoChanges(Change change) {
		try {
			Statement stmt = connection.createStatement();

			String sql = "insert ignore into changes values ( '" + change.getId() + "', '" + change.getCommitId()
					+ "', '" + change.getFile() + "');";

			stmt.executeUpdate(sql);

		} catch (SQLException e) {
			System.err.println("changes: " + e.getMessage());
		}
	}

	public void insertIntoChanges(ArrayList<Change> changes) {
		for (Change c : changes)
			insertIntoChanges(c);
	}

	public void insertIntoRanges(Range range) {
		try {
			Statement stmt = connection.createStatement();

			String sql = "insert ignore into ranges values ( '" + range.getId() + "', '" + range.getChangeId() + "', '"
					+ range.getStartRow() + "', '" + range.getInterval() + "');";

			stmt.executeUpdate(sql);

		} catch (SQLException e) {
			System.err.println("ranges: " + e.getMessage());
		}
	}

	public void insertIntoRanges(ArrayList<Range> ranges) {
		for (Range r : ranges)
			insertIntoRanges(r);
	}

	public void insertIntoBadCodeSmells(String badCodeSmellId, BadCodeSmell badCodeSmell, String name, String version) {
		try {
			Statement stmt = connection.createStatement();
			String sql = null;

			if (badCodeSmell instanceof DeadCode) {

				sql = "insert ignore into badcodesmells values ( '" + badCodeSmellId + "', '"
						+ badCodeSmell.getClassName() + "', " + null + ", '" + badCodeSmell.getPackageName() + "', '"
						+ badCodeSmell.getFile() + "', '" + ((DeadCode) badCodeSmell).getType() + "', '"
						+ ((DeadCode) badCodeSmell).getVariable() + "', " + badCodeSmell.getStartRow() + ", "
						+ badCodeSmell.getEndRow() + ", '" + name + "', '" + version + "');";
			}

			else if (badCodeSmell instanceof DuplicatedCode) {
				sql = "insert ignore into badcodesmells values ( '" + badCodeSmellId + "', '"
						+ badCodeSmell.getClassName() + "', " + null + ", '" + badCodeSmell.getPackageName() + "', '"
						+ badCodeSmell.getFile() + "', '" + getTypeBadCodeSmell(badCodeSmell) + "', " + null + ", "
						+ badCodeSmell.getStartRow() + ", " + badCodeSmell.getEndRow() + ", '" + name + "', '" + version
						+ "');";
			}

			else if (badCodeSmell instanceof LargeClass) {
				sql = "insert ignore into badcodesmells values ( '" + badCodeSmellId + "', '"
						+ badCodeSmell.getClassName() + "', " + null + ", '" + badCodeSmell.getPackageName() + "', '"
						+ badCodeSmell.getFile() + "', '" + getTypeBadCodeSmell(badCodeSmell) + "', " + null + ", "
						+ badCodeSmell.getStartRow() + ", " + badCodeSmell.getEndRow() + ", '" + name + "', '" + version
						+ "');";
			}

			else if (badCodeSmell instanceof LongMethod) {
				sql = "insert ignore into badcodesmells values ( '" + badCodeSmellId + "', '"
						+ badCodeSmell.getClassName() + "', '" + ((LongMethod) badCodeSmell).getMethodName() + "', '"
						+ badCodeSmell.getPackageName() + "', '" + badCodeSmell.getFile() + "', '"
						+ getTypeBadCodeSmell(badCodeSmell) + "', " + null + ", " + badCodeSmell.getStartRow() + ", "
						+ badCodeSmell.getEndRow() + ", '" + name + "', '" + version + "');";
			}

			else if (badCodeSmell instanceof LongParameterList) {
				sql = "insert ignore into badcodesmells values ( '" + badCodeSmellId + "', '"
						+ badCodeSmell.getClassName() + "', '" + ((LongParameterList) badCodeSmell).getMethod() + "', '"
						+ badCodeSmell.getPackageName() + "', '" + badCodeSmell.getFile() + "', '"
						+ getTypeBadCodeSmell(badCodeSmell) + "', " + null + ", " + badCodeSmell.getStartRow() + ", "
						+ badCodeSmell.getEndRow() + ", '" + name + "', '" + version + "');";
			}

			stmt.executeUpdate(sql);

		} catch (SQLException e) {

			System.err.println("bad code smells: " + e.getMessage());
		}
	}

	public void insertIntoAssociations(CommitterBadCodeSmell association) {
		try {
			Statement stmt = connection.createStatement();

			String sql = "use ComBad";

			String sql1 = "insert ignore into associations values ( '" + association.getCommitterEmail() + "', '"
					+ association.getBadCodeSmellId() + "');";

			stmt.executeUpdate(sql);
			stmt.executeUpdate(sql1);

		} catch (SQLException e) {
			System.err.println("associations: " + e.getMessage());
		}
	}

	public void insertIntoAssociations(HashMap<String, CommitterBadCodeSmell> associations) {
		for (CommitterBadCodeSmell a : associations.values())
			insertIntoAssociations(a);
	}

	public HashSet<BadCodeSmell> selectFromBadCodeSmellsJoinAssociations(String name, String version) {

		HashSet<BadCodeSmell> badCodeSmells = new HashSet<BadCodeSmell>();
		try {
			Statement stmt = connection.createStatement();

			ResultSet rs = stmt.executeQuery(
					"select badcodesmells.type, " + "badcodesmells.class_name, " + "badcodesmells.package_name,"
							+ "badcodesmells.file, " + "badcodesmells.start_row, " + "badcodesmells.end_row, "
							+ "badcodesmells.variable, badcodesmells.method_name from badcodesmells "
							+ "join associations on badcodesmells.badcodesmell_id = " + "associations.badcodesmell_id "
							+ "where badcodesmells.software_system_name = '" + name
							+ "' and badcodesmells.software_system_version = '" + version + "';");

			while (rs.next()) {
				if (rs.getString("type").contentEquals("DuplicatedCode")) {

					badCodeSmells.add(new DuplicatedCode(rs.getString("class_name"), rs.getString("package_name"),
							rs.getString("file"), rs.getInt("start_row"), rs.getInt("end_row"),
							(rs.getInt("end_row") - rs.getInt("start_row"))));

				} else if (rs.getString("type").contentEquals("LargeClass")) {

					badCodeSmells.add(new LargeClass(rs.getString("class_name"), rs.getString("package_name"),
							rs.getString("file"), rs.getInt("start_row"), rs.getInt("end_row")));

				} else if (rs.getString("type").contentEquals("LongMethod")) {

					badCodeSmells.add(new LongMethod(rs.getString("class_name"), rs.getString("package_name"),
							rs.getString("file"), rs.getInt("start_row"), rs.getInt("end_row"),
							rs.getString("method_name")));

				} else if (rs.getString("type").contentEquals("LongParametersList")) {

					badCodeSmells.add(new LongParameterList(rs.getString("class_name"), rs.getString("package_name"),
							rs.getString("file"), rs.getInt("start_row"), rs.getInt("end_row"),
							rs.getString("method_name")));

				} else {
					badCodeSmells.add(new DeadCode(rs.getString("class_name"), rs.getString("package_name"),
							rs.getString("file"), rs.getInt("start_row"), rs.getInt("end_row"), rs.getString("type"),
							rs.getString("variable")));
				}

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return badCodeSmells;

	}

	public HashMap<String, Committer> selectFromCommittersJoinAssociations(String name, String version) {

		HashMap<String, Committer> committers = new HashMap<String, Committer>();
		try {
			Statement stmt = connection.createStatement();

			ResultSet rs = stmt.executeQuery(
					"select committers.name, committers.email from committers join commits on committers.email = commits.committer_email join associations on committers.email = associations.committer_email "
							+ "where commits.software_system_name = '" + name
							+ "' and commits.software_system_version = '" + version + "';");

			while (rs.next()) {
				if (committers.get(rs.getString("email")) == null)
					committers.put(rs.getString("email"), new Committer(rs.getString("name"), rs.getString("email")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return committers;

	}

	public HashSet<BadCodeSmell> selectFromBadCodeSmellsJoinAssociations(String name, String version,
			String emailCommitter) {

		HashSet<BadCodeSmell> badCodeSmells = new HashSet<BadCodeSmell>();
		try {
			Statement stmt = connection.createStatement();

			ResultSet rs = stmt.executeQuery(
					"select badcodesmells.type, " + "badcodesmells.class_name, " + "badcodesmells.package_name,"
							+ "badcodesmells.file, " + "badcodesmells.start_row, " + "badcodesmells.end_row, "
							+ "badcodesmells.variable, badcodesmells.method_name from badcodesmells "
							+ "join associations on badcodesmells.badcodesmell_id = " + "associations.badcodesmell_id "
							+ "where badcodesmells.software_system_name = '" + name
							+ "' and badcodesmells.software_system_version = '" + version + "' and "
							+ "associations.committer_email = '" + emailCommitter + "';");

			while (rs.next()) {
				if (rs.getString("type").contentEquals("DuplicatedCode")) {

					badCodeSmells.add(new DuplicatedCode(rs.getString("class_name"), rs.getString("package_name"),
							rs.getString("file"), rs.getInt("start_row"), rs.getInt("end_row"),
							(rs.getInt("end_row") - rs.getInt("start_row"))));

				} else if (rs.getString("type").contentEquals("LargeClass")) {

					badCodeSmells.add(new LargeClass(rs.getString("class_name"), rs.getString("package_name"),
							rs.getString("file"), rs.getInt("start_row"), rs.getInt("end_row")));

				} else if (rs.getString("type").contentEquals("LongMethod")) {

					badCodeSmells.add(new LongMethod(rs.getString("class_name"), rs.getString("package_name"),
							rs.getString("file"), rs.getInt("start_row"), rs.getInt("end_row"),
							rs.getString("method_name")));

				} else if (rs.getString("type").contentEquals("LongParametersList")) {

					badCodeSmells.add(new LongParameterList(rs.getString("class_name"), rs.getString("package_name"),
							rs.getString("file"), rs.getInt("start_row"), rs.getInt("end_row"),
							rs.getString("method_name")));

				} else {
					badCodeSmells.add(new DeadCode(rs.getString("class_name"), rs.getString("package_name"),
							rs.getString("file"), rs.getInt("start_row"), rs.getInt("end_row"), rs.getString("type"),
							rs.getString("variable")));
				}

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return badCodeSmells;

	}

	public HashMap<String, Committer> selectFromCommittersJoinAssociations(String name, String version,
			String badCodeSmellId) {

		HashMap<String, Committer> committers = new HashMap<String, Committer>();
		try {
			Statement stmt = connection.createStatement();

			ResultSet rs = stmt.executeQuery(
					"select committers.name, committers.email from committers join commits on committers.email = commits.committer_email join associations on committers.email = associations.committer_email "
							+ "where commits.software_system_name = '" + name
							+ "' and commits.software_system_version = '" + version + "' and "
							+ "associations.badcodesmell_id = '" + badCodeSmellId + "';");

			while (rs.next()) {
				if (committers.get(rs.getString("email")) == null)
					committers.put(rs.getString("email"), new Committer(rs.getString("name"), rs.getString("email")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return committers;

	}

	public HashSet<BadCodeSmell> selectFromBadCodeSmells(String name, String version) {

		HashSet<BadCodeSmell> badCodeSmells = new HashSet<BadCodeSmell>();
		try {
			Statement stmt = connection.createStatement();

			ResultSet rs = stmt
					.executeQuery("select * from badcodesmells " + "where badcodesmells.software_system_name = '" + name
							+ "' and badcodesmells.software_system_version = '" + version + "';");

			while (rs.next()) {
				if (rs.getString("type").contentEquals("DuplicatedCode")) {

					badCodeSmells.add(new DuplicatedCode(rs.getString("class_name"), rs.getString("package_name"),
							rs.getString("file"), rs.getInt("start_row"), rs.getInt("end_row"),
							(rs.getInt("end_row") - rs.getInt("start_row"))));

				} else if (rs.getString("type").contentEquals("LargeClass")) {

					badCodeSmells.add(new LargeClass(rs.getString("class_name"), rs.getString("package_name"),
							rs.getString("file"), rs.getInt("start_row"), rs.getInt("end_row")));

				} else if (rs.getString("type").contentEquals("LongMethod")) {

					badCodeSmells.add(new LongMethod(rs.getString("class_name"), rs.getString("package_name"),
							rs.getString("file"), rs.getInt("start_row"), rs.getInt("end_row"),
							rs.getString("method_name")));

				} else if (rs.getString("type").contentEquals("LongParametersList")) {

					badCodeSmells.add(new LongParameterList(rs.getString("class_name"), rs.getString("package_name"),
							rs.getString("file"), rs.getInt("start_row"), rs.getInt("end_row"),
							rs.getString("method_name")));

				} else {
					badCodeSmells.add(new DeadCode(rs.getString("class_name"), rs.getString("package_name"),
							rs.getString("file"), rs.getInt("start_row"), rs.getInt("end_row"), rs.getString("type"),
							rs.getString("variable")));
				}

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return badCodeSmells;

	}

	public HashMap<String, Committer> selectFromCommitters(String name, String version) {

		HashMap<String, Committer> committers = new HashMap<String, Committer>();
		try {
			Statement stmt = connection.createStatement();

			ResultSet rs = stmt.executeQuery(
					"select committers.name, committers.email from committers join commits on committers.email = commits.committer_email "
							+ "where commits.software_system_name = '" + name
							+ "' and commits.software_system_version = '" + version + "';");

			while (rs.next()) {
				if (committers.get(rs.getString("email")) == null)
					committers.put(rs.getString("email"), new Committer(rs.getString("name"), rs.getString("email")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return committers;

	}

	public HashMap<String, Commit> selectFromCommit(String name, String version) {

		HashMap<String, Commit> commits = new HashMap<String, Commit>();
		try {
			Statement stmt = connection.createStatement();

			ResultSet rs = stmt.executeQuery("select * from commits " + "where commits.software_system_name = '" + name
					+ "' and commits.software_system_version = '" + version + "';");

			while (rs.next()) {

				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

				Date commitDate = sdf.parse(rs.getString("date_commit"));

				commits.put(rs.getString("commit_id"), new Commit(rs.getString("commit_id"),
						rs.getString("committer_email"), null, commitDate, rs.getString("description"), null));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return commits;

	}

	public boolean checkData(String name, String version) {

		try {
			Statement stmt = connection.createStatement();

			ResultSet rs = stmt.executeQuery("select count(*) from software_systems " + "where name = '" + name
					+ "' and version = '" + version + "';");

			while (rs.next()) {
				if (rs.getInt("count(*)") == 1) {
					return true;
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

}
