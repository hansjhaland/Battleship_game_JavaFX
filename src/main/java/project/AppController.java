package project;

import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.List;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class AppController {
	
	@FXML private TextField column, row;
	@FXML private GridPane userGrid, enemyGrid;
	@FXML private Button confirm;
	@FXML private Label messageToUser;
	@FXML private Label messageToUser2;
	@FXML private Label highscores;
	@FXML private RadioButton horizontal, vertical;
	private Battleship game;
	private int remainingShips;
	
	private FileSupport fileSupport = new FileSupport();
	
	
	public void onNewGame() {
		messageToUser2.setText("");
		confirm.setText("Confirm");
		game = new Battleship();
		char[][] hiddenBoard = game.getEnemyBoard().getHiddenBoard();
		for (int y = 0; y < 10; y++) {
			for (int x = 0; x < 10; x++) {
				String color = characterToColorConverter(hiddenBoard[y][x]);
				Pane pane = new Pane();
				pane.setStyle("-fx-background-color:" + color + ";-fx-border-width: 1; -fx-border-style: solid; -fx-border-color: black");
				enemyGrid.add(pane, x, y);	
			}
		}

		char[][] userBoard = game.getUserBoard().getUserBoard();
		for (int y = 0; y < 10; y++) {
			for (int x = 0; x < 10; x++) {
				String color = characterToColorConverter(userBoard[y][x]);
				Pane pane = new Pane();
				pane.setStyle("-fx-background-color:" + color + ";-fx-border-width: 1; -fx-border-style: solid; -fx-border-color: black");
				userGrid.add(pane, x, y);	
			}
		}
		remainingShips = game.getUserBoard().getShipsToPlace();
		messageToUser.setText("Tell us where you want to place a \n"
				+ " ship of lenght " + remainingShips + " \n"
				+ "Enter the coordinates in the 'Column' \n "
				+ "and 'Row' section. \n"
				+ "Then press 'confirm' to place the ship");
		
		printHighscores();

	}
	
	//Printer ingenting fra start, men legges til når spillet er ferdig
	private void printHighscores() {
		highscores.setText("");
		String highscoreString = "";
		List<Integer> highscoreList = game.getHighscores();
		Collections.reverse(highscoreList); 
		int size = highscoreList.size();
		if (size == 0) {
			highscores.setText(highscoreString);
		} else {
			for (Integer score: highscoreList) {
				highscoreString += score.toString() + "\n";
			}
			highscores.setText(highscoreString);
		}
		
	}
	
	public String characterToColorConverter(char tileType) {
		String color = "";
		if (tileType == 'W') {
			color = "#00FFFF"; //blå
		} else if (tileType == 'H') {
			color = "#A9A9A9"; //grå
		} else if (tileType == 'S') {
			color = "#000080"; //marineblå
		} else if (tileType == 'X') {
			color = "#B22222"; //rød
		} else if (tileType == 'O') {
			color = "#FFFFFF"; //hvit
		} 
		return color;
	}

	public void onConfirm() {
		if (game == null) {
			messageToUser.setText("You need to press 'New Game' first!!!");
		} else {
			remainingShips = game.getUserBoard().getShipsToPlace();
			messageToUser2.setText("");
			int x;
			int y;
			if (isValidDigit(column.getText()) && isValidDigit(row.getText())) {
				x = Integer.parseInt(column.getText());
				y = Integer.parseInt(row.getText());				
				if (remainingShips > 0) {
					messageToUser.setText("Place a ship of length " + String.valueOf(remainingShips - 1) + "!");
					boolean verticalDir = vertical.isSelected();
					boolean isValidShipHead;
					if (verticalDir) {
						isValidShipHead = game.getUserBoard().validateShipHead(x, y, "vertical");
					} else {
						isValidShipHead = game.getUserBoard().validateShipHead(x, y, "horizontal");
					}
					if (!isValidShipHead) {
						messageToUser2.setText("Watch ou for collisions! \n"
								+ "And you need to place the \n"
								+ "ships on the board!");
						column.clear();
						row.clear();
					} else {
						if (verticalDir) {
							game.getUserBoard().createUserShip(x, y, "vertical");
						} else {
							game.getUserBoard().createUserShip(x, y, "horizontal");
						}
						renderUserGrid();
						remainingShips = game.getUserBoard().getShipsToPlace();
						if (remainingShips == 0) {
							messageToUser.setText("All ships placed! \n"
									+ "Now fire at the enemy ships and \n"
									+ "see if you can hit them!");
							confirm.setText("Fire!");
						}
					}
				} else {
					runGame(x,y);
				}
			} else {
				messageToUser.setText("Input has to be a digit from 0 to 9!");
			}
		}
		
	}
	
	public boolean isValidDigit(String input) {
		try {
			Integer.parseInt(input);
			if (Integer.parseInt(input) >= 0 && Integer.parseInt(input) <= 9) {
				return true;				
			} else {
				return false;
			}
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	public void renderUserGrid() {
		userGrid.getChildren().clear();
		char[][] userBoard = game.getUserBoard().getUserBoard();
		for (int y = 0; y < 10; y++) {
			for (int x = 0; x < 10; x++) {
				String color = characterToColorConverter(userBoard[y][x]);
				Pane pane = new Pane();
				pane.setStyle("-fx-background-color:" + color + ";-fx-border-width: 1; -fx-border-style: solid; -fx-border-color: black");
				userGrid.add(pane, x, y);		
			}
		}
		column.clear();
		row.clear();
	}
	
	private void renderEnemyGrid() {
		enemyGrid.getChildren().clear();
		char[][] enemyBoard = game.getEnemyBoard().getHiddenBoard(); //getEnemyBoard() for testing
		for (int y = 0; y < 10; y++) {
			for (int x = 0; x < 10; x++) {
				String color = characterToColorConverter(enemyBoard[y][x]);
				Pane pane = new Pane();
				pane.setStyle("-fx-background-color:" + color + ";-fx-border-width: 1; -fx-border-style: solid; -fx-border-color: black");
				enemyGrid.add(pane, x, y);		
			}
		}	
	}
	
	private void runGame(int x, int y){
		messageToUser2.setText("");
		if (game.getUserHitCount() < 15 && game.getEnemyHitCount() < 15) {
			if (game.isNewCoordinates(x,y)) {
				boolean result = game.userFire(x,y);		
				if (result) {
					messageToUser.setText("It's a hit!");
				} else {
					messageToUser.setText("We missed!");
				}
				renderEnemyGrid();
				result = game.enemyFire();
				if (result) {
					messageToUser2.setText("The enemy got a hit!");
				} else {
					messageToUser2.setText("The enemy missed!");
				}
				game.increaseTurnCount();
				renderUserGrid();
				if (game.getUserHitCount() == 15 || game.getEnemyHitCount() == 15) {
					gameOver();
				}
			} else {
				messageToUser.setText("Coordinate has already \n"
						+ "been targeted! Fire somwhere else!");
			}
		} else {
		messageToUser.setText("The game is already over! \n"
				+ "Press 'New Game' to \n"
				+ "play another round!");	
		}
	}
	
	public void gameOver() {
		messageToUser2.setText("");
		if (game.getUserHitCount() == 15) {
			messageToUser.setText("Congratulations! You won!");
			boolean isHighscore = game.checkNewHighscore(game.getTurnCount());
			if (isHighscore) {
				messageToUser.setText("You won after " + game.getTurnCount() + " turns! \n"
						+ "Thats a new highscore! \n"
						+ "Congratulations!");
			}
		}
		else if (game.getEnemyHitCount() == 15) {
			messageToUser.setText("We lost, captain...");
		}
		printHighscores();
	}
	
	public void onSaveGame() {
		try {
			fileSupport.saveGame("save_file", game);
			messageToUser2.setText("Game saved");
		} catch (FileNotFoundException e) {
			messageToUser2.setText("File not found");
		}
	}
	
	public void onLoadGame() {
		try {
			game = fileSupport.loadGame("save_file");
		} catch (FileNotFoundException e) {
			messageToUser2.setText("File not found");
		}
		remainingShips = game.getUserBoard().getShipsToPlace();
		if (remainingShips == 0) {
			confirm.setText("Fire!");
		} else {
			confirm.setText("Confirm");
		}
		renderUserGrid();
		renderEnemyGrid();
	}
}
