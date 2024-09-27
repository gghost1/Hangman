package backend.academy.words;

import backend.academy.exception.NotAvailableException;
import backend.academy.exception.StorageNotInitializedException;
import backend.academy.utils.WordParser;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import static backend.academy.game.process.ui.staticOutput.LanguageManager.dictionary;

@Slf4j
public class WordsStorage {

    private static WordsStorage instance;
    @Getter
    private Map<String, Category> catalog = new HashMap<>();

    public static WordsStorage instance(String path) throws StorageNotInitializedException {
        if (instance == null) {
            instance = new WordsStorage();
            try {
                instance.init(path);
            } catch (FileNotFoundException e) {
                throw new StorageNotInitializedException(e.getMessage());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return instance;
    }

    public static void reset() {
        instance = null;
    }

    public WordsStorage createOwnStorage(String path) throws StorageNotInitializedException {
        WordsStorage ownStorage = new WordsStorage();
        try {
            ownStorage.init(path);
        } catch (IOException e) {
            throw new StorageNotInitializedException(e.getMessage());
        }
        return ownStorage;
    }

    public void addCategory(Category category) {
        catalog.put(category.name(), category);
    }

    public Category getCategoryByName(String name) throws IllegalArgumentException, NotAvailableException {
        Category category = catalog.get(name.toLowerCase());
        if (category == null) {
            throw new IllegalArgumentException(dictionary().exception("No such category: ") + name);
        }
        return category;
    }

    public Category getRandomCategory(List<String> usedCategories) {
        return catalog.values()
            .stream()
            .filter(category -> !usedCategories.contains(category.name()))
            .toList()
            .get((int) (Math.random() * (catalog.size() - usedCategories.size())));
    }

    private WordsStorage() {}

    private void init(String path) throws IOException {
        WordParser wordParser = new WordParser(path);
        catalog = wordParser.parseFromFile();
    }
}
