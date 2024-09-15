package backend.academy;

import backend.academy.Exceptions.AllWordsWereUsedException;
import backend.academy.Exceptions.NotAvailableException;
import backend.academy.Exceptions.StorageNotInitializedException;
import backend.academy.GameProcess.Core.Core;
import backend.academy.GameProcess.FrontEnd.GameDisplay;
import backend.academy.GameProcess.Session.Session;
import backend.academy.Words.Category;
import backend.academy.Words.Level;
import backend.academy.Words.Word;
import backend.academy.Words.datasource.StaticWordsTestVariables;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.Test;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.util.HashSet;

public class FrontTest extends StaticWordsTestVariables {

    @Test
    public void test() {
        GameDisplay gameDisplay = new GameDisplay(new OutputStreamWriter(System.out), new Word("hello", "a"), 1);
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
        Core core = new Core(new Session(PATH2), new StringReader("к\nх\nо\nт"));
        core.setting(ANIMAL_CATEGORY.name(), Level.EASY);
        core.running();
    }
    @Test
    public void runningLoseTest() throws StorageNotInitializedException, AllWordsWereUsedException, NotAvailableException {
        Core core = new Core(new Session(PATH2), new StringReader("к\nх\np\nz\nr\ng\ns\nk\nq\na\nn\nm\nо\nт"));
        core.setting(ANIMAL_CATEGORY.name(), Level.EASY);
        core.running();
    }

    @Test
    public void runningExceptionTwoLettersTest() throws StorageNotInitializedException, AllWordsWereUsedException, NotAvailableException {
        Core core = new Core(new Session(PATH2), new StringReader("к\nхa\nо\nт"));
        core.setting(ANIMAL_CATEGORY.name(), Level.EASY);
        core.running();
    }

    @Test
    public void runningExceptionUsedLetterTest() throws StorageNotInitializedException, AllWordsWereUsedException, NotAvailableException {
        Core core = new Core(new Session(PATH2), new StringReader("к\nх\nх\nо\nт"));
        core.setting(ANIMAL_CATEGORY.name(), Level.EASY);
        core.running();
    }

}
