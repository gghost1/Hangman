package backend.academy.words;

import backend.academy.exceptions.NotAvailableException;
import backend.academy.StaticWordsTestVariables;
import backend.academy.exceptions.NoWordsWereFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
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
    public void getWordsByLevelTest() {
        assertEquals(0, ANIMAL_CATEGORY_EMPTY.getWordsByLevel(Level.EASY).size());
        assertEquals(2, ANIMAL_CATEGORY_FULL_FILED.getWordsByLevel(Level.EASY).size());
        assertThat("собака").isIn(ANIMAL_CATEGORY_FULL_FILED.getWordsByLevel(Level.EASY).keySet());

    }


    @Test
    public void getWordByLevelAndNameTest() {
        assertEquals("собака", ANIMAL_CATEGORY_FULL_FILED.getWordByLevelAndName(Level.EASY, "собака").name());
    }

    @Test
    public void getRandomWordByLevelTest() throws NoWordsWereFoundException, NotAvailableException {
        Set<Word> words = new HashSet<>();
        for (int i = 0; i < 10; i++) {
            words.add(ANIMAL_CATEGORY_FULL_FILED.getRandomWordByLevel(Level.EASY, new HashSet<>()));
        }
        assertNotEquals(words.size(), 1);
        assertNotEquals(words.size(), 0);
    }

    @Test
    public void getRandomWordTest() throws NoWordsWereFoundException, NotAvailableException {
        Set<Word> words = new HashSet<>();
        for (int i = 0; i < 10; i++) {
            words.add(ANIMAL_CATEGORY_FULL_FILED.getRandomWordByLevel(ANIMAL_CATEGORY_FULL_FILED.getRandomLevel(new HashSet<>()), new HashSet<>()));
        }
        assertNotEquals(words.size(), 1);
        assertNotEquals(words.size(), 0);
    }

}
