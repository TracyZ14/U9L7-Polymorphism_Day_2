import java.util.Scanner;
public class GridGame
{
    private Space[][] board;
    private Player player;
    private Scanner scanner;

    public GridGame()
    {
        scanner = new Scanner(System.in);
        createPlayer();
        setupBoard();
        play();
    }

    private void createPlayer()
    {
        System.out.print("Please enter your name: ");
        String name = scanner.nextLine();
        player = new Player(name);
    }

    // initialize the board instance variable to be a 8x8 board;
    // place new Space object with "_" as the symbol into each board position;
    // place the Player object at lower left corner;
    // initialize and place a Goal object with the symbol "X" in the upper right corner;
    // create several Treasure objects (up to you how many), with symbol of your choice,
    // each with a point value that you decide, and place them throughout the board
    private void setupBoard()
    {
        board = new Space[8][8];
        Space empty = new Space("_");
        for(int row = 0; row < 8; row++)
        {
            for(int column = 0; column < 8; column++)
            {
                board[row][column] = empty;
            }
        }
        Goal goal = new Goal("X");
        board[0][7] = goal;
        board[7][0] = player;
        int numberOfTreasures = (int) (1 + Math.random() * 7);
        for(int i = 0; i < numberOfTreasures; i++)
        {
            int pointValue = (int) (1 + Math.random() * 5);
            Treasure randomTreasure = new Treasure("#", pointValue);
            boolean isRandom = false;
            while(!isRandom)
            {
                int row = (int) (Math.random() * 8);
                int column = (int) (Math.random() * 8);
                if((row != 0) && (column != 7))
                {
                    if((row != 7) && (column != 0))
                    {
                        isRandom = true;
                        board[row][column] = randomTreasure;
                    }
                }
            }
        }
    }

    /* prints the 2D array board, showing the symbol for each Space, e.g.
       _______X
       __#_____
       _____#__
       _#______
       ________
       ______#_
       ________
       M___#___
     */
    private void printBoard()
    {
        for(Space[] row : board)
        {
            for(Space symbol : row)
            {
                System.out.print(symbol.getSymbol());
            }
            System.out.println();
        }
    }

    // plays the game;
    private void play()
    {
        // WRITE THIS METHOD
        // main game loop:
        // while the player has not yet reached the goal, print the board (complete can call helper method below)
        // then asks user to enter a direction: W, A, S, D (up, left, down, right).
        // if the intended direction is in bounds, move the Player to the new location and fill previous position
        // with a Space object (with "_" symbol).
        // if player moves to a position occupied by a Treasure, add its point value to the players score,
        // and replace that element with a Space object (with "_" symbol).
        // if the player reaches the goal, end the game and print their final score and the number of moves it took
        printBoard();
        boolean reachedGoal = false;
        while(!reachedGoal)
        {
            int playerRow = -1;
            int playerColumn = -1;
            boolean foundPosition = false;
            for(int row = 0; (row < 8) && (!foundPosition); row++)
            {
                for(int column = 0; (column < 8) && (!foundPosition); column++)
                {
                    String currentSymbol = board[row][column].getSymbol();
                    if(currentSymbol.equals(player.getSymbol()))
                    {
                        playerRow = row;
                        playerColumn = column;
                        foundPosition = true;
                    }
                }
            }
            boolean isInBounds = false;
            int newPlayerRow = playerRow;
            int newPlayerColumn = playerColumn;
            while(!isInBounds)
            {
                System.out.println("Enter W, A, S, D: ");
                String direction = scanner.nextLine();
                isInBounds = true;
                if((direction.equals("W")) && ((newPlayerRow - 1) >= 0))
                {
                    newPlayerRow--;
                }
                else if((direction.equals("A")) && ((newPlayerColumn - 1) >= 0))
                {
                    newPlayerColumn--;
                }
                else if((direction.equals("S")) && ((newPlayerRow + 1) < 8))
                {
                    newPlayerRow++;
                }
                else if((direction.equals("D")) && (( newPlayerColumn + 1) < 8))
                {
                    isInBounds = false;
                    newPlayerColumn++;
                }
                else
                {
                    System.out.println("You will go out of bounds!");
                    isInBounds = false;
                }
            }
            Space empty = new Space("_");
            board[playerRow][playerColumn] = empty;
            board[newPlayerRow][newPlayerColumn] = player;
            printBoard();
        }
    }
}