package A2;

import java.util.ArrayList;
import java.util.List;

public class SharedData {
    private List<Integer> value = new ArrayList<>();
    private double average;
    private int max;
    private int min;

    public SharedData(List<Integer> value) {
        this.value = value;
        this.average = 0;
        this.max = Integer.MIN_VALUE;
        this.min = Integer.MAX_VALUE;
    }

    public List<Integer> getValue() {
        return value;
    }

    public void setValue(List<Integer> value) {
        this.value = value;
    }

    public double getAverage() {
        return average;
    }

    public void setAverage(double average) {
        this.average = average;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    
    
}
