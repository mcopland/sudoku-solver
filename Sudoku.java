/**
* This program solves sudoku puzzles by testing each required integer,
* 1-9, in cells that do not have set values. It continues to test
* each value until a rule is violated, where it will backtrack and
* try the next increment.
*
* @author   Michael Copland
* @version  1.0
* @since    2020-12-10
*/
public class Sudoku
{
    private static final int SIZE = 9;
    private static final int UNASSIGNED = 0;

    public static void main(String[] args)
    {
        int[][] board =
        {
            {1, 0, 0, 0, 0, 7, 0, 9, 0},
            {0, 3, 0, 0, 2, 0, 0, 0, 8},
            {0, 0, 9, 6, 0, 0, 5, 0, 0},
            {0, 0, 5, 3, 0, 0, 9, 0, 0},
            {0, 1, 0, 0, 8, 0, 0, 0, 2},
            {6, 0, 0, 0, 0, 4, 0, 0, 0},
            {3, 0, 0, 0, 0, 0, 0, 1, 0},
            {0, 4, 0, 0, 0, 0, 0, 0, 7},
            {0, 0, 7, 0, 0, 0, 3, 0, 0}
        };

        // Solve puzzle, print board if valid
        if (solveSudoku(board, 0, 0))
        {
            printSudoku(board);
        }
        else
        {
            System.out.println("Board is invalid.");
        }
    }


    /**
    * Returns true if all cells have been checked and contain valid
    * numbers according to the rules. Returns false if the puzzle
    * is not 9x9 or any set clue value breaks a rule itself.
    *
    * @param    board   unsolved sudoku puzzle
    * @return           true if all cells have been checked
    */
    private static boolean solveSudoku(int[][] board, int row, int col)
    {
        // Begin at top-left cell
        for (int i = row; i < SIZE; i++, col = 0)
        {
            for (int j = col; j < SIZE; j++)
            {
                // Only continue if current cell is not set
                if (board[i][j] != UNASSIGNED) continue;

                // Increment through required values (1-9)
                for (int k = 1; k <= SIZE; k++)
                {
                    // Check k against current row, column, and 3x3 box
                    if (isValid(board, i, j, k))
                    {
                        // Set cell and move on to the next unassigned
                        board[i][j] = k;
                        if (solveSudoku(board, i, j+1)) return true;

                        // Recursion failed, reset cell and try k + 1
                        board[i][j] = UNASSIGNED;
                    }
                }

                return false;
            }
        }

        // Every cell value is valid
        return true;
    }


    /**
    * Returns true if all rules are followed with current number in
    * the selected cell. A number must not be repeated in the current
    * row, column, or 3x3 grid it is in.
    *
    * @param    board   unsolved sudoku puzzle
    * @param    row     current row index
    * @param    col     current column index
    * @param    num     value of current cell
    * @return           true if num does not violate sudoku rules
    */
    private static boolean isValid(int[][] board, int row, int col, int num)
    {
        for (int i = 0; i < SIZE; i++)
        {
            // Check row and column
            if (board[row][i] == num || board[i][col] == num) return false;

            // Check 3x3 box
            int j = 3 * (row / 3) + i / 3;
            int k = 3 * (col / 3) + i % 3;
            if (board[j][k] == num) return false;
        }

        return true;
    }


    /**
    * Prints the completed sudoku puzzle to the terminal.
    *
    * @param    board   solved sudoku puzzle
    */
    private static void printSudoku(int[][] board)
    {
        System.out.println(".-----------------------------.");

        for (int i = 0; i < SIZE; i++)
        {
            if (i % 3 == 0 && i != 0)
            {
                System.out.println("|---------+---------+---------|");
            }

            for (int j = 0; j < SIZE; j++)
            {
                if (j % 3 == 0)
                {
                    System.out.print("|");
                }

                System.out.print(" " + board[i][j] + " ");
            }

            System.out.println('|');
        }

        System.out.println("\'-----------------------------\'");
    }
}
