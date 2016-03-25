package com.sudoku;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SudokuController {

	static Logger logger = LoggerFactory.getLogger(SudokuSolver.class);

	@RequestMapping(value = "/board", method = RequestMethod.POST)
	public @ResponseBody String solveSudoku(@RequestBody String input) throws Exception {
		logger.debug("Attempt to solve");
		ObjectMapper mapper = new ObjectMapper();
		SudokuBoard board = new SudokuBoard((ArrayNode) mapper.readTree(input));
		SudokuBoard solvedBoard = SudokuSolver.solve(board);
		return solvedBoard.toString();
	}

	@RequestMapping(value = "/board", method = RequestMethod.GET)
	public @ResponseBody String getBoard(HttpServletRequest request) throws Exception {
		String version = request.getParameter("version");
		logger.debug("Loading Board Version: " + version);

		int[][] board = null;
		if ("1".equals(version)) {
			board = getBoard1();
		} else if ("2".equals(version)) {
			board = getBoard2();
		} else if ("3".equals(version)) {
			board = getBoard3();
		} else if ("4".equals(version)) {
			board = getBoard4();
		} else {
			board = getBoard1();
		}

		SudokuBoard sudokuBoard = new SudokuBoard(board);
		return sudokuBoard.toString();
	}

	private int[][] getBoard1() {
		int[][] boardValues = new int[9][9];
		boardValues[0] = new int[] { 9, 0, 0, 0, 8, 0, 4, 5, 0 };
		boardValues[1] = new int[] { 0, 0, 0, 6, 0, 1, 0, 2, 0 };
		boardValues[2] = new int[] { 0, 7, 0, 0, 9, 4, 1, 0, 8 };

		boardValues[3] = new int[] { 7, 0, 6, 0, 0, 0, 2, 0, 0 };
		boardValues[4] = new int[] { 1, 8, 0, 0, 4, 0, 0, 6, 7 };
		boardValues[5] = new int[] { 0, 0, 3, 0, 0, 0, 5, 0, 1 };

		boardValues[6] = new int[] { 3, 0, 2, 9, 7, 0, 0, 4, 0 };
		boardValues[7] = new int[] { 0, 9, 0, 4, 0, 6, 0, 0, 0 };
		boardValues[8] = new int[] { 0, 6, 8, 0, 1, 0, 0, 0, 2 };
		return boardValues;
	}

	private int[][] getBoard2() {
		int[][] boardValues = new int[9][9];
		boardValues[0] = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		boardValues[1] = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		boardValues[2] = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0 };

		boardValues[3] = new int[] { 7, 0, 6, 0, 0, 0, 0, 0, 0 };
		boardValues[4] = new int[] { 1, 8, 0, 0, 0, 0, 0, 0, 0 };
		boardValues[5] = new int[] { 0, 0, 3, 0, 0, 0, 0, 0, 0 };

		boardValues[6] = new int[] { 3, 0, 2, 9, 7, 0, 0, 0, 0 };
		boardValues[7] = new int[] { 0, 9, 0, 4, 0, 6, 0, 0, 0 };
		boardValues[8] = new int[] { 0, 6, 8, 0, 1, 0, 0, 0, 0 };
		return boardValues;
	}

	private int[][] getBoard3() {
		int[][] boardValues = new int[9][9];
		boardValues[0] = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		boardValues[1] = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		boardValues[2] = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0 };

		boardValues[3] = new int[] { 7, 0, 0, 0, 0, 0, 0, 0, 0 };
		boardValues[4] = new int[] { 1, 8, 0, 0, 0, 0, 0, 0, 0 };
		boardValues[5] = new int[] { 0, 0, 3, 0, 0, 0, 0, 0, 0 };

		boardValues[6] = new int[] { 3, 0, 2, 0, 0, 0, 0, 0, 0 };
		boardValues[7] = new int[] { 0, 9, 0, 0, 0, 0, 0, 0, 0 };
		boardValues[8] = new int[] { 0, 6, 8, 0, 0, 0, 0, 0, 0 };
		return boardValues;
	}

	private int[][] getBoard4() {
		int[][] boardValues = new int[9][9];
		boardValues[0] = new int[] { 0, 9, 0, 0, 1, 7, 0, 6, 0 };
		boardValues[1] = new int[] { 6, 4, 0, 9, 2, 0, 0, 0, 0 };
		boardValues[2] = new int[] { 0, 0, 5, 0, 0, 0, 8, 0, 0 };

		boardValues[3] = new int[] { 0, 0, 0, 0, 3, 0, 0, 0, 6 };
		boardValues[4] = new int[] { 7, 0, 0, 2, 0, 6, 0, 0, 3 };
		boardValues[5] = new int[] { 3, 0, 0, 0, 8, 0, 0, 0, 0 };

		boardValues[6] = new int[] { 0, 0, 7, 0, 0, 0, 5, 0, 0 };
		boardValues[7] = new int[] { 0, 0, 0, 0, 7, 1, 0, 2, 4 };
		boardValues[8] = new int[] { 0, 2, 0, 6, 9, 0, 0, 7, 0 };
		return boardValues;
	}

}
