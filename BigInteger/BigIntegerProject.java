import java.math.BigInteger;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;

public class BigIntegerProject {

    static boolean done;
    static ArrayList<Integer> pattern;

    public static void main(String[] args) {

        ArrayList<String> lines = new ArrayList<String>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("LargeNumberPracticeSet.txt"));
            String temp;
            while ((temp = reader.readLine()) != null) {
                lines.add(temp);
            }
            reader.close();
        } catch (Exception e) {

        }

        for (String string : lines) {

            BigInteger base, power;
            String string1 = string.substring(0, string.indexOf(" "));
            string1 = string1.substring(string1.length() - 1);

            String string2 = string.substring(string.indexOf(" ") + 1);

            base = new BigInteger(string1);
            power = new BigInteger(string2);
            int exponent = 0;

            if(power.mod(new BigInteger("4")).intValue() == 0) {
                exponent = 4;
            } else {
                exponent = power.mod(new BigInteger("4")).intValue();
            }

            BigInteger answer = base.pow(exponent);
            String ans = answer.intValue() + "";

            System.out.println(ans.substring(ans.length() - 1));

        }

    }

}
