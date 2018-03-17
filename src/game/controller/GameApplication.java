package game.controller;

import game.viewer.board.AppFrame;

public class GameApplication {
	
	public static void main(String[] args) {
		GameManager game = GameManager.getInstance();
		AppFrame newJFrame  = AppFrame.getInstance();
	}

}
