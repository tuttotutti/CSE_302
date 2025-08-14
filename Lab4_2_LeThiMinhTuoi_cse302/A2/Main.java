import java.util.Random;

public class Main {
    public static void main(String[] args) {
        MyThreadPool pool = new MyThreadPool(5);

        int n = 100;
        for (int i = 0; i < n; i++) {
            Task t = new Task(i);
            pool.add(t);
        }

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
        }

        pool.shutdown();
        System.out.println("Done");
    }
}

class Task implements Runnable {
    private int id;

    public Task(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        Random rd = new Random();
        try {
            Thread.sleep(rd.nextInt(200));
        } catch (InterruptedException e) {
        }
        System.out.println("Task" + this.id + " runs.");
    }
}
