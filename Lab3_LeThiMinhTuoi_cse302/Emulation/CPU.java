public class CPU {
    private TaskThread taskThread = null;
    public CPU(){
        
    }
    public void execute(Task task){
        this.taskThread = new TaskThread(task);
        this.taskThread.start();
        try {
            this.taskThread.join();
        } catch (InterruptedException e) {;
        }
    }

    public void interrupt(){
        if (this.taskThread != null) {
        this.taskThread.interrupt();
        this.taskThread = null;
    }
    }
}
class TaskThread extends Thread{
    private Task task;
    
    public TaskThread (Task t){
        this.task = t;
    }

    @Override
    public void run(){
        long start = System.currentTimeMillis();
        this.task.setStartTime(start);

        try{
        Thread.sleep(this.task.getRemain());
        } catch (InterruptedException e) {

        }
        long end = System.currentTimeMillis();
        int duration = (int) (end - start);

        if(duration > this.task.getRemain()){
            duration = this.task.getRemain();
        }

        this.task.setDuration(duration);
        this.task.setRemain(this.task.getRemain() - duration);
    }
} 
