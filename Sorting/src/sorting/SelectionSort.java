package sorting;

public class SelectionSort implements SortingAlgorithm {

	public String getName() {
		return "Selection Sort";
	}

	public void run(DataAccessor data) {
		//avatar is the array
		//goes through the array
		//fire is 'i'
		for (int fire = data.size() - 1; fire > 0; fire--)
		{
			//make first the very first one
			//earth is the first
			int earth = 0;
			//goes through the rest of the array
			for (int water = 1; water <= fire; water++)
			{
				//if the first one is bigger than the second
				if (data.get(water) > data.get(earth))
				{
					//the first is set to the one found
					earth = water;
				}
			}
			//then they switch
			//air is temp
			int air = data.get(earth);
			data.set(earth, data.get(fire));
			data.set(fire, air);
		}

		data.done();
	}
}
