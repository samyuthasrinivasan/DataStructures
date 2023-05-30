import java.util.Stack;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;  

public class Character {
    String name, homeWorld, species, gender; 
    String year;
    
    public Character(String name, String homeWorld, String species, String gender, String year) {
        this.name = name;
        this.homeWorld = homeWorld;
        this.species = species;
        this.gender = gender;
        this.year = year;
    } 

    public String toString() {
        return name + "\t" + homeWorld + "\t" + species + "\t" + gender + "\t" + year;
    }
    public static void main(String[] args) {

        Scanner reader = new Scanner(System.in);
        //1
        Stack<Integer> stack = new Stack<Integer>();
        System.out.println("Enter a number: ");
        int i = reader.nextInt();
        reader.nextLine();
        
        while(i != 0) {
            int mod = i%2;
            stack.push(mod);
            i /= 2;
        }

        while(!stack.isEmpty()) {
            System.out.print(stack.peek());
            stack.pop();
        }
        System.out.println();

        // 2
        Stack<String> stack2 = new Stack<String>();
        System.out.print("Enter a string: ");
        String s = reader.nextLine();
        reader.close();

        for(int j = 0; j < s.length(); j++) 
        stack2.push(s.substring(j, j+1));

        while(!stack2.isEmpty()) {
            System.out.print(stack2.peek());
            stack2.pop();
        }
        System.out.println();

        //3
        Stack<Character> birthYears = new Stack<Character>();
        Stack<Character> females = new Stack<Character>();
        Stack<Character> males = new Stack<Character>();
        Stack<Character> droids = new Stack<Character>();

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("StarWarsCharacters.csv"));
            String temp = bufferedReader.readLine();
            while((temp=bufferedReader.readLine()) != null) {
                String st = temp.substring(0, temp.indexOf(","));
                for(int k = 0; k < 5; k++) {
                    temp = temp.substring(temp.indexOf(",") + 1);
                }
                temp = st + "," + temp; 
                String [] a = temp.split(",");

                String name = a[0];
                String homeWorld = a[3];
                String species = a[4];
                String gender = a[2];
                String birthYear = a[1];

                if(birthYear.contains("B")){
                    birthYear = birthYear.substring(0, birthYear.indexOf("B"));
                    birthYears.push(new Character(name, homeWorld, species, gender, birthYear));
                }
                else 
                birthYear = "NA";

                
                switch(gender) {
                    case "female":
                    females.push(new Character(name, homeWorld, species, gender, birthYear));
                    break;
                    case "male":
                    males.push(new Character(name, homeWorld, species, gender, birthYear));
                    break;
                    default:
                    break;
                }

                if(species.contains("Droid")) {
                    droids.push(new Character(name, homeWorld, species, gender, birthYear));
                }
            }

            bufferedReader.close();

        } catch(Exception e) {
            e.printStackTrace();
        }

        System.out.println("\nMale Characters");
        while(!males.isEmpty()) {
            System.out.println(males.peek().toString());
            males.pop();
        }
        
        System.out.println("\nFemale Characters");
        while(!females.isEmpty()) {
            System.out.println(females.peek().toString());
            females.pop();
        }

        System.out.println("\nDroids");
        while(!females.isEmpty()) {
            System.out.println(droids.peek().toString());
            droids.pop();
        }

        System.out.println("\nAges");
        while(!birthYears.isEmpty()) {
            System.out.println(birthYears.peek().toString());
            birthYears.pop();
        }

    }
}


