import java.util.concurrent.Semaphore;

public class Main {
    public static void main(String[] args) throws InterruptedException{
        SharedSemaphores sharedSemaphores = new SharedSemaphores();

        HEThread he = new HEThread(sharedSemaphores);
        LThread l = new LThread(sharedSemaphores);
        OThread o = new OThread(sharedSemaphores);

        he.start();
        l.start();
        o.start();

        Thread.sleep(2);

        he.join();
        l.join();
        o.join(); 
    }
}

class SharedSemaphores {
    Semaphore waitHE = new Semaphore(0);
    Semaphore waitL = new Semaphore(0);
    Semaphore waitO = new Semaphore(1);
}

class HEThread extends Thread {

    private SharedSemaphores sharedSemaphores;

    public HEThread (SharedSemaphores sharedSemaphores){
        this.sharedSemaphores = sharedSemaphores;
    }

    @Override
    public void run(){
        try {
            while (Thread.interrupted() == false) {
                sharedSemaphores.waitO.acquire();
                System.out.print("H");
                System.out.print("E");
                sharedSemaphores.waitHE.release();
            }
        } catch (InterruptedException e) {}
    }
}

class LThread extends Thread {

    private SharedSemaphores sharedSemaphores;
    private int times;

    public LThread (SharedSemaphores sharedSemaphores){
        this.sharedSemaphores = sharedSemaphores;
        this.times = 0;
    }

    @Override
    public void run(){
        try {
            while (Thread.interrupted() == false) {
                sharedSemaphores.waitHE.acquire();
                System.out.print("L");
                 if (times == 0){
                    sharedSemaphores.waitHE.release();
                    times = 1;
                }
                else {
                    sharedSemaphores.waitL.release();
                    times = 0;
                }
            }
        } catch (InterruptedException e) {}
    }
}

class OThread extends Thread {

    private SharedSemaphores sharedSemaphores;

    public OThread (SharedSemaphores sharedSemaphores){
        this.sharedSemaphores = sharedSemaphores;
    }

    @Override
    public void run(){
        try {
            while (Thread.interrupted() == false) {
                sharedSemaphores.waitL.acquire();
                System.out.print("O");
                sharedSemaphores.waitO.release();
            }
        } catch (InterruptedException e) {}
    }
}
