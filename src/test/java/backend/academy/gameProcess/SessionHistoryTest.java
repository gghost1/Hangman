package backend.academy.gameProcess;

import backend.academy.StaticWordsTestVariables;
import backend.academy.gameProcess.session.SessionHistory;
import backend.academy.words.Level;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
public class SessionHistoryTest extends StaticWordsTestVariables {

    @Test
    public void getPassedLevelsForCategoryTest() {
        SessionHistory history = new SessionHistory();
        history.addPassedLevelForCategory("category1", Level.EASY);

        assertEquals(new HashSet<>(List.of(Level.EASY)), history.getPassedLevelsForCategory("category1"));
    }

}
