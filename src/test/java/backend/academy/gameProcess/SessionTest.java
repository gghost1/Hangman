package backend.academy.gameProcess;

import backend.academy.gameProcess.ui.staticOutput.LanguageManager;
import backend.academy.StaticWordsTestVariables;
import backend.academy.exceptions.StorageNotInitializedException;
import backend.academy.gameProcess.session.Session;
import backend.academy.gameProcess.session.SessionState;
import backend.academy.words.WordsStorage;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.OutputStreamWriter;
import java.io.StringReader;
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
        WordsStorage wordsStorage = WordsStorage.instance(EMPTY_CATEGORY_PATH);
        Session session = new Session(EMPTY_CATEGORY_PATH, new OutputStreamWriter(System.out), new StringReader(""));

        assertEquals(SessionState.READY, session.state());
        assertEquals(wordsStorage, session.wordsStorage());
    }

    @Test
    public void chooseSpecificWordStoragePositiveTest() throws StorageNotInitializedException {
        WordsStorage wordsStorage = WordsStorage.instance(EMPTY_CATEGORY_PATH);
        Session session = new Session(EMPTY_CATEGORY_PATH, new OutputStreamWriter(System.out), new StringReader(""));
        assertEquals(wordsStorage, session.wordsStorage());

        session.chooseSpecificWordStorage(FULL_FILED_CATEGORY_PATH);

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
        WordsStorage wordsStorage = WordsStorage.instance(EMPTY_CATEGORY_PATH);
        Session session = new Session(EMPTY_CATEGORY_PATH, new OutputStreamWriter(System.out), new StringReader(""));
        assertEquals(wordsStorage, session.wordsStorage());

        try {
            session.chooseSpecificWordStorage(UNDEFINED_PATH);
        } catch (StorageNotInitializedException _) {

        }
        assertEquals(wordsStorage, session.wordsStorage());
    }

}
