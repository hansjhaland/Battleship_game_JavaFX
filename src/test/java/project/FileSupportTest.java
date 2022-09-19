package project;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class FileSupportTest {

	private Battleship game;
	private FileSupport fileSupport = new FileSupport();
	
	
	@Test 
	public void testLoadGame() {
		game = new Battleship();
		Battleship loadedGame;
		try {
			loadedGame = fileSupport.loadGame("test_save");
		} catch (FileNotFoundException e) {
			fail("Could not load file");
			return;
		}
		assertEquals(game.getUserBoard().getShipsToPlace(), loadedGame.getUserBoard().getShipsToPlace());
		assertEquals(game.getTurnCount(), loadedGame.getTurnCount());
		assertEquals(game.getUserHitCount(), loadedGame.getUserHitCount());
		assertEquals(game.getEnemyHitCount(), loadedGame.getEnemyHitCount());
		
		for (int y = 0; y < 10; y++) {
			for (int x = 0; x < 10; x++) {
				assertEquals(game.getEnemyBoard().getHiddenBoard()[y][x], loadedGame.getEnemyBoard().getHiddenBoard()[y][x]);
			}
		}
		for (int y = 0; y < 10; y++) {
			for (int x = 0; x < 10; x++) {
				assertEquals(game.getUserBoard().getUserBoard()[y][x], loadedGame.getUserBoard().getUserBoard()[y][x]);
			}
		}
		int shipCount = 0;
		int loadedShipCount = 0;
		for (int y = 0; y < 10; y++) {
			for (int x = 0; x < 10; x++) {
				if (game.getUserBoard().getUserBoard()[y][x] == 'S') {
					shipCount++;
				}
			}
		}
		for (int y = 0; y < 10; y++) {
			for (int x = 0; x < 10; x++) {
				if (loadedGame.getUserBoard().getUserBoard()[y][x] == 'S') {
					loadedShipCount++;
				}
			}
		}
		assertEquals(loadedShipCount, shipCount);
	}
	
	@Test
	public void testLoadGameFileNotFound() {
		assertThrows( FileNotFoundException.class, () -> {
			game = fileSupport.loadGame("notfound");
		});
	}
	
	@Test
	public void testSaveGame() {
		game = new Battleship();
		try {
			fileSupport.saveGame("test_save", game);
		} catch (FileNotFoundException e1) {
			fail("Could not save test file");
		}
		try {
			fileSupport.saveGame("new_test_save", game);
		} catch (FileNotFoundException e2) {
			fail("Could not save test file");
		}
		
		byte[] testFile = null, newFile = null;
		
		try {
			testFile = Files.readAllBytes(Path.of(FileSupport.getFilePath("test_save")));
		} catch (IOException e) {
			fail("Could not load test file");
		}
		try {
			newFile = Files.readAllBytes(Path.of(FileSupport.getFilePath("new_test_save")));
		} catch (IOException e) {
			fail("Could not load new test file");
		}
		assertTrue(testFile != null);
		assertTrue(newFile != null);
		assertTrue(Arrays.equals(testFile, newFile));
	}
	
	@AfterAll
	static void teardown() {
		File newTestSave = new File(FileSupport.getFilePath("new_test_save"));
		newTestSave.delete();
	}
}
