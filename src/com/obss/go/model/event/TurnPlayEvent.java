package com.obss.go.model.event;

import java.util.EventObject;

import com.obss.go.model.Player;

public class TurnPlayEvent extends EventObject {
	
	private String cellKey;
	private Player player;

	public TurnPlayEvent(Object source, String cellKey, Player p) {
		super(source);
		this.cellKey = cellKey;
		this.player = p;
	}
	
	public String getCellKey() {
		return cellKey;
	}

	public Player getPlayer() {
		return player;
	}

}
