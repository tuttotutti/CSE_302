package A3;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws InterruptedException{
        StringBuilder sb = new StringBuilder();
        Scanner sc = new Scanner(System.in);
        int value = sc.nextInt();
        sc.close();

        SharedData data = new SharedData(value);

        PrimeThread prime = new PrimeThread(data);
        prime.start();
        prime.join();

        List<Integer> list = data.getPrimeList();
        for(int num: list){
            sb.append(num+" ");
        }

        System.out.println(sb);
    }
}
