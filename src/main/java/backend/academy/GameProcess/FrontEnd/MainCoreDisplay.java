package backend.academy.GameProcess.FrontEnd;

import backend.academy.Exceptions.NotAvailableException;
import backend.academy.GameProcess.FrontEnd.StaticOutput.EngGameOutput;
import backend.academy.GameProcess.FrontEnd.StaticOutput.GameOutput;
import backend.academy.Utils.Output;
import java.io.Writer;
import static backend.academy.GameProcess.FrontEnd.StaticOutput.LanguageManager.dictionary;

public class MainCoreDisplay extends Output {


    public MainCoreDisplay(Writer writer) {
        super(writer);
    }

    public void initMessage() throws NotAvailableException {
        writeOutput(dictionary().phrase("Hi! You are in the game!"));
        writeOutput(dictionary().phrase("To start the game type: start"));
        writeOutput(dictionary().phrase("To exit the game type: exit"));
        flush();
    }

    public void exceptionMessage(String message) {
        writeOutput(message);
        flush();
    }
}
