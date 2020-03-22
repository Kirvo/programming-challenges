package AlboriasTales.libs;

import java.util.InputMismatchException;
import java.util.Scanner;

public class util {
    public static void log(String line) {
        System.out.println(line);
    }

    public static String[] getLines(int linesCount, Scanner input) {
        String[] lines = new String[linesCount];

        for(int i = 0; i < linesCount; i++) {
            lines[i] = input.nextLine();
        }

        return lines;
    }

    public static int getInt(Scanner input) {
        int cases = -1;

        while (cases == -1) {
            try {
                cases = input.nextInt();
            } catch(InputMismatchException e) {
                System.err.println("This is not a number, try again!");
            }
        }

        return cases;
    }
}
