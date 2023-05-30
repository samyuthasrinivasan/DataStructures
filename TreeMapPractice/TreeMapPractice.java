import java.io.FileReader;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;
import java.util.TreeMap;

public class TreeMapPractice {
    public TreeMapPractice(){
        ArrayList<Show> shows = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(new FileReader("Broadway2022.csv"));
            scanner.useDelimiter("\n");
            scanner.nextLine();
            while(scanner.hasNext()) {
                String string = scanner.next();
                String date = string.substring(0, string.indexOf(","));
                string = string.substring(string.indexOf(",") + 1);
                String name = string.substring(0, string.indexOf(","));
                string = string.substring(string.indexOf(",") + 1);
                String type = string.substring(0, string.indexOf(","));
                string = string.substring(string.indexOf(",") + 1);
                String theatre = string.substring(0, string.indexOf(","));
                string = string.substring(string.indexOf(",") + 1);
                int gross = Integer.parseInt(string.substring(0, string.indexOf(",")));
                string = string.substring(string.indexOf(",") + 1);
                int attendance = Integer.parseInt(string.substring(0, string.indexOf(",")));
                string = string.substring(string.indexOf(",") + 1);
                float cap = Float.parseFloat(string.substring(0));
                shows.add(new Show(date, name, type, theatre, gross, attendance, cap));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        showByTheatre(shows);
        
    }

    public void grossByMonth(ArrayList<Show> shows) {
        TreeMap<Integer, String> month = new TreeMap<Integer, String>();
        NumberFormat nf = NumberFormat.getInstance(Locale.US);
        for(Show s: shows) {
            month.put(Integer.parseInt(s.date.substring(0, s.date.indexOf("/"))), "$" + nf.format(s.gross));
        }

        System.out.println("Gross Earnings by Month:");
        for(int i: month.keySet()) {
            System.out.println(i + ": " + month.get(i));
        }
    }

    public void grossByType(ArrayList<Show> shows) {
        TreeMap<String, String> type = new TreeMap<String, String>();
        NumberFormat nf = NumberFormat.getInstance(Locale.US);
        for(Show s: shows) {
            type.put(s.type, "$" + nf.format(s.gross));
        }
        for(String s: type.keySet()) {
            System.out.println(s + ": " + type.get(s));
        }
    }

    public void attendanceByType(ArrayList<Show> shows) {
        TreeMap<String, Integer> type = new TreeMap<String, Integer>();
        for(Show s: shows) {
            type.put(s.type, s.attendance);
        }
        for(String s: type.keySet()) {
            System.out.format(s + ": %,d%n",  type.get(s));
        }
    }

    public void grossByShowPerWeek(ArrayList<Show> shows) {
        TreeMap<String, ArrayList<Integer>> g = new TreeMap<String, ArrayList<Integer>>();
        NumberFormat nf = NumberFormat.getInstance(Locale.US);

        for(Show s: shows) {
            String nameOfShow = s.name;
            if(!g.containsKey(nameOfShow)) {
                g.put(nameOfShow,new ArrayList<Integer>());
            } 
            g.get(nameOfShow).add(s.gross);
        }

        System.out.println("Gross Earnings Per Week by Show");
        TreeMap<String, String> grossIncomes = new TreeMap<String, String>();
        for(String s: g.keySet()) {
            System.out.println(s + ":");
            ArrayList<Integer> temp = g.get(s);
            int sum = 0;
            for(int i: temp) {
                System.out.format("%5s%s%n", "$", nf.format(i));
                sum += i;
            }
            grossIncomes.put(s, "$" + nf.format(sum));
        }

        System.out.println("Total Gross Earnings by Show");
        for(String s: grossIncomes.keySet()) {
            System.out.println(s + ": " + grossIncomes.get(s));
        }
    }

    public void attendanceByShowPerWeek(ArrayList<Show> shows) {
        TreeMap<String, ArrayList<Integer>> a = new TreeMap<String, ArrayList<Integer>>();

        for(Show s: shows) {
            String nameOfShow = s.name;
            if(!a.containsKey(nameOfShow)) {
                a.put(nameOfShow,new ArrayList<Integer>());
            } 
            a.get(nameOfShow).add(s.attendance);
        }

        System.out.println("Attendance Per Week by Show");
        int sum = 0;
        for(String s: a.keySet()) {
            System.out.println(s + ":");
            ArrayList<Integer> temp = a.get(s);
            for(int i: temp) {
                System.out.format("%,8d%n", i);
                sum += i;
            }
        }

        TreeMap<String, Integer> attendances = new TreeMap<String, Integer>();
        for(String s: a.keySet()) {
            attendances.put(s, sum);
        }

        System.out.println("Total Attendance by Show");
        for(String s: attendances.keySet()) {
            System.out.print(s + ": ");
            System.out.format("%,d\n", attendances.get(s));
        }
    }
    
    public void datesByShow(ArrayList<Show> shows) {
        TreeMap<String, ArrayList<String>> dates = new TreeMap<String, ArrayList<String>>();
        System.out.println("Dates Show is Run");
        for(Show s: shows) {
            String nameOfShow = s.name;
            if(!dates.containsKey(nameOfShow)) {
                dates.put(nameOfShow,new ArrayList<String>());
            } 
            dates.get(nameOfShow).add(s.date);
        }

        for(String s: dates.keySet()) {
            System.out.println(s + ":");
            ArrayList<String> temp = dates.get(s);
            for(String i: temp) {
                System.out.println(i);
            }
        }

        TreeMap<String, ArrayList<String>> ranges = new TreeMap<String, ArrayList<String>>();
        for(String s: dates.keySet()) {
            ranges.put(s, new ArrayList<String>());
            ranges.get(s).add(dates.get(s).get(0));
            ranges.get(s).add(dates.get(s).get(dates.get(s).size() - 1));
        }

        System.out.println("Weeks Run");
        for(String s: ranges.keySet()) {
            System.out.print(s + ": ");
            System.out.println(ranges.get(s).get(0) + " - " + ranges.get(s).get(1));
        }
    }

    public void capByShowPerWeek(ArrayList<Show> shows) {
        TreeMap<String, ArrayList<Float>> caps = new TreeMap<String, ArrayList<Float>>();
        System.out.println("Caps Per Show");
        for(Show s: shows) {
            String nameOfShow = s.name;
            if(!caps.containsKey(nameOfShow)) {
                caps.put(nameOfShow,new ArrayList<Float>());
            } 
            caps.get(nameOfShow).add(s.cap);
        }

        for(String s: caps.keySet()) {
            System.out.println(s + ":");
            ArrayList<Float> temp = caps.get(s);
            for(float i: temp) {
                System.out.println(i);
            }
        }

        TreeMap<String, ArrayList<Float>> ranges = new TreeMap<String, ArrayList<Float>>();
        for(String s: caps.keySet()) {
            ranges.put(s, new ArrayList<Float>());
            float min = 1f;
            float max = 0f;

            ArrayList<Float> temp = caps.get(s);
            for(float f: temp) {
                if(f > max) {
                    max = f;
                } 

                if(f < min) {
                    min = f;
                }
            }
            ranges.get(s).add(min);
            ranges.get(s).add(max);
        }

        System.out.println("Caps Ranges For Each Show");
        for(String s: ranges.keySet()) {
            System.out.print(s + ": ");
            System.out.println(ranges.get(s).get(0) + " - " + ranges.get(s).get(1));
        }
    }

    public void showByTheatre(ArrayList<Show> shows) {
        TreeMap<String, String> theatres = new TreeMap<String, String>();
        for(Show s: shows) {
            String nameOfShow = s.name;
            if(!theatres.containsKey(nameOfShow)) 
            theatres.put(nameOfShow, s.theatre);
        }

        System.out.println("Shows by Theatre");
        for(String s: theatres.keySet()) {
            System.out.println(s + ": " + theatres.get(s));
        }
    }

    public class Show {
        String date, name, type, theatre;
        int gross, attendance;
        float cap;
        public Show(String date, String name, String type, String theatre, int gross, int attendance, float cap) {
            this.date = date;
            this.name = name;
            this.type = type;
            this.theatre = theatre;
            this.gross = gross;
            this.attendance = attendance;
            this.cap = cap;
        }

        public String toString(){
            return date + " " + name + " " + type + " " + theatre + " " + gross + " " + attendance + " " + cap;
        }
    }
    
    public static void main(String[]args) {
        TreeMapPractice app = new TreeMapPractice();
    }
}