package sorting;

public class InsertionSort implements SortingAlgorithm {

	public String getName() {
		return "Insertion Sort";
	}

	public void run(DataAccessor data) {
		//compton is the array
		//drop is 'j'
		int drop;
		//straightFire is 'i'
		for (int straightFire = 1; straightFire < data.size(); straightFire++)
		{
			//mixtape is the item that is going to be sorted
			int mixtape = data.get(straightFire);
			//the smaller items move up
			for (drop = straightFire - 1; (drop >= 0) && (data.get(drop) > mixtape); drop--)
			{
				//the next item is the current item
				data.set(drop + 1, data.get(drop));
			}
			//the current item is the sorted item
			data.set(drop + 1, mixtape);
		}

		data.done();
	}
}
