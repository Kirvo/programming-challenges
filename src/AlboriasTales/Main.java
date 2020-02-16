package AlboriasTales;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        System.out.println("Hello world!");
        Scanner input = new Scanner(System.in);

        int cases = getCases(input);

        input.nextLine(); // getBlankLine

        for(int i = 0; i < cases; i++) {
            int numOfCandidates = getNumOfCandidates(input);
            String[] candidates = getCandidates(numOfCandidates, input);
            int[][] votes = getVotes(numOfCandidates, input);

            String[] winners = getWinner(votes, candidates);
        }
    }

    private static int getCases(Scanner input) {
        int cases = -1;

        while (cases == -1) {
            try {
                cases = input.nextInt();
            } catch(InputMismatchException e) {
                System.err.println("This is not a number, try again!");
                continue;
            }
        }

        return cases;
    }

    private static int getNumOfCandidates(Scanner input) {
        int numOfCandidates = -1;

        while(numOfCandidates > 20 || numOfCandidates == -1) {
            try {
                numOfCandidates = input.nextInt();

                if(numOfCandidates > 20) {
                    System.err.println("There can only be 20 or less candidates, try again!");
                    continue;
                }

            } catch(InputMismatchException e) {
                System.err.println("This is not a number, try again!");
            }
        }

        return numOfCandidates;
    }

    private static String[] getCandidates(int numOfCandidates, Scanner input) {
        String[] candidates = new String[numOfCandidates];
        int count = 0;

        while(count < numOfCandidates) {
            String name = "ERROR";

            while (name == "ERROR") {
                name = input.nextLine();
                if(name.length() == 0) name = "ERROR";

                if(name.length() > 80) {
                    System.err.println("Names must be less than 80 characters. Try again");
                    name = "ERROR";
                }

            }
            candidates[count++] = name;
        }

        return candidates;
    }

    /**
     * Falta verificar que los datos enviados son válidos!
     * @param input
     * @param numOfCandidates
     * @return
     */
    private static int[][] getVotes(int numOfCandidates, Scanner input) {
        int[][] votes = new int[1000][numOfCandidates];
        int cont = 0;
        String line = input.nextLine();

        while (!line.isEmpty()) {
            line = line.replaceAll("\\s+","");

            for(int i = 0; i < line.length(); i++) {
                votes[cont][i] = line.charAt(i);
            }

            cont++;
            line = input.nextLine();
        }

        return votes;
    }

    private static String[] getWinner(int[][] votes, String[] candidates) {
        String[] winners = new String[candidates.length];
        int[] voteCount;
        boolean goon = false;
        int cont = 0;

        while(goon) {
            voteCount = new int[candidates.length];

            for(int i = 0; i < votes.length; i++) {
               voteCount[votes[i][cont] - 1]++;
            }

            int indexOfWinner = -1; int max = 0; int min = candidates.length;
            for(int i = 0; i < voteCount.length; i++) {
                if(voteCount[i] >= max) { max = voteCount[i]; indexOfWinner = i; }
                if(voteCount[i] <= min) { min = voteCount[i]; }
            }

            if(max == min) { /* xd */ }
        }

        return winners;
    }
}
