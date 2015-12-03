package sorting;

/**
 * Implements the merge sort algorithm.
 */
public class MergeSort implements SortingAlgorithm {

	public String getName() {
		return "Merge Sort";
	}

	public void run(DataAccessor data) {
		int[] work = new int[data.size()];

		// Increase subarray size
		for(int width = 1; width < data.size(); width *= 2) {
			// Loop through every two subarrays
			for(int i = 0; i < data.size(); i += 2*width) {
				bottomUpMerge(data, i, Math.min(i+width, data.size()), Math.min(i+2*width, data.size()), work);
			}

			for(int i=0; i < data.size(); i++) {
				data.set(i, work[i]);
			}
		}

		data.done();
	}

	private static void bottomUpMerge(DataAccessor a, int iLeft, int iRight, int iEnd, int[] work) {
		int i0 = iLeft;
		int i1 = iRight;

		// Loop through every element of the two subarrays
		for(int j = iLeft; j < iEnd; j++) {
			if(i0 < iRight && (i1 >= iEnd || a.get(i0) <= a.get(i1))) {
				// Source from the first subarray if we still have elements in
				// there, we're out of elements in the second subarray, or the
				// element of the first subarray is less than the second.
				work[j] = a.get(i0);
				i0++;
			} else {
				// Source from the second subarray if we're out of elements in
				// the first or if the second is less than the first.
				work[j] = a.get(i1);
				i1++;
			}
		}
	}

}
