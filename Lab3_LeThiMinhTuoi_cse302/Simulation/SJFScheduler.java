import java.util.ArrayList;
import java.util.List;

public class SJFScheduler implements Scheduler{

    @Override
    public List<String> schedule(List<Task> taskQueue){
        List<String> results = new ArrayList<>();

        taskQueue.sort((t1, t2) -> Integer.compare(t1.getBurst(), t2.getBurst()));

        int start = 0;
        for (Task task : taskQueue){
            //imagine the task executing (em comment nha co)
            task.setDuration(task.getBurst());
            task.setRemain(0);
            String res = start + " : " + task.toString();
            results.add(res);
            start += task.getDuration();
        }
        return results;
    }
}
