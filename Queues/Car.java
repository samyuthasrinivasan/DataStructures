import java.io.BufferedReader;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

public class Car implements Comparable<Car>{
    int id, miles, engineSize, hpowers, weight, acc, country, numOfCyl;
    public Car(int id, int miles, int engineSize, int hpowers, int weight, int acc, int country, int numOfCyl){
        this.id = id;
        this.miles = miles;
        this.engineSize = engineSize;
        this.hpowers = hpowers;
        this.weight = weight;
        this.acc = acc;
        this.country = country;
        this.numOfCyl = numOfCyl;
    }

    static Stack<Car> stack = new Stack<Car>();
    static PriorityQueue<Car> pq = new PriorityQueue<Car>();
    static Queue<Car> q = new LinkedList<>();
    public static void main(String[]args) {
        String title;
        String [] tt;
        try {
            BufferedReader reader = new BufferedReader(new FileReader("CarData.txt"));
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

        
        while(!stack.empty()) {
            Car c = stack.pop();
            System.out.format("%-20d " + "%-20d " + "%-20d " + "%-20d " + "%-20d " + "%-20d " + "%-20d " + "%-20d", c.id, c.miles, c.engineSize, c.hpowers, c.weight, c.acc, c.country, c.numOfCyl);
            System.out.println();
        }
        System.out.println();

        while(!q.isEmpty()) {
            Car c = q.poll();
            System.out.format("%-20d " + "%-20d " + "%-20d " + "%-20d " + "%-20d " + "%-20d " + "%-20d " + "%-20d", c.id, c.miles, c.engineSize, c.hpowers, c.weight, c.acc, c.country, c.numOfCyl);
            System.out.println();
        }
        System.out.println();

        while(!pq.isEmpty()) {
            Car c = pq.poll();
            System.out.format("%-20d " + "%-20d " + "%-20d " + "%-20d " + "%-20d " + "%-20d " + "%-20d " + "%-20d", c.id, c.miles, c.engineSize, c.hpowers, c.weight, c.acc, c.country, c.numOfCyl);
            System.out.println();
        }
        System.out.println();

    }
    @Override
    public int compareTo(Car o) {
        if(this.miles != o.miles){
            Integer i = new Integer(o.miles);
            return i.compareTo(new Integer(this.miles));
        } else if(this.engineSize != o.engineSize){
            Integer i = new Integer(o.engineSize);
            return i.compareTo(new Integer(this.engineSize));
        } else if(this.hpowers != o.hpowers){
            Integer i = new Integer(o.hpowers);
            return i.compareTo(new Integer(this.hpowers));
        } else if(this.weight != o.weight){
            Integer i = new Integer(o.weight);
            return i.compareTo(new Integer(this.weight));
        } else if(this.acc != o.acc){
            Integer i = new Integer(o.acc);
            return i.compareTo(new Integer(this.acc));
        } else if(this.country != o.country){
            Integer i = new Integer(o.country);
            return i.compareTo(new Integer(this.country));
        } else if(this.numOfCyl != o.numOfCyl){
            Integer i = new Integer(o.numOfCyl);
            return i.compareTo(new Integer(this.numOfCyl));
        }
        Integer i = new Integer(o.id);
        return i.compareTo(new Integer(this.id));
    }
}
