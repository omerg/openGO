package com.obss.go.exception;

import com.obss.go.api.Constants;

public class CellCoordinateOutOfBoundsException extends GoException {
	
	public CellCoordinateOutOfBoundsException(Integer coordinate) {
		super("Invalid cellPosition: " + coordinate + " for "
				+ Constants.CELLS_PER_ROW + " sized board.");
	}
}
