public class InterruptTimer {
    private CPU cpu;
    private InterruptThread interThread = null;

    public InterruptTimer (CPU cpu){
        this.cpu = cpu;
    }

    public void start (int quantum, Task task){
        this.interThread = new InterruptThread(this.cpu, quantum, task);
        this.interThread.setPriority(Thread.MAX_PRIORITY);
        this.interThread.start();
    }

    public void stop(){
        if(this.interThread != null){
            this.interThread.interrupt();
        }
        try {
            this.interThread.join();
        } catch (InterruptedException e) {
        }
    }
}

class InterruptThread extends Thread{
    private CPU cpu;
    private int quantum;
    private Task task;

    public InterruptThread (CPU cpu, int quantum, Task task) {
        this.cpu = cpu;
        this.quantum = quantum;
        this.task = task;
    }

    @Override
    public void run(){
        try {
            Thread.sleep(this.quantum - 5);
            long start = this.task.getStartTime();
            while (System.currentTimeMillis() < start + this.quantum);
            this.cpu.interrupt();
        } catch (InterruptedException e) {
        }
    }
}
