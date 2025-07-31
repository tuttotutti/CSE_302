import java.util.LinkedList;
import java.util.List;

public class PriorityScheduler implements Scheduler{
    private CPU cpu;

    public PriorityScheduler(){
        this.cpu = new CPU();
    }

    @Override
    public List<String> schedule(List<Task> taskQueue){
        taskQueue.sort((t1, t2) -> Integer.compare(t1.getPriority(), t2.getPriority()));
        
        List<String> results = new LinkedList<>();
        long start = System.currentTimeMillis();

        for(Task task: taskQueue){
            int currentTime = (int) (System.currentTimeMillis() - start);
            this.cpu.execute(task);

            String info = "" + currentTime + " : " + task.toString();
            results.add(info);
        }
        return results;
    }
}
