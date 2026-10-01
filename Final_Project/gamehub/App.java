package gamehub;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.Timer;

public class App {
    static JFrame gameSelectionFrame;
    private static JFrame gameWindowFrame;
    private static int playerScore = 0;
    private static int computerScore = 0;
    private static String playerChoice;
    private static String computerChoice;
    private static String currentGame;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(App::createAndShowGameSelectionGUI);
    }

    private static void createAndShowGameSelectionGUI() {
        gameSelectionFrame = new JFrame("Game Selection");
        gameSelectionFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameSelectionFrame.setSize(700, 600);

        BackgroundPanel backgroundPanel = new BackgroundPanel("resources/images/mainbackground.jpg");
        backgroundPanel.setLayout(new GridBagLayout());
        gameSelectionFrame.setContentPane(backgroundPanel);

        JLabel titleLabel = new JLabel("What games will we play today?", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.ITALIC, 34));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(20, 0, 30, 0);
        gbc.anchor = GridBagConstraints.NORTH;
        backgroundPanel.add(titleLabel, gbc);

        JPanel gameButtonsPanel = new JPanel(new GridLayout(5, 1, 0, 20));
        gameButtonsPanel.setOpaque(false);
        gameButtonsPanel.add(createGameButton("Rock Paper Scissors", "resources/images/rock_paper_scissors_icon.png"));
        gameButtonsPanel.add(createGameButton("Who wants to be a millionaire!", "resources/images/millionaire_icon.png"));
        gameButtonsPanel.add(createGameButton("Don't copy me!", "resources/images/dont_copy_me_icon.png"));
        gameButtonsPanel.add(createGameButton("Tic-Tac-Toe", "resources/images/Tic-tac-toe.png"));
        gameButtonsPanel.add(createGameButton("1A2B", "resources/images/1a2b_icon.png"));
        gameButtonsPanel.add(createGameButton("Sudoku 9x9", "resources/images/sudoku_icon.png"));
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        backgroundPanel.add(gameButtonsPanel, gbc);

        gameSelectionFrame.setVisible(true);
        gameSelectionFrame.setLocationRelativeTo(null);
    }

    private static JButton createGameButton(String gameTitle, String iconFileName) {
        ImageIcon icon = new ImageIcon(iconFileName);
        Image img = icon.getImage();
        Image scaledImg = img.getScaledInstance(60, 60, Image.SCALE_SMOOTH);
        icon = new ImageIcon(scaledImg);
        JButton gameButton = new JButton(gameTitle, icon);
        gameButton.setHorizontalTextPosition(SwingConstants.RIGHT);
        gameButton.setVerticalTextPosition(SwingConstants.CENTER);
        gameButton.setFont(new Font("Serif", Font.PLAIN, 16));
        gameButton.addActionListener(e -> {
            createAndShowGameWindow(gameTitle);
            gameSelectionFrame.setVisible(false);
        });
        return gameButton;
    }

    private static void createAndShowGameWindow(String title) {
        currentGame = title;
        gameWindowFrame = new JFrame(title);
        gameWindowFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameWindowFrame.setSize(600, 400);

        String backgroundImageFile = getBackgroundImageForGame(title);
        BackgroundPanel backgroundPanel = new BackgroundPanel(backgroundImageFile);
        backgroundPanel.setLayout(new BorderLayout());
        gameWindowFrame.setContentPane(backgroundPanel);

        JPanel titlePanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(255, 255, 255, 150));
                g.fillRect(0, 70, getWidth(), getHeight());
            }
        };
        titlePanel.setOpaque(false);

        JLabel newTitleLabel = new JLabel("Game: " + title, SwingConstants.CENTER);
        newTitleLabel.setFont(new Font("Serif", Font.BOLD, 24));
        titlePanel.add(newTitleLabel, BorderLayout.CENTER);
        backgroundPanel.add(titlePanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setOpaque(false);
        JButton backButton = new JButton("Back");
        buttonPanel.add(backButton);
        JButton startButton = new JButton("Start");
        buttonPanel.add(startButton);
        backgroundPanel.add(buttonPanel, BorderLayout.SOUTH);

        gameWindowFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                gameSelectionFrame.setVisible(true);
            }
        });

        backButton.addActionListener(e -> {
            gameWindowFrame.dispose();
            gameSelectionFrame.setVisible(true);
        });

        startButton.addActionListener(e -> {
            gameWindowFrame.dispose();
            switch (currentGame) {
                case "Rock Paper Scissors":
                    createAndShowRockPaperScissorsGame();
                    break;
                case "Who wants to be a millionaire!":
                    createAndShowWhoWantsToBeAMillionaire();
                    break;
                case "Tic-Tac-Toe":
                    createAndShowTicTacToe();
                    break;
                case "1A2B":
                    createAndShowBullsAndCowsGame();
                    break;
                case "Sudoku 9x9":
                    createAndShowSudokuGame();
                    break;
                default:
                    createAndShowGuessingGame();
            }
        });

        gameWindowFrame.setLocationRelativeTo(null);
        gameWindowFrame.setVisible(true);
        gameSelectionFrame.setVisible(false);
    }

    private static String getBackgroundImageForGame(String gameTitle) {
        switch (gameTitle) {
            case "Rock Paper Scissors":
                return "resources/images/rps_background.jpg";
            case "Who wants to be a millionaire!":
                return "resources/images/capsule_616x353.jpg";
            case "Don't copy me!":
                return "resources/images/guessgame2.jpg";
            case "Tic-Tac-Toe":
                return "resources/images/tic.jpg";
            default:
                return "resources/images/default_background.jpg";
        }
    }

    private static void createAndShowRockPaperScissorsGame() {
        gameWindowFrame = new JFrame("Rock Paper Scissors");
        gameWindowFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameWindowFrame.setSize(600, 400);

        BackgroundPanel backgroundPanel = new BackgroundPanel("resources/images/rock_paper_scissors_background.jpg");
        backgroundPanel.setLayout(new BorderLayout());
        gameWindowFrame.setContentPane(backgroundPanel);

        JLabel rulesLabel = new JLabel("Win against a computer 3 times in a row. Good luck!");
        rulesLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        JPanel rulesPanel = new JPanel(new BorderLayout());
        rulesPanel.add(rulesLabel, BorderLayout.CENTER);
        rulesLabel.setForeground(Color.BLACK);

        JOptionPane.showMessageDialog(rulesPanel, "Win against a computer 3 times in a row. Good luck!", computerChoice, JOptionPane.PLAIN_MESSAGE, new ImageIcon("resources/images/rock_paper_scissors_icon.png"));

        JLabel titleLabel = new JLabel("Choose an option", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24));
        backgroundPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setOpaque(false);
        JButton rockButton = new JButton("Rock");
        buttonPanel.add(rockButton);
        JButton paperButton = new JButton("Paper");
        buttonPanel.add(paperButton);
        JButton scissorsButton = new JButton("Scissors");
        buttonPanel.add(scissorsButton);
        backgroundPanel.add(buttonPanel, BorderLayout.CENTER);

        rockButton.addActionListener(e -> {
            playerChoice = "Rock";
            playGameRPC();
        });
        paperButton.addActionListener(e -> {
            playerChoice = "Paper";
            playGameRPC();
        });
        scissorsButton.addActionListener(e -> {
            playerChoice = "Scissors";
            playGameRPC();
        });

        gameWindowFrame.setVisible(true);
        gameWindowFrame.setLocationRelativeTo(null);
    }

    private static void playGameRPC() {
        Random random = new Random();
        int computerChoiceIndex = random.nextInt(3);
        String[] choices = {"Rock", "Paper", "Scissors"};
        computerChoice = choices[computerChoiceIndex];

        showAnimation(playerChoice, computerChoice);

        String result = determineWinner(playerChoice, computerChoice);
        if (result.equals("You win!")) {
            playerScore++;
        } else if (result.equals("You lose!")) {
            computerScore++;
        }
        String message = "You chose: " + playerChoice + "\nComputer chose: " + computerChoice + "\n" + result +
                "\nPlayer Score: " + playerScore + "\nComputer Score: " + computerScore;
        if (result.equals("You win!")) {
            JOptionPane.showMessageDialog(gameWindowFrame, message, "You Win!", JOptionPane.INFORMATION_MESSAGE);
        } else if (result.equals("You lose!")) {
            JOptionPane.showMessageDialog(gameWindowFrame, message, "You Lose!", JOptionPane.ERROR_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(gameWindowFrame, message, "It's a tie!", JOptionPane.PLAIN_MESSAGE);
        }
        if (playerScore == 3) {
            gameWindowFrame.dispose();
            JOptionPane.showMessageDialog(gameSelectionFrame, "Congratulations! You won against a computer!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
            gameSelectionFrame.setVisible(true);
            resetScores();
        } else if (computerScore == 3) {
            gameWindowFrame.dispose();
            JOptionPane.showMessageDialog(gameSelectionFrame, "You Lose! Try again next time...", "Game Over", JOptionPane.ERROR_MESSAGE);
            gameSelectionFrame.setVisible(true);
            resetScores();
        }
        playerChoice = null;
    }

    private static void resetScores() {
        playerScore = 0;
        computerScore = 0;
    }

    private static void showAnimation(String playerChoice, String computerChoice) {
        JFrame animationFrame = new JFrame("Rock Paper Scissors Animation");
        animationFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        animationFrame.setSize(400, 400);
        animationFrame.setLayout(new BorderLayout());

        JPanel animationPanel = new JPanel() {
            private int frameCount = 0;
            private Timer timer;

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setColor(Color.WHITE);
                g2d.fillRect(0, 0, getWidth(), getHeight());
                drawHand(g2d, playerChoice, 50, 100);
                drawHand(g2d, computerChoice, 250, 100);
                frameCount++;
                if (frameCount > 10) {
                    timer.stop();
                    animationFrame.dispose();
                }
            }

            {
                timer = new Timer(100, e -> repaint());
                timer.start();
            }
        };
        animationFrame.add(animationPanel, BorderLayout.CENTER);
        animationFrame.setLocationRelativeTo(null);
        animationFrame.setVisible(true);
    }

    private static void drawHand(Graphics2D g2d, String choice, int x, int y) {
        switch (choice) {
            case "Rock":
                drawRock(g2d, x, y);
                break;
            case "Paper":
                drawPaper(g2d, x, y);
                break;
            case "Scissors":
                drawScissors(g2d, x, y);
                break;
        }
    }

    private static void drawRock(Graphics2D g2d, int x, int y) {
        g2d.setColor(Color.GRAY);
        g2d.fillOval(x, y, 50, 50);
    }

    private static void drawPaper(Graphics2D g2d, int x, int y) {
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.fillRect(x, y, 60, 80);
    }

    private static void drawScissors(Graphics2D g2d, int x, int y) {
        g2d.setColor(Color.DARK_GRAY);
        g2d.setStroke(new BasicStroke(5));
        g2d.drawLine(x, y, x + 30, y + 60);
        g2d.drawLine(x + 30, y, x, y + 60);
    }

    public static void showResultWindow(String message, boolean result, String currentGame) {
        JFrame resultFrame = new JFrame("Game Result");
        resultFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        resultFrame.setSize(400, 200);
        resultFrame.setLayout(new BorderLayout());
        BackgroundPanel backgroundPanel = new BackgroundPanel("resources/images/mainbackground.jpg");
        backgroundPanel.setLayout(new BorderLayout());
        resultFrame.setContentPane(backgroundPanel);

        JLabel resultLabel = new JLabel(message, SwingConstants.CENTER);
        resultLabel.setFont(new Font("Serif", Font.BOLD, 18));
        backgroundPanel.add(resultLabel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setOpaque(false);
        JButton playAgainButton = new JButton("Play Again");
        buttonPanel.add(playAgainButton);
        JButton goToHomeButton = new JButton("Go to Home");
        buttonPanel.add(goToHomeButton);
        backgroundPanel.add(buttonPanel, BorderLayout.SOUTH);

        playAgainButton.addActionListener(e -> {
            gameWindowFrame.dispose();
            resultFrame.setVisible(false);
            switch (currentGame) {
                case "Rock Paper Scissors":
                    createAndShowRockPaperScissorsGame();
                    break;
                case "Who wants to be a millionaire!":
                    createAndShowWhoWantsToBeAMillionaire();
                    break;
                default:
                    createAndShowGuessingGame();
            }
        });

        goToHomeButton.addActionListener(e -> {
            gameWindowFrame.dispose();
            resultFrame.setVisible(false);
            gameSelectionFrame.setVisible(true);
        });

        resultFrame.setLocationRelativeTo(null);
        resultFrame.setVisible(true);
    }

    private static String determineWinner(String playerChoice, String computerChoice) {
        if (playerChoice.equals(computerChoice)) {
            return "It's a tie!";
        } else if (
            (playerChoice.equals("Rock") && computerChoice.equals("Scissors")) ||
            (playerChoice.equals("Paper") && computerChoice.equals("Rock")) ||
            (playerChoice.equals("Scissors") && computerChoice.equals("Paper"))
        ) {
            return "You win!";
        } else {
            return "You lose!";
        }
    }

    private static void createAndShowWhoWantsToBeAMillionaire() {
        gameWindowFrame = null;
        MillionaireGame millionaireGame = new MillionaireGame(gameSelectionFrame);
        millionaireGame.setVisible(true);
    }

    private static void createAndShowTicTacToe() {
        gameWindowFrame = null;
        TicTacToe tictactoeGame = new TicTacToe(gameSelectionFrame);
        tictactoeGame.setVisible(true);
    }

    private static void createAndShowGuessingGame() {
        gameWindowFrame = null;
        GuessingGame guessingGame = new GuessingGame(gameSelectionFrame);
        guessingGame.setVisible(true);
    }

    private static void createAndShowBullsAndCowsGame() {
        gameWindowFrame = null;
        BullsAndCowsGame game = new BullsAndCowsGame(gameSelectionFrame);
        game.setVisible(true);
    }

    private static void createAndShowSudokuGame() {
        gameWindowFrame = null;
        SudokuGame sudokuGame = new SudokuGame(gameSelectionFrame);
        sudokuGame.setVisible(true);
}
}