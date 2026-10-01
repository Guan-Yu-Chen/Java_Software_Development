package gamehub;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

public class GuessingGame extends JFrame implements ActionListener {
    private static JFrame gameSelectionFrame;
    private JLabel promptLabel;
    private JButton[] buttons;
    private int roundCount;
    private String[] prompts = {
        "Think of a color:",
        "Think of a country in East Asia:",
        "Think of a city in America:",
        "Think of a pastry:",
        "Think of a drink:",
        "Think of a religion:",
        "Think of a dessert:",
        "Think of a fictional monster:",
        "Think of an avenger:",
        "Think of a shoe brand:",
        "Think of a book genre:",
        "Think of a musical instrument:",
        "Think of a famous mountain:"
    };

    private String[][] options = {
        {"Red", "Blue", "Yellow", "White"},
        {"Taiwan", "North Korea", "South Korea", "China"},
        {"New York", "Los Angeles", "Washington DC", "Orlando"},
        {"Cake", "Pie", "Bread", "Croissant"},
        {"Water", "Soda", "Coffee", "Tea"},
        {"Muslim", "Christian", "Buddist", "Taoism"},
        {"Ice cream", "Pudding", "Brownie", "Candy"},
        {"Vampires", "Zombies", "Mummies", "Frankenstein"},
        {"Thor", "Iron Man", "Captain America", "Spiderman"},
        {"Nike", "Adidas", "Puma", "Crocs"},
        {"Everest", "Kilimanjaro", "Fuji", "Matterhorn"},
        {"Piano", "Guitar", "Violin", "Flute"},
        {"Mystery", "Fantasy", "Romance", "Science fiction"}
    };

    private JLabel timerLabel;
    private Timer gameTimer;
    private int timeLeft;

    public GuessingGame(JFrame gameSelectionFrame) {
        super("Guessing Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 350);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        JLabel rulesLabel = new JLabel("Rules");
        rulesLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        JPanel rulesPanel = new JPanel(new BorderLayout());
        rulesPanel.add(rulesLabel, BorderLayout.CENTER);
        rulesLabel.setForeground(Color.BLACK);

        JOptionPane.showMessageDialog(
            rulesPanel,
            "DON'T think like a computer. Win 5 times!",
            getTitle(),
            JOptionPane.PLAIN_MESSAGE,
            new ImageIcon("resources/images/dont_copy_me_icon.png")
        );

        BackgroundPanel backgroundPanel = new BackgroundPanel("resources/images/guessgame.jpg");
        backgroundPanel.setLayout(new BorderLayout());
        setContentPane(backgroundPanel);

        promptLabel = new JLabel();
        promptLabel.setForeground(new Color(255, 255, 153));
        promptLabel.setFont(new Font("Serif", Font.BOLD, 21));
        backgroundPanel.add(promptLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 4, 10, 100));
        buttonPanel.setOpaque(false);
        buttons = new JButton[4];

        Color buttonColor = new Color(65, 105, 225); // dark blue
        Color textColor = new Color(255, 255, 153);

        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new JButton("");
            buttons[i].setPreferredSize(new Dimension(50, 30));
            buttons[i].setBackground(buttonColor);
            buttons[i].setForeground(textColor);
            buttons[i].setOpaque(true);
            buttons[i].setBorderPainted(false);
            buttons[i].addActionListener(this);
            buttonPanel.add(buttons[i]);
        }

        add(buttonPanel, BorderLayout.CENTER);

        timerLabel = new JLabel();
        timerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        timerLabel.setFont(new Font("Serif", Font.BOLD, 18));
        timerLabel.setForeground(Color.RED);
        backgroundPanel.add(timerLabel, BorderLayout.SOUTH);

        GuessingGame.gameSelectionFrame = gameSelectionFrame;
        roundCount = 0;
        gameTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timeLeft--;
                timerLabel.setText("Time left: " + timeLeft + " seconds");
                if (timeLeft <= 0) {
                    JOptionPane.showMessageDialog(
                        GuessingGame.this,
                        "Time's up! You lose!",
                        "Game Over",
                        JOptionPane.ERROR_MESSAGE
                    );
                    gameTimer.stop();
                    dispose();
                    gameSelectionFrame.setVisible(true);
                }
            }
        });

        newRound();
    }

    private void stopTimer() {
        gameTimer.stop();
    }

    private void newRound() {
        if (roundCount >= 5) {
            JOptionPane.showMessageDialog(
                this,
                "Congratulations! You won!",
                "You Win!",
                JOptionPane.INFORMATION_MESSAGE
            );
            this.dispose();
            gameSelectionFrame.setVisible(true);
            return;
        }

        int promptIndex = new Random().nextInt(prompts.length);
        promptLabel.setText(prompts[promptIndex]);

        // Shuffle options
        List<String> shuffledOptions = new ArrayList<>(Arrays.asList(options[promptIndex]));
        Collections.shuffle(shuffledOptions);
        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setText(shuffledOptions.get(i));
        }

        // Start the timer for 10 seconds
        timeLeft = 10;
        timerLabel.setText("Time left: " + timeLeft + " seconds");
        gameTimer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        gameTimer.stop();
        JButton button = (JButton) e.getSource();
        String userChoice = button.getText();
        String computerChoice = buttons[new Random().nextInt(buttons.length)].getText();
        if (userChoice.equals(computerChoice)) {
            JOptionPane.showMessageDialog(
                this,
                "You guessed the same as the computer! You lose!",
                "You Lose!",
                JOptionPane.ERROR_MESSAGE
            );
            this.dispose();
            stopTimer();
            gameSelectionFrame.setVisible(true);
        } else {
            roundCount++;
            JOptionPane.showMessageDialog(
                this,
                "Correct! You guessed a different option.",
                "Good Job!",
                JOptionPane.INFORMATION_MESSAGE
            );
            newRound();
            stopTimer();
        }
    }
}