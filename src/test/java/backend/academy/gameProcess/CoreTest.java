package backend.academy.gameProcess;

import backend.academy.gameProcess.ui.staticOutput.LanguageManager;
import backend.academy.StaticWordsTestVariables;
import backend.academy.exceptions.AllWordsWereUsedException;
import backend.academy.exceptions.NotAvailableException;
import backend.academy.exceptions.StorageNotInitializedException;
import backend.academy.gameProcess.core.Core;
import backend.academy.gameProcess.core.GameState;
import backend.academy.gameProcess.session.Session;
import backend.academy.StaticVariables;
import backend.academy.words.Level;
import backend.academy.words.WordsStorage;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.StringWriter;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
public class CoreTest extends StaticWordsTestVariables {

    @BeforeAll
    public static void init() {
        LanguageManager.getDictionary("ru");
    }

    @AfterEach
    public void afterEach() {
        WordsStorage.reset();
    }

    private Core createGame() throws StorageNotInitializedException, NotAvailableException {
        Session session = new Session(FULL_FILED_CATEGORY_PATH, new OutputStreamWriter(System.out), new InputStreamReader(System.in));
        return new Core(session);
    }

    @Test
    public void creationGameTest() throws StorageNotInitializedException, NotAvailableException {
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
        assertEquals(StaticVariables.MISTAKE_STEP_FOR_EASY(), core.mistakeStep());
        assertEquals(StaticVariables.MAX_MISTAKES() / StaticVariables.MISTAKE_STEP_FOR_EASY(), core.mistakesLeft());
    }

    @Test
    public void settingWithCategoryTest() throws StorageNotInitializedException, AllWordsWereUsedException, NotAvailableException {
        Core core = createGame();

        core.setting(ANIMAL_CATEGORY.name());
        assertEquals(core.session().wordsStorage().getCategoryByName(ANIMAL_CATEGORY.name()), core.category());
        assertThat(core.word()).isIn(core.category().getWordsByLevel(core.level()).values());
        assertEquals(StaticVariables.getMistakesStep(core.level()), core.mistakeStep());
        assertEquals(StaticVariables.MAX_MISTAKES() / StaticVariables.getMistakesStep(core.level()), core.mistakesLeft());
    }

    @Test
    public void settingWithLevelTest() throws StorageNotInitializedException, AllWordsWereUsedException, NotAvailableException {
        Core core = createGame();
        core.setting(Level.EASY);
        assertEquals(Level.EASY, core.level());
        assertThat(core.word()).isIn(core.category().getWordsByLevel(Level.EASY).values());
        assertEquals(StaticVariables.MISTAKE_STEP_FOR_EASY(), core.mistakeStep());
        assertEquals(StaticVariables.MAX_MISTAKES() / StaticVariables.MISTAKE_STEP_FOR_EASY(), core.mistakesLeft());
    }

    @Test
    public void settingTest() throws StorageNotInitializedException, AllWordsWereUsedException, NotAvailableException {
        Core core = createGame();

        core.setting();
        assertThat(core.word()).isIn(core.category().getWordsByLevel(core.level()).values());
        assertEquals(StaticVariables.getMistakesStep(core.level()), core.mistakeStep());
        assertEquals(StaticVariables.MAX_MISTAKES() / StaticVariables.getMistakesStep(core.level()), core.mistakesLeft());
    }

    @Test
    public void settingWithIncorrectCategoryTest() throws NotAvailableException, StorageNotInitializedException {
        Core core = createGame();
        assertThrows(IllegalArgumentException.class, () -> core.setting("incorrectCategory"));
    }

    @Test
    public void runningNegativeTest() throws NotAvailableException, StorageNotInitializedException {
        Core core = createGame();
        assertThrows(NotAvailableException.class, () -> core.running());
    }

    @Test
    public void runningPositiveTest() throws NotAvailableException, StorageNotInitializedException, AllWordsWereUsedException {
        Session session = new Session(FULL_FILED_CATEGORY_PATH, new OutputStreamWriter(System.out), new StringReader("к\nо\nт\n"));
        Core core = new Core(session);
        core.setting(ANIMAL_CATEGORY.name(), Level.EASY);
        assertThrows(NotAvailableException.class, () -> core.running());
    }

    @Test
    public void runningIncorrectInputTest() throws NotAvailableException, StorageNotInitializedException, AllWordsWereUsedException {
        StringWriter stringWriter = new StringWriter();
        Session session = new Session(FULL_FILED_CATEGORY_PATH, stringWriter, new StringReader("кj\n\nк\nl\nо\nо\nт\n"));
        Core core = new Core(session);
        core.setting(ANIMAL_CATEGORY.name(), Level.EASY);
        try {
            core.running();
        } catch (NotAvailableException e) {

        } finally {
            assertThat(stringWriter.toString()).contains("Ввод должен содержать только одну букву");
            assertThat(stringWriter.toString()).contains("Буква не должна быть пустой");
            assertThat(stringWriter.toString()).contains("Буква уже использована");
            assertEquals(core.mistakesLeft(), StaticVariables.MAX_MISTAKES() / StaticVariables.getMistakesStep(core.level())-1);
        }
    }


}
