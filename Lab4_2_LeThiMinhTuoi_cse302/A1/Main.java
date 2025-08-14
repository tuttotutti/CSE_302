import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        H2OFair h2o = new H2OFair();
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            threads.add(new HydroThread(h2o));
        }

        for (int i = 0; i < 5; i++) {
            threads.add(new OxygenThread(h2o));
        }
        for (Thread thread : threads) {
            thread.start();
        }
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Molecules formed: " + h2o.getH2OCount());
    }
}

class HydroThread extends Thread {
    private H2OFair h2o;

    public HydroThread(H2OFair h2o) {
        this.h2o = h2o;
    }

    @Override
    public void run() {
        try {
            h2o.hydrogen();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class OxygenThread extends Thread {
    private H2OFair h2o;

    public OxygenThread(H2OFair h2o) {
        this.h2o = h2o;
    }

    @Override
    public void run() {
        try {
            h2o.oxygen();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
