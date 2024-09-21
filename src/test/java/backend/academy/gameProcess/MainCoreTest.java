package backend.academy.gameProcess;

import backend.academy.exceptions.NotAvailableException;
import backend.academy.exceptions.StorageNotInitializedException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import java.io.StringReader;
import java.io.StringWriter;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MainCoreTest {

    @AfterEach
    public void afterEach() {
        MainCore.reset();
    }

    @Test
    public void instanceCreationTest() throws NotAvailableException, StorageNotInitializedException {
        MainCore instance = MainCore.instance(new MainCore.IO(new StringWriter(), new StringReader("")), "ru");

        assertEquals(instance, MainCore.instance());
    }

    @Test
    public void instanceCreationNegativeTest() {
        assertThrows(NotAvailableException.class, () -> MainCore.instance());
    }

}
