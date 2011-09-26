package com.obss.go.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.obss.go.api.Constants;
import com.obss.go.api.Constants.CELL_X;
import com.obss.go.api.Constants.CELL_Y;
import com.obss.go.exception.CellCoordinateOutOfBoundsException;
import com.obss.go.model.Cell;
import com.obss.go.model.Game;

public class GamePlayUtils {
	
	//logger
	private static final Logger logger = Logger.getLogger("GamePlayUtils");

	public void drawStone(Graphics2D g2, CELL_X x, CELL_Y y, Color c) {
	}

	public static Point getCellPoint(CELL_X x, CELL_Y y) {
		int px = Constants.TABLE_MARGIN - (Constants.CELL_SIDE_WIDTH/2) + (x.getValue() * Constants.CELL_SIDE_WIDTH);
		int py = Constants.TABLE_MARGIN - (Constants.CELL_SIDE_WIDTH/2) + (y.getValue() * Constants.CELL_SIDE_WIDTH);
		return new Point(px, py);
	}

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
		Integer roundedX = (((int)point.getX()) - (Constants.TABLE_MARGIN - (Constants.CELL_SIDE_WIDTH/2)))  / Constants.CELL_SIDE_WIDTH;
		Integer roundedY = (((int)point.getY()) - (Constants.TABLE_MARGIN - (Constants.CELL_SIDE_WIDTH/2))) / Constants.CELL_SIDE_WIDTH;
		
		if (roundedX > 12 || roundedY > 12) {
			return null;
		}
		//get highlighted cell
    	Cell highlighedCell = null;
		try {
			highlighedCell = Game.getCells().get(new Cell(GamePlayUtils.getCellX(roundedX), GamePlayUtils.getCellY(roundedY)).toString());
		} catch (CellCoordinateOutOfBoundsException e1) {
			Utils.messageHandler(null, e1.getMessage());
			e1.printStackTrace();
		}
		return highlighedCell;
	}
	
	public static List<Cell> getNeighboringCells(Cell c) {
		
		Integer xInt = c.getCellX().getValue();
		Integer yInt = c.getCellY().getValue();
		
		List<Cell> cellList = new ArrayList<Cell>();
		
		try {
			Cell cellTop = getCellByPoint(getCellPoint(getCellX(xInt - 1), getCellY(yInt)));
			cellList.add(cellTop);
		} catch (CellCoordinateOutOfBoundsException e) {
			logger.debug(e.getMessage());		
		}
		try {
			Cell cellLeft = getCellByPoint(getCellPoint(getCellX(xInt), getCellY(yInt - 1)));
			cellList.add(cellLeft);
		} catch (CellCoordinateOutOfBoundsException e) {
			logger.debug(e.getMessage());
		}
		try {
			Cell cellBottom = getCellByPoint(getCellPoint(getCellX(xInt + 1), getCellY(yInt)));
			cellList.add(cellBottom);
		} catch (CellCoordinateOutOfBoundsException e) {
			logger.debug(e.getMessage());
		}
		try {
			Cell cellRight = getCellByPoint(getCellPoint(getCellX(xInt), getCellY(yInt + 1)));
			cellList.add(cellRight);
		} catch (CellCoordinateOutOfBoundsException e) {
			logger.debug(e.getMessage());
		}
		return cellList;
	}

}
