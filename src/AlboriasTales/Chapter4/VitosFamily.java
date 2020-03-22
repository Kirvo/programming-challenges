package AlboriasTales.Chapter4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class VitosFamily {
    public static void main(String [] args) {
        Scanner scan = new Scanner(System.in);
        int testCases = scan.nextInt();
        scan.nextLine();

        while (--testCases >= 0) {
            String line = scan.nextLine();
            int[] stNumbers = getStreetNumbers(line);

            System.out.println(getDistance(stNumbers));
        }
    }

    private static int getDistance(int[] stNumbers) {
        int distance = Integer.MAX_VALUE;
        for (int stNumber : stNumbers) {
            int dif = 0;

            for (int number : stNumbers) {
                dif += Math.abs(number - stNumber);
            }

            distance = Math.min(dif, distance);
        }

        return distance;
    }

    private static int[] getStreetNumbers(String line) {
        String[] numbers = line.trim().split(" ");
        int[] stNumbers = new int[Integer.parseInt(numbers[0])];


        for (int i = 1; i < numbers.length; i++) {
            stNumbers[i - 1] = Integer.parseInt(numbers[i]);
        }

        Arrays.sort(stNumbers);

        return stNumbers;
    }
}