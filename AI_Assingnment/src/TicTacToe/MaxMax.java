package TicTacToe;

public class MaxMax {
    private int[][] board;
    private int depthLimit;
    
    public MaxMax(int[][] board, int depthLimit) {
        this.board = board;
        this.depthLimit = depthLimit;
    }

    public int maxmax(int depth, int alpha, int beta) {
        if (depth >= depthLimit || isGameOver()) {
            return evaluate();
        }

        int best = Integer.MIN_VALUE;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == 0) {
                    board[i][j] = 1;  // Assume player 1 is maximizing
                    int score = maxmax(depth + 1, alpha, beta);
                    board[i][j] = 0;
                    best = Math.max(best, score);
                    alpha = Math.max(alpha, best);
                    if (beta <= alpha) {
                        break;
                    }

                    board[i][j] = 2;  // Assume player 2 is also maximizing
                    score = maxmax(depth + 1, alpha, beta);
                    board[i][j] = 0;
                    best = Math.max(best, score);
                    alpha = Math.max(alpha, best);
                    if (beta <= alpha) {
                        break;
                    }
                }
            }
        }
        return best;
    }

    private boolean isGameOver() {
        return evaluate() != 0 || !isMovesLeft();
    }

    private boolean isMovesLeft() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    private int evaluate() {
        int score = 0;
        // Implement evaluation logic here, similar to the MiniMax class
        return score;
    }

    public int getBestMove() {
        int bestVal = Integer.MIN_VALUE;
        int bestMove = -1;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == 0) {
                    board[i][j] = 1;  // Simulate move for player 1
                    int moveVal = maxmax(0, Integer.MIN_VALUE, Integer.MAX_VALUE);
                    board[i][j] = 0;
                    if (moveVal > bestVal) {
                        bestMove = i * board[0].length + j;
                        bestVal = moveVal;
                    }

                    board[i][j] = 2;  // Simulate move for player 2
                    moveVal = maxmax(0, Integer.MIN_VALUE, Integer.MAX_VALUE);
                    board[i][j] = 0;
                    if (moveVal > bestVal) {
                        bestMove = i * board[0].length + j;
                        bestVal = moveVal;
                    }
                }
            }
        }
        return bestMove;
    }
}
