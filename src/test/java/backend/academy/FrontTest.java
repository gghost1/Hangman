package backend.academy;

import backend.academy.GameProcess.FrontEnd.GameDisplay;
import backend.academy.Words.Word;
import org.junit.jupiter.api.Test;
import java.io.OutputStreamWriter;
import java.util.HashSet;

public class FrontTest {

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

}
