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

    // Side menu -------------------------------------------------------------
    JPanel right_panel = new JPanel(); // New panel for buttons and reset
    JButton[] right_buttons = new JButton[5]; // Right panel buttons
    JButton new_Game_Button = new JButton("New Game");
    JButton playAsXButton = new JButton("Play as X");
    JButton playAsOButton = new JButton("Play as O");
    JLabel player_X_Wins = new JLabel("Player X wins: 0"); // Tally for player 1
    JLabel player_O_Wins = new JLabel("Player O wins: 0"); // Tally for player 2
    int player_X_Score = 0;
    int player_O_Score = 0;

    TicTacToe() {
        // The main window --------------------------------------------
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 800);
        frame.getContentPane().setBackground(new Color(50, 50, 50));
        frame.setLayout(new BorderLayout());
        frame.setVisible(true);

        // Title ------------------------------------------------------
        textfield.setBackground(new Color(25, 25, 25));
        textfield.setForeground(new Color(25, 255, 0));
        textfield.setFont(new Font("Ink Free", Font.BOLD, 75));
        textfield.setHorizontalAlignment(JLabel.CENTER);
        textfield.setText("Tic-Tac-Toe");
        textfield.setOpaque(true);

        title_panel.setLayout(new BorderLayout());
        title_panel.setBounds(0, 0, 800, 100);

        // Makes the buttons for the Tic-Tac-Toe game ----------------
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

        // Side menu ------------------------------------------------
        // Setup for the right panel
        right_panel.setLayout(new BoxLayout(right_panel, BoxLayout.Y_AXIS));
        right_panel.setBackground(new Color(150, 150, 150));
        right_panel.add(player_X_Wins);
        right_panel.add(player_O_Wins);
        right_panel.add(playAsXButton);
        right_panel.add(playAsOButton);
        playAsXButton.addActionListener(this);
        playAsOButton.addActionListener(this);
        for (int i = 0; i < 5; i++) {
            right_buttons[i] = new JButton("Button " + (i + 1));
            right_buttons[i].addActionListener(this);
            right_panel.add(right_buttons[i]);
        }
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
        // Handle clicks on the new buttons
        if (e.getSource() == playAsXButton) {
            player1_turn = true; // Player 1 (X) goes first
            textfield.setText("X turn");
        } else if (e.getSource() == playAsOButton) {
            player1_turn = false; // Player 2 (O) goes first
            textfield.setText("O turn");
        }

        for (int i = 0; i < 9; i++) {
            if (e.getSource() == buttons[i]) {
                if (player1_turn) {
                    if (buttons[i].getText().equals("")) {
                        buttons[i].setForeground(new Color(255, 0, 0));
                        buttons[i].setText("X");
                        player1_turn = false;
                        textfield.setText("O turn");
                        check();
                    }
                } else {
                    if (buttons[i].getText().equals("")) {
                        buttons[i].setForeground(new Color(0, 0, 255));
                        buttons[i].setText("O");
                        player1_turn = true;
                        textfield.setText("X turn");
                        check();
                    }
                }
            }
        }
        // Add code to handle clicks on the right panel buttons and reset button
        if (e.getSource() == new_Game_Button) {
            new_Game();
        }
    }

    // Reset game
    public void new_Game() {
        // Reset the game state
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

    public static void main(String[] args) {
        new TicTacToe();
    }
}
