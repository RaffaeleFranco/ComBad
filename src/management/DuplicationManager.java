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

import base.DuplicatedCode;
import base.Duplication;

public class DuplicationManager {

	private ArrayList<Duplication> duplications;
	private ArrayList<DuplicatedCode> duplicatedCodes;

	@SuppressWarnings("unchecked")
	public DuplicationManager(String fileName) throws ParserConfigurationException, SAXException, IOException {

		duplications = new ArrayList<Duplication>();
		duplicatedCodes = new ArrayList<DuplicatedCode>();

		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		Document document = documentBuilder.parse(new File(fileName));
		document.getDocumentElement().normalize();

		NodeList nodeList = document.getElementsByTagName("duplication");

		int i = 0;
		do {

			Element element = (Element) nodeList.item(i);

			int interval = Integer.parseInt(element.getAttribute("lines"));

			int j = 0;

			do {
				Node node = element.getElementsByTagName("file").item(j);

				int startRow = Integer.parseInt(node.getAttributes().getNamedItem("line").getNodeValue());

				String file = node.getAttributes().getNamedItem("path").getNodeValue();

				String tmp = node.getAttributes().getNamedItem("path").getNodeValue();
				String packageName = "";

				StringTokenizer stringTokenizer = new StringTokenizer(file, "/");
				StringTokenizer stringTokenizer1 = new StringTokenizer(tmp, "/");

				file = stringTokenizer.nextToken();

				while (!file.contains(".java")) {
					file = stringTokenizer.nextToken();
				}

				tmp = stringTokenizer1.nextToken();
				tmp = stringTokenizer1.nextToken();
				tmp = stringTokenizer1.nextToken();
				tmp = stringTokenizer1.nextToken();
				tmp = stringTokenizer1.nextToken();

				while (!tmp.contains(".java")) {
					packageName = packageName + tmp + ".";
					tmp = stringTokenizer1.nextToken();
				}

				StringTokenizer stringTokenizer3 = new StringTokenizer(file, ".");

				String className = stringTokenizer3.nextToken();

				duplicatedCodes.add(new DuplicatedCode(className, packageName.substring(0, packageName.length()-1),
						file, startRow, startRow + interval, interval));
				j++;

			} while (element.getElementsByTagName("file").item(j) != null);

			duplications.add(new Duplication((ArrayList<DuplicatedCode>) duplicatedCodes.clone()));
			duplicatedCodes.clear();

			i++;
		} while (nodeList.item(i) != null);

	} 

	public ArrayList<Duplication> getDuplications() {
		return duplications;
	}

	public ArrayList<DuplicatedCode> getDuplicatedCodes() {
		return duplicatedCodes;
	}

	public void setDuplications(ArrayList<Duplication> duplications) {
		this.duplications = duplications;
	}

	public void setDuplicatedCodes(ArrayList<DuplicatedCode> duplicatedCodes) {
		this.duplicatedCodes = duplicatedCodes;
	}

	public void addDuplication(Duplication duplication) {
		this.duplications.add(duplication);
	}

	public void addDuplicatedCode(DuplicatedCode duplicated) {
		this.duplicatedCodes.add(duplicated);
	}

}
