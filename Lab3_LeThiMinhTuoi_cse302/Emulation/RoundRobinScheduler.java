import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class RoundRobinScheduler implements Scheduler{
    private CPU cpu;
    private InterruptTimer timer;
    private int quantum;

    public RoundRobinScheduler(int quantum){
        this.quantum = quantum;
        this.cpu = new CPU();
        this.timer = new InterruptTimer(this.cpu);
    }

    @Override
    public List<String> schedule(List<Task> taskQueue) {
        List<String> results = new LinkedList<>();
        LinkedList<Task> queue = new LinkedList<>();
        
        for(Task t: taskQueue){
            queue.addLast(t);
        }

        long start = System.currentTimeMillis();

        while (!queue.isEmpty()) {
            int currentTime = (int) (System.currentTimeMillis() - start); 
            Task task = queue.removeFirst();
            this.timer.start(this.quantum, task);
            this.cpu.execute(task);
            this.timer.stop();

            if(task.getRemain() > 0){
                queue.addLast(task);
            }
            
            String info = "" + currentTime + " : " + task.toString();
            results.add(info);
        }

        return results;
    }
    
}
