package com.obss.go.util;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.obss.go.api.Constants;
import com.obss.go.api.Constants.CELL_STATUS;
import com.obss.go.api.Constants.CELL_X;
import com.obss.go.api.Constants.CELL_Y;
import com.obss.go.exception.CellCoordinateOutOfBoundsException;
import com.obss.go.model.Cell;
import com.obss.go.model.Game;
import com.obss.go.model.Player;
import com.obss.go.model.StoneGroup;

public class GamePlayUtils {

	// logger
	private static final Logger logger = Logger.getLogger("GamePlayUtils");

	public static Point getCellPoint(CELL_X x, CELL_Y y) {
		int px = Constants.TABLE_MARGIN - (Constants.CELL_SIDE_WIDTH / 2)
				+ (x.getValue() * Constants.CELL_SIDE_WIDTH);
		int py = Constants.TABLE_MARGIN - (Constants.CELL_SIDE_WIDTH / 2)
				+ (y.getValue() * Constants.CELL_SIDE_WIDTH);
		return new Point(px, py);
	}

	/**
	 * Given an Integer, returns logically corresponding CELL_X object.
	 * 
	 * @param x
	 *            Integer
	 * @return CELL_X
	 * @throws CellCoordinateOutOfBoundsException
	 */
	public static CELL_X getCellX(Integer x)
			throws CellCoordinateOutOfBoundsException {
		if (x < 0 && x > Constants.CELLS_PER_ROW) {
			throw new CellCoordinateOutOfBoundsException(x);
		}
		switch (x) {
		case 0:
			return CELL_X.X0;
		case 1:
			return CELL_X.X1;
		case 2:
			return CELL_X.X2;
		case 3:
			return CELL_X.X3;
		case 4:
			return CELL_X.X4;
		case 5:
			return CELL_X.X5;
		case 6:
			return CELL_X.X6;
		case 7:
			return CELL_X.X7;
		case 8:
			return CELL_X.X8;
		case 9:
			return CELL_X.X9;
		case 10:
			return CELL_X.X10;
		case 11:
			return CELL_X.X11;
		case 12:
			return CELL_X.X12;
		default:
			throw new CellCoordinateOutOfBoundsException(x);
		}
	}

	/**
	 * Given an Integer, returns logically corresponding CELL_Y object.
	 * 
	 * @param y
	 *            Integer
	 * @return CELL_Y
	 * @throws CellCoordinateOutOfBoundsException
	 */
	public static CELL_Y getCellY(Integer y)
			throws CellCoordinateOutOfBoundsException {
		if (y < 0 && y > Constants.CELLS_PER_ROW) {
			throw new CellCoordinateOutOfBoundsException(y);
		}
		switch (y) {
		case 0:
			return CELL_Y.Y0;
		case 1:
			return CELL_Y.Y1;
		case 2:
			return CELL_Y.Y2;
		case 3:
			return CELL_Y.Y3;
		case 4:
			return CELL_Y.Y4;
		case 5:
			return CELL_Y.Y5;
		case 6:
			return CELL_Y.Y6;
		case 7:
			return CELL_Y.Y7;
		case 8:
			return CELL_Y.Y8;
		case 9:
			return CELL_Y.Y9;
		case 10:
			return CELL_Y.Y10;
		case 11:
			return CELL_Y.Y11;
		case 12:
			return CELL_Y.Y12;
		default:
			throw new CellCoordinateOutOfBoundsException(y);
		}
	}

	public static Cell getCellByPoint(Point point) {
		Integer roundedX = (((int) point.getX()) - (Constants.TABLE_MARGIN - (Constants.CELL_SIDE_WIDTH / 2)))
				/ Constants.CELL_SIDE_WIDTH;
		Integer roundedY = (((int) point.getY()) - (Constants.TABLE_MARGIN - (Constants.CELL_SIDE_WIDTH / 2)))
				/ Constants.CELL_SIDE_WIDTH;

		if (roundedX > 12 || roundedY > 12) {
			return null;
		}
		// get highlighted cell
		Cell highlighedCell = null;
		try {
			highlighedCell = Game.getCells().get(
					new Cell(GamePlayUtils.getCellX(roundedX), GamePlayUtils
							.getCellY(roundedY)).toString());
		} catch (CellCoordinateOutOfBoundsException e1) {
			Utils.messageHandler(null, e1.getMessage());
			e1.printStackTrace();
		}
		return highlighedCell;
	}
	
	/**
	 * check if cell is empty
	 * @param x
	 * @param y
	 * @return
	 */
	public static Boolean isCellEmpty(CELL_X x, CELL_Y y) {
		if (CELL_STATUS.EMPTY.equals(Game.getCells().get(new Cell(x, y).toString()).getStatus()) == false) {
			return false;
		}
		return true;
	}

	public static List<Cell> getNeighboringCells(Cell c) {

		Integer xInt = c.getCellX().getValue();
		Integer yInt = c.getCellY().getValue();

		List<Cell> cellList = new ArrayList<Cell>();

		try {
			Cell cellTop = getCellByPoint(getCellPoint(getCellX(xInt - 1),
					getCellY(yInt)));
			cellList.add(cellTop);
		} catch (CellCoordinateOutOfBoundsException e) {
			logger.debug(e.getMessage());
		}
		try {
			Cell cellLeft = getCellByPoint(getCellPoint(getCellX(xInt),
					getCellY(yInt - 1)));
			cellList.add(cellLeft);
		} catch (CellCoordinateOutOfBoundsException e) {
			logger.debug(e.getMessage());
		}
		try {
			Cell cellBottom = getCellByPoint(getCellPoint(getCellX(xInt + 1),
					getCellY(yInt)));
			cellList.add(cellBottom);
		} catch (CellCoordinateOutOfBoundsException e) {
			logger.debug(e.getMessage());
		}
		try {
			Cell cellRight = getCellByPoint(getCellPoint(getCellX(xInt),
					getCellY(yInt + 1)));
			cellList.add(cellRight);
		} catch (CellCoordinateOutOfBoundsException e) {
			logger.debug(e.getMessage());
		}
		return cellList;
	}

	/**
	 * 
	 * Check if a given cell is free to place stone fir a given player
	 * 
	 * @param c
	 * @return
	 */
	public static Boolean isCellAvailableFor(Cell c, Player player) {

		List<Cell> neighboringCellsList = getNeighboringCells(c);

		// iterate neighboring cells
		for (Cell neighboringCell : neighboringCellsList) {

			// return true if any of the neighboring cells is empty
			if (Constants.CELL_STATUS.EMPTY.equals(neighboringCell.getStatus())) {
				logger.debug("Cell: " + c.toString()
						+ " breathes from position: "
						+ neighboringCell.toString());
				return true;
			}

			// return true if the cell color is same with player color
			if (neighboringCell.getStatus() == player.getColor()) {
				return true;
			}
		}

		// finally check for "atari" conditions
		// iterate neighboring cells
		for (Cell neighboringCell : neighboringCellsList) {

			// get neighboring stone group
			StoneGroup stoneGroup = Game.getGroupMap().get(
					neighboringCell.getGroupId());

			if (!isGroupBreathingIfPlayed(stoneGroup, neighboringCell, player)) {
				logger.info("Atari situation enables playing in position: "
						+ c.toString());
				return true;
			}
		}

		return false;
	}

	/**
	 * 
	 * check if stoneGroup breathes
	 * 
	 * @return Boolean
	 */
	public static Boolean isGroupBreathing(StoneGroup s) {
		for (Cell listMemberCell : s.getCellList()) {
			if (listMemberCell.isBreathing()) {
				return true;
			}
		}
		return false;
	}

	/**
	 *<p>When a player wants to place a stone in a given cell,
	 * if cellBreating validation is performed before re-evaluating
	 * the game state for captured stones, the result of the 
	 * validation may be wrong. 
	 * 
	 * <p>This method pre-evaluates the game state that is expected
	 * after turn would be taken. This is done by bypassing the 
	 * validations and performing the gamePlayed action temporarily
	 * and reverting it if needed.
	 * 
	 * <p>If the action taken results in capturing enemy stones and
	 * thus opens a breathing position to a previously non-
	 * breathing cell, this boolean result of this method signals
	 * that the isBreathing validation can be surpassed.
	 * 
	 * <p>This method should always be called when a player wants to
	 * place a stone in a given cell.

	 * 
	 * @param s StoneGroup
	 * @param c Cell
	 * @param p Player
	 * @return
	 */
	public static Boolean isGroupBreathingIfPlayed(StoneGroup s, Cell c, Player p) {

		// apply game played assumption
		Game.getCells().get(c.toString()).setStatus(p.getColor());

		if (isGroupBreathing(s)) {
			return true;
		} else {

			// rollBack otherwise
			Game.getCells().get(c.toString()).setStatus(CELL_STATUS.EMPTY);

			return false;

		}
	}
}
