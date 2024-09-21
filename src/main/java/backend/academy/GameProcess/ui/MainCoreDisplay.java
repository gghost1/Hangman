package backend.academy.gameProcess.ui;

import backend.academy.exceptions.NotAvailableException;
import backend.academy.utils.Output;
import java.io.Writer;
import static backend.academy.gameProcess.ui.staticOutput.LanguageManager.dictionary;

public class MainCoreDisplay extends Output {


    public MainCoreDisplay(Writer writer) {
        super(writer);
    }

    public void initMessage() throws NotAvailableException {
        clear();
        writeOutput(dictionary().phrase("Hi! You are in the game!"));
        writeOutput(dictionary().phrase("To start the game type: start"));
        writeOutput(dictionary().phrase("To exit the game type: exit"));
        flush();
    }

}