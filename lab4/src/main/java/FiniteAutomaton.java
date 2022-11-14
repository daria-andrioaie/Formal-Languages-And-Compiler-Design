import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class FiniteAutomaton {
    public Set<String> alphabet, states, finalStates;
    public String initialState;
    public Map<Pair<String, String>, Set<String>> transitions;

    public FiniteAutomaton(String filename) {
        this.states = new HashSet<>();
        this.alphabet = new HashSet<>();
        this.finalStates = new HashSet<>();
        this.transitions = new HashMap<>();

        readFiniteAutomaton(filename);
    }

    private void readFiniteAutomaton(String filename) {
        try {
            File file = new File(filename);
            Scanner reader = new Scanner(file);

            String statesLine = reader.nextLine();
            states = new HashSet<>(Arrays.asList(statesLine.split(" ")));

            String alphabetLine = reader.nextLine();
            alphabet = new HashSet<>(Arrays.asList(alphabetLine.split(" ")));

            initialState = reader.nextLine();

            String finalStatesLine = reader.nextLine();
            finalStates = new HashSet<>(Arrays.asList(finalStatesLine.split(" ")));

            while(reader.hasNextLine()) {
                String transitionLine = reader.nextLine();
                String[] transitionElements = transitionLine.split(" ");

                if(states.contains(transitionElements[0]) && states.contains(transitionElements[2]) && alphabet.contains(transitionElements[1])) {
                    Pair<String, String> transitionState = new Pair<>(transitionElements[0], transitionElements[1]);

                    if (!transitions.containsKey(transitionState)) {
                        Set<String> transitionStatesSet = new HashSet<>();
                        transitionStatesSet.add(transitionElements[2]);
                        transitions.put(transitionState, transitionStatesSet);
                    } else {
                        transitions.get(transitionState).add(transitionElements[2]);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public boolean checkDFA() {
        return transitions.values().stream().noneMatch(list -> list.size() > 1);
    }

    public boolean checkSequence(String sequence) {
        if(sequence.length() == 0) {
            return finalStates.contains(initialState);
        }

        String state = initialState;
        for(int i = 0; i<sequence.length(); i++) {
            Pair<String, String> key = new Pair<>(state, String.valueOf(sequence.charAt(i)));
            boolean match = false;
            for(Pair<String, String> pairKey: transitions.keySet()) {
                if(pairKey.key.equals(key.key) && pairKey.value.equals(key.value)) {
                    match = true;
                    state = transitions.get(pairKey).iterator().next();
                    break;
                }
            }
            if(!match) {
                return false;
            }
        }
        return finalStates.contains(state);
    }

    public String FAElementToString(String element) {
        if(Objects.equals(element, "Transitions")) {
            StringBuilder builder = new StringBuilder();
            builder.append("Transitions: \n");
            transitions.forEach((K, V) -> {
                builder.append("<").append(K.key).append(",").append(K.value).append("> -> ").append(V).append("\n");
            });

            return builder.toString();
        } else if(Objects.equals(element, "Initial State")) {
            return "Initial State: " + initialState;
        }
        else {
            Set<String> elementSet = switch (element) {
                case "Alphabet" -> alphabet;
                case "States" -> states;
                case "Final States" -> finalStates;
                default -> new HashSet<>();
            };

            StringBuilder builder = new StringBuilder();

            builder.append(element).append(": ");
            for (String a : elementSet){
                builder.append(a).append(" ");
            }

            return builder.toString();
        }
    }
}
