package backend.academy.Words.datasource;

import backend.academy.Words.Category;
import backend.academy.Words.Level;
import backend.academy.Words.Word;
import java.util.HashMap;
import java.util.Map;

public class StaticWordsTestVariables {

    protected final String PATH1 = "src/test/java/backend/academy/Words/datasource/testData1";
    protected final String PATH2 = "src/test/java/backend/academy/Words/datasource/testData2";
    protected final String PATH3 = "src/test/java/backend/academy/Words/datasource/testData3";

    protected final Category ANIMAL_CATEGORY_EMPTY = new Category("животные", new HashMap<>(Map.of(
        Level.EASY, new HashMap<>(),
        Level.MEDIUM, new HashMap<>(),
        Level.HARD, new HashMap<>()
    )));

    protected final Category ANIMAL_CATEGORY = new Category("животные", new HashMap<>(Map.of(
        Level.MEDIUM, new HashMap<>(Map.of(
            "кенгуру" , new Word("кенгуру", "прыгает на задних лапах и носит детёныша в сумке"),
            "медведь", new Word("медведь", "любит мёд и зимует в спячке")
        )),
        Level.EASY, new HashMap<>(Map.of(
            "кот", new Word("кот", "мурлычет и ловит мышей"),
            "собака", new Word("собака", "друг человека")
        )),
        Level.HARD, new HashMap<>(Map.of(
            "ехидна", new Word("ехидна", "откладывающий яйца колючий зверёк")
        )))
    ));

    protected final Category FOOD_CATEGORY = new Category("еда", new HashMap<>(Map.of(
        Level.EASY, new HashMap<>(Map.of(
            "суп", new Word("суп", "жидкое первое блюдо"),
            "яблоко", new Word("яблоко", "круглый фрукт с косточками")
        )),
        Level.MEDIUM, new HashMap<>(Map.of(
            "пицца", new Word("пицца", "итальянское блюдо с начинкой и тестом")
        )),
        Level.HARD, new HashMap<>(Map.of(
            "эклер", new Word("эклер", "французская выпечка с кремом")
        )))
    ));

    protected final Map<String, Category> EXPECTED1 = Map.of(
        "животные", ANIMAL_CATEGORY_EMPTY
    );

    protected final Map<String, Category> EXPECTED2 = new HashMap<>(Map.of(
        "животные", ANIMAL_CATEGORY,
        "еда", FOOD_CATEGORY
    ));

}
