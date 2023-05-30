import java.io.BufferedReader;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class Word implements Comparable<Word> {
    String string;

    public Word(String s) {
        string = s;
    }

    static PriorityQueue<Word> pq = new PriorityQueue<Word>();
    static Queue<Word> q = new LinkedList<>();

    public static void main(String[] args) {

        try {
            BufferedReader reader = new BufferedReader(new FileReader("Words.txt"));
            String temp;
            String[] t;
            while ((temp = reader.readLine()) != null) {
                t = temp.split(" ");
                for (int i = 0; i < t.length; i++) {
                    q.add(new Word(t[i]));
                    pq.add(new Word(t[i]));
                }
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        while(!pq.isEmpty()) {    
            System.out.format("%-20s" + "%-20s", q.poll(), pq.poll());
            System.out.println();
        }
    }

    @Override
    public int compareTo(Word o) {
        return this.toString().compareToIgnoreCase(o.toString());
        //switch o and this to reverse order
    }

    public String toString() {
        return this.string;
    }
}