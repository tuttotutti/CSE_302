import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class H2O {
    private int hCount = 0;
    private int oCount = 0;
    private int moleculeCount = 0;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public H2O() {
    }

    public void hydrogen() throws InterruptedException {
        lock.lock();
        try {
            hCount++;
            if (hCount < 2 || oCount < 1) {
                condition.await();
            } else {
                formMolecule();
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
                condition.await();
            } else {
                formMolecule();
            }
        } finally {
            lock.unlock();
        }
    }

    private void formMolecule() {
        moleculeCount++;
        System.out.println("Molecule " + moleculeCount + " formed: H2O");
        hCount -= 2;
        oCount -= 1;
        condition.signalAll();
    }

    public int getH2OCount() {
        return moleculeCount;
    }
}
