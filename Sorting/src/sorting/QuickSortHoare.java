package sorting;

/**
 * Implements the bubble sort algorithm.
 */
public class QuickSortHoare implements SortingAlgorithm {

	public String getName() {
		return "Quick Sort (Hoare)";
	}

	public void run(DataAccessor data) {
		int hi = data.size() -1;
		int low = 1;

		quicksort(data,low,hi);

		data.done();

	}

	void quicksort(DataAccessor array, int lo, int hi)
	{
		if(lo < hi)
		{
			int p = partition(array, lo, hi);

			quicksort(array,lo,p);
			quicksort(array,p+1,hi);
		}
	}

	int partition(DataAccessor array, int lo, int hi)
	{
		double pivot = array.get(lo);
		int i = lo -1 ;
		int j = hi;

		while(true)
		{
			while(array.get(j) > pivot)
			{
				j = j - 1;
			}

			while(array.get(i) < pivot)
			{
				i = i + 1;
			}
			if(i < j)
			{
				int tmp =  array.get(i);
				array.set(i,array.get(j));
				array.set(j,tmp);
			}
			else
			{
				return j;
			}
		}
	}
}
