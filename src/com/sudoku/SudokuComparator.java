package com.sudoku;

import java.util.Comparator;

public class SudokuComparator implements Comparator<int[][]> {

	@Override
	public int compare(int[][] o1, int[][] o2) {
		int[] a = o1[0];
		int[] b = o2[0];
		
		if( a.length > b.length ) {
			return 1;
		} else if( b.length > a.length ) {
			return -1;
		} else {
			return 0;
		}
	}

}
