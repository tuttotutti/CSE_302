import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class MyThreadPool {
    private int size;
    private ArrayList<WorkingThread> threads = new ArrayList<>();
    private LinkedList<Runnable> taskQueue = new LinkedList<>();
    private ReentrantLock lock = new ReentrantLock();
    private Condition emptyCond = this.lock.newCondition();
    private boolean stopped = false;

    public MyThreadPool() {
        this(3);
    }

    public MyThreadPool(int size) {
        this.size = size;
        for (int i = 0; i < this.size; i++) {
            WorkingThread t = new WorkingThread();
            threads.add(t);
            t.start();
        }
    }

    public void add(Runnable task) {
        if (this.stopped == true) {
            return;
        }
        this.taskQueue.addLast(task);
    }

    public void shutdown() {
        this.stopped = true;
        for (WorkingThread t : this.threads) {
            t.interrupt();
        }

        for (WorkingThread t : this.threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
            }
        }
    }

    public LinkedList<Runnable> getTaskQueue() {
        return taskQueue;
    }

    public boolean isStopped() {
        return stopped;
    }

    class WorkingThread extends Thread {
        @Override
        public void run() {
            while (MyThreadPool.this.stopped == false) {
                // If the taskQueue is empty() => wait
                // If the taskQueue is not empty() => take a task => run task

                lock.lock();
                try {
                    LinkedList<Runnable> queue = MyThreadPool.this.getTaskQueue();
                    if (queue.isEmpty()) {
                        try {
                            MyThreadPool.this.emptyCond.await();
                        } catch (InterruptedException e) {

                        }
                    } else {
                        Runnable task = queue.removeFirst();
                        task.run();
                    }
                } finally {
                    lock.unlock();
                }
            }
        }
    }

}
