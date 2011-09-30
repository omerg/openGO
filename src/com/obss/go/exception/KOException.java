package com.obss.go.exception;

import com.obss.go.api.Constants.CELL_X;
import com.obss.go.api.Constants.CELL_Y;

public class KOException extends GoException {

	public KOException(CELL_X x, CELL_Y y) {
		super("KO Rule prevents moving into cell: "+ x +", " + y + ".");
	}
}
