package A3;

import java.util.ArrayList;
import java.util.List;

public class PrimeThread extends Thread{
    private SharedData data;

    public PrimeThread(SharedData data){
        this.data = data;
    }

    @Override
    public void run(){
        int v = data.getValue();
        List<Integer> primeList = new ArrayList<>();

        for (int i = 2; i <= v; i++) {       
            boolean isPrime = true;
            for (int j = 2; j <= Math.sqrt(i); j++) {
                if(i % j == 0){
                    isPrime = false;
                    break;
                }
            }
            if(isPrime){
                primeList.add(i);
            }
            isPrime = true;
        }

        data.setPrimeList(primeList);
    }
}
