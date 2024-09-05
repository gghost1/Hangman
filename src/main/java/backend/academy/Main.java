package backend.academy;

import backend.academy.Utils.WordParser;
import lombok.experimental.UtilityClass;
import java.io.IOException;

@UtilityClass
public class Main {
    public static void main(String[] args) {
        try {
            WordParser wordParser = new WordParser("src/main/resources/words");
            wordParser.parse();
        } catch (IOException _) {

        }


    }
}
