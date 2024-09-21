package backend.academy.words;

import backend.academy.exceptions.NotAvailableException;
import backend.academy.StaticWordsTestVariables;
import backend.academy.exceptions.StorageNotInitializedException;
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

        Map<String, Category> expected = new HashMap<>(EMPTY_WORDS_STORAGE_CATEGORY);
        expected.put("test", categoryToAdd);

        assertEquals(expected, storage.catalog());
    }

    @Test
    public void getCategoryByNameTest() throws StorageNotInitializedException, NotAvailableException {
        WordsStorage storage = WordsStorage.instance(FULL_FILED_CATEGORY_PATH);
        assertEquals(FOOD_CATEGORY_FULL_FILED, storage.getCategoryByName("еда"));
    }

    @Disabled
    @Test
    public void getRandomCategoryTest() throws StorageNotInitializedException {
        WordsStorage storage = WordsStorage.instance(FULL_FILED_CATEGORY_PATH);
        Category actyal = storage.getRandomCategory(new ArrayList<>());

        assertEquals(FULL_FILED_WORDS_STORAGE_CATEGORY.get(actyal.name()), actyal);
        Set<String> actyalCategories = new HashSet<>();
        for (int i = 0; i < 20; i++) {
            actyalCategories.add(storage.getRandomCategory(new ArrayList<>()).name());
        }
        assertNotEquals(actyalCategories.size(), 1);
    }
}