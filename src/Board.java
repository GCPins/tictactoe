import java.util.*;

public class Board {
    int NUM_ROWS = 3;
    int NUM_COL = 3;

    int[][] board;

    public Board(int numRows, int numCol) {
        this.NUM_ROWS = numRows;
        this.NUM_COL = numCol;
        this.board = new int[NUM_ROWS][NUM_COL];
    }

    public Board() {
        this.board = new int[NUM_ROWS][NUM_COL];
    }

    public void cleanBoard() {
        for (int[] row: this.board) {
            Arrays.fill(row, 0);
        }
    }

    public boolean placeMarker(int r, int c, int player) {
        if (board[r][c] != 0) return false;
        board[r][c] = player;
        return true;
    }

    public void print() {
        //System.out.println("   1   2   3");
        String s = "   ";
        for (int t = 0; t < NUM_COL; t++) s += (t + 1) + "   ";
        System.out.println(s);
        for (int i = 0; i < NUM_ROWS; i++) {
            System.out.print((char) (65 + i) + " ");
            for (int j = 0; j < NUM_COL; j++) {
                System.out.print("[" + (board[i][j] == 0 ? " " : board[i][j] == 1 ? "X" : "0") + "] ");
            }
            System.out.println();
        }
    }

    public int checkWin() {

        boolean check = true;

        // check rows
        for (int i = 0; i < NUM_ROWS; i++) {
            int temp = board[i][0]; // get top left corner
            check = true;
            for (int j = 1; j < NUM_COL; j++) {
                if (board[i][j] != temp || board[i][j] == 0) {
                    check = false;
                }
            }
            if (check) {
                //System.out.println("check is true @ row check");
                return temp;
            }
        }

        // check columns
        for (int j = 0; j < NUM_COL; j++) {
            int temp = board[0][j];
            check = true;
            for (int i = 1; i < NUM_ROWS; i++) {
                if (board[i][j] != temp || board[i][j] == 0) {
                    check = false;
                }
            }
            if (check) {
                //System.out.println("Check is true @ column check");
                return temp;
            }
        }

        // check diagonals
        check = true;
        int temp = board[0][0];
        for (int i = 0; i < NUM_COL; i++) {
            if (board[i][i] != temp || board[i][i] == 0) check = false;
        }
        if (check) {
            //System.out.println("chck is true @ diagtopleft for #" + temp);
            return temp;
        }

        check = true;
        temp = board[NUM_ROWS-1][0];
        for (int i = NUM_ROWS - 1; i >= 0; i--) {
            if (board[i][NUM_COL-i-1] != temp || board[i][NUM_COL-i-1] == 0) {
                //System.out.println("FAILED DIAG check at i = " + i);
                check = false;
            }
        }

        if (check) {
            //System.out.println("check is true @ diag topright check for #" + temp);
            return temp;
        }

        int numFilled = 0;
        for (int i = 0; i < NUM_ROWS; i++) {
            for (int j = 0; j < NUM_COL; j++) {
                if (board[i][j] != 0) ++numFilled;
            }
        }

        if (numFilled >= NUM_ROWS * NUM_COL) {
            return -2; // return code for cat's game
        }

        return 0;
    }
}
