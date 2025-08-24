package A5;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        OldBridge oldBridge = new OldBridge();

        Car car1 = new Car(oldBridge, 1);
        Car car2 = new Car(oldBridge, 1);
        Car car3 = new Car(oldBridge, 1);
        Car car4 = new Car(oldBridge, 1);

        car1.start();
        car2.start();
        car3.start();
        car4.start();

        car1.join();
        car2.join();
        car3.join();
        car4.join();
    }
}

class OldBridge {
    private int size = 3;
    private int count;
    private int currentDir = -1;
    private ReentrantLock lock = new ReentrantLock();
    private Condition goCond = lock.newCondition();

    public OldBridge() {

    }

    public void arriveBridge(int direction) {
        try {
            this.lock.lockInterruptibly();
            try {
                while (count == size || (count > 0 && currentDir != direction)) {
                    try {
                        System.out.println("Car is waiting");
                        goCond.await();
                    } catch (InterruptedException e) {
                    }
                }
                count++;
                currentDir = direction;
            } finally {
                this.lock.unlock();
            }
        } catch (InterruptedException e) {

        }
    }

    public void exitBridge() {
        try {
            this.lock.lockInterruptibly();
            try {
                while (count > 0) {
                    System.out.println("Releasing a car.");
                    count--;
                }
                currentDir = -1;
                goCond.signalAll();
            } finally {
                this.lock.unlock();
            }
        } catch (InterruptedException e) {
        }
    }
}

class Car extends Thread {
    private OldBridge oldBridge;
    private int direction;

    public Car(OldBridge oldBridge, int direction) {
        this.oldBridge = oldBridge;
        this.direction = direction;
    }

    @Override
    public void run() {
        try {
            oldBridge.arriveBridge(direction);
            Thread.sleep(10);
            oldBridge.exitBridge();
        } catch (InterruptedException e) {

        }
    }
}
