package com.sudoku;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SudokuSolver {

	static Logger logger = LoggerFactory.getLogger(SudokuSolver.class);

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
		return solve(sudokuBoard, 0);
	}

	public static SudokuBoard solve(SudokuBoard sudokuBoard, int attempt) throws SudokuSquareException {
		int counter = 0;
		while (!isSolved(sudokuBoard)) {
			counter++;
			logger.debug("Solving Attempt: " + attempt + ", Current Loop: " + counter);
			boolean madeProgress = solveNextIteration(sudokuBoard);
			if (madeProgress) {
				continue;
			}

			SudokuEntry nextSquare = buildPossibleValues(sudokuBoard).get(0);
			sudokuBoard = solveNextIterationGuessing(sudokuBoard, nextSquare, attempt);
		}

		return sudokuBoard;
	}

	private static SudokuEntry getNextSquare(SudokuBoard sudokuBoard, SudokuEntry currentSquare)
			throws SudokuSquareException {
		List<SudokuEntry> squares = buildPossibleValues(sudokuBoard);

		for (SudokuEntry entry : squares) {
			if ((entry.getColumnIndex() <= currentSquare.getColumnIndex())
					&& (entry.getRowIndex() <= currentSquare.getRowIndex())) {
				continue;
			} else {
				return entry;
			}
		}

		throw new SudokuSquareException("ran out of possible squares");
	}

	private static SudokuBoard solveNextIterationGuessing(SudokuBoard sudokuBoard, SudokuEntry nextSquare,
			int attempt) throws SudokuSquareException {
		try {
			SudokuBoard copy = new SudokuBoard(sudokuBoard);
			if (nextSquare.getPossibleValuesLength() == 0) {
				nextSquare = getNextSquare(sudokuBoard, nextSquare);
			}

			int rowIndex = nextSquare.getRowIndex();
			int columnIndex = nextSquare.getColumnIndex();
			int value = nextSquare.getNextValue();

			logger.debug("guessing for row " + rowIndex + ", col: " + columnIndex + ", with value: " + value);
			copy.setSquareValue(rowIndex, columnIndex, value);
			SudokuBoard solvedBoard = solve(copy, (attempt + 1));
			return solvedBoard;
		} catch (SudokuSquareException e) {
			nextSquare.popFirstValue();
			int rowIndex = nextSquare.getRowIndex();
			int columnIndex = nextSquare.getColumnIndex();
			int value = nextSquare.getNextValue();

			logger.debug("SudokuSquareException:  " + rowIndex + ", col: " + columnIndex + ", with value: " + value);
			SudokuBoard copy = new SudokuBoard(sudokuBoard);
			copy.setSquareValue(rowIndex, columnIndex, value);
			
			return solve(copy, (attempt+1));
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
		logger.debug("Possible Value Size: " + allPossibleValues.size());
		for (SudokuEntry entry : allPossibleValues) {
			logger.debug(entry.toString());
		}
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

}
