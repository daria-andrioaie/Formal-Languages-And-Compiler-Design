import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        FiniteAutomaton finiteAutomaton = new FiniteAutomaton("C:\\Users\\daria\\Documents\\study\\facultate\\anul 3\\Semestrul 1\\LFTC\\Lab\\lab4\\src\\main\\java\\Utils\\FA.in");
        printMenu();

        Scanner reader = new Scanner(System.in);
        int option = reader.nextInt();

        while(option != 0) {
            switch (option) {
                case 1 -> runScanner();
                case 2 -> System.out.println(finiteAutomaton.FAElementToString("States"));
                case 3 -> System.out.println(finiteAutomaton.FAElementToString("Alphabet"));
                case 4 -> System.out.println(finiteAutomaton.FAElementToString("Initial State"));
                case 5 -> System.out.println(finiteAutomaton.FAElementToString("Transitions"));
                case 6 -> System.out.println(finiteAutomaton.FAElementToString("Final States"));
                case 7 -> {
                    System.out.println("\nIntroduce the sequence: ");
                    Scanner reader2 = new Scanner(System.in);
                    String sequence = reader2.nextLine();

                    if (finiteAutomaton.checkSequence(sequence)) {
                        System.out.println("The sequence is valid!");
                    } else {
                        System.out.println("Invalid sequence!");
                    }
                }
            }
            System.out.println("\n");
            printMenu();
            option = reader.nextInt();
        }
    }
    private static void printMenu() {
        System.out.println("1. Run the scanner;");
        System.out.println("2. Print the set of states;");
        System.out.println("3. Print the alphabet;");
        System.out.println("4. Print the initial state;");
        System.out.println("5. Print all the transitions;");
        System.out.println("6. Print the final states;");
        System.out.println("7. Check if sequence is accepted by the FA.");
        System.out.println("0. Exit.");
        System.out.println("\nChoose an option: ");
    }

    private static void runScanner() {
        CodeScanner scanner = new CodeScanner("C:\\Users\\daria\\Documents\\study\\facultate\\anul 3\\Semestrul 1\\LFTC\\Lab\\lab4\\src\\main\\java\\Utils\\P1.txt");
        scanner.scan();
    }
}
