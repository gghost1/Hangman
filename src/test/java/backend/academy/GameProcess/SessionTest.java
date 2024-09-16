package backend.academy.GameProcess;

import backend.academy.Exceptions.NotAvailableException;
import backend.academy.GameProcess.FrontEnd.StaticOutput.LanguageManager;
import backend.academy.Words.datasource.StaticWordsTestVariables;
import backend.academy.Exceptions.StorageNotInitializedException;
import backend.academy.GameProcess.Session.Session;
import backend.academy.GameProcess.Session.SessionState;
import backend.academy.Words.WordsStorage;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashSet;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j public class SessionTest extends StaticWordsTestVariables {

    @BeforeAll
    public static void beforeAll() {
        LanguageManager.getDictionary("ru");
    }

    @AfterEach
    public void afterEach() {
        WordsStorage.reset();
    }

    @Test
    public void sessionCreationTest() throws StorageNotInitializedException {
        WordsStorage wordsStorage = WordsStorage.instance(PATH1);
        Session session = new Session(PATH1, new OutputStreamWriter(System.out), new StringReader(""));

        assertEquals(SessionState.READY, session.state());
        assertEquals(wordsStorage, session.wordsStorage());
    }

    @Test
    public void chooseSpecificWordStoragePositiveTest() throws StorageNotInitializedException {
        WordsStorage wordsStorage = WordsStorage.instance(PATH1);
        Session session = new Session(PATH1, new OutputStreamWriter(System.out), new StringReader(""));
        assertEquals(wordsStorage, session.wordsStorage());

        session.chooseSpecificWordStorage(PATH2);

        assertEquals(
            new HashSet<String>(){{
                add("еда");
                add("животные");
            }},
            session.wordsStorage().catalog().keySet());
        assertEquals(SessionState.READY, session.state());
    }

    @Test
    public void chooseSpecificWordStorageNegativeTest() throws StorageNotInitializedException {
        WordsStorage wordsStorage = WordsStorage.instance(PATH1);
        Session session = new Session(PATH1, new OutputStreamWriter(System.out), new StringReader(""));
        assertEquals(wordsStorage, session.wordsStorage());

        try {
            session.chooseSpecificWordStorage(PATH3);
        } catch (StorageNotInitializedException _) {

        }
        assertEquals(wordsStorage, session.wordsStorage());
    }

}
