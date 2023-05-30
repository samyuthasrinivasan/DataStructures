import java.io.BufferedReader;
import java.io.FileReader;

public class HashMaps {
    public HashMaps(){
        try {
            BufferedReader reader = new BufferedReader(new FileReader("Broadway2022.csv"));
            title = reader.readLine();
            tt = title.split("\t");
            String temp;
            String[] t;
            while ((temp = reader.readLine()) != null) {
                t = temp.split("\t");
                stack.add(new Car(Integer.parseInt(t[0]), Integer.parseInt(t[1]), Integer.parseInt(t[2]), Integer.parseInt(t[3]), Integer.parseInt(t[4]), Integer.parseInt(t[5]), Integer.parseInt(t[6]), Integer.parseInt(t[7])));
                q.add(new Car(Integer.parseInt(t[0]), Integer.parseInt(t[1]), Integer.parseInt(t[2]), Integer.parseInt(t[3]), Integer.parseInt(t[4]), Integer.parseInt(t[5]), Integer.parseInt(t[6]), Integer.parseInt(t[7])));
                pq.add(new Car(Integer.parseInt(t[0]), Integer.parseInt(t[1]), Integer.parseInt(t[2]), Integer.parseInt(t[3]), Integer.parseInt(t[4]), Integer.parseInt(t[5]), Integer.parseInt(t[6]), Integer.parseInt(t[7])));
            }

            System.out.format("%-20s " + "%-20s " + "%-20s " + "%-20s " + "%-20s " + "%-20s " + "%-20s " + "%-20s ", tt[0], tt[1], tt[2], tt[3], tt[4], tt[5], tt[6], tt[7]);
            System.out.println();
            
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[]args) {

    }
}