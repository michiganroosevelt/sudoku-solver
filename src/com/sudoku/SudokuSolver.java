package com.sudoku;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

	public static SudokuBoard solve(SudokuBoard sudokuBoard) throws SudokuSquareException {
		int counter = 0;
		while (!isSolved(sudokuBoard)) {
			System.out.println("next iteration: " + ++counter);
			boolean madeProgress = solveNextIteration(sudokuBoard);
			if( madeProgress ) {
				continue;
			}
			
			SudokuEntry nextSquare = buildPossibleValues(sudokuBoard).get(0);
			sudokuBoard = solveNextIterationGuessing(sudokuBoard, nextSquare);
		}
		
		return sudokuBoard;
	}
	
	private static SudokuBoard solveNextIterationGuessing(SudokuBoard sudokuBoard, SudokuEntry nextSquare) {
		try {
			SudokuBoard copy = new SudokuBoard(sudokuBoard);
			if( nextSquare.getPossibleValuesLength() == 0 ) {
				nextSquare = buildPossibleValues(sudokuBoard).get(1);
			}
			copy.setSquareValue(nextSquare.getRowIndex(), nextSquare.getColumnIndex(), nextSquare.getPossibleValues()[0]);
			SudokuBoard solvedBoard = solve(copy);
			return solvedBoard;
		} catch (SudokuSquareException e) {
			int[] values = nextSquare.getPossibleValues();
			int[] newValues = new int[values.length-1];
			for( int i = 1; i < values.length; i++ ) {
				newValues[i-1] = values[i];
			}
			SudokuEntry newEntry = new SudokuEntry(nextSquare.getRowIndex(), nextSquare.getColumnIndex(), newValues);
			return solveNextIterationGuessing(sudokuBoard, newEntry);
		}
	}

	private static boolean solveNextIteration(SudokuBoard sudokuBoard) throws SudokuSquareException {
		List<SudokuEntry> possibleValues = buildPossibleValues(sudokuBoard);
		int[][] board = sudokuBoard.getBoardCopy();
		boolean madeProgress = false;

		for (SudokuEntry entry : possibleValues) {
			if (entry.getPossibleValuesLength() == 1 && entry.getPossibleValues()[0] != 0) {
				board[entry.getRowIndex()][entry.getColumnIndex()] = entry.getPossibleValues()[0];
				madeProgress = true;
			}
		}

		sudokuBoard.setBoard(board);
		return madeProgress;
	}

	private static List<SudokuEntry> buildPossibleValues(SudokuBoard sudokuBoard) throws SudokuSquareException {
		List<SudokuEntry> allPossibleValues = new ArrayList<SudokuEntry>();

		for (int i = 0; i < SudokuBoard.BOARD_SIZE; i++) {
			for (int j = 0; j < SudokuBoard.BOARD_SIZE; j++) {
				int value = sudokuBoard.getValue(i, j);
				int[] possibleValues = null;
				if (value == 0) {
					possibleValues = getPossibleValues(i, j, sudokuBoard);
					allPossibleValues.add(new SudokuEntry(i, j, possibleValues));
				}
			}
		}

		Collections.sort(allPossibleValues, new SudokuComparator());
		return allPossibleValues;
	}

	private static int[] getPossibleValues(int rowIndex, int columnIndex, SudokuBoard sudokuBoard)
			throws SudokuSquareException {
		List<Integer> possibleValues = new ArrayList<Integer>();
		for (int i = 0; i < 9; i++) {
			int[][] board = sudokuBoard.getBoardCopy();
			int value = i + 1;
			board[rowIndex][columnIndex] = value;
			SudokuBoard newBoard = new SudokuBoard(board);
			if (SudokuValidator.isValidEntry(rowIndex, columnIndex, newBoard)) {
				possibleValues.add(new Integer(value));
			}
		}

		if (possibleValues.size() == 0) {
			throw new SudokuSquareException("Could not find possible values for squre: rowIndex: " + rowIndex
					+ ", columnIndex: " + columnIndex);
		}

		int[] returnValues = new int[possibleValues.size()];
		for (int i = 0; i < possibleValues.size(); i++) {
			returnValues[i] = possibleValues.get(i);
		}

		return returnValues;
	}

	private static void print(List<SudokuEntry> possibleValues) {
		for (SudokuEntry entry : possibleValues) {
			System.out.println(entry.toString());
		}
	}

}
