package backend.academy.Words;

import backend.academy.Exceptions.NoWordsWereFoundException;
import backend.academy.StaticVariables;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public record Category(String name, Map<Level, Map<String, Word>> words) {

    public void addWorldByLevel(Level level, Word word) {
        words.get(level).put(word.name(), word);
    }

    public void display() {
        System.out.println(name);
    }

    public Map<String, Word> getWordsByLevel(Level level) {
        return words.get(level);
    }

    public void displayWordsByLevel(Level level, int page) {
        getWordsByLevel(level).keySet().stream().skip((long) StaticVariables.PAGE_SIZE() * page).limit((long) StaticVariables.PAGE_SIZE() * (page + 1)).forEach(System.out::println);
    }

    public Word getWordByLevelAndName(Level level, String name) {
        return getWordsByLevel(level).get(name);
    }

    public Word getRandomWordByLevel(Level level, Set<String> usedWords) throws NoWordsWereFoundException {
        List<Word> words = getWordsByLevel(level).values().stream().filter(word -> !usedWords.contains(word.name())).toList();
        if (words.isEmpty()) {
            throw new NoWordsWereFoundException(StaticVariables.NO_WORDS_WERE_FOUND());
        }
        int index = (int) (Math.random() * (words.size() - usedWords.size()));
        return words.get(index);
    }

    public Level getRandomLevel(Set<Level> passedLevels) throws NoWordsWereFoundException {
        List<Word> words = null;
        Level level = null;
        Set<Level> levelChosen = new HashSet<>(passedLevels);
        while (true) {
            if (levelChosen.size() == Level.values().length) {
                throw new NoWordsWereFoundException(StaticVariables.NO_WORDS_WERE_FOUND());
            }
            level = Arrays.stream(Level.values()).filter(l -> !levelChosen.contains(l)).toList().get((int) (Math.random() * (Level.values().length - levelChosen.size())));
            words = getWordsByLevel(level).values().stream().toList();
            if (!words.isEmpty()) {
                break;
            }
            levelChosen.add(level);
        }
        return level;
    }

}
