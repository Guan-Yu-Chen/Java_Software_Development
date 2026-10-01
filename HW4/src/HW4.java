import java.util.Scanner;

public class HW4 {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int n = Integer.parseInt(scanner.nextLine());
		String line = "";
		int[] nodes = new int[n];
		int min_id = -1;

		for(int i = 0; i < n; i++)
		{
			line = scanner.nextLine();
			nodes[i] = Integer.parseInt(line.split(" ")[0]);
		}
		int end = Integer.parseInt(line.split(" ")[1]);
		
		boolean hasCycle = false;
		for(int i = 0; i < n; i++)
		{
			if(nodes[i] == end)
			{
				hasCycle = true;
				min_id = end;
			}

			if(hasCycle && min_id > nodes[i])
			{
				min_id = nodes[i];
			}
		}

		System.out.println(min_id);
		scanner.close();
	}
}
