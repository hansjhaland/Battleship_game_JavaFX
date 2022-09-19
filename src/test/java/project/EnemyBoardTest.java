package project;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EnemyBoardTest {

	private EnemyBoard enemy;
	private char[][] waterBoard = new char[10][10];
	private char[][] hiddenBoard = new char[10][10];
	
	@BeforeEach
	public void setUp() {
		enemy = new EnemyBoard();
		
		char[] waterRow = new char[10];
		Arrays.fill(waterRow, 'W');
		Arrays.fill(waterBoard, waterRow);
		char[] hiddenRow = new char[10];
		Arrays.fill(hiddenRow, 'H');
		Arrays.fill(hiddenBoard, hiddenRow);
	}
	
	@Test
	public void testConstructor() {
		enemy = new EnemyBoard(waterBoard, hiddenBoard);
		assertEquals(waterBoard, enemy.getEnemyBoard());
		assertEquals(hiddenBoard, enemy.getHiddenBoard());
	}
	
	@Test
	public void testConstructorInvalidInputs() {
		waterBoard[2][4] = '?';
		assertThrows(IllegalArgumentException.class, () -> {
			new EnemyBoard(waterBoard, hiddenBoard);
		});
		waterBoard[2][4] = 'W';
		hiddenBoard[2][4] = '?';
		assertThrows(IllegalArgumentException.class, () -> {
			new EnemyBoard(waterBoard, hiddenBoard);
		});
	}
	
	@Test
	public void testSetEmptyEnemyBoard() {
		enemy.setEmptyEnemyBoard();
		for (int y = 0; y < 10; y++) {
			for (int x = 0; x < 10; x++) {
				assertEquals(waterBoard[y][x], enemy.getEnemyBoard()[y][x]);
			}
		}
	}
	
	@Test
	public void testGenerateHiddenBoard() {
		enemy.generateHiddenBoard();
		for (int y = 0; y < 10; y++) {
			for (int x = 0; x < 10; x++) {
				assertEquals(hiddenBoard[y][x], enemy.getHiddenBoard()[y][x]);
			}
		}
	}
	
	@Test
	public void testGenerateEnemyShips() {
		assertTrue(enemy.getEnemyShip() == null);
		enemy.generateEnemyShip();
		assertTrue(enemy.getEnemyShip() != null);
		
	}
	
	@Test
	public void testPlaceShipBeforeGenerate() {
		assertThrows(IllegalStateException.class, () -> {
			enemy.placeEnemyShip();
		});
	}
	
	@Test
	public void testPlaceEnemyShip() {
		enemy.generateEnemyShip();
		enemy.setEmptyEnemyBoard();
		for (int y = 0; y < 10; y++) {
			for (int x = 0; x < 10; x++) {
				assertEquals(waterBoard[y][x], enemy.getEnemyBoard()[y][x]);
			}
		}
		assertEquals(5,enemy.getShipsToPlace());
		assertEquals(5,enemy.getShipSize());
		enemy.placeEnemyShip();
		int shipcount = 0;
		for (int y = 0; y < 10; y++) {
			for (int x = 0; x < 10; x++) {
				if (enemy.getEnemyBoard()[y][x] == 'S') {
					shipcount++;
				}
			}
		}
		assertEquals(5, shipcount);
		assertEquals(4,enemy.getShipsToPlace());
		assertEquals(4,enemy.getShipSize());
	}
	
	@Test
	public void testUpdateBoard() {
		enemy.updateBoard(5, 5, 'S');
		assertEquals(enemy.getEnemyBoard()[5][5], 'S');
	}
	
	@Test
	public void testUpdateBoardInvalidInputs() {
		assertThrows(IllegalArgumentException.class, () -> {
			enemy.updateBoard(10, 4, 'S');
		});
		assertThrows(IllegalArgumentException.class, () -> {
			enemy.updateBoard(4, 10, 'S');
		});
		assertThrows(IllegalArgumentException.class, () -> {
			enemy.updateBoard(9, 4, '?');
		});
	}
	
	@Test
	public void testUpdateHiddenBoard() {
		enemy.generateHiddenBoard();
		enemy.updateHiddenBoard(5, 5, 'S');
		assertEquals(enemy.getHiddenBoard()[5][5], 'S');
	}
	
	@Test
	public void testUpdateHiddenBoardInvalidInputs() {
		assertThrows(IllegalArgumentException.class, () -> {
			enemy.updateBoard(10, 4, 'H');
		});
		assertThrows(IllegalArgumentException.class, () -> {
			enemy.updateBoard(4, 10, 'H');
		});
		assertThrows(IllegalArgumentException.class, () -> {
			enemy.updateBoard(9, 4, '?');
		});
	}
}
