package backend.academy.game.process;

import backend.academy.StaticVariables;
import backend.academy.exception.NotAvailableException;
import backend.academy.exception.ProgramExit;
import backend.academy.exception.StorageNotInitializedException;
import backend.academy.game.process.session.Session;
import backend.academy.game.process.ui.MainCoreDisplay;
import backend.academy.game.process.ui.staticOutput.LanguageManager;
import backend.academy.utils.CustomReader;
import backend.academy.words.WordsStorage;
import java.io.Reader;
import java.io.Writer;
import static backend.academy.game.process.ui.staticOutput.LanguageManager.dictionary;

public class MainCore {

    private static MainCore instance;
    private MainCoreDisplay display;
    private IO io;
    private final Session session;

    public static synchronized MainCore instance(IO io, String language) throws StorageNotInitializedException {
        if (instance == null) {
            instance = new MainCore(io, language);
        }
        return instance;
    }

    public static MainCore instance() throws NotAvailableException {
        if (instance == null) {
            throw new NotAvailableException("Core not initialized");
        }
        return instance;
    }

    private MainCore(IO io, String language) throws StorageNotInitializedException {
        LanguageManager.getDictionary(language);
        this.io = io;
        display = new MainCoreDisplay(io.writer());
        session = new Session(StaticVariables.PATH(), io.writer(), io.reader);
    }

    public static void reset() {
        instance = null;
        WordsStorage.reset();
    }

    public void start() throws StorageNotInitializedException, NotAvailableException, ProgramExit {
        display.initMessage();
        listener();
    }

    public void listener() throws NotAvailableException, StorageNotInitializedException, ProgramExit {
        CustomReader customReader = new CustomReader(io.reader());
        String command = customReader.readInput().trim();
        if (command.toLowerCase().equals(dictionary().command("start"))) {
            session.startGame();
        } else if (command.toLowerCase().equals(dictionary().command("exit"))) {
            throw new ProgramExit();
        } else {
            display.exception(dictionary().exception("Wrong command!"));
            listener();
        }
    }

    public record IO(Writer writer, Reader reader){}

}
