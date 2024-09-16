package backend.academy.GameProcess;

import backend.academy.Words.datasource.StaticWordsTestVariables;
import backend.academy.Exceptions.AllWordsWereUsedException;
import backend.academy.Exceptions.NotAvailableException;
import backend.academy.Exceptions.StorageNotInitializedException;
import backend.academy.GameProcess.Core.Core;
import backend.academy.GameProcess.Core.GameState;
import backend.academy.GameProcess.Session.Session;
import backend.academy.StaticVariables;
import backend.academy.Words.Level;
import backend.academy.Words.WordsStorage;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class CoreTest extends StaticWordsTestVariables {

    @AfterEach
    public void afterEach() {
        WordsStorage.reset();
    }

    private Core createGame() throws StorageNotInitializedException {
        Session session = new Session(PATH2, new OutputStreamWriter(System.out), new InputStreamReader(System.in));
        return new Core(session);
    }

    @Test
    public void creationGameTest() throws StorageNotInitializedException {
        Core core = createGame();

        assertEquals(GameState.NOT_READY, core.gameState());
    }

    @Test
    public void settingWithCategoryAndLevelTest() throws StorageNotInitializedException, AllWordsWereUsedException,
        NotAvailableException {
        Core core = createGame();

        core.setting(ANIMAL_CATEGORY.name(), Level.EASY);

        assertEquals(Level.EASY, core.level());
        assertEquals(core.session().wordsStorage().getCategoryByName(ANIMAL_CATEGORY.name()), core.category());
        assertThat(core.word()).isIn(core.category().getWordsByLevel(Level.EASY).values());
        assertEquals(StaticVariables.MAX_MISTAKES_FOR_EASY(), core.mistakeStep());
        assertEquals(StaticVariables.MAX_MISTAKES_FOR_EASY(), core.mistakesLeft());
    }

    @Test
    public void settingWithCategoryTest() throws StorageNotInitializedException, AllWordsWereUsedException, NotAvailableException {
        Core core = createGame();

        core.setting(ANIMAL_CATEGORY.name());
        assertEquals(core.session().wordsStorage().getCategoryByName(ANIMAL_CATEGORY.name()), core.category());
        assertThat(core.word()).isIn(core.category().getWordsByLevel(core.level()).values());
        assertEquals(StaticVariables.getMaxMistakes(core.level()), core.mistakeStep());
        assertEquals(StaticVariables.getMaxMistakes(core.level()), core.mistakesLeft());
    }

    @Test
    public void settingTest() throws StorageNotInitializedException, AllWordsWereUsedException, NotAvailableException {
        Core core = createGame();

        core.setting();
        assertThat(core.word()).isIn(core.category().getWordsByLevel(core.level()).values());
        assertEquals(StaticVariables.getMaxMistakes(core.level()), core.mistakeStep());
        assertEquals(StaticVariables.getMaxMistakes(core.level()), core.mistakesLeft());
    }

}
