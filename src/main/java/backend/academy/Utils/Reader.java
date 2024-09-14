package backend.academy.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;

public class Reader {

    private final BufferedReader reader;

    public Reader(InputStreamReader streamReader) {
        reader = new BufferedReader(streamReader);
    }

    public Reader(StringReader stringReader) {
        reader = new BufferedReader(stringReader);
    }

    public String readInput() {
        try {
            return reader.readLine();
        } catch (IOException ex) {
            return "";
        }
    }

}
