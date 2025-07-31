import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;

public class PriorityRRScheduler implements Scheduler {
    private CPU cpu;
    private InterruptTimer timer;
    private int quantum;

    public PriorityRRScheduler(int quantum) {
        this.quantum = quantum;
        this.cpu = new CPU();
        this.timer = new InterruptTimer(this.cpu);
    }

    @Override
    public List<String> schedule(List<Task> taskQueue) {
        List<String> results = new LinkedList<>();
        Map<Integer, Queue<Task>> queues = new TreeMap<>();

        for (Task task : taskQueue) {
            queues.putIfAbsent(task.getPriority(), new LinkedList<>());
            queues.get(task.getPriority()).add(task);
        }

        long start = System.currentTimeMillis();

        while (!queues.isEmpty()) {
            List<Integer> priorities = new ArrayList<>(queues.keySet());
            boolean taskExecuted = false;

            for (int priority : priorities) {
                Queue<Task> queue = queues.get(priority);
                if (queue == null || queue.isEmpty())
                    continue;

                Task task = queue.poll();

                int currentTime = (int) (System.currentTimeMillis() - start);
                this.timer.start(this.quantum, task);
                this.cpu.execute(task);
                this.timer.stop();

                if (task.getRemain() > 0) {
                    queue.add(task);
                }
                String info = "" + currentTime + " : " + task.toString();
                results.add(info);

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
