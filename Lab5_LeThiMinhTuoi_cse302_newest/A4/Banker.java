import java.util.ArrayList;

public class Banker {
    private int resourceTypeNum;
    private int threadNum;
    private int[] available;
    private int[][] maximum;
    private int[][] allocation;

    public Banker(int[] avail, int[][] max, int[][] alloc) {

        this.available = avail;
        this.maximum = max;
        this.allocation = alloc;

        this.resourceTypeNum = this.available.length;
        this.threadNum = this.maximum.length;

    }

    public ArrayList<Integer> isSafe() {

        ArrayList<Integer> result = new ArrayList<>();
        // (1)
        int[] work = available.clone();

        int[][] need = new int[this.threadNum][this.resourceTypeNum];
        for (int r = 0; r < this.threadNum; r++) {
            for (int c = 0; c < this.resourceTypeNum; c++) {
                need[r][c] = this.maximum[r][c] - this.allocation[r][c];
            }
        }

        boolean[] finish = new boolean[this.threadNum];
        for (int i = 0; i < this.threadNum; i++) {
            finish[i] = false;
        }

        // (2)
        boolean found = true;
        while (found) {

            found = false;

            for (int threadId = 0; threadId < this.threadNum; threadId++) {
                if (finish[threadId] == false) {
                    if (this.checkNeed(need[threadId], work) == true) {
                        // (3)
                        for (int i = 0; i < this.resourceTypeNum; i++) {
                            work[i] += this.allocation[threadId][i];
                        }
                        finish[threadId] = true;
                        found = true;
                        result.add(threadId);
                    }
                }
            }

        }
        // (4)
        for (int i = 0; i < this.threadNum; i++) {
            if (finish[i] == false) {
                return null;
            }
        }
        return result;
    }

    private boolean checkNeed(int[] need_i, int[] work) {
        for (int i = 0; i < this.resourceTypeNum; i++) {
            if (need_i[i] > work[i]) {
                return false;
            }
        }
        return true;
    }

    public ArrayList<Integer> request(int threadId, int[] request) {
        ArrayList<Integer> result = new ArrayList<>();

        for (int i = 0; i < resourceTypeNum; i++) {

            if (request[i] > maximum[threadId][i] - allocation[threadId][i]) {
                return null;
            }

        }

        for (int i = 0; i < resourceTypeNum; i++) {

            if (request[i] > available[i]) {
                return null;
            }

        }

        for (int i = 0; i < resourceTypeNum; i++) {

            available[i] -= request[i];
            allocation[threadId][i] += request[i];

        }

        result = isSafe();

        return result;
    }
}
