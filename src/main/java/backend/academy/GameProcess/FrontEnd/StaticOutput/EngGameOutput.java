package backend.academy.GameProcess.FrontEnd.StaticOutput;

import it.unimi.dsi.fastutil.Pair;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EngGameOutput implements GameOutput {

    private final String GAME;
    private final String HINT;
    private final String LOSE;
    private final String WIN;
    private final Set<String> ALPHABET;
    private final List<String> LEVELS;

    private final List<String> INIT_IMAGE;

    private final List<Pair<Integer, String>> REPLACEMENTS;

    public EngGameOutput() {
        GAME = "Game ";
        HINT = "Hint: ";
        LOSE = "You lose. The word was: ";
        WIN = "You win!";
        ALPHABET = new HashSet<>();
        for (char c = 'a'; c <= 'z'; c++) {
            ALPHABET.add(c + "");
        }
        LEVELS = new ArrayList<>(){
            {
                add("Easy");
                add("Medium");
                add("Hard");
            }
        };
        INIT_IMAGE = new ArrayList<>(){
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
        REPLACEMENTS = new ArrayList<>(){
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


    @Override
    public String game() {
        return GAME;
    }

    @Override
    public String hint() {
        return HINT;
    }

    @Override
    public List<String> initImage() {
        return INIT_IMAGE;
    }

    @Override
    public Set<String> alphabet() {
        return ALPHABET;
    }

    @Override
    public List<Pair<Integer, String>> replacements() {
        return REPLACEMENTS;
    }

    @Override
    public String lose() {
        return LOSE;
    }

    @Override
    public String win() {
        return WIN;
    }

    @Override
    public List<String> levels() {
        return LEVELS;
    }

}

