import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        int n = 5;
        DiningPhilosophers1 dp = new DiningPhilosophers1(n);
        List<Philosopher> philosophers = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            Philosopher p = new Philosopher(dp, i);
            philosophers.add(p);
            p.start();
        }

        Thread.sleep(2000);

        for (Philosopher p : philosophers) {
            p.shutdown();
        }

        for (Philosopher p : philosophers) {
            try {
                p.join();
            } catch (InterruptedException e) {
            }
        }
    }
}

class Philosopher extends Thread {
    private DiningPhilosophers1 dp;
    private int id;
    private boolean stopped = false;

    public Philosopher(DiningPhilosophers1 dp, int id) {
        this.dp = dp;
        this.id = id;
    }

    public void shutdown() {
        this.stopped = true;
        this.interrupt();
    }

    @Override
    public void run() {
        Random rd = new Random();

        while (!stopped) {
            this.dp.getForks(this.id);
            // Eating
            try {
                Thread.sleep(rd.nextInt(100));
            } catch (InterruptedException e) {
            }

            this.dp.releaseForks(this.id);
            try {
                Thread.sleep(rd.nextInt(100));
            } catch (InterruptedException e) {
            }
        }
    }
}
