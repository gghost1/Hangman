package backend.academy.GameProcess.Session;


import backend.academy.Exceptions.AllWordsWereUsedException;
import backend.academy.Exceptions.NotAvailableException;
import backend.academy.Exceptions.StorageNotInitializedException;
import backend.academy.GameProcess.Core.Core;
import backend.academy.GameProcess.FrontEnd.SessionDisplay;
import backend.academy.GameProcess.MainCore;
import backend.academy.Utils.Reader;
import backend.academy.Words.Category;
import backend.academy.Words.Level;
import backend.academy.Words.WordsStorage;
import java.io.OutputStreamWriter;
import java.io.Writer;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import static backend.academy.GameProcess.FrontEnd.StaticOutput.LanguageManager.dictionary;

@Slf4j
@Getter
public class Session {

    private final SessionDisplay sessionDisplay;
    private String dataSourcePath;
    private WordsStorage wordsStorage;
    private SessionState state;

    private SessionHistory history;
    private final Reader reader;
    @Getter
    private final java.io.Reader readerIO;
    @Getter
    private final Writer writer;


    public Session(String dataSourcePath, Writer writer, java.io.Reader reader) throws StorageNotInitializedException {
        this.wordsStorage = WordsStorage.instance(dataSourcePath);
        this.sessionDisplay = new SessionDisplay(
                new OutputStreamWriter(System.out),
                wordsStorage.catalog().keySet().stream().toList());
        this.reader = new Reader(reader);
        this.readerIO = reader;
        this.writer = writer;
        this.dataSourcePath = dataSourcePath;
        state = SessionState.READY;
        history = new SessionHistory();
    }

    public void chooseSpecificWordStorage(String dataSourcePath) throws StorageNotInitializedException {
        try {
            wordsStorage = wordsStorage.createOwnStorage(dataSourcePath);
        } catch (Exception e) {
            wordsStorage = WordsStorage.instance(dataSourcePath);
            throw new StorageNotInitializedException(e.getMessage());
        }
    }

    public void startGame() throws NotAvailableException, StorageNotInitializedException {
            if (state == SessionState.READY) {
                Core core = new Core(this);
                boolean isReady = false;
                while (!isReady) {
                    isReady = true;
                    Category selectedCategory = getCategory();
                    Level level = getLevel();
                    try {
                        if (selectedCategory != null && level != null) {
                            core.setting(selectedCategory.name(), level);
                        } else if (selectedCategory == null && level == null) {
                            core.setting();
                        } else if (selectedCategory == null) {
                            core.setting(level);
                        } else {
                            core.setting(selectedCategory.name());
                        }
                    } catch (AllWordsWereUsedException e) {
                        sessionDisplay.exception(dictionary().exception("Choose other category or level"));
                        core = new Core(this);
                        isReady = false;
                    } catch (NotAvailableException e) {
                        sessionDisplay.exception(e.getMessage());
                    } catch (StorageNotInitializedException e) {
                        throw new RuntimeException(e);
                    }
                }
                try {
                    core.running();
                } catch (NotAvailableException e) {
                    sessionDisplay.exception(e.getMessage());
                    MainCore.instance().start();
                }
            }
    }

    private Category getCategory() throws NotAvailableException {
        sessionDisplay.chooseCategory();
        Category selectedCategory = null;
        while (selectedCategory == null) {
            String category = reader().readInput();
            if (category.equals("")) {
                break;
            }
            try {
                selectedCategory = wordsStorage.getCategoryByName(category);
            } catch (IllegalArgumentException e) {
                sessionDisplay.exception(e.getMessage());
            }
        }

        return selectedCategory;
    }

    private Level getLevel() throws NotAvailableException {
        sessionDisplay.chooseLevel();
        Level level = null;
        while (level == null) {
            String levelString = reader().readInput();
            if (levelString.equals("")) {
                break;
            }
            try {
                level = dictionary().getLevelByString(levelString);
            } catch (IllegalArgumentException e) {
                sessionDisplay.exception(e.getMessage());
            }
        }
        return level;
    }

    public void displayHistory() {

    }

}
