public class DecreasedThread extends Thread {
    private SharedData data;
    private int times;

    public DecreasedThread(SharedData data, int times){
        this.data = data;
        this.times = times;
    }

    @Override
    public void run(){
        for (int i = 0; i < this.times; i++) {
            int value = data.getValue();
            value -= 1;
            data.setValue(value);
        }
    }

}
