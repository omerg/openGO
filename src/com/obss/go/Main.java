package com.obss.go;

import javax.swing.JFrame;

import com.obss.go.api.Constants;
import com.obss.go.model.Board;
import com.obss.go.model.Game;

public class Main extends JFrame  {

    public Main() {
    	
    	//create Board() object
    	Board board = new Board();   	
    	
    	//draw board  
    	add(board);
        setTitle("OBSS GO!");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(Constants.BOARD_WIDTH, Constants.BOARD_HEIGHT);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
        validate();
        
    }
    public static void main(String[] args) {
        
    	//set JFrame
    	new Main();
    	
    	//start game
    	Game.getCurrentGame().start();
    
    }
}
