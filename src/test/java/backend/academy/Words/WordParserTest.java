package backend.academy.Words;

import backend.academy.Words.datasource.StaticWordsTestVariables;
import backend.academy.Exceptions.StorageNotInitializedException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class WordParserTest extends StaticWordsTestVariables {

    @AfterEach
    public void afterEach() {
        WordsStorage.reset();
    }

    @Test
    public void parseNoWordsTest() throws StorageNotInitializedException {
        WordsStorage wordsStorage = WordsStorage.instance(PATH1);

        assertEquals(EXPECTED1, wordsStorage.catalog());
    }

    @Test
    public void incorrectPathTest() {
        assertThrows(StorageNotInitializedException.class, () -> WordsStorage.instance(PATH3));
    }

}
