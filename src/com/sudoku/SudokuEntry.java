package com.sudoku;

public class SudokuEntry {

	private int _rowIndex;

	private int _columnIndex;

	private int[] _possibleValues;

	public SudokuEntry(int rowIndex, int columnIndex, int[] possibleValues) {
		_rowIndex = rowIndex;
		_columnIndex = columnIndex;
		_possibleValues = possibleValues;
	}
	
	public void popFirstValue() {
		if( _possibleValues == null || _possibleValues.length == 0 ) {
			return;
		}
		
		int[] newValues = new int[_possibleValues.length - 1];
		for (int i = 1; i < _possibleValues.length; i++) {
			newValues[i - 1] = _possibleValues[i];
		}
		this._possibleValues = newValues;
	}

	public int getRowIndex() {
		return _rowIndex;
	}

	public int getColumnIndex() {
		return _columnIndex;
	}

	public int getPossibleValuesLength() {
		if (_possibleValues == null) {
			return 0;
		} else {
			return _possibleValues.length;
		}
	}

	public int[] getPossibleValues() {
		return _possibleValues;
	}
	
	public int getNextValue() {
		if( this._possibleValues == null || this._possibleValues.length <= 0) {
			return 0;
		} else {
			return this._possibleValues[0];
		}
	}

	@Override
	public String toString() {
		String indexString = "Row Index: " + this._rowIndex + ", Column Index: " + this._columnIndex;
		if (_possibleValues == null) {
			return indexString + " values: null";
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < this._possibleValues.length; i++) {
			sb.append(" " + this._possibleValues[i]);
		}

		return indexString + " values:" + sb.toString();
	}

}
