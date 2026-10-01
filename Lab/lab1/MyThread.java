package Lab.lab1;

public class MyThread extends Thread{
    
    public String name;

    public MyThread(String name)
    {
        this.name = name;
    }

    public void run()
    {
        while(true)
        {
            System.out.println("My name is " + name);
    
            try {
                this.sleep((long)(Math.random()*2000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
