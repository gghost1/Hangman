package backend.academy.Words;

import backend.academy.StaticVariables;
import java.util.List;
import java.util.Map;


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

    public Word getRandomWordByLevel(Level level) {
        List<Word> words = getWordsByLevel(level).values().stream().toList();
        int index = (int) (Math.random() * words.size());
        return words.get(index);
    }

    public Word getRandomWord() {
        List<Word> words = null;
        while (true) {
            Level level = Level.values()[(int) (Math.random() * Level.values().length)];
            words = getWordsByLevel(level).values().stream().toList();
            if (!words.isEmpty()) {
                break;
            }
        }

        int index = (int) (Math.random() * words.size());
        return words.get(index);
    }

}
