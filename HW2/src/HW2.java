import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class HW2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Car> cars = new ArrayList<>();
        ArrayList<Integer> positions = new ArrayList<>();
        ArrayList<Integer> speeds = new ArrayList<>();
        int caseNumber = 1;

        while(scanner.hasNextLine())
        {
            // line 1
            String line = scanner.nextLine();
            if(line.isEmpty())
            {
                System.out.printf("Case %d: %d.", caseNumber, countFleet(cars));
                caseNumber++;

                cars.clear();
                positions.clear();
                speeds.clear();
                continue;
            }
            Car.setMiles(Integer.parseInt(line));
            
            // line 2
            for(String pos: scanner.nextLine().split(" "))
            {
                positions.add(Integer.parseInt(pos));
            }
            
            // line 3
            for(String speed: scanner.nextLine().split(" "))
            {
                speeds.add(Integer.parseInt(speed));
            }
            
            // cars
            for(int i = 0; i < positions.size(); i++)
            {
                cars.add(new Car(positions.get(i), speeds.get(i)));
            }

            // last case
            System.out.printf("Case %d: %d.", caseNumber, countFleet(cars));
            caseNumber++;

            cars.clear();
            positions.clear();
            speeds.clear();
        }

        scanner.close();
    }

    private static int countFleet(ArrayList<Car> cars)
    {
        Collections.sort(cars);
        int fleet = 1;
        double maximal = cars.get(cars.size()-1).time;

        for(int i = cars.size()-2; i >= 0; i--)
        {
            if(cars.get(i).time > maximal)
            {
                maximal = cars.get(i).time;
                fleet++;
            }
        }
        return fleet;
    }
}
