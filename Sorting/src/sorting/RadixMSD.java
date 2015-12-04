package sorting;

import java.util.*;

public class RadixMSD implements SortingAlgorithm {

	public String getName() {
		return "Radix (MSD)";
	}

	public void run(DataAccessor data) {
		ArrayList<Integer> dataList = new ArrayList<>(data.size());
		// Find the largest number
		int max = 0;
		for(int i=0; i<data.size(); i++) {
			int currNum = data.get(i);
			if(currNum > max) {
				max = currNum;
			}
			dataList.add(currNum);
		}

		// Find the digit count of the largest number, telling us how many parent bins we need
		int digitCnt;
		for(digitCnt=0; Math.floor(max / Math.pow(10, digitCnt)) != 0; digitCnt++) {
		}

		// Start sorting into bins
		putInBins(data, 0, data.size(), digitCnt-1);
		data.done();
	}

	private void putInBins(DataAccessor da, int arrayPos, int length, int digitCnt) {
		// Put the numbers into bins
		Map<Integer, ArrayList<Integer>> bins = new LinkedHashMap<>();
		for(int i=0; i<length; i++) {
			int num = da.get(i + arrayPos);
			// Isolate the digit we're soring by
			int digit = (num % (int)Math.pow(10, digitCnt + 1)) / (int)Math.pow(10, digitCnt);
			// Put the digit in the correct bin
			if(bins.containsKey(digit)) {
				bins.get(digit).add(num);
			} else {
				bins.put(digit, new ArrayList<>());
				bins.get(digit).add(num);
			}
		}

		// Put this iteration of bins on the array
		int incArrayPos = arrayPos;
		for(int i=0; i<10; i++) {
			if(bins.containsKey(i)) {
				ArrayList<Integer> bin = bins.get(i);
				for(int j=0; j<bin.size(); j++) {
					da.set(incArrayPos, bin.get(j));
					incArrayPos++;
				}
			}
		}

		// Start a new binning job for each bin
		if(digitCnt-1 >= 0) {
			int nextArrayPos = arrayPos;
			for(int i=0; i<10; i++) {
				if(bins.containsKey(i)) {
					putInBins(da, nextArrayPos, bins.get(i).size(), digitCnt-1);
					nextArrayPos += bins.get(i).size();
				}
			}
		}
	}
}
