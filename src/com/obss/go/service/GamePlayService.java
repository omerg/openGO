package com.obss.go.service;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.event.EventListenerList;

import com.obss.go.api.Constants;
import com.obss.go.api.Constants.CELL_STATUS;
import com.obss.go.api.Constants.CELL_X;
import com.obss.go.api.Constants.CELL_Y;
import com.obss.go.api.IGamePlayService;
import com.obss.go.exception.CellNotBreathingException;
import com.obss.go.exception.CellNotEmptyException;
import com.obss.go.exception.KOException;
import com.obss.go.model.Cell;
import com.obss.go.model.Game;
import com.obss.go.model.StoneGroup;
import com.obss.go.model.event.TurnPlayEvent;
import com.obss.go.model.event.listener.GoEventListener;
import com.obss.go.util.GamePlayUtils;

public class GamePlayService implements IGamePlayService, EventListener {

    
    //switch turn
    public static void switchTurn() {
		Game.getBlackPlayer().setTurnActive(!Game.getBlackPlayer().getTurnActive());
		Game.getWhitePlayer().setTurnActive(!Game.getWhitePlayer().getTurnActive());
    }

	//initialize event listener
	private static EventListenerList listenerList = new EventListenerList();
	
	//register events
	@Override
    public void addTurnPlayEventListener(GoEventListener listener) {
        listenerList.add(GoEventListener.class, listener);
    }

	@Override
	public void playTurn(CELL_X x, CELL_Y y) throws CellNotEmptyException, CellNotBreathingException, KOException {

		String activeCellKey = new Cell(x, y).toString();
		
		// check if cell is empty
		if (isCellEmpty(x, y) == false) {
			throw new CellNotEmptyException(x, y);
		}
		
		//check if cell is available for given player
		if (GamePlayUtils.isCellAvailableFor(Game.getCells().get(activeCellKey), Game.getActivePlayer()) == false) {
			throw new CellNotBreathingException(x, y);
		}
		
		//check for KO 
		if (Game.getCells().get(activeCellKey).equals(Game.getKOCell())) {
			throw new KOException(x, y);
		}
		
		//if play turn was not caught in exception, fire turn played event - notify Game()
		fireTurnPlayEvent(activeCellKey) ;
		
		
		//get colors of active and passive players
		CELL_STATUS colorOfActivePlayer = Game.getCells().get(activeCellKey).getStatus();
		CELL_STATUS colorOfPassivePlayer;
		if(CELL_STATUS.BLACK.equals(colorOfActivePlayer)) {
			colorOfPassivePlayer = CELL_STATUS.WHITE;
		} else {
			colorOfPassivePlayer = CELL_STATUS.BLACK;
		}
				
		//reset kKO cell status
		Game.setKOCell(null);
		
		//check if non-breathing groups have been formed
		evaluateGroups(colorOfPassivePlayer);
		evaluateGroups(colorOfActivePlayer);
		
		//switch turn
		switchTurn();

	}

	/**
	 * remove killed stones from board
	 */
	private void evaluateGroups(CELL_STATUS groupColor) {
		
		Iterator<Entry<Integer, StoneGroup>> it = Game.getGroupMap().entrySet().iterator();
				
		/*
		 * temporary key list of groups that need to be removed from groupMap.
		 *the temporary key list should be kept because the removal operation
		 *should be done outside the for loop in order to prevent
		 *ConcurrentModification to groupMap
		 */
		List<Integer> removalCandidateGroupKeyList = new ArrayList<Integer>();
		
		while (it.hasNext()) {
			Map.Entry<Integer, StoneGroup> stoneGroupEntry = (Map.Entry<Integer, StoneGroup>) it.next();

			
			if (stoneGroupEntry.getValue().getColor().equals(groupColor) && GamePlayUtils.isGroupBreathing(stoneGroupEntry.getValue()) == false) {			
				
				//if the group is not breathing, clean it
				for (Cell c : stoneGroupEntry.getValue().getCellList()) {
					Game.getCells().get(c.toString()).setStatus(Constants.CELL_STATUS.EMPTY);

					//set groupId of cell as null
					c.setGroupId(null);
				}
								
				//record groupKey in temporary list
				removalCandidateGroupKeyList.add(stoneGroupEntry.getKey());
								
				//mark cell as KO cell if the cell is the only member of its group
				if (stoneGroupEntry.getValue().getCellList().size() == 1) {
					Game.setKOCell(stoneGroupEntry.getValue().getCellList().get(0));
				} else {
					Game.setKOCell(null);
				}
			}
		}	
				
		//process removal candidate group list
		for (Integer groupId : removalCandidateGroupKeyList) {
			Game.getGroupMap().remove(groupId);
		}

	}

	/**
	 * check if cell is empty
	 * @param x
	 * @param y
	 * @return
	 */
	@Override
	public Boolean isCellEmpty(CELL_X x, CELL_Y y) {
		if (CELL_STATUS.EMPTY.equals(Game.getCells().get(new Cell(x, y).toString()).getStatus()) == false) {
			return false;
		}
		return true;
	}
	    
    public synchronized void addGoEventListener( GoEventListener listener ) {
        listenerList.add(GoEventListener.class, listener );
    }
	
	// this class is used to fire TurnPlay Events
    private void fireTurnPlayEvent(String cellKey) {
        
    	Object[] listeners = listenerList.getListenerList();
    	
    	//create turn play event
    	TurnPlayEvent turnPlayEvent = new TurnPlayEvent( this, cellKey, Game.getActivePlayer());
               
        // Each listener occupies two elements - the first is the listener class
        // and the second is the listener instance
        for (int i=0; i<listeners.length; i+=2) {
            if (listeners[i]==GoEventListener.class) {
                ((GoEventListener)listeners[i+1]).turnPlayed(turnPlayEvent);
            }
        }
    }
}
