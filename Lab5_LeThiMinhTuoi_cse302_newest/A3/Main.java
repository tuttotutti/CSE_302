import java.util.Random;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Bank bank = new Bank(9, 20_000);

        Thread thread1 = new TransactionThread(bank);
        Thread thread2 = new TransactionThread(bank);
        Thread thread3 = new TransactionThread(bank);
        Thread thread4 = new TransactionThread(bank);
        Thread thread5 = new TransactionThread(bank);
        Thread thread6 = new TransactionThread(bank);

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        thread5.start();
        thread6.start();

        Thread.sleep(10);

        thread1.join();
        thread2.join();
        thread3.join();
        thread4.join();
        thread5.join();
        thread6.join();
    }
}

class TransactionThread extends Thread {
    private Bank bank;

    public TransactionThread(Bank bank) {
        this.bank = bank;
    }

    @Override
    public void run() {
        Random rd = new Random();
        int fromId = rd.nextInt(5);
        int toId = rd.nextInt(5, 10);
        bank.canTransaction(fromId, toId, 5_000);
    }
}
