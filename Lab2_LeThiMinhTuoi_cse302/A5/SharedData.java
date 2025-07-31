package A5;

public class SharedData {
    private int size;
    private int[] fib;
    public SharedData(int size) {
        this.size = size;
        this.fib = new int[size];
    }
    public int getSize() {
        return size;
    }
    public void setSize(int size) {
        this.size = size;
    }
    public int[] getFib() {
        return fib;
    }
    public void setFib(int[] fib) {
        this.fib = fib;
    }
    
}
