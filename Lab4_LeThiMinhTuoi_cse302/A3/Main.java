import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        TSQueue tsQueue = new TSQueue();
        
        AddThread addThread = new AddThread(tsQueue, 3);
        RemoveThread removeThread = new RemoveThread(tsQueue);

        addThread.start();
        removeThread.start();

        addThread.join();
        removeThread.join();

        System.out.println("SIZE: " + tsQueue.getSize());
    }
}

class TSQueue {
    private LinkedList<Integer> queue = new LinkedList<>();
    private ReentrantLock lock = new ReentrantLock();

    public TSQueue() {

    }

    public void addLast(int value) {
         try {
            this.lock.lockInterruptibly();
            try {
                this.queue.addLast(value);
                System.out.println("Add");
            }
            finally {
            this.lock.unlock();
            }
        } catch (InterruptedException e) {
        }
    }

    public int remvoveFirst() {
        int ret = 0;
        try {
            this.lock.lockInterruptibly();
            try {
                if (this.queue.isEmpty() == true) {
                    throw new IllegalStateException("The queue is empty.");
                }
                ret = this.queue.removeFirst();
                System.out.println("Remove");
            }
            finally {
            this.lock.unlock();
            }
        } catch (InterruptedException e) {
        }
        return ret;
    }

    public int getSize(){
        return queue.size();
    }
}

class AddThread extends Thread{
    private TSQueue tsQueue;
    private int item;

    public AddThread(TSQueue tsQueue, int item){
        this.tsQueue = tsQueue;
        this.item = item;
    }

    @Override
    public void run(){
        this.tsQueue.addLast(item);
    }
}

class RemoveThread extends Thread{
    private TSQueue tsQueue;

    public RemoveThread(TSQueue tsQueue){
        this.tsQueue = tsQueue;
    }

    @Override
    public void run(){
            this.tsQueue.remvoveFirst();
    }
}
