package backend.academy.utils;

import java.io.BufferedReader;
import java.io.IOException;

public class CustomReader {

    private final BufferedReader reader;

    public CustomReader(java.io.Reader reader) {
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
