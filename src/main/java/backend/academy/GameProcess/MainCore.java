package backend.academy.GameProcess;

import backend.academy.Exceptions.NotAvailableException;
import backend.academy.Exceptions.StorageNotInitializedException;
import backend.academy.GameProcess.FrontEnd.MainCoreDisplay;
import backend.academy.GameProcess.FrontEnd.StaticOutput.LanguageManager;
import backend.academy.GameProcess.Session.Session;
import backend.academy.StaticVariables;
import java.io.Reader;
import java.io.Writer;

public class MainCore extends LanguageManager{
    public record IO(Writer writer, Reader reader){}

    private static MainCore instance;
    private MainCoreDisplay display;
    private IO io;
    private final Session session;

    public static MainCore instance(IO io, String language) throws StorageNotInitializedException {
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
        super(language);
        this.io = io;
        display = new MainCoreDisplay(io.writer());
        session = new Session(StaticVariables.PATH(), io.writer(), io.reader);
    }

    public void start() throws StorageNotInitializedException, NotAvailableException {
        display.initMessage();
        listener();
    }

    public void listener() throws NotAvailableException, StorageNotInitializedException {
        backend.academy.Utils.Reader reader = new backend.academy.Utils.Reader(io.reader());
        String command = reader.readInput();
        if (command.toLowerCase().trim().equals(dictionary().command("start"))) {
            session.startGame();
        } else if (command.toLowerCase().trim().equals(dictionary().command("exit"))) {
            System.exit(0);
        } else {
            display.exceptionMessage(dictionary().exception("Wrong command!"));
            listener();
        }
    }

}
