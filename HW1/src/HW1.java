import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class HW1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<String> inputs = new ArrayList<>();
        int caseNumber = 1;

        while(scanner.hasNextLine())
        {
            String input = scanner.nextLine();

            if(input.isEmpty())
            {
                int[][] matrix = stringsToMatrix(inputs);
                System.out.printf("Case %d: %s.%n", caseNumber, isSudoku(matrix) ? "True" : "False");
                caseNumber++;
                inputs.clear();
                continue;
            }

            inputs.add(input);
        }

        // last case
        int[][] matrix = stringsToMatrix(inputs);
        System.out.printf("Case %d: %s.%n", caseNumber, isSudoku(matrix) ? "True" : "False");

        scanner.close();
    }
    
    private static int[][] stringsToMatrix(ArrayList<String> inputs)
    {
        int[][] matrix = new int[9][9];
        for(int i = 0; i < 9; i++)
        {
            String input = inputs.get(i);
            for(int j = 0; j < 9; j++)
            {
                matrix[i][j] = input.charAt(j) - '0';
            }
        }
        return matrix;
    }

    private static boolean isSudoku(int[][] matrix)
    {
        // check row
        for(int i = 0; i < 9; i++)
        {
            Set<Integer> set = new HashSet<>();
            for(int j = 0; j < 9; j++)
            {
                set.add(matrix[i][j]);
            }

            if(set.size() != 9)
            {
                return false;
            }
        }

        // check column
        for(int i = 0; i < 9; i++)
        {
            Set<Integer> set = new HashSet<>();
            for(int j = 0; j < 9; j++)
            {
                set.add(matrix[j][i]);
            }

            if(set.size() != 9)
            {
                return false;
            }
        }

        // check subgrid
        for(int i = 0; i < 9; i+=3)
        {
            for(int j = 0; j < 9; j+=3)
            {
                Set<Integer> set = new HashSet<>();
                for(int k = 0; k < 3; k++)
                {
                    for(int l = 0; l < 3; l++)
                    {
                        set.add(matrix[i+k][j+l]);
                    }
                }
                if(set.size() != 9)
                {
                    return false;
                }
            }
        }
        
        return true;
    }
}