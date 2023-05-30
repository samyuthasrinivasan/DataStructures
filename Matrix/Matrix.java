import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Matrix{
    public static void main(String []args){
        ArrayList<String> lines = new ArrayList<String>();
        try{
            BufferedReader reader = new BufferedReader(new FileReader("MatrixFile.txt"));
            String temp;
            while((temp = reader.readLine()) != null) {
                lines.add(temp);
            }
            reader.close();
        } catch(Exception e) {
            e.printStackTrace();
        }

        for (String string : lines) {
            string = string.replaceAll("\\s", " ");
            String m1 = string.substring(0, string.indexOf(" "));
            String m2 = string.substring(string.indexOf(" ") + 1);

            m1 = m1.substring(2, m1.indexOf("}}"));
            m2 = m2.substring(2, m2.indexOf("}}"));

            String [][] matrix1 = matrixedMatrix(m1);
            String [][] matrix2 = matrixedMatrix(m2);

            System.out.println("MATRIX 1:");
            printTwoArray(matrix1);
            System.out.println();

            System.out.println("MATRIX 2:");
            printTwoArray(matrix2);
            System.out.println();

            System.out.print("SUM: ");
            if(matrix1.length != matrix2.length || matrix1[0].length != matrix2[0].length){
                System.out.println("Not possible");
                System.out.println("DIFFERENCE: Not possible");
            }
            else {
                int [][] sum = new int [matrix1[0].length][matrix2.length];
                int [][] difference = new int [matrix1[0].length][matrix2.length];

                for(int i = 0; i < sum.length; i++) {
                    for(int j = 0; j < sum[i].length; j++) {
                        sum[i][j] = Integer.parseInt(matrix1[i][j]) + Integer.parseInt(matrix2[i][j]);
                        difference[i][j] = Integer.parseInt(matrix1[i][j]) - Integer.parseInt(matrix2[i][j]);
                    }
                }

                System.out.println();
                printTwoArray(sum);
                
                System.out.println("\nDIFFERENCE:");
                printTwoArray(difference);
            }

            if(matrix1[0].length != matrix2.length){
                System.out.println("PRODUCT: Not possible");
            } else {
                int [][] product = new int[matrix1.length][matrix2[0].length];
                for(int i = 0; i < product.length; i++){
                    String [] row = matrix1[i];

                    for(int j = 0; j < product[i].length; j++) {
                        String [] col = new String[matrix2.length];
                        for(int k = 0; k < col.length; k++)
                            col[k] = matrix2[k][j];

                            product[i][j] = 0;
                            for(int l = 0; l < matrix2.length; l++)
                                product[i][j] += Integer.parseInt(row[l])*Integer.parseInt(col[l]);
                    }
                }

                System.out.println("\nPRODUCT:");
                printTwoArray(product);

            }   
        }
    }

    public static void printTwoArray(int [][] row) {
        for(int i = 0; i < row.length; i++) {
            for(int j = 0; j < row[i].length; j++)
                System.out.print(row[i][j] + " ");
            System.out.println();
        }
        System.out.println();
    }

    public static void printTwoArray(String [][] row) {
        for(int i = 0; i < row.length; i++) {
            for(int j = 0; j < row[i].length; j++)
                System.out.print(row[i][j] + " ");
            System.out.println();
        }
        System.out.println();
    }

    public static String [][] matrixedMatrix(String m) {

        String [] rows = m.split("\\},\\{");
        m = "";
        for(int i = 0; i < rows.length; i++) {
            m += rows[i];
        }

        ArrayList<String> values = new ArrayList<String>();
        for(int i = 0; i < rows.length; i++){
            String [] s = rows[i].split(",");
            for(int j = 0; j < s.length; j++)
                values.add(s[j]);
        }

        int row = rows.length;
        int col = values.size()/row;
        
        String [] [] matrix = new String [row][col];
        int count = 0;
        for(int i = 0; i < matrix.length; i++) {
            for(int j = 0; j < matrix[i].length; j++) {
                matrix[i][j] = values.get(count);
                count++;
            }
        }

        return matrix; 
    }

    
}