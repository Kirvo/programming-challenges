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
                long key = sdf.parse(times[0]).getTime(), value = sdf.parse(times[1]).getTime();

                // Si tenemos dos citas que empiezan a la misma hora, tiene preferencia la que dure más
                if(dates.containsKey(key)) {
                    value = Math.max(value, dates.get(key));
                }

                dates.put(key, value);
            }

            dates.put(sdf.parse("18:00").getTime(), sdf.parse("18:00").getTime());

            long nap = 0, napStartTime = sdf.parse("10:00").getTime(), maxValue = napStartTime;

            // Sacamos el resultado
            for(long key : dates.keySet()) {
                if(maxValue <= key) {
                    long tempNap = key - maxValue, lastNap = nap;
                    nap = Math.max(tempNap, lastNap);

                    // Si el valor máximo no ha cambiado, predomina el primer valor cuando guardamos la hora de inicio de la siesta
                    if(nap != lastNap) { napStartTime = maxValue; }
                }

                maxValue = Math.max(maxValue, dates.get(key));
            }

            responses.add(getResponse(dayCounter++, napStartTime / 60000, nap / 60000));
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

    private static String getDateFrom(long time) {
        if(time > 60000) time = time / 60000;

        String res = "";
        int hours = (int) (time / 60) + 1; if(hours <= 9) { res += "0"; } res += hours + ":";
        int minutes = (int) (time % 60); if(minutes <= 9) { res += "0"; } res += minutes;
        return res;
    }
}
