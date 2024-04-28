package TicTacToe;

public class MaxMax {
	
	
	    private int[][] board;
	    private int depthLimit;
	    
	    public MaxMax(int[][] board, int depthLimit) {
	        this.board = board;
	        this.depthLimit = depthLimit;
	    }

	    public int maxmax(int depth, boolean isMaximizingPlayer, int alpha, int beta) {
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
	            for (int i = 0; i < board.length; i++) {
	                for (int j = 0; j < board[0].length; j++) {
	                    if (board[i][j] == 0) {
	                        board[i][j] = 1;

	                        // Recursive call with updated alpha
	                        best = Math.max(best, maxmax(depth + 1, !isMaximizingPlayer, alpha, beta));

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
	            for (int i = 0; i < board.length; i++) {
	                for (int j = 0; j < board[0].length; j++) {
	                    if (board[i][j] == 0) {
	                        board[i][j] = 2;

	                        // Recursive call with updated beta
	                        best = Math.min(best, maxmax(depth + 1, !isMaximizingPlayer, alpha, beta));

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
	        for (int i = 0; i < board.length; i++) {
	            for (int j = 0; j < board[0].length; j++) {
	                if (board[i][j] == 0) {
	                    board[i][j] = 2; // Assuming 2 represents the AI's move
	                    // Call maxmax with initial alpha and beta values
	                    int moveVal = maxmax(0, true, -1000, 1000);
	                    board[i][j] = 0; // Undo the move

	                    if (moveVal > bestVal) {
	                        bestMove = i * board[0].length + j;
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
	
	    // change the point system to give more points to seran moves
	    private int evaluate() {
	        // Check rows, columns, and diagonals for a win
	        for (int row = 0; row < board.length; row++) {
	            if (board[row][0] == board[row][1] && board[row][1] == board[row][2]) {
	                if (board[row][0] == 1)
	                    return +10;
	                else if (board[row][0] == 2)
	                    return -10;
	            }
	        }

	        for (int col = 0; col < board[0].length; col++) {
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

	        // Check for potential winning lines and assign scores
	        int score = 0;

	        // Check rows
	        for (int row = 0; row < board.length; row++) {
	            int player1Count = 0;
	            int player2Count = 0;
	            int emptyCount = 0;

	            for (int col = 0; col < board[0].length; col++) {
	                if (board[row][col] == 1)
	                    player1Count++;
	                else if (board[row][col] == 2)
	                    player2Count++;
	                else
	                    emptyCount++;
	            }

	            if (player1Count == 2 && emptyCount == 1)
	                score += 5;
	            else if (player2Count == 2 && emptyCount == 1)
	                score -= 5;
	        }

	        // Check columns
	        for (int col = 0; col < board[0].length; col++) {
	            int player1Count = 0;
	            int player2Count = 0;
	            int emptyCount = 0;

	            for (int row = 0; row < board.length; row++) {
	                if (board[row][col] == 1)
	                    player1Count++;
	                else if (board[row][col] == 2)
	                    player2Count++;
	                else
	                    emptyCount++;
	            }

	            if (player1Count == 2 && emptyCount == 1)
	                score += 5;
	            else if (player2Count == 2 && emptyCount == 1)
	                score -= 5;
	        }

	        // Check diagonals
	        int player1Count = 0;
	        int player2Count = 0;
	        int emptyCount = 0;

	        for (int i = 0; i < board.length; i++) {
	            if (board[i][i] == 1)
	                player1Count++;
	            else if (board[i][i] == 2)
	                player2Count++;
	            else
	                emptyCount++;
	        }

	        if (player1Count == 2 && emptyCount == 1)
	            score += 5;
	        else if (player2Count == 2 && emptyCount == 1)
	            score -= 5;

	        player1Count = 0;
	        player2Count = 0;
	        emptyCount = 0;

	        for (int i = 0; i < board.length; i++) {
	            if (board[i][board.length - 1 - i] == 1)
	                player1Count++;
	            else if (board[i][board.length - 1 - i] == 2)
	                player2Count++;
	            else
	                emptyCount++;
	        }

	        if (player1Count == 2 && emptyCount == 1)
	            score += 5;
	        else if (player2Count == 2 && emptyCount == 1)
	            score -= 5;

	        return score;
	    }

	}

