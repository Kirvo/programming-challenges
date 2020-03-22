package AlboriasTales;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        int cases = getInt(input);
        String[] pages, names = null;
        HashMap<String, List<String>> graph;
        HashMap<String, Integer> erdosGraph = new HashMap<>();

        input.nextLine();

        for(int i = 0; i < cases; i++) {
            // 1. Recieve data from input
            String line = input.nextLine().trim(); // prevencion de errores
            String[] PN = line.split(" ");

            int pagesCount = Integer.parseInt(PN[0]), namesCount = Integer.parseInt(PN[1]);

            pages = getLines(pagesCount, input);
            names = getLines(namesCount, input);

            // 2. Manage data
            graph = getAuthorsGraph(pages, names);

            // 3. Run BFS over graph to get Erdos Numbers
            erdosGraph = new HashMap<>();
            erdos(erdosGraph, graph, "Erdos, P.", 0);
        }


        for(int i = 0; i < cases; i++) {
            System.out.println("Scenario " + (i + 1));

            for(String name: names) {
                if(erdosGraph.get(name) == null) { System.out.println(name + " infinity"); }
                else { System.out.println(name + " " + erdosGraph.get(name)); }
            }
        }
    }

    private static HashMap<String, List<String>> getAuthorsGraph(String[] pages, String[] names) {
        HashMap<String, List<String>> graph = new HashMap<>();
        graph.put("Erdos, P.", new LinkedList<>()); //Erdos is the first node

        List<String> pagesWithoutErdosNumber = new LinkedList<>();

        for(String page: pages) {
            //Obtenemos los autores de la página a revisar
            List<String> authorsInPage = getAuthorsInPage(page, names);

            // Actualizamos el grafo añadiendo nodos segun los co-autores de esa pagina
            if(!checkAuthorOnGraph(authorsInPage, graph)) {
                //Si no encontramos autores en la pagina que existan en el grafo,
                //nos guardamos la pagina para revisarla más tarde
                pagesWithoutErdosNumber.add(page);
            }
        }

        // Revisamos de nuevo las páginas anteriores
        for(String page: pagesWithoutErdosNumber) {
            List<String> authorsInPage = getAuthorsInPage(page, names);

            // Actualizamos el grafo añadiendo nodos segun los co-autores de esa pagina
            checkAuthorOnGraph(authorsInPage, graph);
        }

        return graph;
    }

    private static List<String> getAuthorsInPage(String page, String[] names) {
        List<String> authorsInPage = new LinkedList<>();
        for (String name : names) {
            if (page.contains(name)) { authorsInPage.add(name); }
            else if(page.contains("Erdos, P.")) { authorsInPage.add("Erdos, P."); }
        }
        return authorsInPage;
    }

    private static boolean checkAuthorOnGraph(List<String> authors, HashMap<String, List<String>> graph) {
        List<String> authorsOnGraph = new LinkedList<>(), authorsNotOnGraph = new LinkedList<>();

        for(String author: authors) {
            if(graph.containsKey(author)) authorsOnGraph.add(author);
            else authorsNotOnGraph.add(author);
        }

        if(!authorsOnGraph.isEmpty()) {
            authorsNotOnGraph.forEach(author -> {
                // Añadimos un nuevo nodo al grafo
                graph.put(author, new LinkedList<>());
                // Actualizamos la lista de cada nodo co-autor
                authorsOnGraph.forEach(authorOnGraph -> {
                    graph.get(authorOnGraph).add(author);
                });
            });

            return true;
        } else {
            return false;
        }
    }

    private static void erdos(HashMap<String, Integer> erdosGraph, HashMap<String, List<String>> graph, String name, int erdosNum) {
        erdosGraph.put(name, erdosNum);

        List<String> coAuthors = graph.get(name);
        coAuthors.forEach(coAuthor -> {
            erdos(erdosGraph, graph, coAuthor, erdosNum + 1);
        });
    }

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
