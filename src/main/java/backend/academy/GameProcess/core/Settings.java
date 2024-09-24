package backend.academy.gameProcess.core;

import backend.academy.StaticVariables;
import backend.academy.exceptions.AllWordsWereUsedException;
import backend.academy.exceptions.NoWordsWereFoundException;
import backend.academy.exceptions.NotAvailableException;
import backend.academy.exceptions.StorageNotInitializedException;
import backend.academy.gameProcess.MainCore;
import backend.academy.gameProcess.session.Session;
import backend.academy.utils.Output;
import backend.academy.words.Category;
import backend.academy.words.Level;
import backend.academy.words.Word;
import lombok.Getter;

import static backend.academy.gameProcess.ui.staticOutput.LanguageManager.dictionary;

@Getter
public class Settings {

    protected Session session;
    protected GameState gameState;

    protected Level level;
    protected Category category;
    protected Word word;
    protected int mistakeStep;
    protected int mistakesLeft;

    private final Output output;

    public Settings(Output output, GameState gameState, Session session) {
        this.output = output;
        this.gameState = gameState;
        this.session = session;
    }

    public void setting(String category, Level level)
        throws AllWordsWereUsedException, NotAvailableException, StorageNotInitializedException {
        if (gameState != GameState.RUNNING) {
            this.category = session.wordsStorage().getCategoryByName(category);
            this.level = level;
            try {
                if (session.history().passedCategories().contains(category)
                    || session.history().getPassedLevelsForCategory(category).contains(level)) {
                    throw new AllWordsWereUsedException(dictionary().exception(StaticVariables.ALL_WORDS_WERE_USED()));
                }
                word = this.category.getRandomWordByLevel(level, session.history().passedWords());
            } catch (NoWordsWereFoundException e) {
                output.exception(e.getMessage());
                MainCore.instance().start();
            }
            mistakeStep = StaticVariables.getMistakesStep(level);
            mistakesLeft = StaticVariables.MAX_MISTAKES() / mistakeStep;
            gameState = GameState.READY;
        } else {
            throw new NotAvailableException(dictionary().exception(StaticVariables.GAME_IS_ALREADY_RUNNING()));
        }
    }

    public void setting(String category)
        throws AllWordsWereUsedException, NotAvailableException, StorageNotInitializedException {
        if (gameState != GameState.RUNNING) {
            this.category = session.wordsStorage().getCategoryByName(category);
            try {
                if (session.history().passedCategories().contains(category)) {
                    throw new AllWordsWereUsedException(dictionary().exception(StaticVariables.ALL_WORDS_WERE_USED()));
                }
                this.level = this.category.getRandomLevel(session.history().getPassedLevelsForCategory(category));
                if (session.history().getPassedLevelsForCategory(category).contains(level)) {
                    throw new AllWordsWereUsedException(dictionary().exception(StaticVariables.ALL_WORDS_WERE_USED()));
                }
                word = this.category.getRandomWordByLevel(level, session.history().passedWords());
            } catch (NoWordsWereFoundException e) {
                output.exception(e.getMessage());
                MainCore.instance().start();
            }

            mistakeStep = StaticVariables.getMistakesStep(level);
            mistakesLeft = StaticVariables.MAX_MISTAKES() / mistakeStep;
            gameState = GameState.READY;
        } else {
            throw new NotAvailableException(dictionary().exception(StaticVariables.GAME_IS_ALREADY_RUNNING()));
        }
    }

    public void setting(Level level)
        throws AllWordsWereUsedException, NotAvailableException, StorageNotInitializedException {
        setting(session.wordsStorage().getRandomCategory(
            session.history().passedCategories()
        ).name(), level);
    }

    public void setting() throws AllWordsWereUsedException, NotAvailableException, StorageNotInitializedException {
        setting(session.wordsStorage().getRandomCategory(
            session.history().passedCategories()
        ).name());
    }

}
