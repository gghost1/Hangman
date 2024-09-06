package GameProcess;

import Words.datasource.StaticWordsTestVariables;
import backend.academy.Exceptions.StorageNotInitialized;
import backend.academy.GameProcess.Session;
import backend.academy.GameProcess.SessionState;
import backend.academy.Words.WordsStorage;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j public class SessionTest extends StaticWordsTestVariables {

    @AfterEach
    public void afterEach() {
        WordsStorage.reset();
    }

    @Test
    public void sessionCreationTest() throws StorageNotInitialized {
        WordsStorage wordsStorage = WordsStorage.instance(PATH1);
        Session session = new Session(PATH1);

        assertEquals(SessionState.READY, session.state());
        assertEquals(wordsStorage, session.wordsStorage());
    }

    @Test
    public void chooseSpecificWordStoragePositiveTest() throws StorageNotInitialized {
        WordsStorage wordsStorage = WordsStorage.instance(PATH1);
        Session session = new Session(PATH1);
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
    public void chooseSpecificWordStorageNegativeTest() throws StorageNotInitialized {
        WordsStorage wordsStorage = WordsStorage.instance(PATH1);
        Session session = new Session(PATH1);
        assertEquals(wordsStorage, session.wordsStorage());

        try {
            session.chooseSpecificWordStorage(PATH3);
        } catch (StorageNotInitialized _) {

        }
        assertEquals(wordsStorage, session.wordsStorage());
    }

}
