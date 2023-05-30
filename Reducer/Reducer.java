import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Reducer {
    public static void main(String [] args){
        ArrayList<String> lines = new ArrayList<String>();

        try{
            BufferedReader reader = new BufferedReader(new FileReader("Reducer.txt"));
            String temp;
            while ((temp = reader.readLine()) != null){
                lines.add(temp);
            }
            reader.close();

        } catch(Exception e) {
            e.printStackTrace();
        }

        for (String string : lines) {
            int whole = 0;
            int numerator = 0;
            int denominator = 0;
            if(string.length() == 1) {
                System.out.println(string);
            } else {
                numerator = Integer.parseInt(string.substring(0, string.indexOf("/")));
                denominator = Integer.parseInt(string.substring(string.indexOf("/") + 1));
                
                if(denominator == 0) {
                    System.out.println("undefined");
                } else if(numerator > denominator) {
                    whole = numerator/denominator;
                    numerator %= denominator;
                    System.out.print(whole + " ");
                } else if(numerator == 0) 
                    System.out.println(0);
                
                if(numerator == denominator){
                    System.out.println(1);
                } else if(numerator != 0 && denominator != 0){
                    for(int i = numerator; i > 1; i--) {
                        if(denominator%i == 0 && numerator%i == 0){
                            denominator /= i;
                            numerator /= i;
                        }
                    }

                    System.out.println(numerator + "/" + denominator);
                }
            }
            
        }
    }
}