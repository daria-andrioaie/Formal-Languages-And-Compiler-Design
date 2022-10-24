import java.util.Arrays;
import java.util.List;

public class LanguageSpecification {
    private final List<String> operators = Arrays.asList("+", "-", "*", "/", "=", "==", "<", "<=", ">", ">=", "!=");
    private final List<String> separators = Arrays.asList("{", "}", ":", "\n", "\t", " ", "[", "]", "(", ")");
    private final List<String> reservedWords = Arrays.asList("var", "let", "Int", "String", "char", "if", "else", "while", "input", "print");

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
        String identifierPattern = "^[a-zA-Z]([a-z|A-Z|\\d])*$";
        return token.matches(identifierPattern);
    }

    public boolean isConstant(String token) {
        String integerPattern = "^0|[+|-][1-9]([0-9])*|[1-9]([0-9])*$";
        String characterPattern = "^\'[a-zA-Z0-9_?!#*,./%-+=<>;)(}{ ]\'";
        String stringPattern = "^\"[a-zA-Z0-9_?!#*,./%-+=<>;)(}{ ]*\"";

        return token.matches(integerPattern) || token.matches(characterPattern) || token.matches(stringPattern);
    }
}
