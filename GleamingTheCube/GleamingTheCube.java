import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class GleamingTheCube {
    public static void main(String[]args){
        ArrayList<String> lines = new ArrayList<String>();
        try{
            BufferedReader reader = new BufferedReader(new FileReader("CubeInput..txt"));
            String temp;
        
            while((temp = reader.readLine()) != null){
                lines.add(temp);
            }
            reader.close();
        } catch(Exception e){
            e.printStackTrace();
        }
 
        for (String string : lines) {
            int starting = 1;
            int north = 2;
            int west = 3;
            int east = 7 - west;
            int south = 7 - north;

            for(int i = 0; i < string.length(); i++) {
                String a = string.substring(i, i+1);
                switch(a) {
                    case "N": 
                    int n = north;
                    north = starting;
                    south = 7 - north;
                    starting = 7 - n;
                    break;

                    case "S": 
                    int s = south;
                    south = starting;
                    north = 7 - south;
                    starting = 7 - s;
                    break;

                    case "E": 
                    int e = east;
                    east = starting;
                    west = 7 - starting;
                    starting = 7 - e;
                    break;

                    case "W": 
                    int w = west;
                    west = starting;
                    east = 7 - starting;
                    starting = 7 - w;
                    break;

                }
            }

            System.out.println(starting);
        } 
        
        
    }
}