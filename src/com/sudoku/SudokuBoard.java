package com.sudoku;

import java.util.Iterator;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.IntNode;
import org.codehaus.jackson.node.TextNode;

public class SudokuBoard {

	public static int BOARD_SIZE = 9;

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

	public int getValue(int rowIndex, int columnIndex) {
		return this._board[rowIndex][columnIndex];
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


}
