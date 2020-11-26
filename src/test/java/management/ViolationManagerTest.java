package management;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

class ViolationManagerTest {

	@Test
	void ViolationManagerTest1() throws ParserConfigurationException, SAXException, IOException {

		ViolationManager vm = new ViolationManager("src/test/resources/dnsjava-2.1.9.xml");
		System.out.println(vm.getDeadCodes());
		assertEquals(vm.getDeadCodes().size(), 13);
		System.out.println(vm.getLargeClasses());
		assertFalse(vm.getLargeClasses() == null);
		System.out.println(vm.getLongMethods());
		System.out.println(vm.getLongParametersLists());
		assert true;
	}
}