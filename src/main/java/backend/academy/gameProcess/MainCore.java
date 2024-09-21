package backend.academy.gameProcess;

import backend.academy.StaticVariables;
import backend.academy.exceptions.NotAvailableException;
import backend.academy.exceptions.ProgramExit;
import backend.academy.exceptions.StorageNotInitializedException;
import backend.academy.gameProcess.session.Session;
import backend.academy.gameProcess.ui.MainCoreDisplay;
import backend.academy.gameProcess.ui.staticOutput.LanguageManager;
import backend.academy.words.WordsStorage;
import java.io.Reader;
import java.io.Writer;
import static backend.academy.gameProcess.ui.staticOutput.LanguageManager.dictionary;

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
        backend.academy.utils.Reader reader = new backend.academy.utils.Reader(io.reader());
        String command = reader.readInput().trim();
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
