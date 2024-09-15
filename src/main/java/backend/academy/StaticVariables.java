package backend.academy;

import backend.academy.Words.Level;
import lombok.Getter;

public class StaticVariables {

    private StaticVariables() {
    }

    @Getter
    private static final int LEVEL_COUNT = 3;
    @Getter
    private static final int PAGE_SIZE = 10;
    @Getter
    private static final String PATH = "src/main/resources/words.txt";
    @Getter
    private static final String NO_WORDS_WERE_FOUND = "No words were found";
    @Getter
    private static final String ALL_WORDS_WERE_USED = "All words were used";

    @Getter
    private static final String GAME_IS_RUNNING = "Game is running";

    @Getter
    private static final int MAX_MISTAKES_FOR_EASY = 1;
    @Getter
    private static final int MAX_MISTAKES_FOR_MEDIUM = 2;
    @Getter
    private static final int MAX_MISTAKES_FOR_HARD = 4;

    public static int getMaxMistakes(Level level) {
        return switch (level) {
            case EASY -> MAX_MISTAKES_FOR_EASY;
            case MEDIUM -> MAX_MISTAKES_FOR_MEDIUM;
            case HARD -> MAX_MISTAKES_FOR_HARD;
        };
    }

}
