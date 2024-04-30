package TicTacToe;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import javax.swing.*;

public class TicTacToe implements ActionListener {

    JFrame frame = new JFrame();
    JPanel title_panel = new JPanel();
    JPanel button_panel = new JPanel();
    JLabel textfield = new JLabel();
    JButton[] buttons = new JButton[9];
    boolean player1_turn;

    // Side menu
    JPanel right_panel = new JPanel();
    // Add a variable to store the player's choice
    boolean playerIsX = true; // Default to player being 'X'

    JButton new_Game_Button = new JButton("New Game");
    JButton playAsXButton = new JButton("Play as X");
    JButton playAsOButton = new JButton("Play as O");
    JLabel player_X_Wins = new JLabel("Player X wins: 0");
    JLabel player_O_Wins = new JLabel("Player O wins: 0");
    int player_X_Score = 0;
    int player_O_Score = 0;

    // Minimax button
    JButton playAsMinimaxButton = new JButton("Player vs Minimax");
    boolean isMinimaxMode = false;
    
    // depth buttons ---------------------------------------------
    JButton[] depthButtons = new JButton[8];
    int currentDepth = 1; // Default depth
    
    // change the bord size
    JButton changeTo3x3Button = new JButton("3x3 Board");
    JButton changeTo4x4Button = new JButton("4x4 Board");
    int boardSize = 3; // Default to 3x3
    
    //show the scores
    private boolean showScores = false;
    
    // switch modes
    JButton switchModeButton = new JButton("Switch to Player vs. Computer");
    boolean isPlayerVsComputer = false;

    // MaxMax button
    JButton playAsMaxMaxButton = new JButton("Play vs MaxMax");
    boolean isMaxMaxMode = false;

    
    // MaxMax vs Minimax button
    JButton playMinimaxVsMaxMaxButton = new JButton("Minimax vs MaxMax");
    boolean isMinimaxVsMaxMaxMode = false;

    // instructions to chose game mode
    JLabel pick_player_mode = new JLabel("Pick who you want to play against");
    
    // pick depth label
    JLabel depth_choice = new JLabel("chose how many moves a head the AI cheks ");
    
    // change bord size label
    JLabel bord_size = new JLabel("choose bord size");



    TicTacToe() {

        // Right panel setup

        right_panel.setLayout(new BoxLayout(right_panel, BoxLayout.Y_AXIS));
        right_panel.setBackground(new Color(150, 150, 150));
        right_panel.add(player_O_Wins);
        right_panel.add(player_X_Wins);
        

        // pick player label
        right_panel.add(pick_player_mode);

        
        
        // switch modes
        right_panel.add(switchModeButton);
        switchModeButton.addActionListener(this);

        // MiniMax button setup
        right_panel.add(playAsMinimaxButton);
        playAsMinimaxButton.addActionListener(this);
        
        // MaxMax button
        right_panel.add(playAsMaxMaxButton);
        playAsMaxMaxButton.addActionListener(this);

        // MaxMax vs Minimax button
        right_panel.add(playMinimaxVsMaxMaxButton);
        playMinimaxVsMaxMaxButton.addActionListener(this);
        
        
        

        // New game button
        new_Game_Button.addActionListener(this);
        right_panel.add(new_Game_Button);

        // player select
        right_panel.add(playAsXButton);
        right_panel.add(playAsOButton);
        playAsXButton.addActionListener(this);
        playAsOButton.addActionListener(this);

        // Main window setup
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 800);
        frame.getContentPane().setBackground(new Color(50, 50, 50));
        frame.setLayout(new BorderLayout());
        frame.setVisible(true);

        // Title setup
        textfield.setBackground(new Color(25, 25, 25));
        textfield.setForeground(new Color(25, 255, 0));
        textfield.setFont(new Font("Ink Free", Font.BOLD, 75));
        textfield.setHorizontalAlignment(JLabel.CENTER);
        textfield.setText("Tic-Tac-Toe");
        textfield.setOpaque(true);

        title_panel.setLayout(new BorderLayout());
        title_panel.setBounds(0, 0, 800, 100);

        // Button panel setup
        button_panel.setLayout(new GridLayout(boardSize, boardSize));
        button_panel.setBackground(new Color(150, 150, 150));
        

        //choose bord size label
        right_panel.add(bord_size);
        // change board size 
        right_panel.add(changeTo3x3Button);
        right_panel.add(changeTo4x4Button);
        changeTo3x3Button.addActionListener(this);
        changeTo4x4Button.addActionListener(this);
        
        
        button_panel.setLayout(new GridLayout(boardSize, boardSize));

        
        
        buttons = new JButton[boardSize * boardSize];
        for (int i = 0; i < boardSize * boardSize; i++) {
            buttons[i] = new JButton();
            button_panel.add(buttons[i]);
            buttons[i].setFont(new Font("MV Boli", Font.BOLD, 120));
            buttons[i].setBackground(new Color(220, 220, 220));
            buttons[i].setFocusable(false);
            buttons[i].addActionListener(this);
        }
        
        // chose depth label
        right_panel.add(depth_choice);

        // depth button ------------------------------------------------------
        for (int i = 0; i < 8; i++) {
            depthButtons[i] = new JButton("Depth " + (i + 1));
            depthButtons[i].addActionListener(this);
            right_panel.add(depthButtons[i]);
        }

        // Right panel setup

        right_panel.setLayout(new BoxLayout(right_panel, BoxLayout.Y_AXIS));
        right_panel.setBackground(new Color(150, 150, 150));
        
        
        
        


        // Modify the main frame layout to include the right panel
        frame.add(right_panel, BorderLayout.EAST);

        title_panel.add(textfield);
        frame.add(title_panel, BorderLayout.NORTH);
        frame.add(button_panel);
        
        //show scores
        JButton showScoresButton = new JButton("Show best moves");
        showScoresButton.addActionListener(e -> {
            showScores = !showScores;
            showScoresButton.setText(showScores ? "Hide Scores" : "Show Scores");
            updateBoard();
        });
        right_panel.add(showScoresButton);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // MiniMax button action
        if (e.getSource() == playAsMinimaxButton) {
            isMinimaxMode = true;
            textfield.setText("Minimax turn");
        }

        // Player selection buttons action
        if (e.getSource() == playAsXButton) {
            playerIsX = true;
            player1_turn = true;  // Player starts if playing as X
            textfield.setText("X turn");
            isMinimaxMode = false;  // Ensure AI is turned off when switching sides
        } else if (e.getSource() == playAsOButton) {
            playerIsX = false;
            player1_turn = true;  // Player also starts if playing as O
            textfield.setText("O turn");
            isMinimaxMode = false;  // Ensure AI is turned off when switching sides
        }
        

    

        // Game buttons action
       // Existing player move logic
       for (int i = 0; i < boardSize * boardSize; i++) {
        if (e.getSource() == buttons[i]) {
            if (buttons[i].getText().equals("")) {
                if (player1_turn) {
                    buttons[i].setForeground(new Color(255, 0, 0)); // Set the foreground color to red for X
                    buttons[i].setText("X");
                    player1_turn = false;
                    textfield.setText("O turn");
                } else {
                    buttons[i].setForeground(new Color(0, 0, 255)); // Set the foreground color to blue for O
                    buttons[i].setText("O");
                    player1_turn = true;
                    textfield.setText("X turn");
                }
                check();
            }
        }
    }
    

        // AI move logic

        // Trigger AI move if it's AI's turn and Minimax mode is active
        if (isMinimaxMode && !player1_turn && ((playerIsX && textfield.getText().equals("O turn")) || (!playerIsX && textfield.getText().equals("X turn")))) {
            makeAIMove();
        }


        // if (isMinimaxMode && !player1_turn) {
        //     int[][] boardState = convertToBoardState();
        //     MiniMax minimax = new MiniMax(boardState, currentDepth);
        //     int bestMove = minimax.getBestMove();
        //     if (bestMove != -1) {
        //         int row = bestMove / boardSize;
        //         int col = bestMove % boardSize;
        //         buttons[row * boardSize + col].setText(playerIsX ? "O" : "X");
        //         buttons[row * boardSize + col].setEnabled(false);
        //         player1_turn = true;  // Switch turn
        //         textfield.setText("Player's turn");
        //         check(); // Check for win or draw after AI move
        //     }
        // }

        // switch player buttons player vs plaer or player vs AI
        if (e.getSource() == switchModeButton) {
            isPlayerVsComputer = !isPlayerVsComputer;
            if (isPlayerVsComputer) {
                switchModeButton.setText("Switch to Player vs. Player");
                isMinimaxMode = true;

                
            } else {
                switchModeButton.setText("Switch to Player vs. Computer");
                isMinimaxMode = false;
            }
            new_Game();
        }


        // New game button action
        if (e.getSource() == new_Game_Button) {
            new_Game(); 
        }
        // depth ------------------------------------------------
        for (int i = 0; i < 8; i++) {
        	if (e.getSource() == depthButtons[i]) {
        		currentDepth = i + 1;
        		textfield.setText("Depth set to " + currentDepth);
        	}
        }
        
        // Minimax AI's turn
        // Inside your actionPerformed method, after a player makes a move
     // Minimax AI's turn
        if (isPlayerVsComputer && player1_turn != playerIsX) {
            // Check if the game has ended
            boolean isGameEnded = true;
            for (JButton button : buttons) {
                if (button.isEnabled()) {
                    isGameEnded = false;
                    break;
                }
            }
            
            // MaxMax AI's turn
            if (isMaxMaxMode && player1_turn != playerIsX) {
                // Check if the game has ended
                isGameEnded = true;
                for (JButton button : buttons) {
                    if (button.isEnabled()) {
                        isGameEnded = false;
                        break;
                    }
                }

                // Proceed with the AI's move only if the game has not ended
                if (!isGameEnded) {
                    // Update the board state in the MaxMax class
                    int[][] boardState = convertToBoardState();
                    MaxMax maxmax = new MaxMax(boardState, currentDepth);
                    int bestMove = maxmax.getBestMove();
                    // Ensure the best move is valid
                    if (bestMove != -1) {
                        // Make the AI's move
                        int row = bestMove / boardSize;
                        int col = bestMove % boardSize;
                        buttons[row * boardSize + col].setForeground(new Color(0, 0, 255));
                        buttons[row * boardSize + col].setText(playerIsX ? "O" : "X");
                        // Update the board state to reflect the AI's move
                        boardState[row][col] = playerIsX ? 2 : 1; // Assuming 2 is the AI's symbol
                        // Switch back to player 1's turn
                        player1_turn = !player1_turn;
                        textfield.setText(playerIsX ? "X turn" : "O turn");
                        // Check for a win or draw
                        check();
                    }
                }
            }
            
            
            // Proceed with the AI's move only if the game has not ended
            if (!isGameEnded) {
                // Update the board state in the MiniMax class
                int[][] boardState = convertToBoardState();
                MiniMax minimax = new MiniMax(boardState, currentDepth);
                int bestMove = minimax.getBestMove();
                // Ensure the best move is valid
                if (bestMove != -1) {
                    // Make the AI's move
                	int row = bestMove / boardSize;
                    int col = bestMove % boardSize;
                    buttons[row * boardSize + col].setForeground(new Color(0, 0, 255));
                    buttons[row * boardSize + col].setText(playerIsX ? "O" : "X");
                    // Update the board state to reflect the AI's move
                    boardState[row][col] = playerIsX ? 2 : 1; // Assuming 2 is the AI's symbol
                    // Switch back to player 1's turn
                    player1_turn = !player1_turn;
                    textfield.setText(playerIsX ? "X turn" : "O turn");
                    // Check for a win or draw
                    check();
                }
            }
          }

        

        // change board size
        if (e.getSource() == changeTo3x3Button) {
            boardSize = 3;
            new_Game(); // Reset the game with the new board size
        } else if (e.getSource() == changeTo4x4Button) {
            boardSize = 4;
            new_Game(); // Reset the game with the new board size
        }
        

        // MaxMax button
        if (e.getSource() == playAsMaxMaxButton) {
            isMaxMaxMode = true;
            textfield.setText("MaxMax turn");
        }
        
        // MaxMax vs Minimax button
        // MaxMax vs Minimax button
        if (e.getSource() == playMinimaxVsMaxMaxButton) {
            isMinimaxVsMaxMaxMode = true;
            isPlayerVsComputer = true;
            new_Game();
        }
        
     // Minimax vs MaxMax gameplay
        if (isMinimaxVsMaxMaxMode) {
            // Check if the game has ended
            boolean isGameEnded = true;
            for (JButton button : buttons) {
                if (button.isEnabled()) {
                    isGameEnded = false;
                    break;
                }
            }

            // Proceed with the AI's move only if the game has not ended
            if (!isGameEnded) {
                // Update the board state
                int[][] boardState = convertToBoardState();

                // Minimax's turn
                if (player1_turn) {
                    MiniMax minimax = new MiniMax(boardState, currentDepth);
                    int bestMove = minimax.getBestMove();
                    // Make Minimax's move
                    int row = bestMove / boardSize;
                    int col = bestMove % boardSize;
                    buttons[row * boardSize + col].setForeground(new Color(255, 0, 0));
                    buttons[row * boardSize + col].setText("X");
                    boardState[row][col] = 1;
                    player1_turn = false;
                    textfield.setText("MaxMax turn");
                }
                // MaxMax's turn
                else {
                    MaxMax maxmax = new MaxMax(boardState, currentDepth);
                    int bestMove = maxmax.getBestMove();
                    // Make MaxMax's move
                    int row = bestMove / boardSize;
                    int col = bestMove % boardSize;
                    buttons[row * boardSize + col].setForeground(new Color(0, 0, 255));
                    buttons[row * boardSize + col].setText("O");
                    boardState[row][col] = 2;
                    player1_turn = true;
                    textfield.setText("Minimax turn");
                }
                // Check for a win or draw
                check();
            }
        }

        
        	updateBoard();
        
        }
    
    
        private void makeAIMove() {
            int[][] boardState = convertToBoardState();
            MiniMax minimax = new MiniMax(boardState, currentDepth);
            int bestMove = minimax.getBestMove();
            if (bestMove != -1) {
                int row = bestMove / boardSize;
                int col = bestMove % boardSize;
                buttons[row * boardSize + col].setText(playerIsX ? "O" : "X");
                buttons[row * boardSize + col].setForeground(playerIsX ? new Color(0, 0, 255) : new Color(255, 0, 0));
                player1_turn = true;  // Switch turn back to the player
                textfield.setText(playerIsX ? "X turn" : "O turn");
                check(); // Check for win or draw after AI move
            }
        }
        
        

    // this gives the score
    private void updateBoard() {
        if (showScores) {
            int[][] boardState = convertToBoardState();
            MiniMax minimax = new MiniMax(boardState, currentDepth);

            for (int i = 0; i < boardSize * boardSize; i++) {
                if (buttons[i].isEnabled()) {
                    int row = i / boardSize;
                    int col = i % boardSize;
                    boardState[row][col] = playerIsX ? 1 : 2;
                    int score = minimax.minimax(0, !playerIsX, -1000, 1000);
                    boardState[row][col] = 0;
                    
                    String buttonText = buttons[i].getText();
                    if (!buttonText.isEmpty()) {
                        buttons[i].setText("<html><center>" + buttonText + "<br><small>" + score + "</small></center></html>");
                    } else {
                        buttons[i].setText("<html><center><small>" + score + "</small></center></html>");
                    }
                }
            }
        } else {
            for (JButton button : buttons) {
                String buttonText = button.getText();
                if (buttonText.contains("<html>")) {
                    buttonText = buttonText.replaceAll("<[^>]*>", "");
                    button.setText(buttonText.equals("X") || buttonText.equals("O") ? buttonText : "");
                }
            }
        }
        
        // Make the buttons clickable again
        for (JButton button : buttons) {
            button.setEnabled(button.getText().isEmpty());
        }
    }




    private String getScoreText(int score) {
        if (score == 10) {
            return "+10";
        } else if (score == -10) {
            return "-10";
        } else {
            return "0";
        }
    }





    // Reset game
    public void new_Game() {
        for (JButton button : buttons) {
            button.setText("");
            button.setEnabled(true);
            button.setBackground(new Color(220, 220, 220));
        }
        player1_turn = true;  // Always let the player start the new game
        textfield.setText(playerIsX ? "X turn" : "O turn");
    }
    


    public void check() {
        // Define the winning conditions for rows, columns, and diagonals
        int[][] winningConditions = getWinningConditions();
    
        // Check for X wins
        for (int[] condition : winningConditions) {
            if (checkWinCondition(condition, "X")) {
                xWins(condition);
                return; // Exit if a win is found
            }
        }
    
        // Check for O wins
        for (int[] condition : winningConditions) {
            if (checkWinCondition(condition, "O")) {
                oWins(condition);
                return; // Exit if a win is found
            }
        }
    
        // Check for a draw
        if (isDraw()) {
            for (JButton button : buttons) {
                button.setEnabled(false); // Disable all buttons if it's a draw
            }
            textfield.setText("It's a draw!");
        }
    }
    
    private boolean checkWinCondition(int[] condition, String player) {
        for (int index : condition) {
            if (!buttons[index].getText().equals(player)) {
                return false; // If any button does not match the player, return false
            }
        }
        return true; // All buttons in the condition match the player
    }
    
    private boolean isDraw() {
        for (JButton button : buttons) {
            if (button.getText().equals("")) {
                return false; // If any button is still empty, it's not a draw
            }
        }
        return true; // No empty buttons, and no winner found
    }
    
    public void resetGame() {
        for (JButton button : buttons) {
            button.setText("");
            button.setEnabled(true);
        }
        player1_turn = true;  // Reset to player 1's turn
        textfield.setText("Start Game");
    }

    private int[][] getWinningConditions() {
        if (boardSize == 3) {
            return new int[][] {
                {0, 1, 2}, {3, 4, 5}, {6, 7, 8}, // Rows
                {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, // Columns
                {0, 4, 8}, {2, 4, 6} // Diagonals
            };
        } else if (boardSize == 4) {
            return new int[][] {
                {0, 1, 2, 3}, {4, 5, 6, 7}, {8, 9, 10, 11}, {12, 13, 14, 15}, // Rows
                {0, 4, 8, 12}, {1, 5, 9, 13}, {2, 6, 10, 14}, {3, 7, 11, 15}, // Columns
                {0, 5, 10, 15}, {3, 6, 9, 12} // Diagonals
            };
        }
        return new int[0][0];
    }
    
    public void xWins(int[] condition) {
        for (int index : condition) {
            buttons[index].setBackground(Color.GREEN);
            buttons[index].setForeground(new Color(255, 0, 0)); // Set the foreground color to red for X
        }

        for (int i = 0; i < boardSize * boardSize; i++) {
            buttons[i].setEnabled(false);
        }
        textfield.setText("X wins");
        player_X_Score++;
        player_X_Wins.setText("Player X Wins: " + player_X_Score);
    }

    

    public void oWins(int[] condition) {
        for (int index : condition) {
            buttons[index].setBackground(Color.GREEN);
            buttons[index].setForeground(new Color(0, 0, 255)); // Set the foreground color to blue for O
        }

        for (int i = 0; i < boardSize * boardSize; i++) {
            buttons[i].setEnabled(false);
        }
        textfield.setText("O wins");
        player_O_Score++;
        player_O_Wins.setText("Player O Wins: " + player_O_Score);
    }


    // Convert the game state to a format the Minimax algorithm can use
    private int[][] convertToBoardState() {
    int[][] boardState = new int[boardSize][boardSize];
    for (int i = 0; i < boardSize; i++) {
        for (int j = 0; j < boardSize; j++) {
            JButton btn = buttons[i * boardSize + j];
            if (btn.getText().equals("X")) {
                boardState[i][j] = 1;  // Assuming '1' is the representation for 'X'
            } else if (btn.getText().equals("O")) {
                boardState[i][j] = 2;  // Assuming '2' is the representation for 'O'
            } else {
                boardState[i][j] = 0;  // Empty cell
            }
        }
    }
    return boardState;
}



    // Make the best move
    private void makeMove(int bestMove) {
        buttons[bestMove].setForeground(new Color(0, 0, 255));
        buttons[bestMove].setText("O");
        player1_turn = true;
        textfield.setText("X turn");
        check();
    }

    public static void main(String[] args) {
        new TicTacToe();
    }
}
