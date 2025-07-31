package A2;

import java.util.List;

public class MaximumThread extends Thread {
    private SharedData data;
    
    public MaximumThread(SharedData data){
        this.data = data;
    }

    @Override
    public void run(){
        List<Integer> list = data.getValue();
        int max = Integer.MIN_VALUE;

        for (int i = 0; i < list.size(); i++) {
            int v = list.get(i);
            if(v > max){
                max = v;
            }
        }

        data.setMax(max);
    }
}
