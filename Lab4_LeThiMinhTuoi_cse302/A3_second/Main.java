import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        BoundedBuffer bb = new BoundedBuffer(5);

        AddThread addThread = new AddThread(bb, 3);
        RemoveThread removeThread = new RemoveThread(bb);

        addThread.start();
        removeThread.start();

        addThread.join();
        removeThread.join();

        System.out.println("SIZE: " + bb.getSize());
    }
}

class BoundedBuffer {
    private int size;
    private LinkedList<Integer> buffer = new LinkedList<>();
    private ReentrantLock lock = new ReentrantLock();
    private Condition add = lock.newCondition();
    private Condition remove = lock.newCondition();

    public BoundedBuffer(int size) {
        this.size = size;
    }

    public void add(int value) {
         try {
            this.lock.lockInterruptibly();
            try {
                while (this.buffer.size() == size) {
                    try {
                        this.add.await();
                    } catch (InterruptedException e) {}
                }
                this.buffer.addLast(value);
                this.remove.signal();
            }
            finally {
            this.lock.unlock();
            }
        } catch (InterruptedException e) {
        }

    }

    public int remove() {
        int ret = 0;
        try {
            this.lock.lockInterruptibly();
            try {
                while (this.buffer.isEmpty() == true) {
                    try {
                        this.remove.await();
                    } catch (InterruptedException e) {}
                }
                ret = this.buffer.removeFirst();
                this.add.signal();
            }
            finally {
            this.lock.unlock();
            }
        } catch (InterruptedException e) {
        }
        return ret;
    }

    public int getSize(){
        return buffer.size();
    }

}

class AddThread extends Thread{
    private BoundedBuffer bb;
    private int item;

    public AddThread(BoundedBuffer bb, int item){
        this.bb = bb;
        this.item = item;
    }

    @Override
    public void run(){
        this.bb.add(item);
    }
}

class RemoveThread extends Thread{
    private BoundedBuffer bb;

    public RemoveThread(BoundedBuffer bb){
        this.bb = bb;
    }

    @Override
    public void run(){
            this.bb.remove();
    }
}
