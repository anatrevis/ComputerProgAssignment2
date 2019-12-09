import java.util.Random;
import java.util.Scanner;

public class Main {
	
	public static boolean gamestate; //this is a control variable for the game while loop. It changes to false if the player wins - if the score is 1024 or if there arent any possible movements
	
	public static int score, number_of_free_cells; // this variables control respectively the game score (or the highest number in grid) and the number of free cells in the grid;

	public static void main(String[] args) {
		int height=0, width=0, play;
		boolean inputok = false;
		gamestate = true;
		score = 1;
		
		System.out.println("--- 1048 GAME ---");
		
		while(inputok == false){ //asks the user to input the grid height and width
			System.out.println("\nAttention: You can only choose integers between 4 and 8");
			Scanner in = new Scanner(System.in);
			System.out.println("Choose grid height and width:");
			height = in.nextInt();
			
			if (height>4 && height<8) { //if users input is a integer <4 or >8, repeat the instructions and ask again 
				width = height;
				inputok = true;
				System.out.println("--- LET'S PLAY ---"); // when user inputs valid grid measurements, the game can start
			}
			
			else {
				inputok = false;
			}
		}

		
		int[][] grid= new int[height][width]; //allocates memory space with the grid measurements in a matrix
		
		
		for (int i=0; i<2; i++) { //adds two 1 to random cells in the beginning
			grid = addrandom1(grid, height, width);
		};
		
		displayGrid(grid, height, width); //creates the game grid
		
		while (gamestate == true) { 
			play = playChoices(grid,height,width); //show movements instructions and return the user input 
			grid = move(grid, height, width, play); //moves according to the movement user chose in "play". inside this function "addrandom1" is called
			displayGrid(grid, height, width);  //prints the grid after user played
			//System.out.println("Number of free cells:"+number_of_free_cells);
			if (number_of_free_cells == 1) { //if there is ony one free cell in the grid, then it needs testing
				boolean validmovements = testValidMovements(grid, height, width); //tests if there are still any possible valid movements in the game 
				if (!validmovements) { //if there arent any valid movements, the game ends and player lose
					System.out.println("No more movements. YOU LOST.");
					gamestate = false; 
				}
			}

			System.out.println("SCORE: " +score +"\n");
		}

	}
	
	public static int playChoices(int[][] grid, int height, int width) { //show movements instructions and return the user input 
		int play=0;
		
		do {
			System.out.println("You can choose these movements..." );
			System.out.println("w to go up;" ); //1
			System.out.println("s to go down" ); //2
			System.out.println("a to go left" ); //3
			System.out.println("d to go right" ); //4
			Scanner in = new Scanner(System.in); 
			System.out.println("Please, enter your choice: " );
			char option = in.next().charAt(0);
			
			switch(option) {
			
			case 'w':
				play = 1;
				break;
				
			case 'a':
				play = 3;
				break;
			
			case 's':
				play = 2;
				break;
			
			case 'd':
				play = 4;
				break;
				
			default:
				System.out.println("Invalid input.\n" ); //if the user inputs an invalid play, the turn doesnt counts. The grid is printed and the play is asked again
				displayGrid(grid, height, width);
			
			}
		}while(play > 4 || play < 1);
		
		return play;
	}
	
	public static void displayGrid(int[][] grid, int grid_height, int grid_width ){ //this function prints the grid
		
		for (int x=0; x<grid_width; x++) {
			System.out.print("+-----"); //prints the upper border'
		}
		System.out.print("+"); //prints the last + of upper border
		
		for(int i=0; i<grid_height;i++) {
			System.out.print("\n"); //skips the line
			
			for(int j=0;j<grid_width;j++) { 
				if (grid[i][j]== 0) {
					
					System.out.print("|");//prints separator before each cell
					System.out.print("     "); //prints a space for 4 numbers (free cell)
				}
				
				else {

					String str = String.valueOf(grid[i][j]);
					if (str.length()==1){
						System.out.print("|");
						System.out.print("  " + str + "  "); //prints a space for 3 numbers (cell occupied by 1 digit number)
					}
					else if (str.length()==2){
						System.out.print("|");
						System.out.print(" " + str + "  "); //prints a space for 2 numbers (cell occupied by 2 digits number)
					}
					else if (str.length()==3){
						System.out.print("|");
						System.out.print(" " + str + " "); //prints a space for 1 numbers (cell occupied by 3 digits number)
					}
					else if (str.length()==4){
						System.out.print("|");
						System.out.print(" " + str); //prints a space for 0 numbers (cell occupied by 4 digits number)
					}
				}
					
				if(j==grid_width-1) 
					System.out.print("|"); //prints separator for external right border
			}

			if(i<grid_height-1){
				System.out.print("\n+-----"); //prints the lines to separate the cells horizontally (just in the first column)
				for (int x=0; x<grid_width-1; x++) {
					System.out.print("+-----"); //prints the lines to separate the rest of the cells horizontally
				}
				System.out.print("+"); //prints + for external right border
				
			}
		}
		System.out.print("\n");
		for (int x=0; x<grid_width; x++) {
			System.out.print("+-----"); //prints the bottom border'
		}
		System.out.print("+"); //prints the last + of bottom border'
		System.out.println("\n");
	}

	public static int[][] addrandom1(int grid[][], int height, int width) { //this function adds a 1 to a random unoccupied cell of the grid
		number_of_free_cells = 0; //sets the global variable on 0 in the start
	
		for(int i=0; i<height;i++) { //percorre the matrix and determine the necessary memory for a list with the current free cells
			for(int j=0;j<width;j++) {
				
				if (grid[i][j]>score) { //gets the game's highest score
					score = grid[i][j];
				}
				
				if (grid[i][j]==0) { //check if its a free cell;
					number_of_free_cells++; //sums 1 to the number of free cells
				}
			}
		}
		
		
		if (score == 1024) { //if the game score is 1024, the player wins
			System.out.println("Congratulations!!! YOU WON.");
			gamestate = false;
		}

		int[] free_cells_list = new int[number_of_free_cells]; //allocates the memory for a free cell list
		int pos=0;
		
		for(int i=0; i<height;i++) { //adds the indexes of the free cells in the free cells list
			for(int j=0;j<width;j++) {
				if (grid[i][j]==0) {
					int index = (i*width)+j;
					free_cells_list[pos] = index; //codes the positions into indexes (from 0 to number of free cells)
					pos++;
					
				}
			}
		}
		
		Random random = new Random();
		int random_free = random.nextInt(number_of_free_cells); //select one index of the free cells list 

		//decode the index into positions:
		
		int pos_height = free_cells_list[random_free]/width; //this is the height

		int pos_width = free_cells_list[random_free]-(pos_height * width); //this is the width of the selected index from free cells list
		
		grid [pos_height][pos_width] = 1; // adds 1 to the position sorted
		
		return grid;
	}
	

	public static int[][] move(int grid[][], int height, int width, int play){ //moves the grid according to the movement the player chose
		boolean moved = false; //control variable to see if there were any changes in the grid with the selected play
		
		if (play == 4) { //moves grid to the right 
			for(int i=0; i<height;i++) { 
				for(int j=width-1;j>=0;j--) {
					
					int nextcell = 1;
					int x=j;
					// this while moves all the numbers to the right of the grid
					while (x>0 && nextcell <=x ) {  //compares each cell to their next

						if (grid[i][x] == 0) { //if the actual cell is emty
							if(grid[i][x-nextcell] != 0) { //cheks if the next one isnt empty
								moved = true; //if the next one is not empty, the grid moved it to the empty cell
							}
							grid[i][x] = grid[i][x-nextcell]; //actual gets next
							grid[i][x-nextcell] = 0; //next gets 0
							
						}
						else if(grid[i][x] != 0 && grid[i][x-nextcell]!=0 && grid[i][x] != grid[i][x-nextcell]){ //if the actual cell is not empty, the next cell is not empty and they are different from each other, it doesnt change
							break; //gets out of the while
						}
						
						else if(grid[i][x] != 0 && grid[i][x] == grid[i][x-nextcell]){//if the actual cell is not empty, and the next cell is equal, the actual cell gets a sum of both cells
							grid[i][x] = grid[i][x-nextcell] + grid[i][x]; //actual sums with next
							grid[i][x-nextcell] = 0; //next gets 0;
							moved = true;
							break;//gets out of the while
							
						}
						nextcell ++;
					}	

				}
			}
		}
		
		if (play == 3) { //moves grid to the left
			
			for(int i=0; i<height;i++) { 
				for(int j=0;j<=width-1;j++) {
					
					int nextcell = 1;
					int x=j;
					while (x < width && nextcell <= width-x-1 ) { // this while moves all the numbers to the left of the grid
						if (grid[i][x] == 0) {
							if(grid[i][x+nextcell] != 0) {
								moved = true;
							}
							grid[i][x] = grid[i][x+nextcell]; //actual gets next
							grid[i][x+nextcell] = 0; //next gets empty
						}
						else if(grid[i][x] != 0 && grid[i][x+nextcell] != 0 && grid[i][x] != grid[i][x+nextcell] ){
							break;
						}
						
						else if(grid[i][x] != 0 && grid[i][x] == grid[i][x+nextcell]){
							grid[i][x] = grid[i][x+nextcell] + grid[i][x]; //actual sums next
							grid[i][x+nextcell] = 0; //next gets empty
							moved = true;
							break;
						}
						nextcell ++;
					}	

				}
			}
		}
		
		if (play == 1) { //moves grid up
			
			for(int i=0; i<width;i++) { 
				for(int j=0;j<=height-1;j++) {
					
					int nextcell = 1;
					int x=j;
					while (x < height && nextcell <= height-x-1 ) { // this while moves all the numbers to the top of the grid
						if (grid[x][i] == 0) {
							if(grid[x+nextcell][i] != 0) {
								moved = true;
							}
							grid[x][i] = grid[x+nextcell][i]; //actual gets next
							grid[x+nextcell][i] = 0; //next gets empty
						}
						else if(grid[x][i] != 0  && grid[x+nextcell][i] !=0 && grid[x][i] != grid[x+nextcell][i]){
							break;
						}
						else if(grid[x][i] != 0 && grid[x][i] == grid[x+nextcell][i]){
							grid[x][i] = grid[x+nextcell][i] + grid[x][i]; //actual sums next
							grid[x+nextcell][i] = 0; //next gets empty
							moved = true;
							break;
						}

						nextcell ++;
					}	

				}
			}
		}
		
		if (play == 2) { //moves grid down
			
			for(int i=0; i<width;i++) { 
				for(int j=height-1;j>=0;j--) {
					
					int nextcell = 1;
					int x=j;
					while (x > 0 && nextcell <= x ) { // this while moves all the numbers to the bottom of the grid
						if (grid[x][i] == 0) {
							if(grid[x-nextcell][i] != 0) {
								moved = true;
							}
							grid[x][i] = grid[x-nextcell][i]; //actual gets next
							grid[x-nextcell][i] = 0; //next gets empty
						}
						
						else if(grid[x][i] != 0 && grid[x-nextcell][i] !=0 && grid[x][i] != grid[x-nextcell][i]){
							break;
						}
						
						else if(grid[x][i] != 0 && grid[x][i] == grid[x-nextcell][i]){
							grid[x][i] = grid[x-nextcell][i] + grid[x][i]; //actual sums next
							grid[x-nextcell][i] = 0; //next goes empty
							moved = true;
							break;
							
						}
						nextcell ++;
					}	

				}
			}
		}
		
		if(!moved) {
			System.out.println("\nINVALID MOVEMENT! Try another\n"); //if the grid didnt move any cell with the play, this turn doesnt count
		} 
		
		else {
			grid = addrandom1(grid, height, width); // if grid moved, adds a random 1 to a free cell of the grid
		}
	
		return grid;
		
	};
	
	public static boolean testValidMovements(int grid[][], int height, int width) { //checks if there are any valid movements player can do
		boolean validmovement=false;
		for(int i=0; i<height;i++) { //goes trough all the array
			if(validmovement) {  //if there were any valid movement, gets out the for and returns true so game can continue
				break;
			}
			for(int j=0;j<width;j++) {   //goes trough all the array
				if (grid [i][j] == 0 ) {
					validmovement = true;
					break;
				}
				
				if (j<width-1) {
					if (grid[i][j] == grid[i][j+1]) { //compares horizontally
						validmovement = true;
						break;
					}
				}

				if (i < height-1) {
					if(grid[i][j] == grid[i+1][j]) { //compares vertically
						validmovement = true;
						break;
					}
				}

			}
		}
		
		return validmovement; //returns
	}
	
}
