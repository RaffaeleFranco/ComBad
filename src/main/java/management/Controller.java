package management;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import base.BadCodeSmell;
import base.DeadCode;
import base.DuplicatedCode;
import base.Duplication;
import base.LargeClass;
import base.LongMethod;
import base.LongParameterList;
import gui.AllBadCodeSmellsFrame;
import gui.AllCommittersFrame;
import gui.CommitsFrame;
import gui.ProgressFrame;
import gui.RelatedBadCodeSmellsFrame;
import gui.RelatedCommittersFrame;

public class Controller {

	private static Controller instance;
	private HashMap<String, CommitManager> commitManagers;
	private HashMap<String, DuplicationManager> duplicationManagers;
	private HashMap<String, ViolationManager> violationManagers;
	private HashMap<String, AssociationManager> associationManagers;
	private DBManager dbManager;

	/** Singleton design pattern 
	 * @return A single instance of Controller
	 * @throws IOException 
	 */
	public static Controller getInstance()  {
		if (instance == null)
			instance = new Controller();
		return instance;
	}

	private Controller()  {
		try {
		dbManager = DBManager.getInstance();
		} catch (IOException e) {
			System.err.println("Problem with DB");
		}
		commitManagers = new HashMap<String, CommitManager>();
		duplicationManagers = new HashMap<String, DuplicationManager>();
		violationManagers = new HashMap<String, ViolationManager>();
		associationManagers = new HashMap<String, AssociationManager>();

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

	public void readSoftwareSystem(String name) {

		CommitManager cm = null;
		try {
			cm = new CommitManager(new BufferedReader(new FileReader("src/main/resources/" + name + ".txt")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		DuplicationManager dm = null;
		try {
			dm = new DuplicationManager("src/main/resources/" + name + "_dc.xml");
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
		ViolationManager vm = null;
		try {
			vm = new ViolationManager("src/main/resources/" + name + ".xml");
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}

		ArrayList<BadCodeSmell> badCodeSmells = new ArrayList<BadCodeSmell>();
		badCodeSmells.addAll(vm.getDeadCodes());
		badCodeSmells.addAll(vm.getLargeClasses());
		badCodeSmells.addAll(vm.getLongMethods());
		badCodeSmells.addAll(vm.getLongParametersLists());

		for (Duplication d : dm.getDuplications()) {
			badCodeSmells.addAll(d.getDuplicatedCodes());
		}

		AssociationManager am = new AssociationManager(cm.getCommits(), badCodeSmells);
		commitManagers.put(name, cm);
		duplicationManagers.put(name, dm);
		violationManagers.put(name, vm);
		associationManagers.put(name, am);

		StringTokenizer stringTokenizer = new StringTokenizer(name, "-");

		String systemName = stringTokenizer.nextToken();
		String systemVersion = stringTokenizer.nextToken();

		if (!dbManager.checkData(systemName, systemVersion)) {

			dbManager.insertIntoSoftwareSystems(systemName, systemVersion);
			dbManager.insertIntoCommitters(cm.getCommitters());
			dbManager.insertIntoCommits(cm.getCommits(), systemName, systemVersion);
			dbManager.insertIntoChanges(cm.getChanges());
			dbManager.insertIntoRanges(cm.getRanges());

			String badCodeSmellId;

			for (BadCodeSmell b : badCodeSmells) {

				badCodeSmellId = getTypeBadCodeSmell(b) + b.getFile() + b.getStartRow() + b.getEndRow();
				dbManager.insertIntoBadCodeSmells(badCodeSmellId, b, systemName, systemVersion);

			}

			dbManager.insertIntoAssociations(am.getAssociations());
		}

	}

	public HashMap<String, CommitManager> getCommitManagers() {
		return commitManagers;
	}

	public void setCommitManagers(HashMap<String, CommitManager> committerManagers) {
		this.commitManagers = committerManagers;
	}

	public HashMap<String, DuplicationManager> getDuplicationManagers() {
		return duplicationManagers;
	}

	public void setDuplicationManagers(HashMap<String, DuplicationManager> duplicationManagers) {
		this.duplicationManagers = duplicationManagers;
	}

	public HashMap<String, ViolationManager> getViolationManagers() {
		return violationManagers;
	}

	public void setViolationManagers(HashMap<String, ViolationManager> violationManagers) {
		this.violationManagers = violationManagers;
	}

	public HashMap<String, AssociationManager> getAssociationManagers() {
		return associationManagers;
	}

	public void setAssociationManagers(HashMap<String, AssociationManager> associationManagers) {
		this.associationManagers = associationManagers;
	}

	public DBManager getDbManager() {
		return dbManager;
	}

	public void setDbManager(DBManager dbManager) {
		this.dbManager = dbManager;
	}

	public void visualizeSystem(String name) {

		try {
			new ProgressFrame(name);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void visualizeRelatedBadCodeSmells(String name, String committerEmail) {
		new RelatedBadCodeSmellsFrame(name, committerEmail);
	}

	public void visualizeRelatedCommitters(String name, String type, String className, String packageName,
			String startRow, String endRow) {
		new RelatedCommittersFrame(name, type, className, packageName, startRow, endRow);
	}

	public void visualizeAllBadCodeSmells(String name) {
		new AllBadCodeSmellsFrame(name);
	}

	public void visualizeAllCommitters(String name) {
		new AllCommittersFrame(name);
	}

	public void visualizeCommits(String name) {
		new CommitsFrame(name);
	}

}
