package Words;

import Words.datasource.StaticWordsTestVariables;
import backend.academy.Exceptions.StorageNotInitialized;
import backend.academy.Words.WordsStorage;
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
    public void parseNoWordsTest() throws StorageNotInitialized {
        WordsStorage wordsStorage = WordsStorage.instance(PATH1);

        assertEquals(EXPECTED1, wordsStorage.catalog());
    }

    @Deprecated
    @Test
    public void parseWordsTest() throws StorageNotInitialized {
        WordsStorage wordsStorage = WordsStorage.instance(PATH2);

        assertEquals(EXPECTED2, wordsStorage.catalog());
    }

    @Test
    public void incorrectPathTest() {
        assertThrows(StorageNotInitialized.class, () -> WordsStorage.instance(PATH3));
    }

}
