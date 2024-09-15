package backend.academy.GameProcess.FrontEnd;

import backend.academy.Exceptions.IncorrectInputException;
import backend.academy.GameProcess.FrontEnd.StaticOutput.GameOutput;
import backend.academy.GameProcess.FrontEnd.StaticOutput.EngGameOutput;
import backend.academy.Utils.Output;
import backend.academy.Words.Word;
import it.unimi.dsi.fastutil.Pair;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GameDisplay extends Output {

    private final GameOutput gameOutput;
    private final Word word;
    private Set<String> guessedLetters;
    private Set<String> usedLetters;

    private int stage;
    private final int stepStage;

    private List<String> currentImage;

    public GameDisplay(OutputStreamWriter outputStreamWriter, Word word, int stepStage) {
        super(outputStreamWriter);
        gameOutput = new EngGameOutput();
        guessedLetters = new HashSet<>();
        usedLetters = new HashSet<>();
        this.word = word;

        stage = 0;
        this.stepStage = stepStage;

        currentImage = gameOutput.initImage();

        output();
    }

    public GameDisplay(FileWriter fileWriter, Word word, int stepStage) {
        super(fileWriter);
        gameOutput = new EngGameOutput();
        guessedLetters = new HashSet<>();
        usedLetters = new HashSet<>();
        this.word = word;

        stage = 0;
        this.stepStage = stepStage;

        currentImage = gameOutput.initImage();

        output();
    }

    public void update(boolean isMistake, Set<String> usedLetters, Set<String> guessedLetters) {
        this.usedLetters = usedLetters;
        if (!isMistake) {
            this.guessedLetters = guessedLetters;
        } else {
            stage+=stepStage;
            updateImage();
        }
        output();
        if (stage == gameOutput.replacements().size()) {
            outputLose();
        }
    }

    private void updateImage() {
        for (int i = stage-stepStage; i < stage; i++) {
            Pair<Integer, String> replacement = gameOutput.replacements().get(i);
            currentImage.set(replacement.key(), replacement.value());
        }
    }

    public void output() {
        clear();
        writeOutput(gameOutput.game());
        writeOutput(gameOutput.hint(), false, "");
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
        writeOutput(gameOutput.alphabet()
            .stream()
            .map(x ->
                (usedLetters.contains(x) ? "_" : x)
            ).toList(),
            false, " ");
        writeOutput("");
        flush();
    }

    public void exception(Exception e) {
        output();
        if (e instanceof IncorrectInputException) {
            writeOutput(e.getMessage());
        }
        flush();
    }

    private void outputLose() {
        writeOutput(gameOutput.lose(), false, "");
        writeOutput(word.name());
        flush();
    }

    public void outputWin() {
        writeOutput(gameOutput.win(), false, "");
        flush();
    }

}
