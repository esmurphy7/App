package com.example.app;


/*
 * Singleton class to handle game data such as score health etc.
 * Refered to by page 20 in AndEngine manual
 */
public class GameManager {

	private static GameManager gameManager = null;
	
	private GameManager(){

	}
	
	public GameManager getInstance(){
		if(gameManager == null){
			gameManager = new GameManager();
		}
		return gameManager;
	}
	
}
