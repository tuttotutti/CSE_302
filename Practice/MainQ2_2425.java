
// A particular river crossing is shared by both women and men. A boat is used to cross the river, 
// but it only seats four people, and must always carry a full load. 
// In order to guarantee the safety, you cannot put three women and one man in the same boat; 
// similarly, you cannot put three men in the same boat as a woman. All other combinations are safe. 
// Two procedures are needed, womanArrives and manArrives, called by a woman or a man when he/she arrives at the river bank. 
// The procedures arrange the arriving women and men into safe boatloads. 
// Women and men arrive at the river bank, they must wait until a boat is full.

// 1. Implement two methods womanArrives and manArrives that arrange the arriving women and men into safe boatloads. (18 marks)
// 2. Write a main method to test these methods. (12 marks)

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class MainQ2_2425 {
    public static void main(String[] args) throws InterruptedException {
        Boat boat = new Boat();

        Men m1 = new Men(boat);
        Men m2 = new Men(boat);
        Men m3 = new Men(boat);
        Men m4 = new Men(boat);
        Men m5 = new Men(boat);
        Men m6 = new Men(boat);

        Women w1 = new Women(boat);
        Women w2 = new Women(boat);
        Women w3 = new Women(boat);
        Women w4 = new Women(boat);
        Women w5 = new Women(boat);
        Women w6 = new Women(boat);

        m1.start();
        m2.start();
        m3.start();
        m4.start();
        m5.start();
        m6.start();

        w1.start();
        w2.start();
        w3.start();
        w4.start();
        w5.start();
        w6.start();

        Thread.sleep(10);

        m1.join();
        m2.join();
        m3.join();
        m4.join();
        m5.join();
        m6.join();

        w1.join();
        w2.join();
        w3.join();
        w4.join();
        w5.join();
        w6.join();

        System.out.println("done");
    }
}

class Boat {
    private int size = 4;
    private int womenNum = 0;
    private int menNum = 0;
    private int count = 0;
    private ReentrantLock lock = new ReentrantLock();
    private Condition womenCond = lock.newCondition();
    private Condition menCond = lock.newCondition();

    public Boat() {

    }

    public void womenArrive() {
        this.lock.lock();

        try {
            while (count >= size || menNum == 3 || (menNum == 1 && womenNum == 2)) {
                try {
                    System.out.println("A woman waiting.....");
                    womenCond.await();
                } catch (InterruptedException e) {
                }
            }
            System.out.println("A woman on the boat");
            womenNum++;
            count++;
            if (count == size) {
                leave();
            }
        } finally {
            this.lock.unlock();
        }
    }

    public void menArrive() {
        this.lock.lock();

        try {
            while (count >= size || womenNum == 3 || (womenNum == 1 && menNum == 2)) {
                try {
                    System.out.println("A man waiting..........");
                    menCond.await();
                } catch (InterruptedException e) {
                }
            }
            System.out.println("A man on the boat");
            menNum++;
            count++;
            if (count == size) {
                leave();
            }
        } finally {
            this.lock.unlock();
        }
    }

    public void leave() {
        System.out.println("All leave.");
        try {
            System.out.println("Boat moving.........");
            Thread.sleep(2);
        } catch (InterruptedException e) {
        }
        System.out.println("Boat return.");
        menNum = 0;
        womenNum = 0;
        count = 0;
        menCond.signalAll();
        womenCond.signalAll();
    }
}

class Men extends Thread {
    private Boat boat;

    public Men(Boat boat) {
        this.boat = boat;
    }

    @Override
    public void run() {
        this.boat.menArrive();
    }
}

class Women extends Thread {
    private Boat boat;

    public Women(Boat boat) {
        this.boat = boat;
    }

    @Override
    public void run() {
        this.boat.womenArrive();
    }
}
