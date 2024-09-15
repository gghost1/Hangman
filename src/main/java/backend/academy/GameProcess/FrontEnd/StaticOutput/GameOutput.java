package backend.academy.GameProcess.FrontEnd.StaticOutput;

import it.unimi.dsi.fastutil.Pair;
import java.util.List;
import java.util.Set;

public interface GameOutput {
    String game();
    String hint();
    List<String> initImage();
    Set<String> alphabet();
    List<Pair<Integer, String>> replacements();
    String lose();
    String win();
}
