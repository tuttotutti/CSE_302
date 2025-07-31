import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
    public static void main(String[] args) throws InterruptedException{

        // Synchronized
        SharedData sharedDataA = new SharedDataA(0);
        
        IncreaseThread increaseThread = new IncreaseThread(sharedDataA);
        DecreaseThread decreaseThread = new DecreaseThread(sharedDataA);

        increaseThread.start();
        decreaseThread.start();

        increaseThread.join();
        decreaseThread.join();

        System.out.println("Result (Synchronized): "+ sharedDataA.getValue());
        
         // ReentrantLock
        SharedData sharedDataB = new SharedDataB(0);
        
        IncreaseThread increaseThreadB = new IncreaseThread(sharedDataB);
        DecreaseThread decreaseThreadB = new DecreaseThread(sharedDataB);

        increaseThreadB.start();
        decreaseThreadB.start();

        increaseThreadB.join();
        decreaseThreadB.join();

        System.out.println("Result (ReetrantLock): "+ sharedDataB.getValue());  

         // Semaphore
        SharedData sharedDataC = new SharedDataC(0);
        
        IncreaseThread increaseThreadC = new IncreaseThread(sharedDataC);
        DecreaseThread decreaseThreadC = new DecreaseThread(sharedDataC);

        increaseThreadC.start();
        decreaseThreadC.start();

        increaseThreadC.join();
        decreaseThreadC.join();

        System.out.println("Result (Semaphore): "+ sharedDataC.getValue());  
    } 
}

// Synchronized
class SharedDataA implements SharedData{
    private int value;

    public SharedDataA (int value) {
        this.value = value;
    }

    @Override
    public synchronized void increase() {
        this.value += 1;
    }

    @Override
    public synchronized void decrease() {
        this.value -= 1;
    }

    public int getValue() {
        return this.value;
    }
}

// ReentrantLock
class SharedDataB implements SharedData{
    private int value;
    private ReentrantLock lock = new ReentrantLock();

    public SharedDataB (int value) {
        this.value = value;
    }

    @Override
    public void increase() {
        try {
            this.lock.lockInterruptibly();
            try {
                this.value += 1;
            }
            finally {
            this.lock.unlock();
            }
        } catch (InterruptedException e) {
        }
    }

    @Override
    public  void decrease() {
        try {
            this.lock.lockInterruptibly();
            try {
                this.value -= 1;
            }
            finally {
            this.lock.unlock();
            }
        } catch (InterruptedException e) {
        }
    }

    public int getValue() {
        return this.value;
    }
}

//Semaphore
class SharedDataC implements SharedData{
    private int value;
    private Semaphore s = new Semaphore(1);

    public SharedDataC (int value) {
        this.value = value;
    }

    @Override
    public void increase() {
        try {
            s.acquire();
            this.value += 1;
            s.release();
        } catch (InterruptedException e) {}
    }

    @Override
    public  void decrease() {
        try {
            s.acquire();
            this.value -= 1;
            s.release();
        } catch (InterruptedException e) {}
    }

    public int getValue() {
        return this.value;
    }
}

class IncreaseThread extends Thread{
    private SharedData sharedData;

    public IncreaseThread (SharedData sharedData){
        this.sharedData = sharedData;
    }

    @Override
    public void run (){
        for (int i = 0; i < 1_000_000; i++) {
            sharedData.increase();
        }
    }
}

class DecreaseThread extends Thread{
    private SharedData sharedData;

    public DecreaseThread (SharedData sharedData){
        this.sharedData = sharedData;
    }

    @Override
    public void run (){
        for (int i = 0; i < 1_000_000; i++) {
            sharedData.decrease();
        }
    }
}
