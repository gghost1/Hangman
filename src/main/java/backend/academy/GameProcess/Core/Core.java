package backend.academy.GameProcess.Core;

import backend.academy.Exceptions.AllWordsWereUsedException;
import backend.academy.Exceptions.IncorrectInputException;
import backend.academy.Exceptions.NoWordsWereFoundException;
import backend.academy.Exceptions.NotAvailableException;
import backend.academy.Exceptions.StorageNotInitializedException;
import backend.academy.GameProcess.FrontEnd.GameDisplay;
import backend.academy.GameProcess.MainCore;
import backend.academy.GameProcess.Session.Result;
import backend.academy.GameProcess.Session.Session;
import backend.academy.StaticVariables;
import backend.academy.Utils.Output;
import backend.academy.Words.Category;
import backend.academy.Words.Level;
import backend.academy.Words.Word;
import static backend.academy.GameProcess.FrontEnd.StaticOutput.LanguageManager.dictionary;
import java.io.Reader;
import java.io.Writer;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class Core {

    private Session session;
    private GameState gameState;

    private Level level;
    private Category category;
    private Word word;
    private int mistakeStep;

    private final Set<String> usedLetters;
    private final Set<String> correctLetters;
    private GameDisplay gameDisplay;
    private int mistakesLeft;

    private final Reader readerIO;
    private final backend.academy.Utils.Reader reader;
    private final Writer writer;
    private final Output output;

    public Core(Session session) {
        this.session = session;
        this.gameState = GameState.NOT_READY;

        usedLetters = new HashSet<>();
        correctLetters = new HashSet<>();
        this.readerIO = session.readerIO();
        this.reader = new backend.academy.Utils.Reader(readerIO);
        this.writer = session.writer();
        output = new Output(writer);
    }

    public void setting(String category, Level level)
        throws AllWordsWereUsedException, NotAvailableException, StorageNotInitializedException {
        if (gameState != GameState.RUNNING) {
            this.category = session.wordsStorage().getCategoryByName(category);
            this.level = level;
            try {
                if (session.history().passedCategories().contains(category)
                    || session.history().getPassedLevelsForCategory(category).contains(level)) {
                    throw new AllWordsWereUsedException(dictionary().exception("All words were used"));
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
            throw new NotAvailableException(dictionary().exception("Game is already running"));
        }
    }

    public void setting(String category)
        throws AllWordsWereUsedException, NotAvailableException, StorageNotInitializedException {
        if (gameState != GameState.RUNNING) {
            this.category = session.wordsStorage().getCategoryByName(category);
            try {
                if (session.history().passedCategories().contains(category)) {
                    throw new AllWordsWereUsedException(dictionary().exception("All words were used"));
                }
                this.level = this.category.getRandomLevel(session.history().getPassedLevelsForCategory(category));
                if (session.history().getPassedLevelsForCategory(category).contains(level)) {
                    throw new AllWordsWereUsedException(dictionary().exception("All words were used"));
                }
                word = this.category.getRandomWordByLevel(level, session.history().passedWords());
            } catch (NoWordsWereFoundException e) {
                output.exception(e.getMessage());
                MainCore.instance().start();
            }

            mistakeStep = StaticVariables.getMistakesStep(level);
            mistakesLeft = StaticVariables.MAX_MISTAKES()/ mistakeStep;
            gameState = GameState.READY;
        } else {
            throw new NotAvailableException(dictionary().exception("Game is already running"));
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

    private void stop(Result result) throws NotAvailableException {
        if (gameState == GameState.RUNNING) {
            if (result == Result.WIN) {
                gameDisplay.outputWin();
            } else {
                gameDisplay.outputLose();
            }
            session.history().addPassedWord(word.name());
            session.history().addGameResult(result, word);
            gameState = GameState.NOT_READY;
            try {
                MainCore.instance().start();
            } catch (StorageNotInitializedException e) {
                throw new RuntimeException(e);
            }

        } else {
            throw new NotAvailableException(dictionary().exception("Game is not running"));
        }
    }

    public void running() throws NotAvailableException {
        if (gameState == GameState.READY) {
            gameDisplay = new GameDisplay(writer, word, category.name(), level, StaticVariables.getMistakesStep(level));
            gameState = GameState.RUNNING;
            while (mistakesLeft > 0) {
                String letter = reader().readInput();
                try {
                    if (isLetterGuessed(letter)) {
                        if (isWordGuessed()) {
                            gameDisplay.update(false, usedLetters, correctLetters);
                            stop(Result.WIN);
                            break;
                        } else {
                            gameDisplay.update(false, usedLetters, correctLetters);
                        }
                    } else {
                        gameDisplay.update(true, usedLetters, correctLetters);
                    }
                } catch (IncorrectInputException ex) {
                    gameDisplay.exception(ex);
                }
                if (mistakesLeft <= 0) {
                    stop(Result.LOSE);
                }
            }
        } else {
            throw new NotAvailableException(dictionary().exception("Game is not ready or already running"));
        }
    }

    private boolean isLetterGuessed(String letter) throws IncorrectInputException, NotAvailableException {
        String localLetter = letter.trim();
        if (localLetter.isEmpty()) {
            throw new IncorrectInputException(dictionary().exception("Letter should not be empty"));
        }
        if (localLetter.length() > 1) {
            throw new IncorrectInputException(dictionary().exception("Input should contains only one letter"));
        }
        if (usedLetters.contains(localLetter)) {
            throw new IncorrectInputException(dictionary().exception("Letter already used"));
        }
        usedLetters.add(localLetter);
        if (word.name().contains(localLetter)) {
            correctLetters.add(localLetter);
            return true;
        } else {
            mistakesLeft--;
            return false;
        }
    }

    private boolean isWordGuessed() {
        for (String letter : word.name().split("")) {
            if (!correctLetters.contains(letter)) {
                return false;
            }
        }
        return true;
    }

}
