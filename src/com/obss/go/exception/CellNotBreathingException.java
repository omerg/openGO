package com.obss.go.exception;

import com.obss.go.api.Constants.CELL_X;
import com.obss.go.api.Constants.CELL_Y;

public class CellNotBreathingException extends GoException {

	public CellNotBreathingException(CELL_X x, CELL_Y y) {
		super("Cell (" + x.getValue() + ", " + y.getValue() + ") is not breathing");
	}
}
