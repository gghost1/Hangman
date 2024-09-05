package backend.academy.Utils;

import backend.academy.Words.Category;
import backend.academy.Words.Level;
import backend.academy.Words.Word;
import lombok.extern.slf4j.Slf4j;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class WordParser {

    private final FileReader FILE_READER;

    public WordParser(String path) throws FileNotFoundException {
        this.FILE_READER = new FileReader(path);
    }

    public Map<String, Category> parse() {
        BufferedReader reader = new BufferedReader(FILE_READER);
        Map<String, Category> catalog = new HashMap<>();
        try {
            String line = reader.readLine();

            String categoryName = line.replace(":", "").trim().toLowerCase();

            while ((line = reader.readLine()) != null) {
                HashMap<Level, Map<String, Word>> levelMap = new HashMap<>();
                for (int i = 0; i < 3; i++) {
                    levelMap.put(Level.values()[i], parseLevelLine(line));
                    line = reader.readLine();
                }
                Category category = new Category(categoryName, levelMap);
                catalog.put(categoryName, category);
                if (line == null) break;
                categoryName = line.replace(":", "").trim().toLowerCase();
            }

        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return catalog;
    }

    private Map<String, Word> parseLevelLine(String line) {
        if (line == null || line.split(":").length < 2) return new HashMap<>();
        line = line.split(":")[1].trim();
        String[] words = line.split(",");
        return Arrays.stream(words)
            .map(word -> {
                String[] parts = word.trim().split(" ", 2);
                return new Word(
                    parts[0]
                        .trim()
                        .toLowerCase(),
                    parts[1]
                        .replace("(", "")
                        .replace(")", "")
                        .trim()
                        .toLowerCase());
            })
            .collect(Collectors.toMap(Word::name, word -> word));
    }

}
