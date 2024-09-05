package Words.datasource;

import backend.academy.Words.Category;
import backend.academy.Words.Level;
import backend.academy.Words.Word;
import java.util.HashMap;
import java.util.Map;

public class StaticWordsTestVariables {

    protected final String PATH1 = "src/test/java/Words/datasource/testData1";
    protected final String PATH2 = "src/test/java/Words/datasource/testData2";
    protected final String PATH3 = "src/test/java/Words/datasource/testData3";

    protected final Category ANIMAL_CATEGORY_EMPTY = new Category("животные", Map.of(
        Level.EASY, new HashMap<>(),
        Level.MEDIUM, new HashMap<>(),
        Level.HARD, new HashMap<>()
    ));

    protected final Category ANIMAL_CATEGORY = new Category("животные", Map.of(
        Level.EASY, Map.of(
            "собака", new Word("собака", "друг человека"),
            "кошка", new Word("кошка", "мяучит")
        ),
        Level.MEDIUM, new HashMap<>(),
        Level.HARD, new HashMap<>()
    ));

    protected final Category FOOD_CATEGORY = new Category("еда", Map.of(
        Level.EASY, new HashMap<>(),
        Level.MEDIUM, new HashMap<>(),
        Level.HARD, new HashMap<>()
    ));

    protected final Map<String, Category> EXPECTED1 = Map.of(
        "животные", ANIMAL_CATEGORY_EMPTY
    );

    protected final Map<String, Category> EXPECTED2 = Map.of(
        "животные", ANIMAL_CATEGORY,
        "еда", FOOD_CATEGORY
    );

}
