package backend.academy.GameProcess.FrontEnd;

import backend.academy.GameProcess.FrontEnd.StaticOutput.EngGameOutput;
import backend.academy.GameProcess.FrontEnd.StaticOutput.GameOutput;
import backend.academy.Utils.Output;
import java.io.Writer;

public class MainCoreDisplay extends Output {

    private final GameOutput gameOutput;

    public MainCoreDisplay(Writer writer) {
        super(writer);
        gameOutput = new EngGameOutput();
    }

    public void initMessage() {
        writeOutput("Hi! You are in the game!");
        writeOutput("To start the game type: start");
        writeOutput("To exit the game type: exit");
        flush();
    }

    public void exceptionMessage(String message) {
        writeOutput(message);
        flush();
    }
}
