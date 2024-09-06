package backend.academy.GameProcess;

import backend.academy.Exceptions.StorageNotInitialized;
import backend.academy.Words.WordsStorage;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import java.util.HashMap;

@Slf4j
@Getter
public class Session {
    private String dataSourcePath;
    private WordsStorage wordsStorage;
    private SessionState state;

    private HashMap<String, String> history = new HashMap<>();

    public Session(String dataSourcePath) throws StorageNotInitialized {
        this.wordsStorage = WordsStorage.instance(dataSourcePath);
        this.dataSourcePath = dataSourcePath;
        state = SessionState.READY;
    }

    public void chooseSpecificWordStorage(String dataSourcePath) throws StorageNotInitialized {
        try {
            wordsStorage = wordsStorage.createOwnStorage(dataSourcePath);
        } catch (Exception e) {
            wordsStorage = WordsStorage.instance(dataSourcePath);
            throw new StorageNotInitialized(e.getMessage());
        }
    }

    public void startGame() {

    }

    public void displayHistory() {

    }

}
