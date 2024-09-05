package backend.academy.Words;

import backend.academy.StaticVariables;
import backend.academy.Utils.WordParser;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class WordsStorage {

    private static WordsStorage instance;

    public static WordsStorage instance(String path) {
        if (instance == null) {
            instance = new WordsStorage();
            try {
                instance.init(path);
            } catch (FileNotFoundException e) {
                log.error("Cannot initialize words storage", e);
            }
        }
        return instance;
    }

    private WordsStorage() {}

    @Getter
    private Map<String, Category> catalog = new HashMap<>();

    private void init(String path) throws FileNotFoundException {
        WordParser wordParser = new WordParser(path);
        catalog = wordParser.parse();
    }

    public void reset() {
        instance = null;
        catalog.clear();
    }

    public void addCategory(Category category) {
        catalog.put(category.name(), category);
    }

    public void displayCategories(int page) {
        catalog.keySet().stream().skip((long) StaticVariables.PAGE_SIZE() * page).limit((long) StaticVariables.PAGE_SIZE() * (page + 1)).forEach(System.out::println);
    }

    public Category getCategoryByName(String name) {
        return catalog.get(name);
    }

    public Category getRandomCategory() {
        return catalog.values().stream().toList().get((int) (Math.random() * catalog.size()));
    }

}
