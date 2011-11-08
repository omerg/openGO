package com.obss.go.api;

import com.obss.go.api.Constants.CELL_X;
import com.obss.go.api.Constants.CELL_Y;
import com.obss.go.exception.CellNotBreathingException;
import com.obss.go.exception.CellNotEmptyException;
import com.obss.go.exception.KOException;

public interface IGamePlayService {

	void 	playTurn(CELL_X x, CELL_Y y) throws CellNotEmptyException, CellNotBreathingException, KOException;

}
