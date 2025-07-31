import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;

public class PriorityRoundRobinScheduler implements Scheduler {
    private int quantum;

    public PriorityRoundRobinScheduler(int quantum) {
        this.quantum = quantum;
    }

    @Override
    public List<String> schedule(List<Task> taskQueue) {
        List<String> results = new LinkedList<>();
        Map<Integer, Queue<Task>> queues = new TreeMap<>();

        for (Task task : taskQueue) {
            queues.putIfAbsent(task.getPriority(), new LinkedList<>());
            queues.get(task.getPriority()).add(task);
        }

        long start = 0;

        while (!queues.isEmpty()) {
            List<Integer> priorities = new ArrayList<>(queues.keySet());
            boolean taskExecuted = false;

            for (int priority : priorities) {
                Queue<Task> queue = queues.get(priority);
                if (queue == null || queue.isEmpty())
                    continue;

                Task task = queue.poll();

                int executedTime = Math.min(task.getRemain(), quantum);
                task.setDuration(executedTime);
                task.setRemain(task.getRemain() - executedTime);

                results.add(start + " : " + task.toString());
                start += executedTime;

                if (task.getRemain() > 0) {
                    queue.add(task);
                }

                taskExecuted = true;
                break;
            }

            priorities.forEach(p -> {
                if (queues.get(p).isEmpty()) {
                    queues.remove(p);
                }
            });

            if (!taskExecuted)
                break;
        }
        return results;
    }
}
