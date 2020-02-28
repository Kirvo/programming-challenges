package AlboriasTales;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        int cases = getCases(input);

        input.nextLine(); // getBlankLine

        for(int i = 0; i < cases; i++) {
            int numOfCandidates = getNumOfCandidates(input);
            String[] candidates = getCandidates(numOfCandidates, input);
            ArrayList<int[]> votes = getVotes(numOfCandidates, input);

            ArrayList<String> winners = getWinner(votes, candidates);
            winners.forEach(System.out::println);
            System.out.println();
        }
    }

    private static int getCases(Scanner input) {
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

    private static int getNumOfCandidates(Scanner input) {
        int numOfCandidates = -1;

        while(numOfCandidates > 20 || numOfCandidates == -1) {
            try {
                numOfCandidates = input.nextInt();

                if(numOfCandidates > 20) {
                    System.err.println("There can only be 20 or less candidates, try again!");
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

            while (name.equals("ERROR")) {
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

    private static ArrayList<int[]> getVotes(int numOfCandidates, Scanner input) {
        ArrayList<int[]> votes = new ArrayList<>();
        String line = input.nextLine();

        while (!line.isEmpty()) {
            int[] newVotes = new int[numOfCandidates];
            line = line.trim(); // prevencion de errores
            String[] numbers = line.split(" ");

            for(int i = 0; i < numOfCandidates; i++) {
                newVotes[i] = Integer.parseInt(numbers[i]);
                if(newVotes[i] == -1) { System.err.println("ESTO NO ES UN NUMERO"); break; }
            }

            votes.add(newVotes); 
            line = input.nextLine();

            if(votes.size() >= 1000) { break; }
        }

        return votes;
    }

    private static ArrayList<String> getWinner(ArrayList<int[]> votes, String[] candidates) {
        ArrayList<String> winners = new ArrayList<>();
        ArrayList<Integer> eliminated = new ArrayList<>();
        int[] votesOfCandidate;

        boolean goon = true;
        int cont = 0;

        while(goon) {
            votesOfCandidate = new int[candidates.length];

            for(int[] vote : votes) {
                // (EJ) vote: 3 2 1 4 | cont: 0 | vote[0] = 3
                // canditato 3, posicion = 3 - 1 = 2 --> candidates[2] == candidato 3
                votesOfCandidate[vote[cont] - 1]++;
            }

            int max = 0, min = candidates.length;
            for(int i = 0; i < votesOfCandidate.length; i++) {
                // Si el candidato se ha eliminado, resetamos su contador de votos a 0
                if(eliminated.contains(i)) {
                    votesOfCandidate[i] = 0; continue;
                }

                if(votesOfCandidate[i] >= max) { max = votesOfCandidate[i]; }
                if(votesOfCandidate[i] <= min) { min = votesOfCandidate[i]; }
            }

            int numOfValidVotes = Arrays.stream(votesOfCandidate).sum();

            if(max * 2 > numOfValidVotes || min == max) {
                for(int i = 0; i < votesOfCandidate.length; i++) {
                    if(votesOfCandidate[i] == max) { winners.add(candidates[i]); }
                }

                goon = false;
            } else {
                for(int i = 0; i < votesOfCandidate.length; i++) {
                    if(votesOfCandidate[i] == min) { eliminated.add(i); }
                }

                cont++;
            }

            StringBuilder vot = new StringBuilder();
            for(int v: votesOfCandidate) vot.append(v).append(" ");

           // log("Votos por candidato: " + vot);
           // log("max: " + max);
           // log("min: " + min);
        }

        return winners;
    }


    public static void log(String line) {
        System.out.println(line);
    }
}
