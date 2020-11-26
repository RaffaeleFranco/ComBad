package management;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import base.BadCodeSmell;
import base.CommitterBadCodeSmell;
import base.Duplication;

class AssociationManagerTest {

	@Test
	void AssociationManagerTest1() throws ParserConfigurationException, SAXException, IOException {
		CommitManager cm = new CommitManager(
				new BufferedReader(new FileReader("src/test/resources/dnsjava-2.1.9.txt")));
		DuplicationManager dm = new DuplicationManager("src/test/resources/dnsjava-2.1.9_dc.xml");
		ViolationManager vm = new ViolationManager("src/test/resources/dnsjava-2.1.9.xml");

		ArrayList<BadCodeSmell> badCodeSmells = new ArrayList<BadCodeSmell>();
		badCodeSmells.addAll(vm.getDeadCodes());
		badCodeSmells.addAll(vm.getLargeClasses());
		badCodeSmells.addAll(vm.getLongMethods());
		badCodeSmells.addAll(vm.getLongParametersLists());

		for (Duplication d : dm.getDuplications()) {
			badCodeSmells.addAll(d.getDuplicatedCodes());
		}
		assertNotNull(cm);

		AssociationManager am = new AssociationManager(cm.getCommits(), badCodeSmells);

		for (CommitterBadCodeSmell a : am.getAssociations().values())
			System.out.println(a);
		assertNotNull(am);
	}

}