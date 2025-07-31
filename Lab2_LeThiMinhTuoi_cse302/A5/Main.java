package A5;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws InterruptedException{
        StringBuilder sb = new StringBuilder();
        Scanner sc = new Scanner(System.in);
        int size = sc.nextInt();
        sc.close();

        SharedData data = new SharedData(size);

        FibThread fib = new FibThread(data);
        fib.start();
        fib.join();

        int[] fibArray = data.getFib();
        for(int num: fibArray){
            sb.append(num+" ");
        }

        System.out.println(sb);
    }
}
