package GameProcess;

import Words.datasource.StaticWordsTestVariables;
import backend.academy.Exceptions.AllWordsWereUsedException;
import backend.academy.Exceptions.NotAvailableException;
import backend.academy.Exceptions.StorageNotInitializedException;
import backend.academy.GameProcess.Game.Game;
import backend.academy.GameProcess.Game.GameState;
import backend.academy.GameProcess.Session.Session;
import backend.academy.StaticVariables;
import backend.academy.Words.Level;
import backend.academy.Words.WordsStorage;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import java.io.InputStreamReader;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class GameTest extends StaticWordsTestVariables {

    @AfterEach
    public void afterEach() {
        WordsStorage.reset();
    }

    private Game createGame() throws StorageNotInitializedException {
        Session session = new Session(PATH2);
        return new Game(session, new InputStreamReader(System.in));
    }

    @Test
    public void creationGameTest() throws StorageNotInitializedException {
        Game game = createGame();

        assertEquals(GameState.NOT_READY, game.gameState());
    }

    @Test
    public void settingWithCategoryAndLevelTest() throws StorageNotInitializedException, AllWordsWereUsedException,
        NotAvailableException {
        Game game = createGame();

        game.setting(ANIMAL_CATEGORY.name(), Level.EASY);

        assertEquals(Level.EASY, game.level());
        assertEquals(game.session().wordsStorage().getCategoryByName(ANIMAL_CATEGORY.name()), game.category());
        assertThat(game.word()).isIn(game.category().getWordsByLevel(Level.EASY).values());
        assertEquals(StaticVariables.MAX_MISTAKES_FOR_EASY(), game.maxMistakes());
        assertEquals(StaticVariables.MAX_MISTAKES_FOR_EASY(), game.mistakesLeft());
    }

    @Test
    public void settingWithCategoryTest() throws StorageNotInitializedException, AllWordsWereUsedException, NotAvailableException {
        Game game = createGame();

        game.setting(ANIMAL_CATEGORY.name());
        assertEquals(game.session().wordsStorage().getCategoryByName(ANIMAL_CATEGORY.name()), game.category());
        assertThat(game.word()).isIn(game.category().getWordsByLevel(game.level()).values());
        assertEquals(StaticVariables.getMaxMistakes(game.level()), game.maxMistakes());
        assertEquals(StaticVariables.getMaxMistakes(game.level()), game.mistakesLeft());
    }

    @Test
    public void settingTest() throws StorageNotInitializedException, AllWordsWereUsedException, NotAvailableException {
        Game game = createGame();

        game.setting();
        assertThat(game.word()).isIn(game.category().getWordsByLevel(game.level()).values());
        assertEquals(StaticVariables.getMaxMistakes(game.level()), game.maxMistakes());
        assertEquals(StaticVariables.getMaxMistakes(game.level()), game.mistakesLeft());
    }

}
