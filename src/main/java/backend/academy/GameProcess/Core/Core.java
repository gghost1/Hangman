package backend.academy.GameProcess.Core;

import backend.academy.Exceptions.AllWordsWereUsedException;
import backend.academy.Exceptions.IncorrectInputException;
import backend.academy.Exceptions.NoWordsWereFoundException;
import backend.academy.Exceptions.NotAvailableException;
import backend.academy.GameProcess.FrontEnd.GameDisplay;
import backend.academy.GameProcess.Session.Result;
import backend.academy.GameProcess.Session.Session;
import backend.academy.StaticVariables;
import backend.academy.Utils.Reader;
import backend.academy.Words.Category;
import backend.academy.Words.Level;
import backend.academy.Words.Word;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringReader;
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

    private Set<String> usedLetters;
    private Set<String> correctLetters;
    private GameDisplay gameDisplay;
    private int mistakesLeft;

    private final Reader reader;

    public Core(Session session, InputStreamReader streamReader) {
        this.session = session;
        this.gameState = GameState.NOT_READY;

        usedLetters = new HashSet<>();
        correctLetters = new HashSet<>();
        reader = new Reader(streamReader);
    }

    public Core(Session session, StringReader stringReader) {
        this.session = session;
        this.gameState = GameState.NOT_READY;

        usedLetters = new HashSet<>();
        correctLetters = new HashSet<>();
        reader = new Reader(stringReader);
    }

    private int calculateMaxMistakes(Level level) {
        return switch (level) {
            case EASY -> StaticVariables.MAX_MISTAKES_FOR_EASY();
            case MEDIUM -> StaticVariables.MAX_MISTAKES_FOR_MEDIUM();
            case HARD -> StaticVariables.MAX_MISTAKES_FOR_HARD();
        };
    }

    public void setting(String category, Level level) throws AllWordsWereUsedException, NotAvailableException {
        if (gameState != GameState.RUNNING) {
            this.category = session.wordsStorage().getCategoryByName(category);
            this.level = level;
            try {
                if (session.history().passedCategories().contains(category)
                    || session.history().getPassedLevelsForCategory(category).contains(level)) {
                    throw new AllWordsWereUsedException(StaticVariables.ALL_WORDS_WERE_USED());
                }
                word = this.category.getRandomWordByLevel(level, session.history().passedWords());
            } catch (NoWordsWereFoundException e) {
                log.error(e.getMessage(), e);
            }
            mistakeStep = calculateMaxMistakes(level);
            mistakesLeft = 8/ mistakeStep;
            gameState = GameState.READY;
        } else {
            throw new NotAvailableException(StaticVariables.GAME_IS_RUNNING());
        }
    }

    public void setting(String category) throws AllWordsWereUsedException, NotAvailableException {
        if (gameState != GameState.RUNNING) {
            this.category = session.wordsStorage().getCategoryByName(category);
            try {
                if (session.history().passedCategories().contains(category)) {
                    throw new AllWordsWereUsedException(StaticVariables.ALL_WORDS_WERE_USED());
                }
                this.level = this.category.getRandomLevel(session.history().getPassedLevelsForCategory(category));
                if (session.history().getPassedLevelsForCategory(category).contains(level)) {
                    throw new AllWordsWereUsedException(StaticVariables.ALL_WORDS_WERE_USED());
                }
                word = this.category.getRandomWordByLevel(level, session.history().passedWords());
            } catch (NoWordsWereFoundException e) {
                log.error(e.getMessage(), e);
            }

            mistakeStep = calculateMaxMistakes(level);
            mistakesLeft = 8/ mistakeStep;
            gameState = GameState.READY;
        } else {
            throw new NotAvailableException(StaticVariables.GAME_IS_RUNNING());
        }
    }

    public void setting() throws AllWordsWereUsedException, NotAvailableException {
        setting(session.wordsStorage().getRandomCategory(
            session.history().passedCategories()
        ).name());
    }

    private void stop(Result result) throws NotAvailableException {
        if (gameState == GameState.RUNNING) {
            gameDisplay.outputWin();
            session.history().addGameResult(result, word);
            gameState = GameState.NOT_READY;
        } else {
            throw new NotAvailableException("Game is not running");
        }
    }

    public void running() throws NotAvailableException {
        if (gameState == GameState.READY) {
            gameDisplay = new GameDisplay(new OutputStreamWriter(System.out), word, calculateMaxMistakes(level));
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
            }
        } else {
            throw new NotAvailableException("Game is not ready or already running");
        }
    }

    private boolean isLetterGuessed(String letter) throws IncorrectInputException {
        String localLetter = letter.trim();
        if (localLetter.isEmpty()) {
            throw new IncorrectInputException("Letter should not be empty");
        }
        if (localLetter.length() > 1) {
            throw new IncorrectInputException("Input should contains only one letter");
        }
        if (usedLetters.contains(localLetter)) {
            throw new IncorrectInputException("Letter already used");
        }
        usedLetters.add(localLetter);
        if (word.name().contains(localLetter)) {
            correctLetters.add(localLetter);
            return true;
        } else {
            mistakesLeft-= mistakeStep;
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
