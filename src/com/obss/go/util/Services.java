package com.obss.go.util;

import com.obss.go.api.IGamePlayService;
import com.obss.go.service.GamePlayService;

public class Services {
	
    private static Object lock = new Object();
    private static GamePlayService gamePlayService;
    
    public static IGamePlayService getGamePlayService() {
        if (gamePlayService == null) {
            synchronized (lock) {
                if (gamePlayService == null) {
                    gamePlayService = new GamePlayService();
                }
            }
        }
        return gamePlayService;
    }

}
