import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        int[] available = { 3, 3, 2 };
        int[][] allocation = {
                { 0, 1, 0 },
                { 2, 0, 0 },
                { 3, 0, 2 },
                { 2, 1, 1 },
                { 0, 0, 2 }
        };
        int[][] maximum = {
                { 7, 5, 3 },
                { 3, 2, 2 },
                { 9, 0, 2 },
                { 2, 2, 2 },
                { 4, 3, 3 }
        };

        Banker banker = new Banker(available, maximum, allocation);
        ArrayList<Integer> result;
        result = banker.isSafe();
        if (result == null) {
            System.out.println("The system is NOT safe.");
        } else {
            System.out.println("The system IS safe.");
        }

        int[] request = { 1, 0, 2 };
        result = banker.request(1, request);
        if (result == null) {
            System.out.println("The request is NOT granted.");
        } else {
            System.out.println("The request IS granted.");
        }

        int[] request2 = { 3, 3, 0 };
        result = banker.request(4, request2);
        if (result == null) {
            System.out.println("The request is NOT granted.");
        } else {
            System.out.println("The request IS granted.");
        }

        int[] request3 = { 0, 2, 0 };
        result = banker.request(0, request3);
        if (result == null) {
            System.out.println("The request is NOT granted.");
        } else {
            System.out.println("The request IS granted.");
        }
    }
}
