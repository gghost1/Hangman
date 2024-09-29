package backend.academy.words;

import backend.academy.exception.NotAvailableException;
import backend.academy.StaticWordsTestVariables;
import backend.academy.exception.StorageNotInitializedException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class WordsStorageTest extends StaticWordsTestVariables {

    @AfterEach
    public void afterEach() {
        WordsStorage.reset();
    }

    @Test
    public void addCategoryTest() throws StorageNotInitializedException {
        WordsStorage storage = WordsStorage.instance(EMPTY_CATEGORY_PATH);

        Category categoryToAdd = new Category("test", Map.of(
            Level.EASY, Map.of("test", new Word("test", "test"))
        ));

        storage.addCategory(categoryToAdd);

        Map<String, Category> expected = new HashMap<>(EXPECTED1);
        expected.put("test", categoryToAdd);

        assertEquals(expected, storage.catalog());
    }

    @Test
    public void getCategoryByNameTest() throws StorageNotInitializedException, NotAvailableException {
        WordsStorage storage = WordsStorage.instance(FULL_FILED_CATEGORY_PATH);
        assertEquals(FOOD_CATEGORY, storage.getCategoryByName("еда"));
    }

    @Disabled
    @Test
    public void getRandomCategoryTest() throws StorageNotInitializedException {
        WordsStorage storage = WordsStorage.instance(FULL_FILED_CATEGORY_PATH);
        Category actual = storage.getRandomCategory(new ArrayList<>());

        assertEquals(EXPECTED2.get(actual.name()), actual);
        Set<String> actualCategories = new HashSet<>();
        for (int i = 0; i < 20; i++) {
            actualCategories.add(storage.getRandomCategory(new ArrayList<>()).name());
        }
        assertNotEquals(actualCategories.size(), 1);
    }
}
