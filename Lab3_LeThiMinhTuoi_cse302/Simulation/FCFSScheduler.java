import java.util.ArrayList;
import java.util.List;

public class FCFSScheduler implements Scheduler{

    @Override
    public List<String> schedule(List<Task> taskQueue){
        List<String> results = new ArrayList<>();

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
