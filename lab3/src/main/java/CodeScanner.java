import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CodeScanner {
    private final String programFilePath;
    private LanguageSpecification language;
    private final ConstantsSymbolTable constantsST;
    private final IdentifiersSymbolTable identifiersST;

    public CodeScanner(String filepath) {
        this.programFilePath = filepath;
        this.language = new LanguageSpecification();
        this.constantsST = new ConstantsSymbolTable();
        this.identifiersST = new IdentifiersSymbolTable();
    }

    public void scan() {
        List<Pair<String, Integer>> tokensWithLineNumbers = new ArrayList<>();
        try {
            File sourceFile = new File(programFilePath);
            Scanner fileScanner = new Scanner(sourceFile);

            int lineNumber = 1;
            while(fileScanner.hasNextLine()) {
                String currentLine = fileScanner.nextLine();
                List<String> tokensOnCurrentLine = tokenize(currentLine);
                for(String token : tokensOnCurrentLine) {
                    tokensWithLineNumbers.add(new Pair<>(token, lineNumber));
                }
                lineNumber += 1;
            }

            fileScanner.close();
            buildSymbolTable(tokensWithLineNumbers);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<String> tokenize(String line) {
        ArrayList<String> tokens = new ArrayList<>();

        for(int i = 0; i < line.length(); i++) {
            char currentCharacter = line.charAt(i);

            if(language.isSeparator(String.valueOf(currentCharacter)) && !(String.valueOf(currentCharacter)).equals(" ")) {
                // separator different from space
                tokens.add(String.valueOf(currentCharacter));
            } else if (currentCharacter == '\"') {
                // STRING
                String stringConstant = identifyStringConstant(line, i);
                tokens.add(stringConstant);
                i += stringConstant.length() - 1;
            } else if(language.isPartOfOperator(line.charAt(i)))  {
                // OPERATOR
                String operator = identifyOperator(line, i);
                tokens.add(operator);
                i += operator.length() - 1;
            } else if (line.charAt(i) != ' ') {
                // ANOTHER TOKEN
                String otherToken = identifyToken(line, i);
                tokens.add(otherToken);
                i += otherToken.length() - 1;
            }
        }
        return tokens;
    }

    public String identifyStringConstant(String line, int index) {
        StringBuilder constant = new StringBuilder();
        for(int i = index; i<line.length(); i++) {
            char currentCharacter = line.charAt(i);
            // example: if (a == b)
            if((language.isSeparator(String.valueOf(currentCharacter)) || language.isOperator(String.valueOf(currentCharacter))) && ((i == line.length() - 2 && line.charAt(i + 1) != '\"') || (i == line.length() - 1))) {
                break;
            }
            constant.append(currentCharacter);
            if(currentCharacter == '\"' && i != index) {
                break;
            }
        }
        return constant.toString();
    }

    public String identifyOperator(String line, int index) {
        StringBuilder operator = new StringBuilder();
        operator.append(line.charAt(index));
        operator.append(line.charAt(index + 1));
        if(language.isOperator(operator.toString()))
            return operator.toString();
        return String.valueOf(line.charAt(index));
    }

    public String identifyToken(String line, int index) {
        StringBuilder token = new StringBuilder();
        for(int i = index; i < line.length() && !language.isSeparator(String.valueOf(line.charAt(i)))
                && !language.isPartOfOperator(line.charAt(i))
                && line.charAt(i) != ' '; i++ ) {
            token.append(line.charAt(i));
        }

        return token.toString();
    }

    private void buildSymbolTable(List<Pair<String, Integer>> tokensWithLineNumbers) {
        boolean isLexicallyCorrect = true;
        for(Pair<String, Integer> tokenPair: tokensWithLineNumbers) {
            String token = tokenPair.getKey();

            if(language.isReservedWord(token) || language.isSeparator(token) || language.isOperator(token)) {
                continue;
            }
            if(language.isIdentifier(token)) {
                this.identifiersST.add(token);
            } else if(language.isConstant(token)) {
                this.constantsST.add(token);
            } else {
                System.out.println("Error on line " + tokenPair.getValue() + ": invalid token " + token);
                isLexicallyCorrect = false;
                break;
            }
        }

        if(isLexicallyCorrect) {
            System.out.println("Program is lexically correct.");
        }

        System.out.println();
        System.out.println("------------------ IDENTIFIERS --------------------");
        System.out.println(identifiersST.sorted());
        System.out.println();
        System.out.println("------------------ CONSTANTS --------------------");
        System.out.println(constantsST.sorted());

    }
}
