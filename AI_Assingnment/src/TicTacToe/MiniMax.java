package TicTacToe;

public class MiniMax {
    private int[][] board;
    private int depthLimit;
    
    public MiniMax(int[][] board, int depthLimit) {
        this.board = board;
        this.depthLimit = depthLimit;
    }

    public int minimax(int depth, boolean isMaximizingPlayer, int alpha, int beta) {
        // Base case: if the depth limit is reached or the game is over
        if (depth >= depthLimit) {
            return evaluate();
        }

        // If the game is over, return the score
        int score = evaluate();
        if (score == 10)
            return score;

        if (score == -10)
            return score;

        // If there are no moves left, return 0
        if (isMovesLeft() == false)
            return 0;

        // Maximizing player's turn
        if (isMaximizingPlayer) {
            int best = -1000;

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == 0) {
                        board[i][j] = 1;

                        // Recursive call with updated alpha
                        best = Math.max(best, minimax(depth + 1, !isMaximizingPlayer, alpha, beta));

                        board[i][j] = 0;
                        // Update alpha with the best score found so far
                        alpha = Math.max(alpha, best);
                        // Prune the search tree if beta <= alpha
                        if (beta <= alpha)
                            return best;
                    }
                }
            }
            return best;
        } else {
            // Minimizing player's turn
            int best = 1000;

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == 0) {
                        board[i][j] = 2;

                        // Recursive call with updated beta
                        best = Math.min(best, minimax(depth + 1, !isMaximizingPlayer, alpha, beta));

                        board[i][j] = 0;
                        // Update beta with the best score found so far
                        beta = Math.min(beta, best);
                        // Prune the search tree if beta <= alpha
                        if (beta <= alpha)
                            return best;
                    }
                }
            }
            return best;
        }
    }
    
    public int getBestMove() {
        int bestVal = -1000;
        int bestMove = -1;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == 0) {
                    board[i][j] = 2; // Assuming 2 represents the AI's move
                    // Call minimax with initial alpha and beta values
                    int moveVal = minimax(0, true, -1000, 1000);
                    board[i][j] = 0; // Undo the move

                    if (moveVal > bestVal) {
                        bestMove = i * 3 + j;
                        bestVal = moveVal;
                    }
                }
            }
        }

        return bestMove;
    }

    private boolean isMovesLeft() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (board[i][j] == 0)
                    return true;
        return false;
    }

    private int evaluate() {
        // Check rows, columns, and diagonals for a win
        for (int row = 0; row < 3; row++) {
            if (board[row][0] == board[row][1] && board[row][1] == board[row][2]) {
                if (board[row][0] == 1)
                    return +10;
                else if (board[row][0] == 2)
                    return -10;
            }
        }

        for (int col = 0; col < 3; col++) {
            if (board[0][col] == board[1][col] && board[1][col] == board[2][col]) {
                if (board[0][col] == 1)
                    return +10;
                else if (board[0][col] == 2)
                    return -10;
            }
        }

        if (board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
            if (board[0][0] == 1)
                return +10;
            else if (board[0][0] == 2)
                return -10;
        }

        if (board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
            if (board[0][2] == 1)
                return +10;
            else if (board[0][2] == 2)
                return -10;
        }
        return 0;
    }
}
