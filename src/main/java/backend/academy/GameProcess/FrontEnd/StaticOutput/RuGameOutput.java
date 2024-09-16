package backend.academy.GameProcess.FrontEnd.StaticOutput;

import backend.academy.Words.Level;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RuGameOutput implements GameOutput {

    private final Set<String> ALPHABET;
    private final HashMap<String, String> LEVELS;
    private final HashMap<String, String> COMMANDS;
    private final HashMap<String, String> EXCEPTIONS;
    private final HashMap<String, String> PHRASES;


    public RuGameOutput() {
        ALPHABET = new HashSet<>();
        for (char c = 'а'; c <= 'я'; c++) {
            ALPHABET.add(c + "");
        }
        LEVELS = new HashMap<>(){
            {
                put("easy", "легкий");
                put("medium", "средний");
                put("hard", "тяжелый");
            }
        };
        COMMANDS = new HashMap<>(){
            {
                put("start", "начать");
                put("exit", "выход");
            }
        };
        EXCEPTIONS = new HashMap<>(){
            {
                put("Wrong command!", "Неправильная команда!");
                put("Choose other category or level!", "Выберите другую категорию или сложность!");
                put("Such level is not exist!", "Такого уровня не существует!");
                put("Game is not running", "Игра не запущена");
                put("Game is not ready or already running", "Игра не настроена или уже запущена");
                put("Letter should not be empty", "Буква не должна быть пустой");
                put("Input should contains only one letter", "Ввод должен содержать только одну букву");
                put("Letter already used", "Буква уже использована");
                put("All words were used", "Все слова использованы");
                put("Game is already running", "Игра уже запущена");
                put("No words were found", "Не обнаружено ни одного слова");
                put("No such category: ", "Такой категории нет: ");
                put("Core not initialized", "Ядро не инициализировано");
            }
        };
        PHRASES = new HashMap<>(){
            {
                put("Choose category: ", "Выберите категорию: ");
                put("(Tap Enter to choose random category)", "(Нажмите Enter чтобы выбрать случайную категорию)");
                put("(Tap Enter to choose random level)", "(Нажмите Enter чтобы выбрать случайеую сложность)");
                put("Choose level: ", "Выберите сложность: ");
                put("Hi! You are in the game!", "Привет! Ты в игре Висилица!");
                put("To start the game type: start", "Чтобы начать игру введите: начать");
                put("To exit the game type: exit", "Чтобы выйти из игры введите: выход");
                put("Game ", "Игра ");
                put("Hint: ", "Подсказка: ");
                put("Category: ", "Категория: ");
                put("Level: ", "Сложность: ");
                put("You lose. The word was: ", "Вы проиграли. Слово было: ");
                put("You win!", "Вы выиграли!");
                put("You have ", "У вас есть ");
                put(" attempts.", " попыток.");
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
    public Level getLevelByString(String levelString) throws IllegalArgumentException {
        return switch (levelString.toLowerCase()) {
            case "легкий" -> Level.EASY;
            case "средний" -> Level.MEDIUM;
            case "сложный" -> Level.HARD;
            default -> throw new IllegalArgumentException(exception("Such level is not exist!"));
        };
    }

    @Override
    public String phrase(String string) {
        return PHRASES.get(string);
    }

}
