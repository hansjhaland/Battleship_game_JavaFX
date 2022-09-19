package project;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

public class BattleshipTest {

	private Battleship game;
	private Battleship loadedGame;
	private UserBoard user;
	private EnemyBoard enemy;
	
	char[][] waterBoard = new char[10][10];
	char[][] hiddenBoard = new char[10][10];
	
	
	@BeforeEach
	public void setUp() {
		game = new Battleship();
		
		char[] waterRow = new char[10];
		Arrays.fill(waterRow, 'W');
		Arrays.fill(waterBoard, waterRow);
		
		
		char[] hiddenRow = new char[10];
		Arrays.fill(hiddenRow, 'H');
		Arrays.fill(hiddenBoard, hiddenRow);
	}
	
	
	@Test
	public void testConstructor() {
		game = new Battleship();
		assertTrue(game.getUserBoard() != null);
		assertTrue(game.getEnemyBoard() != null);
		int shipCount = 0;
		int waterCount = 0;
		for (int y = 0; y < 10; y++) {
			for (int x = 0; x < 10; x++) {
				if (game.getEnemyBoard().getEnemyBoard()[y][x] == 'S') {
					shipCount++;
				}
				if (game.getEnemyBoard().getEnemyBoard()[y][x] == 'W') {
					waterCount++;
				}
			}
		}
		assertEquals(15, shipCount);
		assertEquals(85, waterCount);
		for (int y = 0; y < 10; y++) {
			for (int x = 0; x < 10; x++) {
				assertEquals(hiddenBoard[y][x], game.getEnemyBoard().getHiddenBoard()[y][x]);
			}
		}
	}
	
	@Test
	public void testConstructorValidInput() {
		assertTrue(loadedGame == null);
		
		user = new UserBoard(waterBoard,5);
		enemy = new EnemyBoard(waterBoard, hiddenBoard);
		loadedGame = new Battleship(user,enemy,0,0,0);
		
		assertTrue(loadedGame.getUserBoard() == user);
		assertTrue(loadedGame.getEnemyBoard() == enemy);
		assertTrue(loadedGame.getUserHitCount() == 0);
		assertTrue(loadedGame.getEnemyHitCount() == 0);
		assertTrue(loadedGame.getTurnCount() == 0);
	}
	
	@Test
	public void testConstructorInvalidInput() {
		assertTrue(loadedGame == null);
		user = null;
		enemy = new EnemyBoard(waterBoard, hiddenBoard);
		assertThrows(IllegalArgumentException.class, () -> {
			loadedGame = new Battleship(user,enemy,0,0,0);
		});
		user = new UserBoard(waterBoard,5);
		enemy = null;
		assertThrows(IllegalArgumentException.class, () -> {
			loadedGame = new Battleship(user,enemy,0,0,0);
		});
		enemy = new EnemyBoard(waterBoard, hiddenBoard);
		assertThrows(IllegalArgumentException.class, () -> {
			loadedGame = new Battleship(user,enemy,17,0,0);
		});
		assertThrows(IllegalArgumentException.class, () -> {
			loadedGame = new Battleship(user,enemy,0,17,0);
		});
		assertThrows(IllegalArgumentException.class, () -> {
			loadedGame = new Battleship(user,enemy,0,0,-1);
		});
	}
	
	@Test
	public void testUserFire() {
		if (game.getEnemyBoard().getEnemyBoard()[0][1] == 'S') {
			game.userFire(1, 0);
			assertTrue(game.getEnemyBoard().getEnemyBoard()[0][1] == 'X');
			assertEquals(1,game.getUserHitCount());
		}
		if (game.getEnemyBoard().getEnemyBoard()[0][1] == 'W') {
			game.userFire(1, 0);
			assertTrue(game.getEnemyBoard().getEnemyBoard()[0][1] == 'O');
			assertEquals(0,game.getUserHitCount());
		}
		if (game.getEnemyBoard().getEnemyBoard()[0][2] == 'S') {
			assertTrue(game.userFire(0, 0));
		}
		if (game.getEnemyBoard().getEnemyBoard()[0][2] == 'W') {
			assertTrue(!game.userFire(0, 0));
		}
	}
	
	@Test
	public void testUserFireInvalidInput() {
		assertThrows(IllegalArgumentException.class, () -> {
			game.userFire(10, 0);
		});
		assertThrows(IllegalArgumentException.class, () -> {
			game.userFire(-1, 0);
		});
		assertThrows(IllegalArgumentException.class, () -> {
			game.userFire(2, 10);
		});
		assertThrows(IllegalArgumentException.class, () -> {
			game.userFire(2, -1);
		});
		assertThrows(IllegalArgumentException.class, () -> {
			game.userFire(10, -1);
		});
		game.userFire(1, 0);
		assertThrows(IllegalArgumentException.class, () -> {
			game.userFire(1, 0);
		});
	}
	
	@Test
	public void testEnemyFire() {
		boolean returnValue = game.enemyFire();
		assertTrue(game.getEnemyGuessX() <= 9 && game.getEnemyGuessX() >= 0);
		assertTrue(game.getEnemyGuessY() <= 9 && game.getEnemyGuessY() >= 0);
		if (game.getUserBoard().getUserBoard()[game.getEnemyGuessY()][game.getEnemyGuessX()] == 'X') {
			assertEquals('X', game.getUserBoard().getUserBoard()[game.getEnemyGuessY()][game.getEnemyGuessX()]);
			assertEquals(game.getEnemyHitCount(), 1);
			assertEquals(true, returnValue);
		}
		if (game.getUserBoard().getUserBoard()[game.getEnemyGuessY()][game.getEnemyGuessX()] == 'O') {
			assertEquals('O', game.getUserBoard().getUserBoard()[game.getEnemyGuessY()][game.getEnemyGuessX()]);
			assertEquals(false, returnValue);
		}
	} 
	
	@Test
	public void testIsNewCoordinate() {
		assertTrue(game.isNewCoordinates(3, 2));
		game.userFire(3, 2);
		assertTrue(!game.isNewCoordinates(3, 2));
	}
	
	@Test
	public void testIsNewCoordinateInvalidInput() {
		assertThrows(IllegalArgumentException.class, () -> {
			game.isNewCoordinates(10, 0);
		});
		assertThrows(IllegalArgumentException.class, () -> {
			game.isNewCoordinates(-1, 0);
		});
		assertThrows(IllegalArgumentException.class, () -> {
			game.isNewCoordinates(1, 10);
		});
		assertThrows(IllegalArgumentException.class, () -> {
			game.isNewCoordinates(1, -1);
		});
	}
	
	@Test
	public void increaseTurnCount() {
		assertEquals(0, game.getTurnCount());
		game.increaseTurnCount();
		assertEquals(1, game.getTurnCount());
	}
	
	@Test
	public void testCheckNewHighscore() {
		assertTrue(game.checkNewHighscore(20));
		assertTrue(game.checkNewHighscore(19));
		assertTrue(!game.checkNewHighscore(21));
	}
	
}
