package Lab.lab2;

public class lab2 {
    public static void main(String[] args) {
        Thread thread = new Thread(new Task());
        thread.start();
    }
}
