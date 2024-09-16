package backend.academy;

import backend.academy.Exceptions.AllWordsWereUsedException;
import backend.academy.Exceptions.NotAvailableException;
import backend.academy.Exceptions.StorageNotInitializedException;
import backend.academy.GameProcess.Core.Core;
import backend.academy.GameProcess.FrontEnd.GameDisplay;
import backend.academy.GameProcess.FrontEnd.SessionDisplay;
import backend.academy.GameProcess.Session.Session;
import backend.academy.Words.Level;
import backend.academy.Words.Word;
import backend.academy.Words.datasource.StaticWordsTestVariables;
import org.junit.jupiter.api.Test;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashSet;

public class FrontTest extends StaticWordsTestVariables {

    @Test
    public void test() {
        GameDisplay gameDisplay = new GameDisplay(new OutputStreamWriter(System.out), new Word("hello", "a"), "hello", Level.EASY, 1);
        gameDisplay.output();
        HashSet<String> usedLetters = new HashSet<>();
        usedLetters.add("h");
        HashSet<String> guessedLetters = new HashSet<>();
        guessedLetters.add("h");
        gameDisplay.update(false, usedLetters, guessedLetters);
        usedLetters.add("m");
        gameDisplay.update(true, usedLetters, guessedLetters);
    }

    @Test
    public void runningWinTest() throws StorageNotInitializedException, AllWordsWereUsedException, NotAvailableException {
        Core core = new Core(new Session(PATH2, new OutputStreamWriter(System.out), new StringReader("к\nх\nо\nт")));
        core.setting(ANIMAL_CATEGORY.name(), Level.EASY);
        core.running();
    }
    @Test
    public void runningLoseTest() throws StorageNotInitializedException, AllWordsWereUsedException, NotAvailableException {
        Core core = new Core(new Session(PATH2, new OutputStreamWriter(System.out), new StringReader("к\nх\np\nz\nr\ng\ns\nk\nq\na\nn\nm\nо\nт")));
        core.setting(ANIMAL_CATEGORY.name(), Level.EASY);
        core.running();
    }

    @Test
    public void runningExceptionTwoLettersTest() throws StorageNotInitializedException, AllWordsWereUsedException, NotAvailableException {
        Core core = new Core(new Session(PATH2, new OutputStreamWriter(System.out), new StringReader("к\nхa\nо\nт")));
        core.setting(ANIMAL_CATEGORY.name(), Level.EASY);
        core.running();
    }

    @Test
    public void runningExceptionUsedLetterTest() throws StorageNotInitializedException, AllWordsWereUsedException, NotAvailableException {
        Core core = new Core(new Session(PATH2, new OutputStreamWriter(System.out), new StringReader("к\nх\nх\nо\nт")));
        core.setting(ANIMAL_CATEGORY.name(), Level.EASY);
        core.running();
    }

    @Test
    public void chooseLevel() {
        SessionDisplay sessionDisplay = new SessionDisplay(new OutputStreamWriter(System.out), new ArrayList<>());
        sessionDisplay.chooseLevel();
    }

    @Test
    public void startGame() throws StorageNotInitializedException {
        Session session = new Session(PATH2, new OutputStreamWriter(System.out), new StringReader("a\n\nhard\n"));
        session.startGame();

    }



}
