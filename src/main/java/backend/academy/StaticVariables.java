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
    private static final int MISTAKE_STEP_FOR_EASY = 1;
    @Getter
    private static final int MISTAKE_STEP_FOR_MEDIUM = 2;
    @Getter
    private static final int MISTAKE_STEP_FOR_HARD = 4;

    public static int getMistakesStep(Level level) {
        return switch (level) {
            case EASY -> MISTAKE_STEP_FOR_EASY;
            case MEDIUM -> MISTAKE_STEP_FOR_MEDIUM;
            case HARD -> MISTAKE_STEP_FOR_HARD;
        };
    }

    @Getter
    private static final int MAX_MISTAKES = 8;
    @Getter
    private static final String ALL_WORDS_WERE_USED = "All words were used";
    @Getter
    private static final String GAME_IS_ALREADY_RUNNING = "Game is already running";

}
