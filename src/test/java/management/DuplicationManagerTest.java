package management;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import base.Duplication;

class DuplicationManagerTest {

    @Test
    void DuplicationManagerTest1() throws ParserConfigurationException, SAXException, IOException {

        DuplicationManager dm = new DuplicationManager("src/test/resources/dnsjava-2.1.9_dc.xml");
        for (Duplication d : dm.getDuplications())
            System.out.println(d);
        assert true;
    }
}