package Words;

import Words.datasource.StaticWordsTestVariables;
import backend.academy.Exceptions.NoWordsWereFound;
import backend.academy.Exceptions.StorageNotInitialized;
import backend.academy.Words.Category;
import backend.academy.Words.Level;
import backend.academy.Words.Word;
import backend.academy.Words.WordsStorage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashSet;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.assertj.core.api.Assertions.assertThat;

public class CategoryTest extends StaticWordsTestVariables {

    @AfterEach
    public void afterEach() {
        WordsStorage.reset();
    }

    @Test
    public void addWorldByLevelTest() {
        Category category = ANIMAL_CATEGORY_EMPTY;
        Word expected = new Word("собака", "друг человека");
        category.addWorldByLevel(Level.EASY, expected);
        assertEquals(expected, category.words().get(Level.EASY).get("собака"));
    }

    @Test
    public void displayTest() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;

        System.setOut(new PrintStream(outContent));

        ANIMAL_CATEGORY.display();

        assertEquals("животные\r\n", outContent.toString());

        System.setOut(originalOut);
    }

    @Test
    public void getWordsByLevelTest() {
        assertEquals(0, ANIMAL_CATEGORY_EMPTY.getWordsByLevel(Level.EASY).size());
        assertEquals(2, ANIMAL_CATEGORY.getWordsByLevel(Level.EASY).size());
        assertThat("собака").isIn(ANIMAL_CATEGORY.getWordsByLevel(Level.EASY).keySet());

    }

    @Disabled
    @Test
    public void displayWordsByLevelTest() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;

        System.setOut(new PrintStream(outContent));

        ANIMAL_CATEGORY.displayWordsByLevel(Level.EASY, 0);

        assertEquals("собака\r\nкошка\n", outContent.toString());

        System.setOut(originalOut);
    }

    @Test
    public void getWordByLevelAndNameTest() {
        assertEquals("собака", ANIMAL_CATEGORY.getWordByLevelAndName(Level.EASY, "собака").name());
    }

    @Test
    public void getRandomWordByLevelTest() throws NoWordsWereFound {
        Set<Word> words = new HashSet<>();
        for (int i = 0; i < 10; i++) {
            words.add(ANIMAL_CATEGORY.getRandomWordByLevel(Level.EASY, new HashSet<>()));
        }
        assertNotEquals(words.size(), 1);
        assertNotEquals(words.size(), 0);
    }

    @Test
    public void getRandomWordTest() throws NoWordsWereFound {
        Set<Word> words = new HashSet<>();
        for (int i = 0; i < 10; i++) {
            words.add(ANIMAL_CATEGORY.getRandomWord(new HashSet<>()));
        }
        assertNotEquals(words.size(), 1);
        assertNotEquals(words.size(), 0);
    }

}
