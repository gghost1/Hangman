package backend.academy.GameProcess.Session;


import backend.academy.Exceptions.StorageNotInitializedException;
import backend.academy.GameProcess.Game.Game;
import backend.academy.Words.WordsStorage;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class Session {
    private String dataSourcePath;
    private WordsStorage wordsStorage;
    private SessionState state;

    private SessionHistory history;

    private List<Game> runningGames;

    public Session(String dataSourcePath) throws StorageNotInitializedException {
        this.wordsStorage = WordsStorage.instance(dataSourcePath);
        this.dataSourcePath = dataSourcePath;
        state = SessionState.READY;
        history = new SessionHistory();
        runningGames = new ArrayList<>();
    }

    public void chooseSpecificWordStorage(String dataSourcePath) throws StorageNotInitializedException {
        try {
            wordsStorage = wordsStorage.createOwnStorage(dataSourcePath);
        } catch (Exception e) {
            wordsStorage = WordsStorage.instance(dataSourcePath);
            throw new StorageNotInitializedException(e.getMessage());
        }
    }

    public void startGame() {

    }

    public void displayHistory() {

    }

}
