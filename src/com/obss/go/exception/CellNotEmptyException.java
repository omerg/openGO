package com.obss.go.exception;

import com.obss.go.api.Constants.CELL_X;
import com.obss.go.api.Constants.CELL_Y;

public class CellNotEmptyException extends GoException {

	public CellNotEmptyException(CELL_X x, CELL_Y y) {
		super("Cell (" + x.getValue() + ", " + y.getValue() + ") is not empty");
	}
}
