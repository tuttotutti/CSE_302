public class Main {
    public static void main(String[] args) {
        SharedData data = new SharedData(0);

        IncrementThread t1 = new IncrementThread(data);
        t1.start();
        t1.join();
    }
}
