import java.util.Random;
import java.util.Scanner;

public class Main {
	
	public static boolean gamestate;
	
	public static int highest_number;

	public static void main(String[] args) {
		int height=0, width=0, play;
		boolean inputok = false;
		gamestate = true;
		highest_number = 1;
		
		System.out.println("--- 1048 GAME ---");
		
		while(inputok == false){ //asks the user to input the grid height and width
			System.out.println("You can only choose integers between 4 and 8");
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
			play = playChoices(grid,height,width); //show movements instructions and gets the user input 
			grid = move(grid, height, width, play);
			displayGrid(grid, height, width); 
			
			boolean validmovements = testValidMovements(grid, height, width); //tests to see if there are still any valid movements in the game 
			
			if (!validmovements) {
				System.out.println("No more movements. YOU LOST.");
				gamestate = false;
			}
			
			System.out.println("SCORE: " +highest_number +"\n");
			//System.out.println("Game state: " +gamestate +"\n");
		}

	}
	
	public static int playChoices(int[][] grid, int height, int width) {
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
				System.out.println("Invalid input.\n" );
				displayGrid(grid, height, width);
			
			}
		}while(play > 4 || play < 1);
		
		return play;
	}
	
	public static void displayGrid(int[][] grid, int grid_height, int grid_width ){
		
		for (int x=0; x<grid_width; x++) {
			System.out.print("+-----");
		}
		System.out.print("+");
		
		for(int i=0; i<grid_height;i++) {
			System.out.print("\n");
			
			for(int j=0;j<grid_width;j++) { //width
				if (grid[i][j]== 0) {
					
					System.out.print("|");
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
					System.out.print("|"); //prints separator
			}

			if(i<grid_height-1){
				System.out.print("\n+-----");
				for (int x=0; x<grid_width-1; x++) {
					System.out.print("+-----");
				}
				System.out.print("+");
				
			}
		}
		System.out.print("\n");
		for (int x=0; x<grid_width; x++) {
			System.out.print("+-----");
		}
		System.out.print("+");
		System.out.println("\n");
	}

	public static int[][] addrandom1(int grid[][], int height, int width) {
		int number_of_free_cells=0;
		
		for(int i=0; i<height;i++) { //allocates the necessary memory for a list with the current free cells
			for(int j=0;j<width;j++) {
				
				if (grid[i][j]>highest_number) {
					highest_number = grid[i][j];
				}
				
				if (grid[i][j]==0) {
					//System.out.println("grid[" +i+"]["+j+"] is free cell");
					number_of_free_cells++;
				}
			}
		}
		
		if (highest_number == 1024) {
			System.out.println("Congratulations!!! YOU WON.");
			gamestate = false;
		}
		
		
		//System.out.println("Number of free cells: "+ number_of_free_cells);

		int[] free_cells_list = new int[number_of_free_cells]; //creates a free cell array
		int pos=0;
		
		for(int i=0; i<height;i++) { //adds the indexes of the free cells in the free cells list
			for(int j=0;j<width;j++) {
				if (grid[i][j]==0) {
					
					int index = (i*width)+j;
					free_cells_list[pos] = index; //codes the positions into indexes
//					System.out.print(free_cells_list[pos]+ " ");
					pos++;
					
				}
			}
		}
		
		Random random = new Random();
		int random_free = random.nextInt(number_of_free_cells);
//		System.out.println("\nthis is the random: "+free_cells_list[random_free]);

		//decode the index into positions
		
		int pos_height = free_cells_list[random_free]/width;
		//System.out.println("this is the pos_height: "+pos_height);
		int pos_width = free_cells_list[random_free]-(pos_height * width);
		
		//System.out.println("random added at grid["+pos_height+"]["+pos_width+"]\n");
		
		grid [pos_height][pos_width] = 1; // adds 1 to the position sorted
		
		return grid;
	}

	public static int[][] move(int grid[][], int height, int width, int play){ 
		boolean moved = false; 
		
		if (play == 4) { //moves grid to the right 
			for(int i=0; i<height;i++) { 
				for(int j=width-1;j>=0;j--) {
					
					int nextcell = 1;
					int x=j;
					
					while (x>0 && nextcell <=x ) { // this while moves all the numbers to the right of the grid

						if (grid[i][x] == 0) {
							if(grid[i][x-nextcell] != 0) {
								moved = true;
							}
							grid[i][x] = grid[i][x-nextcell]; //atual recebe prox
							grid[i][x-nextcell] = 0; //prox recebe 0
							
						}
						else if(grid[i][x] != 0 && grid[i][x-nextcell]!=0 && grid[i][x] != grid[i][x-nextcell]){
							break;
						}
						
						else if(grid[i][x] != 0 && grid[i][x] == grid[i][x-nextcell]){
							grid[i][x] = grid[i][x-nextcell] + grid[i][x]; //atual soma prox
							grid[i][x-nextcell] = 0; //prox recebe 0
							moved = true;
							break;
							
						}
						nextcell ++;
					}	

				}
			}
		}
		
		if (play == 3) { //moves grid to the left
			
			for(int i=0; i<height;i++) { 
				for(int j=0;j<=width-1;j++) {
//					System.out.println("i:"+ i+" j:" +j);
//					System.out.println("grid:"+ grid[i][j] );
					
					int nextcell = 1;
					int x=j;
					while (x < width && nextcell <= width-x-1 ) { // this while moves all the numbers to the left of the grid
						//System.out.println("atual: i:"+ i+" j:" +x);
						//System.out.println("prox: i:"+ i+" j:" + (x+nextcell));
						//System.out.println("num atual: "+ grid[i][x]+" num prox: " +grid[i][x+nextcell]+ "\n");
////						System.out.println("num atual: "+ grid[i][x]+" num prox: " +grid[i][x-nextcell]);
						if (grid[i][x] == 0) {
							if(grid[i][x+nextcell] != 0) {
								moved = true;
							}
							grid[i][x] = grid[i][x+nextcell]; //atual recebe prox
							grid[i][x+nextcell] = 0; //prox recebe 0
						}
						else if(grid[i][x] != 0 && grid[i][x+nextcell] != 0 && grid[i][x] != grid[i][x+nextcell] ){
							break;
						}
						
						else if(grid[i][x] != 0 && grid[i][x] == grid[i][x+nextcell]){
							grid[i][x] = grid[i][x+nextcell] + grid[i][x]; //atual soma prox
							grid[i][x+nextcell] = 0; //prox recebe 0
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
//					System.out.println("j:"+ j+" i:" +i);
//					System.out.println("grid:"+ grid[j][i] );
//					
					int nextcell = 1;
					int x=j;
					while (x < height && nextcell <= height-x-1 ) { // this while moves all the numbers to the top of the grid
//						System.out.println("atual: j:"+ x+" i:" +i);
//						System.out.println("prox: j:"+ (x+nextcell)+" i:" + i);
//						System.out.println("num atual: "+ grid[x][i]+" num prox: " +grid[x+nextcell][i]+ "\n");

						if (grid[x][i] == 0) {
							if(grid[x+nextcell][i] != 0) {
								moved = true;
							}
							grid[x][i] = grid[x+nextcell][i]; //atual recebe prox
							grid[x+nextcell][i] = 0; //prox recebe 0
						}
						else if(grid[x][i] != 0  && grid[x+nextcell][i] !=0 && grid[x][i] != grid[x+nextcell][i]){
							break;
						}
						else if(grid[x][i] != 0 && grid[x][i] == grid[x+nextcell][i]){
							grid[x][i] = grid[x+nextcell][i] + grid[x][i]; //atual soma prox
							grid[x+nextcell][i] = 0; //prox recebe 0
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
//					System.out.println("j:"+ j+" i:" +i);
//					System.out.println("grid:"+ grid[j][i] );
					
					int nextcell = 1;
					int x=j;
					while (x > 0 && nextcell <= x ) { // this while moves all the numbers to the bottom of the grid
//						System.out.println("atual: j:"+ x+" i:" +i);
//						System.out.println("prox: j:"+ (x-nextcell)+" i:" + i);
//						System.out.println("num atual: "+ grid[x][i]+" num prox: " +grid[x-nextcell][i]+ "\n");
//
						if (grid[x][i] == 0) {
							if(grid[x-nextcell][i] != 0) {
								moved = true;
							}
							grid[x][i] = grid[x-nextcell][i]; //atual recebe prox
							grid[x-nextcell][i] = 0; //prox recebe 0
						}
						
						else if(grid[x][i] != 0 && grid[x-nextcell][i] !=0 && grid[x][i] != grid[x-nextcell][i]){
							break;
						}
						
						else if(grid[x][i] != 0 && grid[x][i] == grid[x-nextcell][i]){
							grid[x][i] = grid[x-nextcell][i] + grid[x][i]; //atual soma prox
							grid[x-nextcell][i] = 0; //prox recebe 0
							moved = true;
							break;
							
						}
						nextcell ++;
					}	

				}
			}
		}
		
		if(!moved) {
			System.out.println("Invalid Movement!");
		} 
		
		else {
			grid = addrandom1(grid, height, width);
		}
	
		return grid;
		
		
	};
	
	public static boolean testValidMovements(int grid[][], int height, int width) {
		boolean validmovement=false;
		//System.out.println(grid);
		
		for(int i=0; i<height;i++) { 
			if(validmovement) {
				break;
			}
			for(int j=0;j<width;j++) {
				//System.out.println("hello again"+ grid[i][j]);
				if (grid [i][j] == 0 ) {
					validmovement = true;
					break;
				}
				
				if (j<width-1) {
					if (grid[i][j] == grid[i][j+1]) { //compares horizontally
						//System.out.println("horizontally comparing grid["+i+"]["+j+"] with grid["+i+"]["+(j+1)+"]");
						validmovement = true;
						break;
					}
				}

				if (i < height-1) {
					if(grid[i][j] == grid[i+1][j]) { //compares vertically
						//System.out.println("vertically comparing grid["+i+"]["+j+"] with grid["+(i+1)+"]["+j+"]");
						validmovement = true;
						break;
					}
				}

			}
		}
		
		return validmovement;
	}
	
}
