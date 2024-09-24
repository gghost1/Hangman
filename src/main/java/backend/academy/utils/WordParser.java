package backend.academy.utils;

import backend.academy.StaticVariables;
import backend.academy.words.Category;
import backend.academy.words.Level;
import backend.academy.words.Word;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WordParser {

    @SuppressWarnings("MemberName")
    private final String path;

    public WordParser(String path) {
        this.path = path;
    }

    public Map<String, Category> parse() throws IOException {
        BufferedReader reader = new BufferedReader(
                new FileReader(path, StandardCharsets.UTF_8));
        Map<String, Category> catalog = new HashMap<>();
        try {
            String line = reader.readLine();

            String categoryName = line.replace(":", "").trim().toLowerCase();

            while ((line = reader.readLine()) != null) {
                HashMap<Level, Map<String, Word>> levelMap = new HashMap<>();
                for (int i = 0; i < StaticVariables.LEVEL_COUNT(); i++) {
                    levelMap.put(Level.values()[i], parseLevelLine(line));
                    line = reader.readLine();
                }
                Category category = new Category(categoryName, levelMap);
                catalog.put(categoryName, category);
                if (line == null) {
                    break;
                }
                categoryName = line.replace(":", "").trim().toLowerCase();
            }

        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return catalog;
    }

    private Map<String, Word> parseLevelLine(String line) {
        String localLine = line;
        if (localLine == null || localLine.split(":").length < 2) {
            return new HashMap<>();
        }
        localLine = localLine.split(":")[1].trim();
        String[] words = localLine.split(",");
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
