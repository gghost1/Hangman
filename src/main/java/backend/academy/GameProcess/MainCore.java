package backend.academy.GameProcess;

import backend.academy.Exceptions.StorageNotInitializedException;
import backend.academy.GameProcess.FrontEnd.MainCoreDisplay;
import backend.academy.GameProcess.Session.Session;
import backend.academy.StaticVariables;
import java.io.Reader;
import java.io.Writer;
import java.util.List;

public class MainCore {
    public record IO(Writer writer, Reader reader){}

    private static MainCore instance;
    private Session session;
    private MainCoreDisplay display;
    private IO io;

    public static MainCore instance(IO io) {
        if (instance == null) {
            instance = new MainCore(io);
        }
        return instance;
    }

    private MainCore(IO io) {
        this.io = io;
        display = new MainCoreDisplay(io.writer());
    }

    public void start() throws StorageNotInitializedException {
        display.initMessage();
        backend.academy.Utils.Reader reader = new backend.academy.Utils.Reader(io.reader());
        String command = reader.readInput();
        if (command.toLowerCase().trim().equals("start")) {
            session = new Session(StaticVariables.PATH(), io.writer(), io.reader);
            session.startGame();
        } else if (command.toLowerCase().trim().equals("exit")) {
            System.exit(0);
        } else {
            display.exceptionMessage("Wrong command!");
        }
    }

}
