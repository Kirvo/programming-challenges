package AlboriasTales.Chapter2;

import java.util.*;

import AlboriasTales.libs.util;

public class ErdosNumbers {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        int cases = util.getInt(input);
        String[] pages, names = null;
        Map<String, String> authorsByErdos = null;

        for(int i = 0; i < cases; i++) {
            /* 1. Recieve data from input */
            String line = input.nextLine().trim(); // prevencion de errores
            String[] PN = line.split(" ");
            int pagesCount = Integer.parseInt(PN[0]), namesCount = Integer.parseInt(PN[0]);

            pages = util.getLines(pagesCount, input);
            names = util.getLines(namesCount, input);

            /* 2. Manage data */
            authorsByErdos = getAuthorsByErdos(pages, names, namesCount);
        }

        for(int i = 0; i < cases; i++) {
            util.log("Scenario " + i);
            for(String name: names) util.log(name + " " + authorsByErdos.get(name));
        }
    }

    private static Map<String, String> getAuthorsByErdos(String[] pages, String[] names, int namesCount) {
        Map<String, String> authorsByErdos;
        authorsByErdos = new HashMap<>();
        for(String name: names) authorsByErdos.put(name, "infinity");

        for(String page: pages) {
            ArrayList<String> namesInPage = new ArrayList<>();

            for(String name: names) {
                if(page.contains(name)) { namesInPage.add(name); }
            }

            for(String name: namesInPage) {
                if(page.contains("Erdos, P.")) {
                    authorsByErdos.replace(name, "1");
                } else {
                    String coAuthor = "";
                    int maxValue = namesCount; // El máximo sera el 1, siguiente el 2, etc...

                    for(String name2: namesInPage) {
                        String aux = authorsByErdos.get(name2);
                        if(aux != null && Integer.parseInt(aux) < maxValue) {
                            maxValue = Integer.parseInt(aux);
                            coAuthor = name2;
                        }
                    }

                    if(maxValue < namesCount) {
                        String erdosValue = (Integer.parseInt(authorsByErdos.get(coAuthor)) + 1) + "";
                        authorsByErdos.replace(name, erdosValue);
                    }
                }
            }
        }
        return authorsByErdos;
    }
}
