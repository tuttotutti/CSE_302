package A4;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        int totalPoints = 1_000_000_000;
        int nThread = 10;

        SharedData sharedData = new SharedData();
        MonteCarloThread[] threads = new MonteCarloThread[nThread];

        int pointsPerThread = totalPoints / nThread;

        for (int i = 0; i < nThread; i++) {
            int nPoint = pointsPerThread;
            threads[i] = new MonteCarloThread(nPoint, sharedData);
            threads[i].start();
        }

        for (MonteCarloThread thread : threads) {
            thread.join();
        }

        double pi = 4.0 * sharedData.getPointsInsideCircle() / totalPoints;
        System.out.println(pi);
    }
}