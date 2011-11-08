package com.obss.go.model;

import org.apache.log4j.Logger;

import com.obss.go.api.Constants.CELL_STATUS;
import com.obss.go.exception.NoActivePlayerException;
import com.obss.go.model.event.TurnPlayEvent;
import com.obss.go.model.event.listener.GoEventListener;

public class Player implements GoEventListener {

	private CELL_STATUS color;
	private Boolean turnActive;
	
	private Logger logger;


	public Player(CELL_STATUS color) {
		super();
		this.color = color;
		
		//logger
		logger = Logger.getLogger(getColor() + " Player");
	}

	public CELL_STATUS getColor() {
		return color;
	}

	public void setColor(CELL_STATUS color) {
		this.color = color;
	}

	public Boolean getTurnActive() {
		return turnActive;
	}

	public void setTurnActive(Boolean turnActive) {
		this.turnActive = turnActive;
	}

	void playTurn(String cellKey) {
		
		logger.info(getColor().toString() + " Player takes turn");
		//set cell status with players color
		try {
			Game.getCells().get(cellKey).setStatus(Game.getActivePlayer().getColor());
		} catch (NoActivePlayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//new stone group data is stored by calling the following method:
		Game.getCells().get(cellKey).setGroupId();

	}

	/** 
	 * event listener for TurnPlayEvent
	 */
	@Override
	public void turnPlayed(TurnPlayEvent e) {		
		logger.debug(getColor().toString() + " Player takes hears turnPlayed event");
		if (this.equals(e.getPlayer())) {
			playTurn(e.getCellKey());
		}
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((color == null) ? 0 : color.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Player other = (Player) obj;
		if (color != other.color)
			return false;
		return true;
	}
}
