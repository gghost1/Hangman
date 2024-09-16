package backend.academy.GameProcess.FrontEnd;

import backend.academy.GameProcess.FrontEnd.StaticOutput.EngGameOutput;
import backend.academy.GameProcess.FrontEnd.StaticOutput.GameOutput;
import backend.academy.Utils.Output;
import java.io.Writer;
import java.util.List;

public class SessionDisplay extends Output {

    private int stage;
    private final List<String> categories;
    private final GameOutput gameOutput;

    public SessionDisplay(Writer writer, List<String> categories) {
        super(writer);
        stage = 0;
        this.categories = categories;
        gameOutput = new EngGameOutput();
    }


    public void output() {
        writeOutput("Привет, давай выберем игру.");
        chooseCategory();
        flush();
    }

    public void chooseCategory() {
        writeOutput("Выберите категорию: ");
        writeOutput(categories);
        writeOutput("(нажмите Enter для выбора категории по умолчанию)");
        flush();
    }

    public void chooseLevel() {
        writeOutput("Выберите уровень: ");
        writeOutput(gameOutput.levels());
        writeOutput("(нажмите Enter для выбора уровня по умолчанию)");
        flush();
    }

    public void exception(String message) {
        writeOutput(message);
        flush();
    }

}
