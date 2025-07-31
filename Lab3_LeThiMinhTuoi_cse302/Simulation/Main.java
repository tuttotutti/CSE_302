import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args){
        String filename = "schedule.txt";
        List<Task> taskQueue = new ArrayList<>();

        BufferedReader reader = null;
        try{
            reader = new BufferedReader(new FileReader(filename));
            String line;
            line = reader.readLine();
            while (line != null) {
                String comps[] = line.split(",");
                String name = comps[0];
                int priority = Integer.parseInt(comps[1].trim());
                int burst = Integer.parseInt(comps[2].trim());

                Task task = new Task(name, priority, burst);
                taskQueue.add(task);

                line = reader.readLine();
            }
        } catch (FileNotFoundException e){
            System.err.println(filename+": File not found.");
            return;
        } catch (IOException e){
            System.err.println("There are some errors in reading the file: "+filename);
            return;
        } finally {
            if(reader != null){
                try{
                    reader.close();;
                } catch (IOException e){

                }
            }
        }

        Scheduler scheduler;
        List<String> results;

        System.out.println("-------------------------------------------------------------------");
        System.out.println("FCFS Algorithm: ");
        scheduler = new FCFSScheduler();
        results = scheduler.schedule(taskQueue);
        for(String s : results){
            System.out.println(s);
        }
        for(Task t : taskQueue){
            t.reset();
        }

        System.out.println("-------------------------------------------------------------------");
        System.out.println("Round Robin Algorithm: ");
        scheduler = new RoundRobinScheduler(100);
        results = scheduler.schedule(taskQueue);
        for(String s : results){
            System.out.println(s);
        }
        for(Task t : taskQueue){
            t.reset();
        }

        System.out.println("-------------------------------------------------------------------");
        System.out.println("Priority Algorithm: ");
        scheduler = new PriorityScheduler();
        results = scheduler.schedule(taskQueue);
        for(String s : results){
            System.out.println(s);
        }
        for(Task t : taskQueue){
            t.reset();
        }

        System.out.println("-------------------------------------------------------------------");
        System.out.println("Shortest-Job-First Algorithm: ");
        scheduler = new SJFScheduler();
        results = scheduler.schedule(taskQueue);
        for(String s : results){
            System.out.println(s);
        }
        for(Task t : taskQueue){
            t.reset();
        }

        System.out.println("-------------------------------------------------------------------");
        System.out.println("Priority Round Robin Algorithm: ");
        scheduler = new PriorityRoundRobinScheduler(100);
        results = scheduler.schedule(taskQueue);
        for(String s : results){
            System.out.println(s);
        }
        for(Task t : taskQueue){
            t.reset();
        }
    }
}
