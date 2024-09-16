package backend.academy.GameProcess.FrontEnd.StaticOutput;

import backend.academy.Words.Level;
import it.unimi.dsi.fastutil.Pair;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

@SuppressWarnings({"MultipleStringLiterals", "MagicNumber", "EmptyLineSeparator"})
public interface GameOutput {
    default List<String> initImage() {
        return new ArrayList<>() {
            {
                add("|------   ");
                add("|    |    ");
                add("|         ");
                add("|         ");
                add("|         ");
                add("|         ");
                add("|         ");
                add("|         ");
            }
        };
    }

    default List<Pair<Integer, String>> replacements() {
        return new ArrayList<>() {
            {
                add(Pair.of(2, "|    O    "));
                add(Pair.of(3, "|    |    "));
                add(Pair.of(4, "|    |    "));
                add(Pair.of(4, "|   /|    "));
                add(Pair.of(4, "|   /|\\   "));
                add(Pair.of(5, "|    |    "));
                add(Pair.of(6, "|   /     "));
                add(Pair.of(6, "|   / \\   "));
            }
        };
    }
    Set<String> alphabet();
    List<String> levels();
    HashMap<String, String> levelsMap();
    String level(String string);
    String command(String string);
    String exception(String string);
    Level getLevelByString(String string);
    String phrase(String string);
}
