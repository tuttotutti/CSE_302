import java.util.LinkedList;
import java.util.List;

public class FCFSScheduler implements Scheduler{
    private CPU cpu;

    public FCFSScheduler(){
        this.cpu = new CPU();
    }

    @Override
    public List<String> schedule(List<Task> taskQueue){
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
