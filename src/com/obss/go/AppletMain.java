package com.obss.go;
import javax.swing.JApplet;

import com.obss.go.model.Board;
import com.obss.go.model.Game;


public class AppletMain extends JApplet {
	
	public void init() {
        
		getContentPane().add(new Board());
        
    	//start game
    	Game.getCurrentGame().start();
    }

}
