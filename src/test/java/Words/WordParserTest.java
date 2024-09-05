package Words;

import backend.academy.Utils.WordParser;
import backend.academy.Words.Category;
import backend.academy.Words.Level;
import backend.academy.Words.Word;
import backend.academy.Words.WordsStorage;
import org.junit.jupiter.api.Test;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class WordParserTest {


    @Test
    public void parseNoWordsTest() {
        WordsStorage wordsStorage = WordsStorage.instance("src/test/java/Words/datasource/testData1");
        Map<String, Category> expected = Map.of(
            "животные", new Category("животные", Map.of(
                Level.EASY, new HashMap<>(),
                Level.MEDIUM, new HashMap<>(),
                Level.HARD, new HashMap<>()
            ))
        );
        assertEquals(expected, wordsStorage.catalog());
        wordsStorage.reset();
    }

    @Test
    public void parseWordsTest() {
        WordsStorage wordsStorage = WordsStorage.instance("src/test/java/Words/datasource/testData2");
        Map<String, Category> expected = Map.of(
            "животные", new Category("животные", Map.of(
                Level.EASY, Map.of(
                    "собака", new Word("собака", "друг человека"),
                    "кошка", new Word("кошка", "мяучит")
                ),
                Level.MEDIUM, new HashMap<>(),
                Level.HARD, new HashMap<>()
            ))
        );

        assertEquals(expected, wordsStorage.catalog());
        wordsStorage.reset();
    }



}
