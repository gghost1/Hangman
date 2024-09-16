package backend.academy.GameProcess.FrontEnd;

import backend.academy.Exceptions.IncorrectInputException;
import backend.academy.Exceptions.NotAvailableException;
import backend.academy.GameProcess.FrontEnd.StaticOutput.GameOutput;
import backend.academy.GameProcess.FrontEnd.StaticOutput.EngGameOutput;
import backend.academy.Utils.Output;
import backend.academy.Words.Level;
import backend.academy.Words.Word;
import it.unimi.dsi.fastutil.Pair;
import java.io.Writer;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import static backend.academy.GameProcess.FrontEnd.StaticOutput.LanguageManager.dictionary;

public class GameDisplay extends Output {

    private final Word word;
    private final String category;
    private final String level;
    private Set<String> guessedLetters;
    private Set<String> usedLetters;

    private int stage;
    private final int stepStage;

    private List<String> currentImage;

    public GameDisplay(Writer writer, Word word, String category, Level level, int stepStage)
        throws NotAvailableException {
        super(writer);
        guessedLetters = new HashSet<>();
        usedLetters = new HashSet<>();
        this.word = word;
        this.category = category;
        this.level = level.name();

        stage = 0;
        this.stepStage = stepStage;

        currentImage = dictionary().initImage();

        output();
    }

    public void update(boolean isMistake, Set<String> usedLetters, Set<String> guessedLetters)
        throws NotAvailableException {
        this.usedLetters = usedLetters;
        if (!isMistake) {
            this.guessedLetters = guessedLetters;
        } else {
            stage+=stepStage;
            updateImage();
        }
        output();
    }

    private void updateImage() throws NotAvailableException {
        for (int i = stage-stepStage; i < stage; i++) {
            Pair<Integer, String> replacement = dictionary().replacements().get(i);
            currentImage.set(replacement.key(), replacement.value());
        }
    }

    public void output() throws NotAvailableException {
        clear();
        writeOutput(dictionary().phrase("Game "));
        writeOutput(dictionary().phrase("Category: ") + category, false, ". ");
        writeOutput(dictionary().phrase("Level: ") + level, false, ". ");
        writeOutput(dictionary().phrase("You have ") + 8/stepStage + dictionary().phrase(" attempts."));
        writeOutput(dictionary().phrase("Hint: "), false, "");
        writeOutput(word.hint());

        writeOutput(Arrays.stream(word.name().split(""))
            .map(x -> guessedLetters
                    .contains(x) ? x : null)
                    .toList(),
                ((x) -> x == null ? " _ " : x),
            false, "");
        writeOutput("");
        writeOutput(currentImage);
        writeOutput("");
        writeOutput(dictionary().alphabet()
            .stream()
            .map(x ->
                (usedLetters.contains(x) ? "_" : x)
            ).toList(),
            false, " ");
        writeOutput("");
        flush();
    }

    public void exception(Exception e) throws NotAvailableException {
        output();
        if (e instanceof IncorrectInputException) {
            writeOutput(e.getMessage());
        }
        flush();
    }

    public void exception(String message) {
        writeOutput(message);
        flush();
    }

    public void outputLose() throws NotAvailableException {
        writeOutput(dictionary().phrase("You lose. The word was: "), false, "");
        writeOutput(word.name());
        flush();
    }

    public void outputWin() throws NotAvailableException {
        writeOutput(dictionary().phrase("You win!"), true, "");
        flush();
    }

}
