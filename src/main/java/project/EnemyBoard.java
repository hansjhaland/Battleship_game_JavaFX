package project;

import java.util.Random;

public class EnemyBoard {
	
	private int boardSize = 10;
	private int shipSize = 5;
	private int shipsToPlace = 5;
	private char water = 'W';
	private char ship = 'S';
	private char hidden = 'H';
	private char[][] enemyBoard = new char[boardSize][boardSize];
	private char[][] hiddenBoard = new char[boardSize][boardSize];
	private int[][] enemyShip;
	private int x;
	private int y;
	private String shipDirection;
	
	
	public EnemyBoard() {
		setEmptyEnemyBoard();
	}
	
	public EnemyBoard(char[][] board, char[][] hiddenBoard) {
		for (char[] row: board) {
			for (char coordinate: row) {
				if(coordinate != Battleship.getHit() && coordinate != Battleship.getMiss() && coordinate != water && coordinate != ship) {
					throw new IllegalArgumentException("Invalid enemy board");
				}
			}
		}
		for (char[] row: hiddenBoard) {
			for (char coordinate: row) {
				if(coordinate != Battleship.getHit() && coordinate != Battleship.getMiss() && coordinate != hidden && coordinate != ship) {
					throw new IllegalArgumentException("Invalid hidden board");
				}
			}
		}
		this.enemyBoard = board;
		this.hiddenBoard = hiddenBoard;
	}
	
	
	public char[][] getEnemyBoard() {
		return enemyBoard;
	}
	
	public int getBoardSize() {
		return boardSize;
	}
	
	public int[][] getEnemyShip() {
		return enemyShip;
	}
	
	public String getShipDirection() {
		return shipDirection;
	}
	
	public int getShipSize() {
		return shipSize;
	}
	
	public int getShipsToPlace() {
		return shipsToPlace;
	}
	
	public char[][] getHiddenBoard() {
		return hiddenBoard;
	}
	
	public char getHidden() {
		return hidden;
	}
	
	public char getShip() {
		return ship;
	}
	
	public char getWater() {
		return water;
	}
	
	
	public void setEmptyEnemyBoard() {
		for (int y = 0; y < boardSize; y++) {
			for (int x = 0; x < boardSize; x++) {
				enemyBoard[y][x] = water;
			}
		}
	}
	
	public void generateEnemyShip() {
		enemyShip = new int[shipSize][2];
		do {
			int[] coordinates = new int[2];
			coordinates[0] = new Random().nextInt(boardSize);
			coordinates[1] = new Random().nextInt(boardSize);
			x = coordinates[0];
			y = coordinates[1];
			setRandomDirection();
		} while (!validShipHead()); 
		if (shipDirection.equals("vertical")) {
			generateVerticalShip();
		} else if (shipDirection.equals("horizontal")) {
			generateHorizontalShip();
		}
	}
	
	private boolean validShipHead() {
		if (shipDirection.equals("vertical")) {
			if (y + shipSize - 1 >= boardSize) {
				return false;
			}
			for (int i = 0; i < shipSize; i++) {
				if(enemyBoard[y + i][x] == ship ) {
					return false;
				}
			}
		} else if (shipDirection.equals("horizontal")) {
			if (x + shipSize - 1 >= boardSize) {
				return false;
			}
			for (int i = 0; i < shipSize; i++) {
				if(enemyBoard[y][x + i] == ship ) {
					return false;
				}
			}
		}
		return true;
	}

	private void generateHorizontalShip() {
			for (int i = 0; i < shipSize; i++) {
				enemyShip[i][0] = x + i;
				enemyShip[i][1] = y;
			}
	}
	
	private void generateVerticalShip() {
		for (int i = 0; i < shipSize; i++) {
			enemyShip[i][0] = x;
			enemyShip[i][1] = y + i;
		}
	}
	
	public void placeEnemyShip() {
		if (enemyShip == null) {
			throw new IllegalStateException("ship is null");
		}
		for (int[] coordinates : enemyShip) {
			int x = coordinates[0];
			int y = coordinates[1];
			enemyBoard[y][x] = ship;
		}
		shipsToPlace--;
		shipSize--;
	}

	private void setRandomDirection() {
		Random random = new Random();
		int binaryNumber = random.nextInt(2);
		if(binaryNumber == 0) {
			shipDirection = "vertical";
		} 
		else {
			shipDirection = "horizontal";
		}
	}
	
	public void printEnemyBoard(){
		for (int i = 0; i < boardSize; i++) {
			String boardString = "";
			for (int j = 0; j < boardSize; j++) {
				boardString += enemyBoard[i][j] + " "; 
			}
			System.out.println(boardString);
		}
	}
	
	public void updateBoard(int x, int y, char result) {
		if (x < 0 || x > 9) {
			throw new IllegalArgumentException("Invalid x coordinate");
		}
		if (y < 0 || y > 9) {
			throw new IllegalArgumentException("Invalid y coordinate");
		}
		if (result != 'S' && result != 'W' && result != 'X' && result != 'O') {
			throw new IllegalArgumentException("Invalid result input");
		}
		enemyBoard[y][x] = result;
//		printEnemyBoard();
	}
	
	public void generateHiddenBoard() {
		for (int y = 0; y < boardSize; y++) {
			for (int x = 0; x < boardSize; x++) {
				hiddenBoard[y][x] = hidden;
			}
		}
	}
	
	public void updateHiddenBoard(int x, int y, char result) {
		if (x < 0 || x > 9) {
			throw new IllegalArgumentException("Invalid x coordinate");
		}
		if (y < 0 || y > 9) {
			throw new IllegalArgumentException("Invalid y coordinate");
		}
		if (result != 'S' && result != 'H' && result != 'X' && result != 'O') {
			throw new IllegalArgumentException("Invalid result input");
		}
		hiddenBoard[y][x] = result;
//		printHiddenBoard();
	}
	
	public void printHiddenBoard() {
		for (int i = 0; i < boardSize; i++) {
			String boardString = "";
			for (int j = 0; j < boardSize; j++) {
				boardString += hiddenBoard[i][j] + " "; 
			}
			System.out.println(boardString);
		}
	}

	public static void main(String[] args) {
		EnemyBoard board = new EnemyBoard();
		board.printEnemyBoard();
	}


}
