import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main2 {
    public static void main(String[] args) throws InterruptedException {
        int n = 5;
        DiningPhilosophers2 dp = new DiningPhilosophers2(n);
        List<Philosophe> philosophers = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            Philosophe p = new Philosophe(dp);
            philosophers.add(p);
            p.start();
        }

        Thread.sleep(2000);

        for (Philosophe p : philosophers) {
            p.shutdown();
        }

        for (Philosophe p : philosophers) {
            try {
                p.join();
            } catch (InterruptedException e) {
            }
        }
    }
}

class Philosophe extends Thread {
    private DiningPhilosophers2 dp;
    private boolean stopped = false;
    public int forkHeldNum = 0;

    public Philosophe(DiningPhilosophers2 dp) {
        this.dp = dp;
    }

    public void shutdown() {
        this.stopped = true;
        this.interrupt();
    }

    @Override
    public void run() {
        Random rd = new Random();

        while (!stopped) {
            this.dp.getFork(this);
            // Eating
            try {
                Thread.sleep(rd.nextInt(100));
            } catch (InterruptedException e) {
            }

            this.dp.releaseForks(this);
            try {
                Thread.sleep(rd.nextInt(100));
            } catch (InterruptedException e) {
            }
        }
    }
}
