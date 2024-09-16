package backend.academy.GameProcess.FrontEnd.StaticOutput;

import backend.academy.Words.Level;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EngGameOutput implements GameOutput {

    private final Set<String> ALPHABET;
    private final HashMap<String, String> LEVELS;
    private final HashMap<String, String> COMMANDS;
    private final HashMap<String, String> EXCEPTIONS;
    private final HashMap<String, String> PHRASES;


    public EngGameOutput() {
        ALPHABET = new HashSet<>();
        for (char c = 'a'; c <= 'z'; c++) {
            ALPHABET.add(c + "");
        }
        LEVELS = new HashMap<>(){
            {
                put("easy", "easy");
                put("medium", "medium");
                put("hard", "hard");
            }
        };
        COMMANDS = new HashMap<>(){
            {
                put("start", "start");
                put("exit", "exit");
            }
        };
        EXCEPTIONS = new HashMap<>(){
            {
                put("Wrong command!", "Wrong command!");
                put("Choose other category or level!", "Choose other category or level!");
                put("Such level is not exist!", "Such level is not exist!");
                put("Game is not running", "Game is not running");
                put("Game is not ready or already running", "Game is not ready or already running");
                put("Letter should not be empty", "Letter should not be empty");
                put("Input should contains only one letter", "Input should contains only one letter");
                put("Letter already used", "Letter already used");
                put("All words were used", "All words were used");
                put("Game is already running", "Game is already running");
                put("No words were found", "No words were found");
                put("No such category: ", "No such category: ");
                put("Core not initialized", "Core not initialized");
            }
        };
        PHRASES = new HashMap<>(){
            {
                put("Choose category: ", "Choose category: ");
                put("(Tap Enter to choose random category)", "(Tap Enter to choose random category)");
                put("(Tap Enter to choose random level)", "(Tap Enter to choose random level)");
                put("Choose level: ", "Choose level: ");
                put("Hi! You are in the game!", "Hi! You are in the game!");
                put("To start the game type: start", "To start the game type: start");
                put("To exit the game type: exit", "To exit the game type: exit");
                put("Game ", "Game ");
                put("Hint: ", "Hint: ");
                put("Category: ", "Category: ");
                put("Level: ", "Level: ");
                put("You lose. The word was: ", "You lose. The word was: ");
                put("You win!", "You win!");
                put("You have ", "You have ");
                put(" attempts.", " attempts.");
            }
        };
    }

    @Override
    public Set<String> alphabet() {
        return ALPHABET;
    }

    @Override
    public List<String> levels() {
        return LEVELS.values().stream().toList();
    }

    @Override
    public HashMap<String, String> levelsMap() {
        return LEVELS;
    }

    @Override
    public String level(String string) {
        return LEVELS.get(string);
    }

    @Override
    public String command(String string) {
        return COMMANDS.get(string);
    }

    @Override
    public String exception(String string) {
        return EXCEPTIONS.get(string);
    }

    @Override
    public Level getLevelByString(String levelString) {
        return switch (levelString.toLowerCase()) {
            case "easy" -> Level.EASY;
            case "medium" -> Level.MEDIUM;
            case "hard" -> Level.HARD;
            default -> throw new IllegalArgumentException(exception("Such level is not exist!"));
        };
    }

    @Override
    public String phrase(String string) {
        return PHRASES.get(string);
    }

}

