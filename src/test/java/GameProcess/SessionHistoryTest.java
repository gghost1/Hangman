package GameProcess;

import Words.datasource.StaticWordsTestVariables;
import backend.academy.GameProcess.Session.SessionHistory;
import backend.academy.Words.Level;
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
