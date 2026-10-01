package Lab.lab3;

public class Racer extends Thread {
    public void run()
    {
        RaceConditionTest.increase();
    }
}
