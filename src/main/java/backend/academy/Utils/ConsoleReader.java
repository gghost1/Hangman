package backend.academy.Utils;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.stream.Stream;

public class ConsoleReader {

    private final BufferedReader reader;

    public ConsoleReader(InputStreamReader streamReader) {
        reader = new BufferedReader(streamReader);
    }
    public ConsoleReader(StringReader stringReader) {
        reader = new BufferedReader(stringReader);
    }

    public String readInput() {
        try {
            return reader.readLine();
        } catch (IOException _) {
            return "";
        }
    }

}
