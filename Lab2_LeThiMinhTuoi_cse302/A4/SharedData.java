package A4;

public class SharedData {
    private int totalPointsInsideCircle;

    public SharedData() {
        this.totalPointsInsideCircle = 0;
    }

    public synchronized void addToTotalPointsInsideCircle() {
        totalPointsInsideCircle++;
    }

    public int getPointsInsideCircle() {
        return totalPointsInsideCircle;
    }
}