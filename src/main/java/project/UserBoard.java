package project;

public class UserBoard {

	private int boardSize = 10;
	private char[][] userBoard = new char[boardSize][boardSize];
	private char water = 'W';
	private char ship = 'S';
	private int shipsToPlace = 5;
	private int shipSize = 5;
	private int[][] userShip;
	private int userX;
	private int userY;
	private String shipDirection;
	
	
	public UserBoard() {
		setEmptyUserBoard();
	}
	
	public UserBoard(char[][] board, int shipsToPlace) {
		for (char[] row: board) {
			for (char coordinate: row) {
				if(coordinate != Battleship.getHit() && coordinate != Battleship.getMiss() && coordinate != water && coordinate != ship) {
					throw new IllegalArgumentException("Invalid board");
				}
			}
		}
		if (shipsToPlace > 5) {
			throw new IllegalArgumentException("Too many ships");
		}
		this.userBoard = board;
		this.shipsToPlace = shipsToPlace;
	}
	
	public char[][] getUserBoard() {
		return userBoard;
	}
	
	public int[][] getUserShip() {
		return userShip;
	}
	
	public int getUserX() {
		return userX;
	}
	
	public int getUserY() {
		return userY;
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
	
	public char getShip() {
		return ship;
	}
	
	public char getWater() {
		return water;
	}
	
	public void setEmptyUserBoard() {
		for (int y = 0; y < boardSize; y++) {
			for (int x = 0; x < boardSize; x++) {
				userBoard[y][x] = water;
			}
		}
	}
	
	public void setShipCoordinates(int x, int y, String dir) {
		if (!validateShipHead(x,y,dir)) {
			throw new IllegalArgumentException("Invalid inputs");
		}
			userX = x;
			userY = y;
			shipDirection = dir; 
	}
	
	public void createUserShip(int x, int y, String dir) {
		setShipCoordinates(x,y,dir);
		userShip = new int[shipSize][2];
		if (shipDirection.equals("vertical")) {
			generateVerticalShip();
		} else if (shipDirection.equals("horizontal")) {
			generateHorizontalShip();
		}
		placeUserShip();
	}
	
	public boolean validateShipHead(int x, int y, String direction) {
		if (x > 9 || y > 9) {
			return false;
		}
		if (!(direction.equals("vertical") || direction.equals("horizontal"))){
			return false;
		}
		if (direction.equals("vertical")) {
			if (y + shipSize - 1 >= boardSize) {
				return false;
			}
			for (int i = 0; i < shipSize; i++) {
				if(userBoard[y + i][x] == ship ) {
					return false;
				}
			}
		} else if (direction.equals("horizontal")) {
			if (x + shipSize - 1 >= boardSize) {
				return false;
			}
			for (int i = 0; i < shipSize; i++) {
				if(userBoard[y][x + i] == ship ) {
					return false;
				}
			}
		}
		return true;
	}
	
	private void generateHorizontalShip() {
		for (int i = 0; i < shipSize; i++) {
			userShip[i][0] = userX + i;
			userShip[i][1] = userY;
		}
	}
	
	private void generateVerticalShip() {
		for (int i = 0; i < shipSize; i++) {
			userShip[i][0] = userX;
			userShip[i][1] = userY + i;
		}
	}
	
	private void placeUserShip() {
		for (int[] coordinates : userShip) {
			int x = coordinates[0];
			int y = coordinates[1];
			userBoard[y][x] = ship;
		}
		shipSize--;
		shipsToPlace--;
	}
	
	public void updateUserBoard(char result, int y, int x) {
		if (x < 0 || x > 9) {
			throw new IllegalArgumentException("Invalid x coordinate");
		}
		if (y < 0 || y > 9) {
			throw new IllegalArgumentException("Invalid y coordinate");
		}
		if (result != 'S' && result != 'W' && result != 'X' && result != 'O') {
			throw new IllegalArgumentException("Invalid result input");
		}
		userBoard[y][x] = result;
//		printUserBoard();
	}
	
	public void printUserBoard(){
		for (int i = 0; i < boardSize; i++) {
			String boardString = "";
			for (int j = 0; j < boardSize; j++) {
				boardString += userBoard[i][j] + " "; 
			}
			System.out.println(boardString);
		}
	}
	
}
