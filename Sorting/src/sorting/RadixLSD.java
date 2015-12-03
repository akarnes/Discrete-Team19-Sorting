package sorting;

import java.util.LinkedList;
import java.util.Queue;

public class RadixLSD implements SortingAlgorithm {

    public String getName() {
        return "Radix (LSD)";
    }

    public void run(DataAccessor arr) {
        Queue<Integer>[] buckets = new Queue[10];
        for (int i = 0; i < 10; i++) {
            buckets[i] = new LinkedList<Integer>();
        }

        boolean sorted = false;
        int expo = 1;

        while (!sorted) {
            sorted = true;

            for (int item = 0; item < arr.size(); item++) {
                int bucket = (arr.get(item) / expo) % 10;
                if (bucket > 0) {
                    sorted = false;
                }
                buckets[bucket].add(arr.get(item));
            }

            expo *= 10;
            int index = 0;

            for (Queue<Integer> bucket : buckets) {
                while (!bucket.isEmpty()) {
                    arr.set(index++, bucket.remove());
                }
            }
        }
        arr.done();
    }
}
