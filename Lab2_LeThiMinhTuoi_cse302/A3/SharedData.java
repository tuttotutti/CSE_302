package A3;

import java.util.ArrayList;
import java.util.List;

public class SharedData {
    private int value;
    private List<Integer> primeList;

    public SharedData(int value) {
        this.value = value;
        this.primeList = new ArrayList<>();
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public List<Integer> getPrimeList() {
        return primeList;
    }

    public void setPrimeList(List<Integer> primeList) {
        this.primeList = primeList;
    }
}
