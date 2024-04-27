package TicTacToe;

import java.awt.*;
import java.awt.event.*;
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
   
    JButton new_Game_Button = new JButton("New Game");
    JButton playAsXButton = new JButton("Play as X");
    JButton playAsOButton = new JButton("Play as O");
    JLabel player_X_Wins = new JLabel("Player X wins: 0");
    JLabel player_O_Wins = new JLabel("Player O wins: 0");
    int player_X_Score = 0;
    int player_O_Score = 0;

    // Minimax button
    JButton playAsMinimaxButton = new JButton("Play as Minimax");
    boolean isMinimaxMode = false;
    
    // depth buttons ---------------------------------------------
    JButton[] depthButtons = new JButton[8];
    int currentDepth = 1; // Default depth

    TicTacToe() {
        // MiniMax button setup
        right_panel.add(playAsMinimaxButton);
        playAsMinimaxButton.addActionListener(this);

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
        button_panel.setLayout(new GridLayout(3, 3));
        button_panel.setBackground(new Color(150, 150, 150));

        for (int i = 0; i < 9; i++) {
            buttons[i] = new JButton();
            button_panel.add(buttons[i]);
            buttons[i].setFont(new Font("MV Boli", Font.BOLD, 120));
            buttons[i].setBackground(new Color(220, 220, 220));
            buttons[i].setFocusable(false);
            buttons[i].addActionListener(this);
        }
        
        // depth button ------------------------------------------------------
        for (int i = 0; i < 8; i++) {
            depthButtons[i] = new JButton("Depth " + (i + 1));
            depthButtons[i].addActionListener(this);
            right_panel.add(depthButtons[i]);
        }

        // Right panel setup
        right_panel.setLayout(new BoxLayout(right_panel, BoxLayout.Y_AXIS));
        right_panel.setBackground(new Color(150, 150, 150));
        right_panel.add(player_X_Wins);
        right_panel.add(player_O_Wins);
        right_panel.add(playAsXButton);
        right_panel.add(playAsOButton);
        playAsXButton.addActionListener(this);
        playAsOButton.addActionListener(this);
       
        new_Game_Button.addActionListener(this);
        right_panel.add(new_Game_Button);

        // Modify the main frame layout to include the right panel
        frame.add(right_panel, BorderLayout.EAST);

        title_panel.add(textfield);
        frame.add(title_panel, BorderLayout.NORTH);
        frame.add(button_panel);
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
            player1_turn = true;
            textfield.setText("X turn");
        } else if (e.getSource() == playAsOButton) {
            player1_turn = false;
            textfield.setText("O turn");
        }

        // Game buttons action
        for (int i = 0; i < 9; i++) {
            if (e.getSource() == buttons[i]) {
                if (buttons[i].getText().equals("")) {
                    if (player1_turn) {
                        buttons[i].setForeground(new Color(255, 0, 0));
                        buttons[i].setText("X");
                        player1_turn = false;
                        textfield.setText("O turn");
                    } else {
                        buttons[i].setForeground(new Color(0, 0, 255));
                        buttons[i].setText("O");
                        player1_turn = true;
                        textfield.setText("X turn");
                    }
                    check();
                }
            }
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
        if (isMinimaxMode && !player1_turn) {
            // Update the board state in the MiniMax class
            int[][] boardState = convertToBoardState();
            MiniMax minimax = new MiniMax(boardState, currentDepth);
            int bestMove = minimax.getBestMove();
            // Ensure the best move is valid
            if (bestMove != -1) {
                // Make the AI's move
                int row = bestMove / 3;
                int col = bestMove % 3;
                buttons[row * 3 + col].setForeground(new Color(0, 0, 255));
                buttons[row * 3 + col].setText("O");
                // Update the board state to reflect the AI's move
                boardState[row][col] = 2; // Assuming 2 is the AI's symbol
                // Switch back to player 1's turn
                player1_turn = true;
                textfield.setText("X turn");
                // Check for a win or draw
                check();
            }
        }

 
        
    }

    // Reset game
    public void new_Game() {
        for (int i = 0; i < 9; i++) {
            buttons[i].setText("");
            buttons[i].setEnabled(true);
            buttons[i].setBackground(new Color(220, 220, 220));
        }
        player1_turn = true;
        textfield.setText("X turn");
    }

    public void check() {
        // Define the winning conditions for rows, columns, and diagonals
        int[][] winningConditions = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8}, // Rows
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, // Columns
            {0, 4, 8}, {2, 4, 6} // Diagonals
        };

        // Check for X wins
        for (int[] condition : winningConditions) {
            if (buttons[condition[0]].getText().equals("X") &&
                buttons[condition[1]].getText().equals("X") &&
                buttons[condition[2]].getText().equals("X")) {
                xWins(condition[0], condition[1], condition[2]);
                return; // Exit if a win is found
            }
        }

        // Check for O wins
        for (int[] condition : winningConditions) {
            if (buttons[condition[0]].getText().equals("O") &&
                buttons[condition[1]].getText().equals("O") &&
                buttons[condition[2]].getText().equals("O")) {
                oWins(condition[0], condition[1], condition[2]);
                return; // Exit if a win is found
            }
        }
    }

    public void xWins(int a, int b, int c) {
        buttons[a].setBackground(Color.GREEN);
        buttons[b].setBackground(Color.GREEN);
        buttons[c].setBackground(Color.GREEN);

        for (int i = 0; i < 9; i++) {
            buttons[i].setEnabled(false);
        }
        textfield.setText("X wins");
        player_X_Score++;
        player_X_Wins.setText("Player X Wins: " + player_X_Score);
    }

    public void oWins(int a, int b, int c) {
        buttons[a].setBackground(Color.GREEN);
        buttons[b].setBackground(Color.GREEN);
        buttons[c].setBackground(Color.GREEN);

        for (int i = 0; i < 9; i++) {
            buttons[i].setEnabled(false);
        }
        textfield.setText("O wins");
        player_O_Score++;
        player_O_Wins.setText("Player O Wins: " + player_O_Score);
    }

    // Convert the game state to a format the Minimax algorithm can use
    private int[][] convertToBoardState() {
        int[][] boardState = new int[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (buttons[i * 3 + j].getText().equals("X")) {
                    boardState[i][j] = 1;
                } else if (buttons[i * 3 + j].getText().equals("O")) {
                    boardState[i][j] = 2;
                } else {
                    boardState[i][j] = 0;
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
