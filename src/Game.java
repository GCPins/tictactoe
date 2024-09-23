import org.apache.commons.text.WordUtils;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public class Game {

    int turn = 0;
    Board board;
    boolean gameOver = false;

    String[] encourage = {"best of luck!", "you got this!", "don't give up!", "you're so close!", "you can do it!", "I believe in you!", "tread carefully!"};

    static int WRAP_LEN = 80;

    public void run(String[] args) {
        start(args);

        if (args.length == 2) {
            board = new Board(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        } else {
            board = new Board();
        }

        /* tests
        board.placeMarker(0,0,1);
        board.placeMarker(1,1,1);
        board.placeMarker(2,2,1);

        board.placeMarker(1,2,2);
        board.placeMarker(0,1,2);
        board.placeMarker(0,2,2);
        board.placeMarker(1,0,2);
        board.placeMarker(2,0,1);
        board.placeMarker(2,1,2);
        */
        Scanner s = new Scanner(System.in);
        while(board.checkWin() == 0) {
            System.out.println("Turn #" + (turn+1) + "\n");
            board.print();
            int pTurn = (turn % 2) + 1;
            System.out.println("\nOk, your turn Player " + (pTurn + " (\"" + ( (turn % 2) == 1 ? "0" : "X") + "\") - " + encourage[ThreadLocalRandom.current().nextInt(0, encourage.length)]));// [" - best of luck!"]);
            System.out.print("\nEnter your move here: ");
            int[] res = parseMove(s.nextLine());
            while (IntStream.of(res).anyMatch(l->l==-1)) {
                System.out.print("INVALID MOVE - try again! Enter your move here: ");
                res = parseMove(s.nextLine());
            }
            board.board[res[0]-1][res[1]-1] = pTurn;
            turn++;
            clearConsole();
        };
        gameOver = true; // set so the tutorial doesn't repeat

        System.out.println("CONGRATS PLAYER " + ( ((turn - 1) % 2) + 1) + " (" + ( ( (turn) % 2 ) == 1 ? 'X' : '0' ) + ") - you won!!! See the final board below.\n");
        board.print();
        System.out.println("\nThe game ended after " + turn + " turns - what a game!\n");

        System.out.print("Press [Enter] to continue...");
        s.nextLine();
        clearConsole();

        board.cleanBoard();
    }

    public final int[] parseMove(String input) {
        if (input.length() != 2) return new int[] {-1,-1};

        char row = input.toCharArray()[0];

        int col;
        try {
            col = Integer.parseInt(input.substring(1, 2));
        } catch (NumberFormatException e) {
            //e.printStackTrace();
            return new int[] {-1,-1};
        }

        int tmp = ( (int) Character.toLowerCase(row) + 1 - 97 );
        if ( tmp > board.NUM_ROWS || tmp < 0) {
            //System.out.println("INVALID MOVE - row out of bounds...");
            tmp = -1;
        }
        if ( col > board.NUM_COL || col <= 0){
            //System.out.println("INVALID MOVE - col out of bounds...");
            col = -1;
        }

        if (tmp != -1 && col != -1) {
            // check if square is avail...
            if (board.board[tmp-1][col-1] != 0) {
                tmp = col = -1;
            }
        }

        return new int[] {tmp, col};
    }

    public void start(String[] args) {
        clearConsole();

        if (gameOver) return;

        System.out.println("-------------------  TIC * TAC * TOE  -------------------\n");

        System.out.print("Would you like to see a tutorial on how to play (yes/no)? ");
        Scanner s = new Scanner(System.in);
        String res = s.nextLine().toLowerCase();
        while (!res.contains("y") && !res.contains("n")) {
            System.out.print("Please enter your choice (y/yes/n/no): ");
            res = s.nextLine().toLowerCase();
        }

        // do the tutorial
        clearConsole();
        if (res.contains("y")) {
            tutorial(args);
        }
    }

    public void reset() {
        board.cleanBoard();
        turn = 0;
    }

    public final static void tutorial(String[] args) {
        System.out.println("Welcome to TicTacToe! Here is your playing field:\n");

        Scanner s = new Scanner(System.in);

        Board tb = new Board();

        if (args.length == 2) {
            tb = new Board(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        } else {
            tb = new Board();
        }

        tb.print();
        System.out.println(WordUtils.wrap("\nTake note of the layout - an example of a valid move in this configuration would be \"A1\" (placing your marker in the top-left corner).\n", WRAP_LEN));

        System.out.print("Press [Enter] to continue...");
        s.nextLine();
        clearConsole();

        tb.board[0][0] = 1;

        System.out.println("(assume you are playing with the marker \"X\")\n");
        tb.print();
        System.out.println("\nIf it was your move, and you selected \"A1\", the board would now look like this.");

        System.out.print("\nPress [Enter] to continue...");
        s.nextLine();
        clearConsole();

        System.out.println("Remember - to win, you must have a \"" + tb.NUM_COL + "-in-a-row\". That would like this:\n");

        for (int i = 1; i < tb.NUM_ROWS; i++) {
            for (int j = 1; j < tb.NUM_COL; j++) {
                if (i == j) tb.board[i][j] = 1;
            }
        }

        tb.print();

        System.out.println(WordUtils.wrap("\nObviously the other player (and yourself) would have markers in different locations, but once you see a line of your markers like this, you've WON! Just make sure your opponent doesn't beat you to the win.", WRAP_LEN));
        System.out.print("\nPress [Enter] to continue...");

        s.nextLine();
        clearConsole();

        System.out.println(WordUtils.wrap("OK - good job! We're done with the tutorial. The game will start soon, and please note that the player to move first will be picked at random (so keep your fingies crossed). Good luck!", WRAP_LEN));
        System.out.print("\nOnce you're ready, press [Enter] to continue...");

        s.nextLine();
        clearConsole();
    }

    //Clears Screen in java
    public final static void clearConsole() {
        try {
            final String os = System.getProperty("os.name");

            if (os.contains("Windows")) {
                Runtime.getRuntime().exec("cls");
            }
            else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        }
        catch (final Exception e) {
            e.printStackTrace();
        }
    }


}
