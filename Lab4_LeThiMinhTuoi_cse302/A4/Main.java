import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Barrier barrier = new Barrier(3);

        Party party0 = new Party(barrier);
        Party party1 = new Party(barrier);
        Party party2 = new Party(barrier);
        Party party3 = new Party(barrier);

        party0.start();
        party1.start();
        party2.start();
        party3.start();

        party0.join();
        party1.join();
        party2.join();
        party3.join();
    }
}

class Barrier {
    private int parites;
    private int count;
    private ReentrantLock lock = new ReentrantLock();
    private Condition pushCond = lock.newCondition();

    public Barrier(int parties) {
        this.parites = parties;
    }

    public void await() {
        try {
            this.lock.lockInterruptibly();
            try {
                count++;
                if (count < parites) {
                    try {
                        System.out.println("Party is waiting.");
                        this.pushCond.await();
                    } catch (InterruptedException e) {
                    }
                } else {
                    System.out.println("All parties push!!!");
                    count = 0;
                    this.pushCond.signalAll();
                }
            } finally {
                this.lock.unlock();
            }
        } catch (InterruptedException e) {
        }
    }
}

class Party extends Thread {
    private Barrier barrier;

    public Party(Barrier barrier) {
        this.barrier = barrier;
    }

    @Override
    public void run() {
        try {
            this.barrier.await();
        } catch (Exception e) {
        }
    }
}
