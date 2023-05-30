import java.util.ArrayList;
import java.io.*;
public class TheLift{
    int totalPassengers;
    public TheLift(){
        try{
            BufferedReader reader = new BufferedReader(new FileReader("TheLiftFile.txt"));
            String text;
            while((text = reader.readLine()) != null) {
                String [] pieces = text.split(" ");
                int floors = Integer.parseInt(pieces[1]);
                int [][] queues = new int[floors][0];
                for(int r = 0; r < floors; r++) {
                    text = reader.readLine();
                    if(!text.equals("")) {
                        pieces = text.split(",");
                        int [] floorQueue = new int [pieces.length];
                        for(int c = 0; c < pieces.length; c++) {
                            floorQueue[c] = Integer.parseInt(pieces[c]);
                        queues[r] = floorQueue;                        }
                    }
                }

                text = reader.readLine();
                pieces = text.split(" ");
                int cap = Integer.parseInt(pieces[1]);
                totalPassengers = 0;

                int[] stops = liftOperation(queues, cap);

                for(int r = 0; r < stops.length; r++) {
                    System.out.print(stops[r]);
                    if(r < stops.length - 1)
                        System.out.print(", ");
                }
                System.out.println();
            }
            reader.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public int[] liftOperation(final int[][] queues, final int capacity) {
        ArrayList<ArrayList<Person>> queuesCopy = new ArrayList<ArrayList<Person>>();
        for(int i = 0; i <queues.length; i++) {
            ArrayList<Person> queue = new ArrayList<Person>();
            for(int j = 0; j < queues[i].length; j++) {
                Person person = new Person(i, queues[i][j]);
                queue.add(person);
                totalPassengers++;
            }
            queuesCopy.add(queue);
        }

        Lift lift = new Lift(queuesCopy, capacity);
        while(totalPassengers > 0 || lift.hasPassengers()) {
            lift.stop();
            lift.move();
        }
        return lift.getStops();
    }

    public static class Person {
        private int currentFloor;
        private int targetFloor;
        public Person(int currentFloor, int targetFloor) {
            this.currentFloor = currentFloor;
            this.targetFloor = targetFloor;
        }
        public boolean isGoingUp(){
            return targetFloor > currentFloor;
        }
        public boolean isGoingDown(){
            return targetFloor < currentFloor;
        }
        public boolean isGettingOff(){
            return targetFloor == currentFloor;
        }
        public void setCurrent(int current) {
            currentFloor = current;
        }
        public String toString() {
            return "Curr: " + currentFloor + "\tDest: " +targetFloor+ "\n";
        }
    }

    public static void main(String[]args) {
        TheLift app = new TheLift();
    }

    public class Lift {
        private ArrayList<ArrayList<Person>> queues;
        private int capacity;
        private int queueSize;
        private int currentFloor;
        private boolean goingUp;
        private ArrayList<Person> passengers;
        private ArrayList<Integer> stops;
        
        public Lift(ArrayList<ArrayList<Person>> queues, int capacity) {
            this.queues = queues;
            this.capacity = capacity;
            currentFloor = 0;
            goingUp = true;
            passengers = new ArrayList<Person>();
            stops = new ArrayList<Integer>();
            stops.add(0);
        }

        public String toString(){
			return String.format("%-10s%-9s%-6s%-16s%-12s", queueSize, capacity, currentFloor, goingUp, passengers, stops);
        }

        public boolean hasPassengers(){
            return passengers.size() > 0;
        }

        public int[] getStops(){
            stops.add(0);
            for(int x = 0; x < stops.size() - 1; x++){
                if(stops.get(x) == stops.get(x+1)){
                    stops.remove(x);
                    x--;
                }
            }
            int[] arr = new int[stops.size()];
            for(int i = 0; i < arr.length; i++){
                arr[i] = stops.get(i);
            } 
            return arr;
        }

        public void move(){
            if(goingUp){
                currentFloor++;
                if(currentFloor == queues.size() - 1)
                goingUp = !goingUp;
            } else {
                currentFloor--;
                if(currentFloor == 0) {
                    goingUp = !goingUp;
                }
            }
        }

        public void stop(){
            for(int i = 0; i < passengers.size(); i++) {
                Person person = passengers.get(i);
                person.setCurrent(currentFloor);
                if(person.isGettingOff()){
                    passengers.remove(i);
                    i--;
                    stops.add(currentFloor);
                }
            }
            for(int i = 0; i < queues.get(currentFloor).size(); i++) {
                Person person = queues.get(currentFloor).get(i);
                if((goingUp && person.isGoingUp()) || (!goingUp && person.isGoingDown())) {
                    if(passengers.size() < capacity) {
                        passengers.add(queues.get(currentFloor).remove(i));
                        totalPassengers--;
                        i--;
                    }
                    stops.add(currentFloor);
                }
            }
        }
    } 
}
