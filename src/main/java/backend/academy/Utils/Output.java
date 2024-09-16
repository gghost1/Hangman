package backend.academy.Utils;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.List;
import java.util.function.Function;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;

public class Output {
    private final PrintWriter writer;

    public Output(Writer writer) {
        this.writer = new PrintWriter(writer);
    }

    protected void writeOutput(String output, boolean newLine, String splitter) {
        writer.print(output + (newLine ? "\n" : splitter));
    }

    protected void writeOutput(String output) {
        writer.print(output + "\n");
    }

    protected void writeOutput(List<String> output) {
        output.forEach(x -> writer.print(x + "\n"));
    }

    protected void writeOutput(List<String> output, boolean newLine, String splitter) {
        output.forEach(x -> writer.print(x + (newLine ? "\n" : splitter)));
    }

    protected void writeOutput(List<String> output, Function<String, String> function) {
        output.forEach(x -> writer.print(function.apply(x) + "\n"));
    }

    protected void writeOutput(List<String> output,
        Function<String, String> function,
        boolean newLine, String splitter) {
        output.forEach(x -> writer.print(function.apply(x) + (newLine ? "\n" : splitter)));
    }

    protected void flush() {
        writer.flush();
    }

    protected void clear() {
        AnsiConsole.systemInstall();
        writer.print(Ansi.ansi().eraseScreen());
        flush();
    }

    public void exception(String message) {
        writeOutput(message);
        flush();
    }
}
