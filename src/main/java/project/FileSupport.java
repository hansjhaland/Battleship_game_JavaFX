package project;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class FileSupport implements FileHandler {
	
	public final static String SAVE_FOLDER = "src/main/java/project/";

	@Override
	public void saveGame(String filename, Battleship game) throws FileNotFoundException{
		try (PrintWriter writer = new PrintWriter(getFilePath(filename))){
			for (int y = 0; y < 10; y++) {
				for (int x = 0; x < 10; x++) {
					char[][] board = game.getUserBoard().getUserBoard();
					writer.print(board[y][x]);
				}
			}
			writer.println();
			for (int y = 0; y < 10; y++) {
				for (int x = 0; x < 10; x++) {
					char[][] board = game.getEnemyBoard().getEnemyBoard();
					writer.print(board[y][x]);
				}
			}
			writer.println();
			for (int y = 0; y < 10; y++) {
				for (int x = 0; x < 10; x++) {
					char[][] board = game.getEnemyBoard().getHiddenBoard();
					writer.print(board[y][x]);
				}
			}
			writer.println();
			writer.println(game.getUserBoard().getShipsToPlace());
			writer.println(game.getTurnCount());
			writer.println(game.getUserHitCount());
			writer.println(game.getEnemyHitCount());
		}
	}

	@Override
	public Battleship loadGame(String filename) throws FileNotFoundException{
		try (Scanner scanner = new Scanner(new File(getFilePath(filename)))){
			String userBoardString = scanner.nextLine();
			String enemyBoardString = scanner.nextLine();
			String hiddenBoardString = scanner.nextLine();
			int remainingShips = scanner.nextInt();
			int turnCount = scanner.nextInt();
			int userHitCount = scanner.nextInt();
			int enemyHitCount = scanner.nextInt();
			
			int i = 0;
			char[][] userBoardArray = new char[10][10];
			for (int y = 0; y < 10; y++) {
				for (int x = 0; x < 10; x++) {
					userBoardArray[y][x] = userBoardString.charAt(i);
					i++;
				}
			}
			UserBoard userBoard = new UserBoard(userBoardArray, remainingShips);
			
			char[][] enemyBoardArray = new char[10][10];
			i = 0;
			for (int y = 0; y < 10; y++) {
				for (int x = 0; x < 10; x++) {
					enemyBoardArray[y][x] = enemyBoardString.charAt(i);
					i++;
				}
			}
			char[][] hiddenBoardArray = new char[10][10];
			i = 0;
			for (int y = 0; y < 10; y++) {
				for (int x = 0; x < 10; x++) {
					hiddenBoardArray[y][x] = hiddenBoardString.charAt(i);
					i++;
				}
			}
			EnemyBoard enemyBoard = new EnemyBoard(enemyBoardArray, hiddenBoardArray);
			
			Battleship game = new Battleship(userBoard, enemyBoard, userHitCount, enemyHitCount, turnCount);
			game.getEnemyBoard().printEnemyBoard();
			
			return game;
		}
	}
	
	public static String getFilePath(String filename) {
		return SAVE_FOLDER + filename + ".txt";
	}

}
