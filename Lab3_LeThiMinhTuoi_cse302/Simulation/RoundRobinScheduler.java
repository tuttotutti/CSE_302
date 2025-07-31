import java.util.LinkedList;
import java.util.List;

public class RoundRobinScheduler implements Scheduler {

    private int quantum;

    public RoundRobinScheduler(int quantum) {
        this.quantum = quantum;
    }

    @Override
    public List<String> schedule(List<Task> taskQueue) {
        List<String> results = new LinkedList<>();
        LinkedList<Task> queue = new LinkedList<>();
        
        for(Task t: taskQueue){
            queue.addLast(t);
        }

        long start = 0;

        while (!queue.isEmpty()) {
            Task task = queue.removeFirst();

            int executedTime = Math.min(task.getRemain(), quantum);
            task.setDuration(executedTime);
            task.setRemain(task.getRemain() - executedTime);

            if(task.getRemain() > 0){
                queue.addLast(task);
            }
            
            String info = "" + start + " : " + task.toString();
            results.add(info);
            start += executedTime;
        }

        return results;
    }
}
