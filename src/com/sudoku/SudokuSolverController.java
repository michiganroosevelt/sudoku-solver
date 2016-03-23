package com.sudoku;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SudokuSolverController {

	@RequestMapping(value = "/board", method = RequestMethod.POST)
	public @ResponseBody String solveSudoku(@RequestBody String input) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		SudokuBoard board = new SudokuBoard((ArrayNode) mapper.readTree(input));
		board.solve();
		return board.toString();
	}

	@RequestMapping(value = "/board", method = RequestMethod.GET)
	public @ResponseBody String getBoard() throws Exception {
		int[][] boardValues = new int[9][9];
		boardValues[0] = new int[]{ 9,0,0, 0,8,0, 4,5,0 };
		boardValues[1] = new int[]{ 0,0,0, 6,0,1, 0,2,0 };
		boardValues[2] = new int[]{ 0,7,0, 0,9,4, 1,0,8 };

		boardValues[3] = new int[]{ 7,0,6, 0,0,0, 2,0,0 };
		boardValues[4] = new int[]{ 1,8,0, 0,4,0, 0,6,7 };
		boardValues[5] = new int[]{ 0,0,3, 0,0,0, 5,0,1 };
		
		boardValues[6] = new int[]{ 3,0,2, 9,7,0, 0,4,0 };
		boardValues[7] = new int[]{ 0,9,0, 4,0,6, 0,0,0 };
		boardValues[8] = new int[]{ 0,6,8, 0,1,0, 0,0,2 };
		
		SudokuBoard board = new SudokuBoard(boardValues);
		return board.toString();
	}

}
