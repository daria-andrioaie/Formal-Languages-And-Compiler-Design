import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class LanguageSpecification {
    private final List<String> operators = Arrays.asList("+", "-", "*", "/", "%", "=", "==", "<", "<=", ">", ">=", "!=");
    private final List<String> separators = Arrays.asList("{", "}", ":", "\n", "\t", " ", "[", "]", "(", ")");
    private final List<String> reservedWords = Arrays.asList("var", "let", "Int", "String", "char", "if", "else", "while", "break", "input", "print");
    private final HashMap<String, Integer> codes = new HashMap<>();

    private final FiniteAutomaton identifierFA = new FiniteAutomaton("C:\\Users\\daria\\Documents\\study\\facultate\\anul 3\\Semestrul 1\\LFTC\\Lab\\lab4\\src\\main\\java\\Utils\\identifierFA.in");
    private final FiniteAutomaton integerConstantFA = new FiniteAutomaton("C:\\Users\\daria\\Documents\\study\\facultate\\anul 3\\Semestrul 1\\LFTC\\Lab\\lab4\\src\\main\\java\\Utils\\integerConstantFA.in");
    public LanguageSpecification() {
        createCodes();
    }

    private void createCodes() {
        codes.put("identifier", 0);
        codes.put("constant", 1);

        int nextCode = 2;

        for(String reservedWord: reservedWords) {
            codes.put(reservedWord, nextCode++);
        }
        for(String separator: separators) {
            codes.put(separator, nextCode++);
        }
        for(String operator: operators) {
            codes.put(operator, nextCode++);
        }
    }

    public Integer getCode(String token) {
        return codes.get(token);
    }

    public boolean isOperator(String token) {
        return operators.contains(token);
    }

    public boolean isPartOfOperator(char op) {
        // the case when we encounter "!="
        return op == '!' || isOperator(String.valueOf(op));
    }

    public boolean isSeparator(String token) {
        return separators.contains(token);
    }

    public boolean isReservedWord(String token) {
        return reservedWords.contains(token);
    }

    public boolean isIdentifier(String token) {
//        String identifierPattern = "^[a-zA-Z]([a-z|A-Z|\\d])*$";
//        return token.matches(identifierPattern);
        return identifierFA.checkSequence(token);
    }

    public boolean isConstant(String token) {
//        String integerPattern = "^0|[+|-][1-9]([0-9])*|[1-9]([0-9])*$";
        String characterPattern = "^\'[a-zA-Z0-9_?!#*,./%-+=<>;)(}{ ]\'";
        String stringPattern = "^\"[a-zA-Z0-9_?!#*,./%-+=<>;)(}{ ]*\"";

        return integerConstantFA.checkSequence(token) || token.matches(characterPattern) || token.matches(stringPattern);
    }
}
