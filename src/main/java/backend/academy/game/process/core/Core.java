package backend.academy.game.process.core;

import backend.academy.StaticVariables;
import backend.academy.exception.IncorrectInputException;
import backend.academy.exception.NotAvailableException;
import backend.academy.exception.StorageNotInitializedException;
import backend.academy.game.process.MainCore;
import backend.academy.game.process.session.Result;
import backend.academy.game.process.session.Session;
import backend.academy.game.process.ui.GameDisplay;
import backend.academy.utils.CustomReader;
import backend.academy.utils.Output;
import java.io.Reader;
import java.io.Writer;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import static backend.academy.game.process.ui.staticOutput.LanguageManager.dictionary;

@Slf4j
@Getter
public class Core extends Settings {

    private final Set<String> usedLetters;
    private final Set<String> correctLetters;

    private GameDisplay gameDisplay;
    private final Reader readerIO;
    private final CustomReader customReader;
    private final Writer writer;
    private final Output output;

    public Core(Session session) {
        super(new Output(session.writer()), GameState.NOT_READY, session);

        usedLetters = new HashSet<>();
        correctLetters = new HashSet<>();
        this.readerIO = session.readerIO();
        this.customReader = new CustomReader(readerIO);
        this.writer = session.writer();
        output = new Output(writer);
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
                String letter = customReader().readInput();
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
