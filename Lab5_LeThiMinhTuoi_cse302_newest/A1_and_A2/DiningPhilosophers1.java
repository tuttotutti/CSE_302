import java.util.concurrent.locks.ReentrantLock;

public class DiningPhilosophers1 {
    private int size;
    private ReentrantLock[] forks;

    public DiningPhilosophers1(int size) {
        this.size = size;
        this.forks = new ReentrantLock[this.size];
        for (int i = 0; i < forks.length; i++) {
            this.forks[i] = new ReentrantLock();
        }
    }

    public void getForks(int id) {
        System.out.println("Philosopher " + id + " is entering.");
        if (id % 2 == 0) {
            this.forks[id].lock();
            this.forks[(id + 1) % size].lock();
            System.out.println("Philosopher " + id + " successfully taken the forks.");
        } else {
            this.forks[(id + 1) % size].lock();
            this.forks[id].lock();
            System.out.println("Philosopher " + id + " successfully taken the forks.");
        }
    }

    public void releaseForks(int id) {
        this.forks[id].unlock();
        this.forks[(id + 1) % size].unlock();
        System.out.println("Philosopher " + id + " releasing the forks.");
    }
}
