package com.obss.go.model;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.obss.go.api.Constants;
import com.obss.go.api.Constants.CELL_STATUS;
import com.obss.go.exception.CellCoordinateOutOfBoundsException;
import com.obss.go.util.GamePlayUtils;
import com.obss.go.util.Services;
import com.obss.go.util.Utils;


/**
 * @author omerg
 * 
 * Single instance of game class represents the state of the game. 
 * The representation includes players, cells and information
 * about the game`s progress, i.e. it contains gameEnded state,
 * active player and logs about taken actions.
 *
 */
public class Game {
	
	//logger
	private static final Logger logger = Logger.getLogger("Game");
	
	//players
	private static final Player blackPlayer = new Player(CELL_STATUS.BLACK);
	private static final Player whitePlayer = new Player(CELL_STATUS.WHITE);
	
    /**
     * All the cells on the board
     */
    private static final Map<String, Cell> cells = new LinkedHashMap<String, Cell>();
        
    /**
     * As the game progresses and stones with same color become
     * neighbors to each other, the new stone group which they form
     * is stored in the <i>groupMap</i> HashMap.
     */
    private static Map<Integer, StoneGroup> groupMap = new HashMap<Integer, StoneGroup>();
    
	static {
		
		// initialize cells
		for (int x = 0; x < Constants.CELLS_PER_ROW; x++) {
			for (int y = 0; y < Constants.CELLS_PER_ROW; y++) {
				try {
					Cell cell = new Cell(GamePlayUtils.getCellX(x),GamePlayUtils.getCellY(y));
					Game.getCells().put(cell.toString(), cell);
				} catch (CellCoordinateOutOfBoundsException e) {
					Utils.messageHandler(null, e.getMessage());
				}
			}
		}
		   	
    	//players listen to Game() object.
    	Services.getGamePlayService().addTurnPlayEventListener(Game.getBlackPlayer());
    	Services.getGamePlayService().addTurnPlayEventListener(Game.getWhitePlayer());
	}
	
	private static Boolean gameEnded = false;
	
	private static Game singletonInstance = new Game();

	private static Cell KOCell;
	
	public static Game getCurrentGame() {
		return singletonInstance;
	}
	
	public static Player getBlackPlayer() {
		return blackPlayer;
	}

	public static Player getWhitePlayer() {
		return whitePlayer;
	}
	
	public static Map<String, Cell> getCells() {
		return cells;
	}
	
	public Boolean isGameEnded() {
		return gameEnded;
	}

	public void setGameEnded(Boolean gameEnd) {
		gameEnded = gameEnd;
	}

	public void start() {
		logger.info("Game Started.");
		
		//black player starts
		getBlackPlayer().setTurnActive(true);
		getWhitePlayer().setTurnActive(false);
	}

	public static void setGroupMap(Map<Integer, StoneGroup> groupMap) {
		Game.groupMap = groupMap;
	}

	public static Map<Integer, StoneGroup> getGroupMap() {
		return groupMap;
	}
	
	/**
	 * get player depending on active turn
	 * @return Player
	 */
	public static Player getActivePlayer() {
		if (getBlackPlayer().getTurnActive()) {
			return getBlackPlayer();
		} else {
			return getWhitePlayer();
		}
	}

	public static void setKOCell(Cell kOCell) {
		if (kOCell != null) {
			logger.info("KO Cell is set to: " + kOCell.toString());
		}
		KOCell = kOCell;
	}

	public static Cell getKOCell() {
		return KOCell;
	}
}
