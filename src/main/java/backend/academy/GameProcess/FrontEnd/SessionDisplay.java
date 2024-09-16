package backend.academy.GameProcess.FrontEnd;

import backend.academy.Exceptions.NotAvailableException;
import backend.academy.Utils.Output;
import java.io.Writer;
import java.util.List;
import static backend.academy.GameProcess.FrontEnd.StaticOutput.LanguageManager.dictionary;

public class SessionDisplay extends Output {

    private final List<String> categories;

    public SessionDisplay(Writer writer, List<String> categories) {
        super(writer);
        this.categories = categories;
    }


    public void chooseCategory() throws NotAvailableException {
        clear();
        writeOutput(dictionary().phrase("Choose category: "));
        writeOutput(categories);
        writeOutput(dictionary().phrase("(Tap Enter to choose random category)"));
        flush();
    }

    public void chooseLevel() throws NotAvailableException {
        clear();
        writeOutput(dictionary().phrase("Choose level: "));
        writeOutput(dictionary().levels());
        writeOutput(dictionary().phrase("(Tap Enter to choose random level)"));
        flush();
    }

}
