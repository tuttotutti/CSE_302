import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class MainQ3_2425 {
    public static void main(String[] args) throws InterruptedException {
        BarberShop barberShop = new BarberShop(3);

        for (int i = 0; i < 13; i++) {
            Thread.sleep(10);
            Customer c = new Customer();
            barberShop.addCustomer(c);
        }

        Thread.sleep(100);
    }
}

class BarberShop {
    private int size;
    private ArrayList<Barber> barberList = new ArrayList<>();
    private LinkedList<Customer> customerList = new LinkedList<>();
    private ReentrantLock lock = new ReentrantLock();
    private Condition emptyCond = lock.newCondition();

    public BarberShop(int size) {
        this.size = size;
        for (int i = 0; i < size; i++) {
            Barber b = new Barber(i);
            barberList.add(b);
            b.start();
        }
    }

    public void addCustomer(Customer c) {
        lock.lock();
        try {
            if (customerList.size() == size) {
                System.out.println("Full. Leave");
                return;
            }
            customerList.add(c);
            emptyCond.signal();
        } finally {
            lock.unlock();
        }
    }

    public LinkedList<Customer> getCustomers() {
        return customerList;
    }

    class Barber extends Thread {
        private int id;

        public Barber(int id) {
            this.id = id;
        }

        @Override
        public void run() {
            while (true) {
                lock.lock();
                try {
                    LinkedList<Customer> customers = BarberShop.this.getCustomers();
                    while (customers.isEmpty()) {
                        System.out.println("Barber " + id + " sleeping");
                        try {
                            BarberShop.this.emptyCond.await();
                        } catch (InterruptedException e) {
                        }
                    }
                    System.out.println("Customer comes. Barber " + id + " Doing a haircut");
                    Customer c = customers.removeFirst();
                    c.run();
                } finally {
                    lock.unlock();
                }
            }
        }
    }
}

class Customer implements Runnable {

    @Override
    public void run() {
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
        }
        System.out.println("Finish haircut");
    }

}
