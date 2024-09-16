package backend.academy.Words;

import backend.academy.Exceptions.NotAvailableException;
import backend.academy.Words.datasource.StaticWordsTestVariables;
import backend.academy.Exceptions.StorageNotInitializedException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
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
        WordsStorage storage = WordsStorage.instance(PATH1);

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
        WordsStorage storage = WordsStorage.instance(PATH2);
        assertEquals(FOOD_CATEGORY, storage.getCategoryByName("еда"));
    }

    @Disabled
    @Test
    public void getRandomCategoryTest() throws StorageNotInitializedException {
        WordsStorage storage = WordsStorage.instance(PATH2);
        Category actyal = storage.getRandomCategory(new ArrayList<>());

        assertEquals(EXPECTED2.get(actyal.name()), actyal);
        Set<String> actyalCategories = new HashSet<>();
        for (int i = 0; i < 20; i++) {
            actyalCategories.add(storage.getRandomCategory(new ArrayList<>()).name());
        }
        assertNotEquals(actyalCategories.size(), 1);
    }
}
