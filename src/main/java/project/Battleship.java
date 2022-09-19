package project;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Battleship {
	
	private UserBoard userBoard;
	private EnemyBoard enemyBoard;
	private static char hit = 'X';
	private static char miss = 'O';
	private int userHitCount;
	private int enemyHitCount;
	private int turnCount;
	private List<Integer> highscores = new ArrayList<Integer>();
	private int enemyGuessX;
	private int enemyGuessY;
	
	public Battleship() {
		userBoard = new UserBoard();
		enemyBoard = new EnemyBoard();
		createEnemyBoard();
	}
	
	public Battleship(UserBoard userBoard, EnemyBoard enemyBoard, int userHitCount, int enemyHitCount, int turnCount) {
		if (userBoard == null) {
			throw new IllegalArgumentException("userBoard is null");
		}
		if (enemyBoard == null) {
			throw new IllegalArgumentException("enemyBoard is null");
		}
		if (userHitCount > 16 || userHitCount < 0) {
			throw new IllegalArgumentException("Invalid userHitCount");
		}
		if (enemyHitCount > 16 || enemyHitCount < 0) {
			throw new IllegalArgumentException("Invalid enemyHitCount");
		}
		if (turnCount < 0 || turnCount > 100) {
			throw new IllegalArgumentException("Invalid turnCount");
		}
		this.userBoard = userBoard;
		this.enemyBoard = enemyBoard;
		this.userHitCount = userHitCount;
		this.enemyHitCount = enemyHitCount;
		this.turnCount = turnCount;
	}
	
	public EnemyBoard getEnemyBoard() {
		return enemyBoard;
	}
	
	public UserBoard getUserBoard() {
		return userBoard;
	}
	
	public int getUserHitCount() {
		return userHitCount;
	}
	
	public int getEnemyHitCount() {
		return enemyHitCount;
	}
	
	public List<Integer> getHighscores(){
		return highscores;
	}
	
	public int getTurnCount() {
		return turnCount;
	}
	
	public static char getHit() {
		return hit;
	}
	
	public static char getMiss() {
		return miss;
	}
	
	public int getEnemyGuessX() {
		return enemyGuessX;
	}
	
	public int getEnemyGuessY() {
		return enemyGuessY;
	}
	
	private void createEnemyBoard() {
		enemyBoard.setEmptyEnemyBoard();
		enemyBoard.generateHiddenBoard();
		while (enemyBoard.getShipsToPlace() > 0) {
			enemyBoard.generateEnemyShip();
			enemyBoard.placeEnemyShip();
		}
	}
	
//	public boolean isValidDigit(String input) {
//		try {
//			Integer.parseInt(input);
//			if (Integer.parseInt(input) >= 0 && Integer.parseInt(input) < 9) {
//				return true;				
//			} else {
//				return false;
//			}
//		} catch (NumberFormatException e) {
//			return false;
//		}
//	}
	
	public boolean userFire(int x, int y) {
		if (x < 0 || x > 9) {
			throw new IllegalArgumentException("Invalid x");
		}
		if (y < 0 || y > 9) {
			throw new IllegalArgumentException("Invalid y");
		}
		if (!isNewCoordinates(x, y)) {
			throw new IllegalArgumentException("Coordinates already chosen");
		}
		char[][] board = enemyBoard.getEnemyBoard();
		char ship = enemyBoard.getShip();
		char water = enemyBoard.getWater();
		if (board[y][x] == ship) {
			enemyBoard.updateBoard(x,y,hit);
			enemyBoard.updateHiddenBoard(x, y, hit);
			userHitCount++;
			return true;
		} else if (board[y][x] == water){
			enemyBoard.updateBoard(x,y,miss);
			enemyBoard.updateHiddenBoard(x, y, miss);
		}
		return false;
	}
	
	
	public boolean enemyFire() {
		int boardSize = enemyBoard.getBoardSize();
		do {
			enemyGuessX = new Random().nextInt(boardSize);
			enemyGuessY = new Random().nextInt(boardSize);
		} while (!isNewCoordinates());
		char[][] board = userBoard.getUserBoard();
		char ship = userBoard.getShip();
		char water = userBoard.getWater();
		if (board[enemyGuessY][enemyGuessX] == ship) {
			userBoard.updateUserBoard(hit, enemyGuessY, enemyGuessX);
			enemyHitCount++;
			return true;
		} else if (board[enemyGuessY][enemyGuessX] == water) {
			userBoard.updateUserBoard(miss, enemyGuessY, enemyGuessX);
		}
		return false;
	}
	
	
	private boolean isNewCoordinates() {
		char[][] board = userBoard.getUserBoard();
		if (board[enemyGuessY][enemyGuessX] == hit || board[enemyGuessY][enemyGuessX] == miss) {
			return false;
		}
		return true;
	}
	
	public boolean isNewCoordinates(int x, int y) {
		if (x < 0 || x > 9) {
			throw new IllegalArgumentException("Invalid x");
		}
		if (y < 0 || y > 9) {
			throw new IllegalArgumentException("Invalid y");
		}
		char[][] board = enemyBoard.getEnemyBoard();
		if (board[y][x] == hit || board[y][x] == miss) {
			return false;
		}
		return true;
	}
	
	public void increaseTurnCount() {
		turnCount++;
	}
	
	public boolean checkNewHighscore(int turnCount) {
		int size = highscores.size();
		if (size == 0 || turnCount < highscores.get(size-1)) {
			highscores.add(turnCount);
			return true;
		}
		return false;
	}
	
}
