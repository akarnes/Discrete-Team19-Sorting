package sorting;

/**
 * Represents a sorting algorithm implementation.
 */
public interface SortingAlgorithm {

	/**
	 * Returns the name of the sorting algorithm.
	 * @return name of the algorithm
	 */
	public String getName();

	/**
	 * Runs the algorithm.
	 * @param data Object for accessing the data points which keeps track of metrics
	 */
	public void run(DataAccessor data);

}
