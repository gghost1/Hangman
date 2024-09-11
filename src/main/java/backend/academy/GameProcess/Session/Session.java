package backend.academy.GameProcess.Session;

import backend.academy.Exceptions.StorageNotInitialized;
import backend.academy.Words.WordsStorage;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class Session {
    private String dataSourcePath;
    private WordsStorage wordsStorage;
    private SessionState state;

    private SessionHistory history;

    public Session(String dataSourcePath) throws StorageNotInitialized {
        this.wordsStorage = WordsStorage.instance(dataSourcePath);
        this.dataSourcePath = dataSourcePath;
        state = SessionState.READY;
        history = new SessionHistory();
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
