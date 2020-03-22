package AlboriasTales.Chapter3;

import java.io.*;
import java.util.*;

class CommonPermutation {
    public static void main(String [] args) {
        Scanner scan = new Scanner(System.in);
        String input = scan.nextLine();

        while (input != null) {
            int [] a = new int[26], b = new int[26];

            for (char c : input.toCharArray()) { a[c - 'a']++; }
            for (char c : scan.nextLine().toCharArray()) { b[c - 'a']++; }

            StringBuilder res = new StringBuilder();
            for (int i = 0; i < 26; i++) {
                while (a[i] > 0 && b[i] > 0) {
                    res.append((char) ('a' + i));
                    a[i]--;
                    b[i]--;
                }
            }

            System.out.println();
            System.out.println(res + "");
            input = scan.nextLine();
        }
    }
}