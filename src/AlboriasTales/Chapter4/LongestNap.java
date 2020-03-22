package AlboriasTales.Chapter4;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class LongestNap {
    public static void main(String [] args) throws Exception {
        int dayCounter = 1;
        DateFormat sdf = new SimpleDateFormat("HH:mm");
        ArrayList<String> responses = new ArrayList<>();

        Scanner scan = new Scanner(System.in);
        while(scan.hasNext()) {
            int numOfLines = Integer.parseInt(scan.nextLine());
            Map<Long, Long> dates = new TreeMap<>();

            // Obtenemos listado de registros
            while (--numOfLines >= 0) {
                String appointment = scan.nextLine().trim();
                String[] times = appointment.split(" ", 3);

                dates.put(sdf.parse(times[0]).getTime(), sdf.parse(times[1]).getTime());
            }

            dates.forEach((key, value) -> {
                int hoursStart = (int) ((key / 60000) / 60) + 1, minutesStart = (int) ((key / 60000) % 60);
                int hoursEnd = (int) ((value / 60000) / 60) + 1, minutesEnd = (int) ((value / 60000) % 60);

                System.out.println(hoursStart + ":" + minutesStart + "  " + hoursEnd + ":" + minutesEnd);
            });

            long nap = 1; int cont = 0;
            Date startTime = sdf.parse("10:00"), time = startTime;

            // Sacamos el resultado
            for(long A : dates.keySet()) {
                long calc = A - startTime.getTime();

                if(A > 0 && Math.max(calc, nap) == calc) {
                    nap = calc;
                    time = startTime;
                }

                startTime = new Date(dates.get(A));

                if(++cont == dates.keySet().size()) {
                    calc = sdf.parse("18:00").getTime() - startTime.getTime();
                    if(Math.max(calc, nap) == calc) {
                        nap = calc;
                        time = startTime;
                    }
                }
            }

            responses.add(getResponse(dayCounter++, time.getTime() / 60000, nap / 60000));
        }

        responses.forEach(System.out::println);
    }

    private static String getResponse(int day, long time, long nap) {
        String res = "Day #" + day + ": the longest nap starts at ";

        int hours = (int) (time / 60) + 1; if(hours <= 9) { res += "0"; } res += hours + ":" ;
        int minutes = (int) (time % 60); if(minutes <= 9) { res += "0"; } res += minutes;

        res += " and will last for ";

        hours = (int) (nap / 60);
        minutes = (int) (nap % 60);

        if(hours > 0) { res += hours + " hours and "; } res += minutes + " minutes.";

        return res;
    }
}
