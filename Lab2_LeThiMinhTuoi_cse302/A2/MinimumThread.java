package A2;

import java.util.List;

public class MinimumThread extends Thread {
    private SharedData data;
    
    public MinimumThread(SharedData data){
        this.data = data;
    }

    @Override
    public void run(){
        List<Integer> list = data.getValue();
        int min = Integer.MAX_VALUE;

        for (int i = 0; i < list.size(); i++) {
            int v = list.get(i);
            if(v < min){
                min = v;
            }
        }

        data.setMin(min);
    }
}
