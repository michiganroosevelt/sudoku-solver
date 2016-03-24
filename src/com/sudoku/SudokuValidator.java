package com.sudoku;

import java.util.HashMap;
import java.util.Map;

public class SudokuValidator {
	
	public static boolean isSolved(SudokuBoard sudokuBoard) {
		for (int i = 0; i < SudokuBoard.BOARD_SIZE; i++) {
			for (int j = 0; j < SudokuBoard.BOARD_SIZE; j++) {
				if (sudokuBoard.getValue(i,j) == 0) {
					return false;
				}
			}
		}

		return true;
	}


	public static boolean isValidBoard(SudokuBoard sudokuBoard) {
		if (!isValidBlocks(sudokuBoard)) {
			return false;
		}

		for (int i = 0; i < SudokuBoard.BOARD_SIZE; i++) {
			if (!isValidRow(i,sudokuBoard)) {
				return false;
			}
			if (!isValidColumn(i, sudokuBoard)) {
				return false;
			}
		}

		return true;
	}

	private static boolean isValidRow(int index, SudokuBoard sudokuBoard) {
		Map<String, Boolean> row = new HashMap<String, Boolean>();
		for (int i = 0; i < SudokuBoard.BOARD_SIZE; i++) {
			int value =  sudokuBoard.getValue(index,i);
			if (value == 0) {
				continue;
			}

			if (row.containsKey(Integer.toString(value))) {
				return false;
			} else {
				row.put(Integer.toString(value), new Boolean(true));
			}
		}

		return true;
	}

	private static boolean isValidColumn(int index, SudokuBoard sudokuBoard) {
		Map<String, Boolean> row = new HashMap<String, Boolean>();
		for (int i = 0; i < SudokuBoard.BOARD_SIZE; i++) {
			int value = sudokuBoard.getValue(i,index);
			if (value == 0) {
				continue;
			}

			if (row.containsKey(Integer.toString(value))) {
				return false;
			} else {
				row.put(Integer.toString(value), new Boolean(true));
			}
		}

		return true;
	}

	private static boolean isValidBlocks(SudokuBoard board) {
		int[][] startingPoints = new int[9][2];
		startingPoints[0] = new int[] { 0, 0 };
		startingPoints[1] = new int[] { 0, 3 };
		startingPoints[2] = new int[] { 0, 6 };
		startingPoints[3] = new int[] { 3, 0 };
		startingPoints[4] = new int[] { 3, 3 };
		startingPoints[5] = new int[] { 3, 6 };
		startingPoints[6] = new int[] { 6, 0 };
		startingPoints[7] = new int[] { 6, 3 };
		startingPoints[8] = new int[] { 6, 6 };

		for (int i = 0; i < startingPoints.length; i++) {
			int rowIndex = startingPoints[i][0];
			int columnIndex = startingPoints[i][1];
			if (!isValidBlock(rowIndex, columnIndex, board)) {
				return false;
			}
		}

		return true;
	}

	private static boolean isValidBlock(int rowIndex, int columnIndex, SudokuBoard sudokuBoard) {
		Map<String, Boolean> row = new HashMap<String, Boolean>();
		for (int i = rowIndex; i < rowIndex + 3; i++) {
			for (int j = columnIndex; j < columnIndex + 3; j++) {
				int value = sudokuBoard.getValue(i,j);
				if (value == 0) {
					continue;
				}

				if (row.containsKey(Integer.toString(value))) {
					return false;
				} else {
					row.put(Integer.toString(value), new Boolean(true));
				}
			}
		}

		return true;
	}

	
}
