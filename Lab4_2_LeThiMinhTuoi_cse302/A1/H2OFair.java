import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class H2OFair {
    private int hCount = 0;
    private int oCount = 0;
    private int moleculeCount = 0;
    private Lock lock = new ReentrantLock();
    private Queue<Condition> hqueue = new LinkedList<>();
    private Queue<Condition> oqueue = new LinkedList<>();

    public H2OFair() {
    }

    public void hydrogen() throws InterruptedException {
        lock.lock();
        try {
            hCount++;
            if (hCount < 2 || oCount < 1) {
                Condition condition = lock.newCondition();
                hqueue.add(condition);
                condition.await();
            } else {
                moleculeCount++;
                hCount -= 2;
                oCount -= 1;
                hqueue.poll().signal();
                oqueue.poll().signal();
                System.out.println("Molecule " + moleculeCount + " formed: H2O");
            }
        } finally {
            lock.unlock();
        }
    }

    public void oxygen() throws InterruptedException {
        lock.lock();
        try {
            oCount++;
            if (hCount < 2) {
                Condition condition = lock.newCondition();
                oqueue.add(condition);
                condition.await();
            } else {
                moleculeCount++;
                hCount -= 2;
                oCount -= 1;
                hqueue.poll().signal();
                hqueue.poll().signal();
                System.out.println("Molecule " + moleculeCount + " formed: H2O");

            }
        } finally {
            lock.unlock();
        }
    }

    public int getH2OCount() {
        return moleculeCount;
    }
}