package backend.academy.game.process.ui;

import backend.academy.exception.NotAvailableException;
import backend.academy.utils.Output;
import java.io.Writer;
import static backend.academy.game.process.ui.staticOutput.LanguageManager.dictionary;

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
