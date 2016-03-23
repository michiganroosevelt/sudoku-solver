package com.sudoku;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.IntNode;
import org.codehaus.jackson.node.TextNode;

public class SudokuBoard {

	private static int BOARD_SIZE = 9;

	private int[][] _board = new int[BOARD_SIZE][BOARD_SIZE];

	public SudokuBoard(int[][] board) {
		this._board = board;
	}

	public void setBoard(int[][] board) {
		this._board = board;
	}

	public int[][] getBoard() {
		int[][] boardCopy = new int[BOARD_SIZE][BOARD_SIZE];

		for (int i = 0; i < BOARD_SIZE; i++) {
			for (int j = 0; j < BOARD_SIZE; j++) {
				boardCopy[i][j] = this._board[i][j];
			}
		}

		return boardCopy;
	}

	public SudokuBoard(ArrayNode node) {
		int i = 0;
		Iterator<JsonNode> itr = node.iterator();
		while (itr.hasNext()) {
			int j = 0;
			ArrayNode innerNode = (ArrayNode) itr.next();
			Iterator<JsonNode> innerItr = innerNode.iterator();
			while (innerItr.hasNext()) {
				Object obj = innerItr.next();
				if (obj instanceof TextNode) {
					TextNode square = (TextNode) obj;
					this._board[i][j++] = square.asInt();
				} else if (obj instanceof IntNode) {
					IntNode square = (IntNode) obj;
					this._board[i][j++] = square.asInt();
				}
			}
			i++;
		}
	}

	public String toString() {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(this._board);
		} catch (Exception e) {
			return "[]";
		}
	}

	public boolean isValidBoard() {
		if (!isValidBlocks()) {
			return false;
		}

		for (int i = 0; i < BOARD_SIZE; i++) {
			if (!isValidRow(i)) {
				return false;
			}
			if (!isValidColumn(i)) {
				return false;
			}
		}

		return true;
	}

	private boolean isValidRow(int index) {
		Map<String, Boolean> row = new HashMap<String, Boolean>();
		for (int i = 0; i < BOARD_SIZE; i++) {
			int value = this._board[index][i];
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

	private boolean isValidColumn(int index) {
		Map<String, Boolean> row = new HashMap<String, Boolean>();
		for (int i = 0; i < BOARD_SIZE; i++) {
			int value = this._board[i][index];
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

	private boolean isValidBlocks() {
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
			if (!isValidBlock(rowIndex, columnIndex)) {
				return false;
			}
		}

		return true;
	}

	private boolean isValidBlock(int rowIndex, int columnIndex) {
		Map<String, Boolean> row = new HashMap<String, Boolean>();
		for (int i = rowIndex; i < rowIndex + 3; i++) {
			for (int j = columnIndex; j < columnIndex + 3; j++) {
				int value = this._board[i][j];
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

	public boolean isSolved() {
		for (int i = 0; i < BOARD_SIZE; i++) {
			for (int j = 0; j < BOARD_SIZE; j++) {
				if (this._board[i][j] == 0) {
					return false;
				}
			}
		}

		return true;
	}

	public void solve() throws Exception {
		while (!isSolved()) {
			solveNextIteration();
		}
	}

	private void solveNextIteration() throws Exception {
		int[][][] possibleValues = buildPossibleValues();
		int[][] board = getBoard();
		boolean madeProgress = false;
		for (int i = 0; i < BOARD_SIZE; i++) {
			for (int j = 0; j < BOARD_SIZE; j++) {
				int[] values = possibleValues[i][j];
				if (values.length == 1 && values[0] != 0) {
					board[i][j] = values[0];
					madeProgress = true;
				}
			}
		}
		
		if( !madeProgress ) {
			throw new Exception("Cannot Solve");
		}

		setBoard(board);
	}

	private int[][][] buildPossibleValues() {
		int[][][] allPossibleValues = new int[BOARD_SIZE][BOARD_SIZE][];
		for (int i = 0; i < BOARD_SIZE; i++) {
			for (int j = 0; j < BOARD_SIZE; j++) {
				int value = this._board[i][j];
				int[] possibleValues = null;
				if (value != 0) {
					possibleValues = new int[] { 0 };
				} else {
					possibleValues = getPossibleValues(i, j);
				}
				allPossibleValues[i][j] = possibleValues;
			}
		}

		return allPossibleValues;
	}

	private int[] getPossibleValues(int rowIndex, int columnIndex) {
		List<Integer> possibleValues = new ArrayList<Integer>();
		for (int i = 0; i < 9; i++) {
			int[][] board = getBoard();
			int value = i + 1;
			board[rowIndex][columnIndex] = value;
			SudokuBoard newBoard = new SudokuBoard(board);
			if (newBoard.isValidBoard()) {
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
