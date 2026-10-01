package gamehub;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TicTacToe extends JFrame implements ActionListener {
    private static JFrame gameSelectionFrame;
    private static final int ROWS = 3;
    private static final int COLS = 3;
    private static final int SIZE = 100;
    private static final int GAP = 10;
    private JButton[][] buttons;
    private int[][] values;
    private int currentPlayer;

    public TicTacToe(JFrame gameSelectionFrame) {
        buttons = new JButton[ROWS][COLS];
        values = new int[ROWS][COLS];
        currentPlayer = 1;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setLocationRelativeTo(null);

        JLabel rulesLabel = new JLabel("Rules");
        rulesLabel.setFont(new Font("Serif", Font.PLAIN, 14));

        JPanel rulesPanel = new JPanel(new BorderLayout());
        rulesPanel.add(rulesLabel, BorderLayout.CENTER);
        rulesLabel.setForeground(Color.BLACK);

        JOptionPane.showMessageDialog(
            rulesPanel,
            "<html>Grab a friend and PLAY!<br><br>X: Player 1 O: Player 2</html>",
            getTitle(),
            JOptionPane.PLAIN_MESSAGE,
            new ImageIcon("resources/images/Tic-tac-toe.png")
        );

        BackgroundPanel backgroundPanel = new BackgroundPanel("resources/images/background_tictactoe.jpg");
        backgroundPanel.setLayout(new GridLayout(ROWS, COLS, GAP, GAP));
        setContentPane(backgroundPanel);

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].setPreferredSize(new Dimension(SIZE, SIZE));
                buttons[i][j].setFont(new Font("Arial", Font.BOLD, 30));
                buttons[i][j].addActionListener(this);
                backgroundPanel.add(buttons[i][j]);
            }
        }
        TicTacToe.gameSelectionFrame = gameSelectionFrame;
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        int row = getRow(button);
        int col = getCol(button);

        if (values[row][col] == 0) {
            values[row][col] = currentPlayer;
            button.setText(currentPlayer == 1 ? "X" : "O");

            if (checkWin(values, currentPlayer)) {
                JOptionPane.showMessageDialog(this, "Player " + currentPlayer + " wins!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
                this.dispose();
                gameSelectionFrame.setVisible(true);
            } else if (isFull(values)) {
                JOptionPane.showMessageDialog(this, "It's a draw!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
                this.dispose();
                gameSelectionFrame.setVisible(true);
            } else {
                currentPlayer = currentPlayer == 1 ? 2 : 1;
            }
        }
    }

    private int getRow(JButton button) {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (buttons[i][j] == button) {
                    return i;
                }
            }
        }
        return -1;
    }

    private int getCol(JButton button) {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (buttons[i][j] == button) {
                    return j;
                }
            }
        }
        return -1;
    }

    private boolean checkWin(int[][] values, int player) {
        for (int i = 0; i < ROWS; i++) {
            if (values[i][0] == player && values[i][1] == player && values[i][2] == player) {
                return true;
            }
        }
        for (int j = 0; j < COLS; j++) {
            if (values[0][j] == player && values[1][j] == player && values[2][j] == player) {
                return true;
            }
        }
        if (values[0][0] == player && values[1][1] == player && values[2][2] == player) {
            return true;
        }
        if (values[0][2] == player && values[1][1] == player && values[2][0] == player) {
            return true;
        }
        return false;
    }

    private boolean isFull(int[][] values) {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (values[i][j] == 0) {
                    return false;
                }
            }
        }
        return true;
    }
}