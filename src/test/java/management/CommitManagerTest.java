package management;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

import org.junit.jupiter.api.Test;

import base.Committer;

public class CommitManagerTest {

    @Test
    void CommitManagerTest1() throws FileNotFoundException {
        CommitManager cm = new CommitManager(new BufferedReader(new FileReader("src/test/resources/dnsjava-2.1.9.txt")));

        for (Committer c : cm.getCommitters().values())
            if (!(c.getName().contains("+")))
                System.out.println(c);

        /*for (Commit c : commitManager.getCommits().values())
            System.out.println(c);*/

        assertNotNull(cm);

    }

}