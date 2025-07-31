package A2;

import java.util.List;

public class AverageThread extends Thread {
    private SharedData data;
    
    public AverageThread(SharedData data){
        this.data = data;
    }

    @Override
    public void run(){
        List<Integer> list = data.getValue();
        double average = 0;

        for (int i = 0; i < list.size(); i++) {
            average += list.get(i);
        }

        average /= list.size();
        data.setAverage(average);
    }

}
