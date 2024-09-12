package backend.academy.GameProcess.Game;

import backend.academy.Exceptions.AllWordsWereUsedException;
import backend.academy.Exceptions.IncorrectInputException;
import backend.academy.Exceptions.NoWordsWereFoundException;
import backend.academy.Exceptions.NotAvailableException;
import backend.academy.GameProcess.Session.Result;
import backend.academy.GameProcess.Session.Session;
import backend.academy.StaticVariables;
import backend.academy.Utils.ConsoleReader;
import backend.academy.Words.Category;
import backend.academy.Words.Level;
import backend.academy.Words.Word;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import java.io.InputStreamReader;
import java.io.StringReader;
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

    private final ConsoleReader reader;

    public Game(Session session, InputStreamReader streamReader) {
        this.session = session;
        this.gameState = GameState.NOT_READY;

        usedLetters = new HashSet<>();
        correctLetters = new HashSet<>();
        reader = new ConsoleReader(streamReader);
    }
    public Game(Session session, StringReader stringReader) {
        this.session = session;
        this.gameState = GameState.NOT_READY;

        usedLetters = new HashSet<>();
        correctLetters = new HashSet<>();
        reader = new ConsoleReader(stringReader);
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
                if (session.history().passedCategories().contains(category) ||
                    session.history().getPassedLevelsForCategory(category).contains(level)) {
                    throw new AllWordsWereUsedException(StaticVariables.ALL_WORDS_WERE_USED());
                }
                word = this.category.getRandomWordByLevel(level, session.history().passedWords());
            } catch (NoWordsWereFoundException e) {
                log.error(e.getMessage(), e);
            }
            maxMistakes = calculateMaxMistakes(level);
            mistakesLeft = maxMistakes;
            gameState = GameState.READY;
        } else throw new NotAvailableException("Game is running");
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

            maxMistakes = calculateMaxMistakes(level);
            mistakesLeft = maxMistakes;
            gameState = GameState.READY;
        } else throw new NotAvailableException("Game is running");
    }

    public void setting() throws AllWordsWereUsedException, NotAvailableException {
        setting(session.wordsStorage().getRandomCategory(
            session.history().passedCategories()
        ).name());
    }

    private void stop(Result result) throws NotAvailableException {
        if (gameState == GameState.RUNNING) {
            session.history().addGameResult(result, word);
            gameState = GameState.NOT_READY;
        } else throw new NotAvailableException("Game is not running");
    }

    public void running() throws NotAvailableException {
        if (gameState == GameState.READY) {
            gameState = GameState.RUNNING;
            // write front
            while (mistakesLeft > 0) {

                String letter = reader().readInput();
                try {
                    if (isLetterGuessed(letter)) {
                        if (isWordGuessed()) {
                            // write front
                            break;
                        } else {
                            // write front
                        }
                    } else {
                        // write front
                    }
                } catch (IncorrectInputException ex) {
                    // write front
                }
            }

        } else throw new NotAvailableException("Game is not ready or already running");
    }

    private boolean isLetterGuessed(String letter) throws IncorrectInputException {
        letter = letter.trim();
        if (letter.isEmpty()) {
            throw new IncorrectInputException("Letter should not be empty");
        }
        if (letter.length() > 1) {
            throw new IncorrectInputException("Input should contains only one letter");
        }
        if (word.name().contains(letter)) {
            usedLetters.add(letter);
            correctLetters.add(letter);
            return true;
        } else {
            mistakesLeft--;
            usedLetters.add(letter);
            return false;
        }
    }

    private boolean isWordGuessed() {
        for (String letter: word.name().split("")) {
            if (!correctLetters.contains(letter)) {
                return false;
            }
        }
        return true;
    }


}
