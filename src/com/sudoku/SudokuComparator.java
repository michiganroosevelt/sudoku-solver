package com.sudoku;

import java.util.Comparator;

public class SudokuComparator implements Comparator<SudokuEntry> {

	@Override
	public int compare(SudokuEntry entry1, SudokuEntry entry2) {

		if (entry1.getPossibleValuesLength() > entry2.getPossibleValuesLength()) {
			return 1;
		} else if (entry2.getPossibleValuesLength() > entry1.getPossibleValuesLength()) {
			return -1;
		} else {
			if (entry1.getRowIndex() > entry2.getRowIndex()) {
				return 1;
			} else if (entry2.getRowIndex() > entry1.getRowIndex()) {
				return -1;
			} else {
				if (entry1.getColumnIndex() > entry2.getColumnIndex()) {
					return 1;
				} else if (entry2.getColumnIndex() > entry1.getColumnIndex()) {
					return -1;
				} else {
					return 0;
				}

			}
		}

	}

}
