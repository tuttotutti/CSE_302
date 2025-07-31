public class IncrementThread extends Thread {
    private SharedData data;

    public IncrementThread(SharedData data) {
        this.data = data;
    }

    @Override
    public void run(SharedData data){
        for (int i = 0; i < 1_000_000; i++) {
            int v = this.data.getValue();
            v = v + 1;
            data.setValue(v);
        }
    }
}
