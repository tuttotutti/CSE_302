import java.util.ArrayList;
import java.util.List;

public class PriorityScheduler implements Scheduler {
    @Override
    public List<String> schedule(List<Task> taskQueue) {
       List<String> results = new ArrayList<>();

       taskQueue.sort((t1, t2) -> Integer.compare(t1.getPriority(), t2.getPriority()));

        int start = 0;
        for (Task task : taskQueue){
            task.setDuration(task.getBurst());
            task.setRemain(0);
            String res = start + " : " + task.toString();
            results.add(res);
            start += task.getDuration();
        }
        return results;
    }
    
}
