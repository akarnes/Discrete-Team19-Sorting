package sorting;

/**
 * Implements the shell sort algorithm that uses the Ciura gap sequence.
 */
public class ShellSortCiura implements SortingAlgorithm {

	private final int[] gaps = {301, 132, 57, 23, 10, 4, 1};

	public String getName() {
		return "Shell Sort (Ciura)";
	}

	public void run(DataAccessor data) {
		// Slowly tighten our gap between compared elements
		for(int gap : gaps) {
			for(int i=gap; i<data.size(); i++) {
				int temp = data.get(i);
				int j;
				for(j=i; j >= gap && data.get(j-gap) > temp; j -= gap) {
					data.set(j, data.get(j - gap));
				}
				data.set(j, temp);
			}
		}

		data.done();
	}

}
