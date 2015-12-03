package sorting;

/**
 * Implements the bubble sort algorithm.
 */
public class QuickSortLomuto implements SortingAlgorithm {

    public String getName() {
        return "Quick Sort (Lomuto)";
    }

    public void run(DataAccessor data) {
        int hi = data.size() -1;
        int low = 0;

        quicksort(data,low,hi);

		data.done();
    }

    void quicksort(DataAccessor array, int lo, int hi)
    {
        if(lo < hi)
        {
            int p = partition(array, lo, hi);

            quicksort(array,lo,p-1);
            quicksort(array,p+1,hi);
        }
    }

    int partition(DataAccessor array, int lo, int hi)
    {
        int pivot = array.get(hi);
        int i = lo;
        for(int j = lo; j < hi; j ++)
        {
            if(array.get(j) <= pivot)
            {
                int tmp =  array.get(i);
                array.set(i,array.get(j));
                array.set(j,tmp);
                i = i + 1;
            }
        }
        int tmp =  array.get(i);
        array.set(i,array.get(hi));
        array.set(hi,tmp);


        return i;
    }
}
