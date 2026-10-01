public class Car implements Comparable<Car>{
    static int miles = 0;
    int position;
    int speed;
    double time;

    public Car(int position, int speed)
    {
        this.position = position;
        this.speed = speed;
        this.time = (double) (Car.miles - this.position) / this.speed;
    }

    public static void setMiles(int newmiles)
    {
        miles = newmiles;
    }

    @Override
    public int compareTo(Car other)
    {
        return this.position - other.position;
    }
}
