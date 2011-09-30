package com.obss.go.model.event.listener;

import java.util.EventListener;

import com.obss.go.model.event.TurnPlayEvent;

public interface GoEventListener extends EventListener {
	
	void turnPlayed(TurnPlayEvent e);

}
