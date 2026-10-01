import java.util.Scanner;

public class HW3 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        Integer p_num = input.length();  // num of palindromic substrings

        for(int i = 1; i < input.length() - 1; i++)     // odd case
        {
            int r = 1;
            p_num += count_p_string(input, i, r);
        }

        for(float i = 0.5f; i < input.length() - 1; i++)   // even case
        {
            float r = 0.5f;
            p_num += count_p_string(input, i, r);
        }

        System.out.print(p_num);
        scanner.close();
    }

    private static int count_p_string(String input, float i, float r)
    {
        int p_num = 0;
        while(i - r >= 0 && i + r <= input.length() - 1)
        {
            char left = input.charAt((int)(i-r));
            char right = input.charAt((int)(i+r));
            if(left == right)
            {
                p_num++;
                r++;
            }
            else
                break;
        }
        return p_num;
    }
}
