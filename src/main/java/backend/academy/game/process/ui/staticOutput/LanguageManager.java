package backend.academy.game.process.ui.staticOutput;

import backend.academy.exception.NotAvailableException;
import lombok.experimental.UtilityClass;

@UtilityClass
public class LanguageManager {

    public static GameOutput instance;

    public static GameOutput getDictionary(String language) {
         switch (language) {
            case "en":
                if (instance == null || instance instanceof RuGameOutput) {
                    instance = new EngGameOutput();
                }
                return instance;
            case "ru":
                if (instance == null || instance instanceof EngGameOutput) {
                    instance = new RuGameOutput();
                }
                return instance;
            default: return null;
        }
    }

    public static GameOutput dictionary() throws NotAvailableException {
        if (instance == null) {
            throw new NotAvailableException("Dictionary not initialized");
        }
        return instance;
    }

}
