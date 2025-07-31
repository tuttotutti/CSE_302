public class Task {
    private String name;
    private int priority;
    private int burst;
    private int duration;
    private int remain;

    public Task(String name, int priority, int burst) {
        this.name = name;
        this.priority = priority;
        this.burst = burst;
        
        this.duration = 0;
        this.remain = this.burst;
    }

    public void reset(){
        this.duration = 0;
        this.remain = this.burst;
    }

    public String getName() {
        return name;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getBurst() {
        return burst;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getRemain() {
        return remain;
    }

    public void setRemain(int remain) {
        this.remain = remain;
    }

    @Override
    public String toString() {
        return this.name+", Pri: " + this.priority + " - Burst: " + burst + " - Duration: " + duration
                + " - Remain: " + remain;
    }
}
