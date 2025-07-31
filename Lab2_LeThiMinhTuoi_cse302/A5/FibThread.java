package A5;

public class FibThread extends Thread{
    private SharedData data;
    
    public FibThread(SharedData data){
        this.data = data;
    }

    @Override
    public void run(){
        int size = data.getSize();
        int[] fib = new int[size];

        fib[0] = 0;
        fib[1] = 1;
        for (int i = 2; i < fib.length; i++) {
            fib[i] = fib[i - 1] + fib[i - 2];
        }

        data.setFib(fib);
    }
}
