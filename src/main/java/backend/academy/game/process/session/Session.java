package backend.academy.game.process.session;


import backend.academy.exception.AllWordsWereUsedException;
import backend.academy.exception.NotAvailableException;
import backend.academy.exception.StorageNotInitializedException;
import backend.academy.game.process.MainCore;
import backend.academy.game.process.core.Core;
import backend.academy.game.process.ui.SessionDisplay;
import backend.academy.utils.CustomReader;
import backend.academy.words.Category;
import backend.academy.words.Level;
import backend.academy.words.WordsStorage;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import static backend.academy.game.process.ui.staticOutput.LanguageManager.dictionary;

@Slf4j
@Getter
public class Session {

    private final SessionDisplay sessionDisplay;
    private String dataSourcePath;
    private WordsStorage wordsStorage;
    private SessionState state;

    private SessionHistory history;
    private final CustomReader customReader;
    @Getter
    private final Reader readerIO;
    @Getter
    private final Writer writer;

    public Session(String dataSourcePath, Writer writer, Reader reader) throws StorageNotInitializedException {
        this.customReader = new CustomReader(reader);
        this.readerIO = reader;
        this.writer = writer;
        this.dataSourcePath = dataSourcePath;
        state = SessionState.READY;
        history = new SessionHistory();

        this.wordsStorage = WordsStorage.instance(dataSourcePath);
        sessionDisplay = new SessionDisplay(
            new OutputStreamWriter(System.out, StandardCharsets.UTF_8),
            wordsStorage.catalog().keySet().stream().toList());
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
            String category = customReader().readInput();
            if (category.isEmpty()) {
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
            String levelString = customReader().readInput();
            if (levelString.isEmpty()) {
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
