package sorting;

import java.util.Random;

public class ShotGunSort implements SortingAlgorithm {

    boolean sorted = false;

    public String getName() {
        return "Shot Gun Sort (aka: Useless Sort)";
    }

    public void run(DataAccessor data) {
        Random rand = new Random();
        int tmp;
        for (int runNum = 0; runNum < 100; ++runNum) {
            sorted = true;
            for (int i = 0; i < data.size(); ++i) {
                int j = rand.nextInt(i + 1);
                tmp = data.get(j);
                data.set(j, data.get(i));
                data.set(i, tmp);
            }
            for (int k = 0; k < data.size() - 1; k++) {
                if (data.get(k) > data.get(k + 1)) {
                    sorted = false;
                }
            }

            if (sorted == true) {
                data.done();
                break;
            }
        }
    }

}
