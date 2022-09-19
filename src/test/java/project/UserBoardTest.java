package project;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;

public class UserBoardTest {

	private UserBoard user;
	
	private char[][] waterBoard = new char[10][10];
	
	@BeforeEach
	public void setUp() {
		user = new UserBoard();
		
		char[] waterRow = new char[10];
		Arrays.fill(waterRow, 'W');
		Arrays.fill(waterBoard, waterRow);
	}
	
	@Test
	public void testConstructor() {
		user = new UserBoard();
		for (int y = 0; y < 10; y++) {
			for (int x = 0; x < 10; x++) {
				assertEquals(user.getUserBoard()[y][x], 'W');
			}
		}
	}
	
	@Test
	public void testConstructorValidInputs() {
		user = new UserBoard(waterBoard, 5);
		assertEquals(waterBoard, user.getUserBoard());
		assertEquals(5, user.getShipsToPlace());
	}
	
	@Test
	public void testConstructorInvalidInputs() {
		waterBoard[4][2] = '?';
		assertThrows(IllegalArgumentException.class, () -> {
			new UserBoard(waterBoard,5);
		});
		waterBoard[4][2] = 'W';
		assertThrows(IllegalArgumentException.class, () -> {
			new UserBoard(waterBoard,6);
		});
	}
	
	@Test
	public void testSetEmptyUserBoards() {
		waterBoard[4][2] = 'S';
		user = new UserBoard(waterBoard,5);
		user.setEmptyUserBoard();
		for (int y = 0; y < 10; y++) {
			for (int x = 0; x < 10; x++) {
				assertEquals(user.getUserBoard()[y][x], 'W');
			}
		}
	}
	
	@Test
	public void testSetShipCoordinates() {
		user.setShipCoordinates(1, 2, "vertical");
		assertEquals(1, user.getUserX());
		assertEquals(2, user.getUserY());
		assertEquals("vertical", user.getShipDirection());
	}
	
	@Test 
	public void testSetShipCoordinatesInvalidInputs() {
		assertThrows(IllegalArgumentException.class, () -> {
			user.setShipCoordinates(10, 2, "vertical");
		});
		assertThrows(IllegalArgumentException.class, () -> {
			user.setShipCoordinates(1, 11, "vertical");
		});
		assertThrows(IllegalArgumentException.class, () -> {
			user.setShipCoordinates(1, 2, "sidelengs");
		});
		assertThrows(IllegalArgumentException.class, () -> {
			user.setShipCoordinates(1, 6, "vertical");
		});
		assertThrows(IllegalArgumentException.class, () -> {
			user.setShipCoordinates(6, 1, "horizontal");
		});
		user.updateUserBoard('S', 1, 1);
		assertThrows(IllegalArgumentException.class, () -> {
			user.setShipCoordinates(1, 1, "vertical");
		});
		assertThrows(IllegalArgumentException.class, () -> {
			user.setShipCoordinates(1, 1, "horizontal");
		});
	}
	
	@Test
	public void testCreateUserShip() {
		assertEquals(5, user.getShipSize());
		assertEquals(5, user.getShipsToPlace());
		user.createUserShip(1, 1, "vertical");
		int shipCount = 0;
		for (int y = 0; y < 10; y++) {
			for (int x = 0; x < 10; x++) {
				if (user.getUserBoard()[y][x] == 'S') {
					shipCount++;
				}
			}
		}
		assertEquals(5, shipCount);
		assertEquals(4, user.getShipSize());
		assertEquals(4, user.getShipsToPlace());
	}
	
	@Test
	public void testUpdateUserBoard() {
		user.updateUserBoard('S', 5, 5);
		assertEquals(user.getUserBoard()[5][5], 'S');
	}
	
	@Test
	public void testUpdateUserBoardInvalidInput() {
		assertThrows(IllegalArgumentException.class, () -> {
			user.updateUserBoard('S', 10, 5);
		});
		assertThrows(IllegalArgumentException.class, () -> {
			user.updateUserBoard('S', 5, 10);
		});
		assertThrows(IllegalArgumentException.class, () -> {
			user.updateUserBoard('?', 5, 5);
		});
	}
}
