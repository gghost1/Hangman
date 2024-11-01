package backend.academy.words;

import backend.academy.StaticVariables;
import backend.academy.exception.NoWordsWereFoundException;
import backend.academy.exception.NotAvailableException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import static backend.academy.game.process.ui.staticOutput.LanguageManager.dictionary;

public record Category(String name, Map<Level, Map<String, Word>> words) {

    public void addWorldByLevel(Level level, Word word) {
        words.get(level).put(word.name(), word);
    }

    public Map<String, Word> getWordsByLevel(Level level) {
        return words.get(level);
    }

    public Word getWordByLevelAndName(Level level, String name) {
        return getWordsByLevel(level).get(name);
    }

    public Word getRandomWordByLevel(Level level, Set<String> usedWords)
            throws NoWordsWereFoundException, NotAvailableException {
        List<Word> localWords = getWordsByLevel(level).values()
            .stream()
            .filter(word -> !usedWords.contains(word.name()))
            .toList();
        if (localWords.isEmpty()) {
            throw new NoWordsWereFoundException(dictionary().exception(StaticVariables.ALL_WORDS_WERE_USED()));
        }
        int index = (int) (Math.random() * (localWords.size() - usedWords.size()));
        return localWords.get(index);
    }

    public Level getRandomLevel(Set<Level> passedLevels)
            throws NoWordsWereFoundException, NotAvailableException {
        List<Word> localWords = null;
        Level level = null;
        Set<Level> levelChosen = new HashSet<>(passedLevels);
        while (true) {
            if (levelChosen.size() == Level.values().length) {
                throw new NoWordsWereFoundException(dictionary().exception(StaticVariables.ALL_WORDS_WERE_USED()));
            }
            level = Arrays
                .stream(Level.values())
                .filter(l -> !levelChosen.contains(l))
                .toList()
                .get((int) (Math.random() * (Level.values().length - levelChosen.size())));
            localWords = getWordsByLevel(level).values()
                .stream()
                .toList();
            if (!localWords.isEmpty()) {
                break;
            }
            levelChosen.add(level);
        }
        return level;
    }

}
