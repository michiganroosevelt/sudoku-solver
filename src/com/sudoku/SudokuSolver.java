package com.sudoku;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SudokuSolver {

	public static boolean isSolved(SudokuBoard sudokuBoard) {
		for (int i = 0; i < SudokuBoard.BOARD_SIZE; i++) {
			for (int j = 0; j < SudokuBoard.BOARD_SIZE; j++) {
				if (sudokuBoard.getValue(i, j) == 0) {
					return false;
				}
			}
		}

		return true;
	}

	public static void solve(SudokuBoard sudokuBoard) throws Exception {
		int counter = 0;
		while (!isSolved(sudokuBoard)) {
			System.out.println("next iteration: " + ++counter);
			solveNextIteration(sudokuBoard);
		}
	}

	private static String getAsString(Map<String, int[]> possibleValues) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < SudokuBoard.BOARD_SIZE; i++) {
			for (int j = 0; j < SudokuBoard.BOARD_SIZE; j++) {
				if( !possibleValues.containsKey(i+"_"+j) ) {
					continue;
				}
				
				StringBuffer innerSb = new StringBuffer();
				for (int k = 0; k < possibleValues.get(i+"_"+j).length; k++) {
					innerSb.append(" " + possibleValues.get(i+"_"+j)[k]);
				}
				sb.append(i + ", " + j + ": " + innerSb + "\n");
			}
		}
		return sb.toString();
	}

	private static void solveNextIteration(SudokuBoard sudokuBoard) throws Exception {
		Map<String, int[]> possibleValues = buildPossibleValues(sudokuBoard);
		System.out.println(getAsString(possibleValues));
		int[][] board = sudokuBoard.getBoard();
		boolean madeProgress = false;
		for (int i = 0; i < SudokuBoard.BOARD_SIZE; i++) {
			for (int j = 0; j < SudokuBoard.BOARD_SIZE; j++) {
				int[] values = possibleValues.get(i + "_" + j);
				if (values != null && values.length == 1 && values[0] != 0) {
					board[i][j] = values[0];
					madeProgress = true;
				}
			}
		}

		if (!madeProgress) {
			throw new Exception("Cannot Solve");
		}

		sudokuBoard.setBoard(board);
	}

	private static Map<String, int[]> buildPossibleValues(SudokuBoard sudokuBoard) {
		Map<String, int[]> allPossibleValues = new HashMap<String, int[]>();

		for (int i = 0; i < SudokuBoard.BOARD_SIZE; i++) {
			for (int j = 0; j < SudokuBoard.BOARD_SIZE; j++) {
				int value = sudokuBoard.getValue(i, j);
				int[] possibleValues = null;
				if (value != 0) {
//					possibleValues = new int[] { 0 };
				} else {
					possibleValues = getPossibleValues(i, j, sudokuBoard);
					allPossibleValues.put(i + "_" + j, possibleValues);
				}
			}
		}

		return allPossibleValues;
	}

	private static int[] getPossibleValues(int rowIndex, int columnIndex, SudokuBoard sudokuBoard) {
		List<Integer> possibleValues = new ArrayList<Integer>();
		for (int i = 0; i < 9; i++) {
			int[][] board = sudokuBoard.getBoard();
			int value = i + 1;
			board[rowIndex][columnIndex] = value;
			SudokuBoard newBoard = new SudokuBoard(board);
			if (SudokuValidator.isValidBoard(newBoard)) {
				possibleValues.add(new Integer(value));
			}
		}

		int[] returnValues = new int[possibleValues.size()];
		for (int i = 0; i < possibleValues.size(); i++) {
			returnValues[i] = possibleValues.get(i);
		}

		return returnValues;
	}

}
