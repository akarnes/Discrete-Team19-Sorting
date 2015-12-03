package sorting;

import java.lang.Math;
//implements the Heap Sort algorithm

public class HeapSort implements SortingAlgorithm{

	public String getName() {
		return "Heap Sort";
	}

	public void run(DataAccessor data) {
		buildMaxHeap(data, data.size());

		//end placement of array
		int end = data.size() - 1;

		while (end > 0){
			//dataArr[0] is the root and largest value, move to front of the sorted elements
			data.swap(end, 0);
			//reduce end for next value
			end = end - 1;
			//restoring heap
			siftDown(data, 0, end);
		}

		data.done();
	}
	//function that builds a heap for the array so the largest value is at the root (base of the array)
	public void buildMaxHeap(DataAccessor data, int size){
		//begin's value is index of the last parent node in "arr"
		int begin = (int)(Math.floor((size) / 2.0)) - 1;

		//cycles through list until all nodes are in order
		while (begin >= 0){
			//sift down the node in "arr" at begin until all the nodes below are in heap order
			siftDown(data, begin, size-1);
			//changing value to next parent node not in order
			begin = begin - 1;
		}
	}
	//moves the value at the index of "begin" to the proper location
	public void siftDown(DataAccessor data, int begin, int end){
		//base value to switch to new location
		int base = begin;
		//value to be switched with
		int endVal;
		//tracking value
		int tmpVal;

		while ((base * 2 + 1) <=end){
			endVal = base * 2 + 1;
			tmpVal = base;

			//if switching value is smaller, then replace switching value with the value to be switched
			if (data.get(tmpVal) < data.get(endVal))
				tmpVal = endVal;
			//checks to see if the value directly to the right is larger
			if (endVal + 1 <= end && data.get(tmpVal) < data.get(endVal + 1))
				tmpVal = endVal + 1;
			if (tmpVal == base){
				//base holds the largest element, so sort is done
				return;
			}
			else{
				//swapping values and repeating the sort
				data.swap(base, tmpVal);
				base = tmpVal;
			}
		}
	}
}
