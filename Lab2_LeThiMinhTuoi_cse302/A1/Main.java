public class Main {
    public static void main(String[] args) throws InterruptedException{
        SharedData data = new SharedData(0);

        //a
        IncreasedThread in1 = new IncreasedThread(data, 1_000_000);
        DecreasedThread de1 = new DecreasedThread(data, 1_000_000);

        in1.start();
        in1.join();

        de1.start();
        de1.join();

        System.out.println("Question a: "+data.getValue());

        //b
        IncreasedThread in2 = new IncreasedThread(data, 1_000_000);
        DecreasedThread de2 = new DecreasedThread(data, 1_000_000);

        in2.start();
        de2.start();

        in2.join();
        de2.join();

        System.out.println("Question b: "+data.getValue());
    }
}
