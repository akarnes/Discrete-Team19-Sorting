package sorting;

/**
 * Implements the bubble sort algorithm.
 */
public class BubbleSort implements SortingAlgorithm {

    public String getName() {
        return "Bubble Sort";
    }

    public void run(DataAccessor data) {
        boolean swapped;
        int loc = 0;
        do {
            swapped = false;
            for (int i = 0; i < data.size() - 1 - loc; i++) {
                if (data.get(i) > data.get(i + 1)) {
                    swapped = true;
                    data.swap(i, i + 1);
                }
            }
            loc++;
        } while (swapped);
        data.done();
    }

}
