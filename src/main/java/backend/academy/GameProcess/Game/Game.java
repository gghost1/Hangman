package backend.academy.GameProcess.Game;

import backend.academy.Exceptions.AllWordsWereUsed;
import backend.academy.Exceptions.NoWordsWereFound;
import backend.academy.GameProcess.Session.Session;
import backend.academy.StaticVariables;
import backend.academy.Words.Category;
import backend.academy.Words.Level;
import backend.academy.Words.Word;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Getter
public class Game {

    private Session session;
    private GameState gameState;

    private Level level;
    private Category category;
    private Word word;
    private int maxMistakes;

    private Set<String> usedLetters;
    private Set<String> correctLetters;
    private String drawer; // specific class will be created
    private int mistakesLeft;

    public Game(Session session) {
        this.session = session;
        this.gameState = GameState.SETTING;

        usedLetters = new HashSet<>();
        correctLetters = new HashSet<>();
    }

    private int calculateMaxMistakes(Level level) {
        return switch (level) {
            case EASY -> StaticVariables.MAX_MISTAKES_FOR_EASY();
            case MEDIUM -> StaticVariables.MAX_MISTAKES_FOR_MEDIUM();
            case HARD -> StaticVariables.MAX_MISTAKES_FOR_HARD();
        };
    }

    public void setting(String category, Level level) throws AllWordsWereUsed {
        this.category = session.wordsStorage().getCategoryByName(category);
        this.level = level;
        try {
            if (session.history().passedCategories().contains(category) || session.history().getPassedLevelsForCategory(category).contains(level)) {
                throw new AllWordsWereUsed(StaticVariables.ALL_WORDS_WERE_USED());
            }
            word = this.category.getRandomWordByLevel(level, session.history().passedWords());
        } catch (NoWordsWereFound e) {
            log.error(e.getMessage(), e);
        }
        maxMistakes = calculateMaxMistakes(level);
        mistakesLeft = maxMistakes;
    }

    public void setting(String category) throws AllWordsWereUsed {
        this.category = session.wordsStorage().getCategoryByName(category);
        try {
            if (session.history().passedCategories().contains(category)) {
                throw new AllWordsWereUsed(StaticVariables.ALL_WORDS_WERE_USED());
            }
            this.level = this.category.getRandomLevel(session.history().getPassedLevelsForCategory(category));
            if (session.history().getPassedLevelsForCategory(category).contains(level)) {
                throw new AllWordsWereUsed(StaticVariables.ALL_WORDS_WERE_USED());
            }
            word = this.category.getRandomWordByLevel(level, session.history().passedWords());
        } catch (NoWordsWereFound e) {
            log.error(e.getMessage(), e);
        }

        maxMistakes = calculateMaxMistakes(level);
        mistakesLeft = maxMistakes;
    }

    public void setting() throws AllWordsWereUsed {
        setting(session.wordsStorage().getRandomCategory(
            session.history().passedCategories()
        ).name());
    }

}
