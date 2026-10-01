package gamehub;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

public class BullsAndCowsGame extends JFrame implements ActionListener {
    private static JFrame gameSelectionFrame;
    private String answer;
    private JTextField inputField;
    private JTextArea historyArea;
    private int attempts;

    public BullsAndCowsGame(JFrame gameSelectionFrame) {
        super("1A2B (Bulls and Cows)");
        BullsAndCowsGame.gameSelectionFrame = gameSelectionFrame;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 350);
        setLocationRelativeTo(null);

        answer = generateAnswer();
        attempts = 0;

        JLabel rulesLabel = new JLabel("Guess a 4-digit number (no repeated digits). A: correct digit & position, B: correct digit, wrong position.");
        rulesLabel.setFont(new Font("Arial", Font.PLAIN, 13));

        inputField = new JTextField();
        JButton guessButton = new JButton("Guess!");
        guessButton.addActionListener(this);

        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(guessButton, BorderLayout.EAST);

        historyArea = new JTextArea();
        historyArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(historyArea);

        JButton backButton = new JButton("Back to Menu");
        backButton.addActionListener(e -> {
            dispose();
            gameSelectionFrame.setVisible(true);
        });

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(inputPanel, BorderLayout.CENTER);
        bottomPanel.add(backButton, BorderLayout.EAST);

        setLayout(new BorderLayout());
        add(rulesLabel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private String generateAnswer() {
        List<Integer> digits = new ArrayList<>();
        for (int i = 0; i < 10; i++) digits.add(i);
        Collections.shuffle(digits);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) sb.append(digits.get(i));
        return sb.toString();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String guess = inputField.getText().trim();
        if (!guess.matches("\\d{4}") || hasDuplicateDigits(guess)) {
            JOptionPane.showMessageDialog(this, "Please enter 4 non-repeating digits!", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        attempts++;
        String result = checkGuess(guess);
        historyArea.append("Attempt " + attempts + ": " + guess + " -> " + result + "\n");
        inputField.setText("");
        if (result.equals("4A0B")) {
            JOptionPane.showMessageDialog(this, "Congratulations! You got it in " + attempts + " attempts!", "Success", JOptionPane.INFORMATION_MESSAGE);
            dispose();
            gameSelectionFrame.setVisible(true);
        }
    }

    private boolean hasDuplicateDigits(String s) {
        Set<Character> set = new HashSet<>();
        for (char c : s.toCharArray()) {
            if (!set.add(c)) return true;
        }
        return false;
    }

    private String checkGuess(String guess) {
        int A = 0, B = 0;
        for (int i = 0; i < 4; i++) {
            if (guess.charAt(i) == answer.charAt(i)) {
                A++;
            } else if (answer.indexOf(guess.charAt(i)) != -1) {
                B++;
            }
        }
        return A + "A" + B + "B";
    }
}