package management;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import base.DeadCode;
import base.LargeClass;
import base.LongMethod;
import base.LongParameterList;

public class ViolationManager {

	private ArrayList<DeadCode> deadCodes;
	private ArrayList<LargeClass> largeClasses;
	private ArrayList<LongMethod> longMethods;
	private ArrayList<LongParameterList> longParametersLists;

	public ViolationManager(String fileName) throws ParserConfigurationException, SAXException, IOException {

		deadCodes = new ArrayList<DeadCode>();
		largeClasses = new ArrayList<LargeClass>();
		longMethods = new ArrayList<LongMethod>();
		longParametersLists = new ArrayList<LongParameterList>();

		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		Document document = documentBuilder.parse(new File(fileName));
		document.getDocumentElement().normalize();

		NodeList nodeList = document.getElementsByTagName("file");

		int i = 0;
		do {
			Element element = (Element) nodeList.item(i);
			String file = element.getAttribute("name");

			StringTokenizer stringTokenizer = new StringTokenizer(file, "/");

			file = stringTokenizer.nextToken();

			while (!file.contains(".java")) {
				file = stringTokenizer.nextToken();
			}

			int j = 0;
			do {
				Node node = element.getElementsByTagName("violation").item(j);

				String smellType = node.getAttributes().getNamedItem("rule").getNodeValue();

				int startRow = Integer.parseInt(node.getAttributes().getNamedItem("beginline").getNodeValue());
				int endRow = Integer.parseInt(node.getAttributes().getNamedItem("endline").getNodeValue());
				String className = node.getAttributes().getNamedItem("class").getNodeValue();
				String packageName = node.getAttributes().getNamedItem("package").getNodeValue();

				switch (smellType) {
				case "ExcessiveClassLength":

					LargeClass lc = new LargeClass(className, packageName, file, startRow, endRow);
					largeClasses.add(lc);

					break;

				case "ExcessiveMethodLength":

					String methodName = node.getAttributes().getNamedItem("method").getNodeValue();

					LongMethod lm = new LongMethod(className, packageName, file, startRow, endRow, methodName);
					longMethods.add(lm);

					break;

				case "ExcessiveParameterList":

					String method = node.getAttributes().getNamedItem("method").getNodeValue();
					LongParameterList lpl = new LongParameterList(className, packageName, file, startRow, endRow,
							method);
					longParametersLists.add(lpl);

					break;

				case "UnusedLocalVariable":

					String type = node.getAttributes().getNamedItem("rule").getNodeValue();
					String variable = node.getAttributes().getNamedItem("variable").getNodeValue();

					DeadCode dc = new DeadCode(className, packageName, file, startRow, endRow, type, variable);
					deadCodes.add(dc);

					break;

				case "UnusedPrivateField":

					String type1 = node.getAttributes().getNamedItem("rule").getNodeValue();
					String variable1 = node.getAttributes().getNamedItem("variable").getNodeValue();

					DeadCode dc1 = new DeadCode(className, packageName, file, startRow, endRow, type1, variable1);
					deadCodes.add(dc1);
					break;

				case "UnusedPrivateMethod":

					String type2 = node.getAttributes().getNamedItem("rule").getNodeValue();

					DeadCode dc2 = new DeadCode(className, packageName, file, startRow, endRow, type2, null);
					deadCodes.add(dc2);
					break;

				case "UnusedFormalParameter":

					String type3 = node.getAttributes().getNamedItem("rule").getNodeValue();
					String variable2 = node.getAttributes().getNamedItem("variable").getNodeValue();

					DeadCode dc3 = new DeadCode(className, packageName, file, startRow, endRow, type3, variable2);
					deadCodes.add(dc3);
					break;

				default:
					System.out.println("Errore...");

				}

				j++;
			} while (element.getElementsByTagName("violation").item(j) != null);
			i++;
		} while (nodeList.item(i) != null);

	}

	public ArrayList<DeadCode> getDeadCodes() {
		return deadCodes;
	}

	public void setDeadCodes(ArrayList<DeadCode> deadCodes) {
		this.deadCodes = deadCodes;
	}

	public ArrayList<LargeClass> getLargeClasses() {
		return largeClasses;
	}

	public void setLargeClasses(ArrayList<LargeClass> largeClasses) {
		this.largeClasses = largeClasses;
	}

	public ArrayList<LongMethod> getLongMethods() {
		return longMethods;
	}

	public void setLongMethods(ArrayList<LongMethod> longMethods) {
		this.longMethods = longMethods;
	}

	public ArrayList<LongParameterList> getLongParametersLists() {
		return longParametersLists;
	}

	public void setLongParametersLists(ArrayList<LongParameterList> longParametersLists) {
		this.longParametersLists = longParametersLists;
	}

}
