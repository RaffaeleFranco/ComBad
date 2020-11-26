package management;

import base.*;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertTrue;

class DBManagerTest {

	/* work only in local
    @Test
    void DBManagerTest1() {
        DBManager db = DBManager.getInstance();

        db.insertIntoSoftwareSystems("ok", "ok");
        db.insertIntoCommitters(new Committer("ok", "ok"));
        db.insertIntoCommits(new Commit("bla", "ok", null, new Date(), "ok", null), "ok", "ok");
        db.insertIntoChanges(new Change("ok", "ok", "ok"));
        db.insertIntoRanges(new Range("ok", 1, 2, "ok", null));

        assert true;

    }

    @Test
    void DBManagerTest2() throws ParserConfigurationException, SAXException, IOException {
        DBManager db = DBManager.getInstance();
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

        System.out.println(badCodeSmells.get(4));
        db.insertIntoBadCodeSmells("ok", badCodeSmells.get(4), "ok", "ok");
        assert true;
    }

    @Test
    void DBManagerTest3() throws ParserConfigurationException, SAXException, IOException {
        DBManager db = DBManager.getInstance();
        CommitManager cm = new CommitManager(new BufferedReader(new FileReader("src/test/resources/dnsjava-2.1.9.txt")));

        db.insertIntoSoftwareSystems("x", "y");

        for (Commit c : cm.getCommits().values())
            db.insertIntoCommits(c, "x", "y");

        System.out.println(db.selectFromBadCodeSmellsJoinAssociations("dnsjava", "2.1.9",
                "bwelling@c76caeb1-94fd-44dd-870f-0c9d92034fc1"));

        System.out
                .println(db.selectFromCommittersJoinAssociations("dnsjava", "2.1.9", "DuplicatedCodebase64.java85103"));
        System.out.println(db.selectFromCommit("dnsjava", "2.1.9"));

        assertTrue(db.checkData("ok", "ok"));

    }
*/
}