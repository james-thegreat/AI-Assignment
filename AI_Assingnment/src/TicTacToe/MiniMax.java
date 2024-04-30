package TicTacToe;

public class MiniMax {
    private int[][] board;
    private int depthLimit;
    private int winLength; // Length needed to win

    public MiniMax(int[][] board, int depthLimit, int winLength) {
        this.board = board;
        this.depthLimit = depthLimit;
        this.winLength = winLength;
    }

    public int minimax(int depth, boolean isMaximizingPlayer, int alpha, int beta) {
        if (depth >= depthLimit || isGameOver()) {
            return evaluate();
        }

        if (isMaximizingPlayer) {
            int best = Integer.MIN_VALUE;
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[0].length; j++) {
                    if (board[i][j] == 0) {
                        board[i][j] = 1;
                        int score = minimax(depth + 1, false, alpha, beta);
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
        } else {
            int best = Integer.MAX_VALUE;
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[0].length; j++) {
                    if (board[i][j] == 0) {
                        board[i][j] = 2;
                        int score = minimax(depth + 1, true, alpha, beta);
                        board[i][j] = 0;
                        best = Math.min(best, score);
                        beta = Math.min(beta, best);
                        if (beta <= alpha) {
                            break;
                        }
                    }
                }
            }
            return best;
        }
    }

    private boolean isMovesLeft() {
        for (int[] row : board) {
            for (int cell : row) {
                if (cell == 0) return true;
            }
        }
        return false;
    }

    private boolean isGameOver() {
        // Check for a win or if all cells are filled
        return evaluate() != 0 || !isMovesLeft();
    }
    
    
    private int evaluate() {
        int score = 0;
        for (int i = 0; i < 3; i++) {
            score += evaluateLine(board[i][0], board[i][1], board[i][2]); // Row
            score += evaluateLine(board[0][i], board[1][i], board[2][i]); // Column
        }
        score += evaluateLine(board[0][0], board[1][1], board[2][2]); // Diagonal
        score += evaluateLine(board[0][2], board[1][1], board[2][0]); // Anti-diagonal
        return score;
    }

    private int evaluateLine(int cell1, int cell2, int cell3) {
        if (cell1 == cell2 && cell2 == cell3) {
            if (cell1 == 1) {
                return 100; // AI wins
            } else if (cell1 == 2) {
                return -100; // Opponent wins
            }
        }
        if (cell1 == cell2 && cell3 == 0 || cell1 == cell3 && cell2 == 0 || cell2 == cell3 && cell1 == 0) {
            if (cell1 == 1 || cell2 == 1 || cell3 == 1) {
                return 10; // AI two in a row
            } else if (cell1 == 2 || cell2 == 2 || cell3 == 2) {
                return -10; // Opponent two in a row
            }
        }
        return 0;
    }

    public int getBestMove() {
        int bestVal = Integer.MIN_VALUE;
        int bestMove = -1;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == 0) {
                    board[i][j] = 1; // Simulate AI move
                    int moveVal = minimax(0, false, Integer.MIN_VALUE, Integer.MAX_VALUE);
                    board[i][j] = 0; // Undo move
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