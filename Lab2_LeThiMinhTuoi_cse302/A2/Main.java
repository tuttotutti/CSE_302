package A2;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws InterruptedException{
        List<Integer> inputList = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        int size = 5;
        for (int i = 0; i < size; i++){
            int input = sc.nextInt();
            inputList.add(input);
        }
        sc.close();

        SharedData data = new SharedData(inputList);

        AverageThread ave = new AverageThread(data);
        MaximumThread max = new MaximumThread(data);
        MinimumThread min = new MinimumThread(data);
        
        //Average
        ave.start();
        ave.join();

        //Max
        max.start();
        max.join();

        //Min
        min.start();
        min.join();

        //output
        System.out.println("Average: "+data.getAverage());
        System.out.println("Max: "+data.getMax());
        System.out.println("Min: "+data.getMin());

    }
}
