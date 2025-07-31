import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class MySemaphore {
    private int value; // = 1
    private ReentrantLock lock;
    private Condition cond;

    public MySemaphore(int value){
        this.value = value;
        this.lock = new ReentrantLock(true);
        this.cond = this.lock.newCondition();
    }


    public void acquire(){
        this.lock.lock();
        try {
            while (value == 0) {
                this.cond.await();
            }
            value--;
        } catch (Exception e) {}
        finally {
            this.lock.unlock();
        }
    }

    public void release(){
        this.lock.lock();
        try {
            if (value == 0){
                value++;
                this.cond.signal();
            }  
        } catch (Exception e) {}
        finally {
            this.lock.unlock();
        }
    }
}
