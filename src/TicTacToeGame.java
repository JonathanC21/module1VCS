//Documentation: https://www.thesprucecrafts.com/tic-tac-toe-game-rules-412170

import java.util.Scanner;
import java.util.Random;

public class TicTacToeGame {
	static boolean wantsToPlay = true;
	static boolean alreadyPlayed = false;
	static int freeSpacesLeft = 9;
	static boolean gameWinner;
	static char [][] gameBoard = new char[3][3];
	static char userMarker;
	static char compMarker;
	
	//Asks the user if they want to play. Will ask them if they want to play again after first run. Not typing yes will be registered as "no".
	public static boolean userWantsToPlay(Scanner inputAsk) {
		boolean playOrNot;
		if (alreadyPlayed == false) {
			System.out.println("Do you want to play a game of Tic Tac Toe?");
		}
		else {
			System.out.println("Do you want to play another game of Tic Tac Toe?");
		}
		
		String playing = inputAsk.next();
		
		if (playing.equals("Yes") || playing.equals("yes")) {
			playOrNot = true;
		}
		else {
			playOrNot = false;
		}
		
		return playOrNot;
	}
	
	//Fills the gameboard array with dashes and prints it to console. Used when starting a new game.
	public static void buildGameBoard() {
		for (int row = 0; row < gameBoard.length; ++row) {
			for (int col = 0; col < gameBoard[row].length; ++col) {
				gameBoard[row][col] = '-';
				System.out.print(gameBoard[row][col]);
			}
			System.out.println();
		}
	}
	
	//Prints the gameboard array to console.
	public static void displayGameBoard() {
		for (int row = 0; row < gameBoard.length; ++row) {
			for (int col = 0; col < gameBoard[row].length; ++col) {
				System.out.print(gameBoard[row][col]);
			}
			System.out.println();
		}
	}
	
	//Asks the player if they want to use "X" or "Y". Will set computers marker automatically depending on the user's choice.
	public static void askForMarker(Scanner userMarkerInput) {
		boolean validInput = true;
		do {
		System.out.println("Do you want to be X or O?");
		char marker = userMarkerInput.next().charAt(0);
		if (marker == 'X') {
			userMarker = 'X';
			compMarker = 'O';
			validInput = true;
		}
		else if (marker == 'O') {
			userMarker = 'O';
			compMarker = 'X';
			validInput = true;
		}
		else {
			printInvadidInput();
			validInput = false;
		}
		} while (validInput == false);
		
	}
	
	//Gets the users input for the row and column of the gameboard. An invalid input will ask the user again. The user input is stored in a variable and incremented by one when transferring to the gameboard array.
	public static void getPlayerMove(Scanner rowInp, Scanner colInp) {
		int selectedRow;
		int selectedColumn;
		boolean validRowInput = true;
		boolean validColumnInput = true;
		boolean validMove = true;
		
		do {
			do {
			System.out.println("Type row to place marker");
			selectedRow = rowInp.nextInt();
			if (selectedRow <= 0 || selectedRow >= 4) {
				printInvadidInput();
				validRowInput = false;
			}
			else {
				validRowInput = true;
			}
			} while (validRowInput == false);
			
			do {
			System.out.println("Type column to place marker");
			selectedColumn = colInp.nextInt();
			if (selectedColumn <= 0 || selectedColumn >= 4) {
				printInvadidInput();
				validColumnInput = false;
			}
			else {
				validColumnInput = true;
			}
			} while (validColumnInput == false);
			
			if (gameBoard[selectedRow - 1][selectedColumn - 1] != '-') {
				System.out.println("That spot is already occupied!");
				validMove = false;
			}
			else {
				System.out.println("Your move: " + selectedRow + ", " + selectedColumn);
				gameBoard[selectedRow - 1][selectedColumn - 1] = userMarker;
				validMove = true;
				freeSpacesLeft -= 1;
			}
		} while (validMove == false);
		displayGameBoard();
		
	}
	
	//Shortcut for telling user they used an invalid input.
	public static void printInvadidInput() {
		System.out.println("Please type a valid input.");
	}
	
	//Identical to the player input except that it uses a random number instead of user input for rows and columns and only prints the final move to console.
	public static void computerMove(Random randGen) {
		int selectedRow;
		int selectedColumn;
		boolean validRowInput = true;
		boolean validColumnInput = true;
		boolean validMove = true;
		
		do {
			do {
				selectedRow = randGen.nextInt(3);
				if (selectedRow < 0 || selectedRow > 3) {
					validRowInput = false;
				}
				else {
					validRowInput = true;
				}
				} while (validRowInput == false);
				
				do {
				selectedColumn = randGen.nextInt(3);
				if (selectedColumn < 0 || selectedColumn > 3) {
					validColumnInput = false;
				}
				else {
					validColumnInput = true;
				}
				} while (validColumnInput == false);
				
				if (gameBoard[selectedRow][selectedColumn] != '-') {
					validMove = false;
				}
				else {
					System.out.println("Computer move: " + (selectedRow + 1) + ", " + (selectedColumn + 1));
					gameBoard[selectedRow][selectedColumn] = compMarker;
					validMove = true;
					freeSpacesLeft -= 1;
				}
			
		} while(validMove == false);
		displayGameBoard();
	}
	
	//Iterates through each row of the gameboard array to determine if any markers are in a row.
	public static boolean horizontalWinner() {
		boolean winner = false;
		for (int row = 0; row < gameBoard.length; ++row) {
			if (gameBoard[row][0] == userMarker && gameBoard[row][1] == userMarker && gameBoard[row][2] == userMarker) {
				winner = playerWinner();
			}
			if (gameBoard[row][0] == compMarker && gameBoard[row][1] == compMarker && gameBoard[row][2] == compMarker) {
				winner = compWinner();
			}
		}
		return winner;
	}
	
	//Iterates through each column of the gameboard array to determine if any markers are in a column.
	public static boolean verticalWinner() {
		boolean winner = false;
		for (int col = 0; col < gameBoard.length; ++col) {
			if (gameBoard[0][col] == userMarker && gameBoard[1][col] == userMarker && gameBoard[2][col] == userMarker) {
				winner = playerWinner();
			}
			if (gameBoard[0][col] == compMarker && gameBoard[1][col] == compMarker && gameBoard[2][col] == compMarker) {
				winner = compWinner();
			}
		}
		return winner;
	}
	
	//Brute force method of detecting a diagonal row for each marker type.
	public static boolean diagonalWinner() {
		boolean winner = false;
		
			if (gameBoard[0][0] == userMarker && gameBoard[1][1] == userMarker && gameBoard[2][2] == userMarker) {
				winner = playerWinner();
			}
			if (gameBoard[0][2] == userMarker && gameBoard[1][1] == userMarker && gameBoard[2][0] == userMarker) {
				winner = playerWinner();
			}
			if (gameBoard[0][0] == compMarker && gameBoard[1][1] == compMarker && gameBoard[2][2] == compMarker) {
				winner = compWinner();
			}
			if (gameBoard[0][2] == compMarker && gameBoard[1][1] == compMarker && gameBoard[2][0] == compMarker) {
				winner = compWinner();
			}
			return winner;
		
	}
	
	//Shorter way of reporting that the player won to the console and winner methods.
	public static boolean playerWinner() {
		boolean winner = true;
		System.out.println("Player Wins!");
		return winner;
	}
	
	//Shorter way of reporting that the computer won to the console and winner methods.
	public static boolean compWinner() {
		boolean winner = true;
		System.out.println("Computer Wins!");
		return winner;
	}
	
	//Checks if any win condition is true in addition to a no winner condition.
	public static boolean checkForWinner() {
		boolean winner = false;
		if (horizontalWinner() == true || verticalWinner() == true || diagonalWinner() == true) {
			winner = true;
		}
		if (freeSpacesLeft <= 0 && winner == false) {
			winner = true;
			System.out.println("No winner! Reset.");
		}
		return winner;
	}
	
	public static void resetVar() {
		alreadyPlayed = true;
		gameWinner = false;
		freeSpacesLeft = 9;
	}
	
	
	public static void main (String [] args) {
		Scanner playScnr = new Scanner (System.in);
		Scanner rowScnr = new Scanner (System.in);
		Scanner colScnr = new Scanner (System.in);
		Scanner markerScnr = new Scanner (System.in);
		Random compChoice = new Random();
		
		while (wantsToPlay == true) {
			wantsToPlay = userWantsToPlay(playScnr);
			if (wantsToPlay == false) {
				break;
			}
			
			if (alreadyPlayed == false) {
				askForMarker(markerScnr);
			}
			
			buildGameBoard();
			
			while (gameWinner == false) {
			getPlayerMove(rowScnr,colScnr);
			gameWinner = checkForWinner();
			if (gameWinner == true) {
				break;
			}
			computerMove(compChoice);
			gameWinner = checkForWinner();
			 }
	
			resetVar();
		}
		
		System.out.println("Thanks for playing!");
		
	}
}
