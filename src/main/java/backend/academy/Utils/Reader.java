package backend.academy.Utils;

import java.io.BufferedReader;
import java.io.IOException;

public class Reader {

    private final BufferedReader reader;

    public Reader(java.io.Reader reader) {
        this.reader = new BufferedReader(reader);
    }

    public String readInput() {
        try {
            String input = reader.readLine();
            while (input == null) {
                input = reader.readLine();
            }
            return input;
        } catch (IOException ex) {
            return "";
        }
    }

}
