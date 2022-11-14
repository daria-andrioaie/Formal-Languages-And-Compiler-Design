import java.util.ArrayList;
import java.util.List;

public class ProgramInternalForm {
    private List<Pair<String, Pair<Integer, Integer>>> pif = new ArrayList<>();

    public void add(String token, Pair<Integer, Integer> codes) {
        Pair<String, Pair<Integer, Integer>> pair = new Pair<>(token, codes);
        pif.add(pair);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for(Pair<String, Pair<Integer, Integer>> pair : pif) {
            result.append(pair.key).append(" ---> (").append(pair.value.key).append(", ").append(pair.value.value).append(")\n");
        }
        return result.toString();
    }
}
