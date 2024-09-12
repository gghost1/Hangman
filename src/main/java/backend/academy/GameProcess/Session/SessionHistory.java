package backend.academy.GameProcess.Session;

import backend.academy.Words.Level;
import backend.academy.Words.Word;
import it.unimi.dsi.fastutil.Pair;
import lombok.Getter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
public class SessionHistory {
    private final List<String> passedCategories;
    private final HashMap<Level, List<String>> passedLevelsOfCategories;
    private final HashSet<String> passedWords;
    private final List<Pair<Result, Word>> results;

    public SessionHistory() {
        passedCategories = new ArrayList<>();
        passedLevelsOfCategories = new HashMap<>();
        init();
        passedWords = new HashSet<>();
        results = new ArrayList<>();
    }

    private void init() {
        passedLevelsOfCategories.put(Level.EASY, new ArrayList<>());
        passedLevelsOfCategories.put(Level.MEDIUM, new ArrayList<>());
        passedLevelsOfCategories.put(Level.HARD, new ArrayList<>());
    }

    public List<String> addPassedCategory(String category) {
        passedCategories.add(category);
        return passedCategories;
    }

    public HashMap<Level, List<String>> addPassedLevelForCategory(String category, Level level) {
        passedLevelsOfCategories.get(level).add(category);
        return passedLevelsOfCategories;
    }

    public HashSet<String> addPassedWord(String word) {
        passedWords.add(word);
        return passedWords;
    }

    public Set<Level> getPassedLevelsForCategory(String category) {
        return new HashSet<>(passedLevelsOfCategories.keySet().stream().filter(l -> passedLevelsOfCategories.get(l).contains(category)).collect(
            Collectors.toCollection(HashSet::new)));
    }

    public List<Pair<Result, Word>> addGameResult(Result result, Word word) {
        results.add(Pair.of(result, word));
        return results;
    }

}
