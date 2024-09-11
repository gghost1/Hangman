package GameProcess;

import Words.datasource.StaticWordsTestVariables;
import backend.academy.Exceptions.AllWordsWereUsed;
import backend.academy.Exceptions.StorageNotInitialized;
import backend.academy.GameProcess.Game.Game;
import backend.academy.GameProcess.Game.GameState;
import backend.academy.GameProcess.Session.Session;
import backend.academy.StaticVariables;
import backend.academy.Words.Level;
import backend.academy.Words.WordsStorage;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class GameTest extends StaticWordsTestVariables {

    @AfterEach
    public void afterEach() {
        WordsStorage.reset();
    }

    private Game createGame() throws StorageNotInitialized {
        Session session = new Session(PATH2);
        return new Game(session);
    }

    @Test
    public void creationGameTest() throws StorageNotInitialized {
        Game game = createGame();

        assertEquals(GameState.SETTING, game.gameState());
    }

    @Test
    public void settingWithCategoryAndLevelTest() throws StorageNotInitialized, AllWordsWereUsed {
        Game game = createGame();

        game.setting(ANIMAL_CATEGORY.name(), Level.EASY);

        assertEquals(Level.EASY, game.level());
        assertEquals(game.session().wordsStorage().getCategoryByName(ANIMAL_CATEGORY.name()), game.category());
        assertThat(game.word()).isIn(game.category().getWordsByLevel(Level.EASY).values());
        assertEquals(StaticVariables.MAX_MISTAKES_FOR_EASY(), game.maxMistakes());
        assertEquals(StaticVariables.MAX_MISTAKES_FOR_EASY(), game.mistakesLeft());
    }

    @Test
    public void settingWithCategoryTest() throws StorageNotInitialized, AllWordsWereUsed {
        Game game = createGame();

        game.setting(ANIMAL_CATEGORY.name());
        assertEquals(game.session().wordsStorage().getCategoryByName(ANIMAL_CATEGORY.name()), game.category());
        assertThat(game.word()).isIn(game.category().getWordsByLevel(game.level()).values());
        assertEquals(StaticVariables.getMaxMistakes(game.level()), game.maxMistakes());
        assertEquals(StaticVariables.getMaxMistakes(game.level()), game.mistakesLeft());
    }

    @Test
    public void settingTest() throws StorageNotInitialized, AllWordsWereUsed {
        Game game = createGame();

        game.setting();
        assertThat(game.word()).isIn(game.category().getWordsByLevel(game.level()).values());
        assertEquals(StaticVariables.getMaxMistakes(game.level()), game.maxMistakes());
        assertEquals(StaticVariables.getMaxMistakes(game.level()), game.mistakesLeft());
    }

}
