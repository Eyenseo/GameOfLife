package original;

public class GameOfLife {
	private final char[] CHOICES = {'\0', 'e', 'a', 'r', 'h', 't'}; // Available options in the Main menu
	private final char ALIVE = (char) 254; // Char for a Cell that's alive
	private final char DEAD = ' '; // Char for a Cell that's dead
	private final int SIZE = 65; // Size of the quadratic world/grid
	private final String OPTIONS =	"\nIhre Optionen:\n" // Text of the main menu
									+ "Enter\tNaechter Zueklus\n"
									+ "t\tZyklen vor springen\n"
									+ "e\tProgramm beenden\n"
									+ "a\tNeue Zelle\n"
									+ "r\tZelle entfernen\n"
									+ "h\tFuer diese Hilfe";
	private final String HELP =	"Zur hilfe mit 'h'."; // Text for the help
	private char[][] grid = new char[SIZE][SIZE]; // Array that represents the world

	private GameOfLife() {
		for(int i = 0; i < SIZE; i++) {					// Chreates the border of the wold
			grid[i][0] = (char) 186; 					// left
			grid[i][SIZE - 1] = (char) 186; 			// right
			for(int j = 0; j < SIZE; j++){
				if(i == 0 || i == SIZE - 1) 			// top and bottom
					grid[i][j] = (char) 205;
				if(i == 0 && j == 0) 					// top left hand corner
					grid[i][j] = (char) 201;
				else if(i == 0 && j == SIZE - 1) 		// top right hand corner
					grid[i][j] = (char) 187;
				else if(i == SIZE - 1 && j == 0) 		// bottom left hand corner
					grid[i][j] = (char) 200;
				else if(i == SIZE - 1 && j == SIZE - 1) // bottom right hand cornter
					grid[i][j] = (char) 188;
			}
		}
	}

	boolean isCorrectOption(char input, char[] options) { // Cheks if the Input is equal to one element of the array
		for(char o : options) {
			if( o == input) {
				return !false;
			}
		}
		return false;
	}

	boolean isCorrectOption(char input) {
		return isCorrectOption(input, CHOICES);
	}

	private boolean isNumber(int number) { // Method to check if the user choose to type 0 or if there was a mistake.
		if(number == 0) {
			char[] options = {'\0', 'n', 'y'};
			char input;

			do {
				IO.println("Haben Sie " + number + " eingetippt? y/n");
				input = IO.readChar();
			} while(!isCorrectOption(input, options));
			return input != 'n';
		}
		return true;
	}

	char getOptionInput() { // checks if the user doesn't know what to do in the small menu
		boolean second = false;
		char input;

		do {
			if(second)
				IO.println(HELP);
			input = IO.readChar();
			second = true;
		} while(!isCorrectOption(input));
		return input;
	}

	private int askForNumber() { // Gets a number of the user
		int number;

		do {
			number = IO.readInt();
		} while(!isNumber(number));
		return number;
	}

	int askForCoordinate(char coordinateChar) { // Asks the user for a coordinate
		int coordinate;

		do {
			IO.nlprint("Bitte geben Sie die " + coordinateChar + "-Koordinate ein:");
			coordinate = askForNumber();
		} while(coordinate > SIZE - 2 || coordinate < 1);
		return coordinate;
	}

	void showGrid() { // print the array on on the screen
		String world = "";
		for(char[] g : grid) {
			for(char c: g) {
				world += c;
			}
			world += '\n';
		}
		IO.println(world);
	}

	void addCell() { // Method to add a living Cell
		int hor = askForCoordinate('X');
		int ver = askForCoordinate('Y');
		grid[ver][hor] = ALIVE;
	}

	void deleteCell() { // Method to delete living Cell
		int hor = askForCoordinate('X');
		int ver = askForCoordinate('Y');
		grid[ver][hor] = DEAD;
	}

	boolean isAlive(int y, int x) { // Returns a boolean if the Cell has to be alive or dead.
		int nighbour = 0;

		for(int i = -1; i < 2; i++) {
			for(int j = -1; j < 2; j++) {
				if(!(j == 0 && i==0) && grid[y + i][x + j] == ALIVE)
					nighbour++;
			}
		}
		return (nighbour == 2 && grid[y][x] == ALIVE || nighbour == 3);
	}

	void nextCycle() { // Runs through the array and checks every cell to be alive or dead
		char[][] copy = new char[SIZE][SIZE];

		for(int i = 0; i < SIZE; i++) {
			for(int j = 0; j < SIZE; j++) {
				if(i == 0 || j == 0 || i == SIZE - 1 || j == SIZE - 1)
					copy[i][j] = grid[i][j];
				else if(isAlive(i, j))
					copy[i][j] = ALIVE;
				else
					copy[i][j] = DEAD;
			}
		}
		grid = copy;
	}

	void nextCycle(int time) {
		for(int i = 0; i < time; i++) {
			nextCycle();
			try {
				Thread.sleep(500);
			} catch(InterruptedException ex) {
				Thread.currentThread().interrupt();
			}
			showGrid();
		}
	}

	void jumpCycle() { // Asks how many cycels should be done 'at once'
		int input;

		do {
			IO.println("Wie viele Cyklen wollen sie ueberspringen?");
			input = IO.readInt();
		} while(!isNumber(input) && input > 0);
		nextCycle(input);
	}

	public static void main(String[] args) {
		GameOfLife gol = new GameOfLife();
		char input = '\0';

		IO.println(gol.OPTIONS + "\n\nWeiter mit Enter!");
		IO.readChar();

		do {
			switch(input) {
				case '\0': gol.nextCycle();
					break;
				case 't': gol.jumpCycle();
					break;
				case 'a': gol.addCell();
					break;
				case 'r': gol.deleteCell();
					break;
				case 'h': IO.println(gol.OPTIONS);
			}
			if(input != 'h')
				gol.showGrid();

			IO.println("Was wollen Sie tun?");
			input = gol.getOptionInput();
		} while(input != 'e');
	}
}
