import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class DiningPhilosophers2 {
    private int size;

    private final ReentrantLock lock = new ReentrantLock();
    private final Condition waitForFork = lock.newCondition();

    private int remainingForkNum;
    private int currentEaterNum;

    public DiningPhilosophers2(int size) {

        this.size = size;
        this.remainingForkNum = size;

    }

    public void getFork(Philosophe p) {

        lock.lock();

        try {

            if (p.forkHeldNum == 0) {
                while (!canTakeFork()) {
                    try {
                        System.out.println("Wait for 1st fork.");
                        waitForFork.await();
                    } catch (InterruptedException e) {
                    }
                }

                System.out.println("Take the first fork.");
                remainingForkNum--;

                p.forkHeldNum++;
            }

            else {

                while (remainingForkNum == 0) {
                    try {
                        System.out.println("Wait for 2nd fork.");
                        waitForFork.await();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                }

                System.out.println("Take 2nd fork - about to eat.");
                remainingForkNum--;
                currentEaterNum++;

            }

        } finally {
            lock.unlock();
        }

    }

    public void releaseForks(Philosophe p) {
        lock.lock();

        try {
            System.out.println("Release 2 forks - done eating.");
            remainingForkNum += 2;
            p.forkHeldNum = 0;
            currentEaterNum--;
            waitForFork.signalAll();
        } finally {
            lock.unlock();
        }

    }

    private boolean canTakeFork() {
        return (remainingForkNum > 1 || (remainingForkNum == 1 && currentEaterNum > 0));
    }
}
