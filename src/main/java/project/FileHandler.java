package project;

import java.io.FileNotFoundException;

public interface FileHandler {

	void saveGame(String filename, Battleship game) throws FileNotFoundException;
	
	Battleship loadGame(String filename) throws FileNotFoundException;
	
}
