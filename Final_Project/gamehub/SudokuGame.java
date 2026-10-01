package gamehub;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class SudokuGame extends JFrame {
    private static final int SIZE = 9;
    private JTextField[][] cells = new JTextField[SIZE][SIZE];
    private int[][] solution = new int[SIZE][SIZE];
    private int[][] puzzle = new int[SIZE][SIZE];
    private JFrame parentFrame;
    private int emptyCells = 36; // 預設中等難度

    public SudokuGame(JFrame parentFrame) {
        super("Sudoku 9x9");
        this.parentFrame = parentFrame;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 800);
        setLocationRelativeTo(null);

        showDifficultyDialog();
    }

    // 難度選擇視窗
    private void showDifficultyDialog() {
        JDialog dialog = new JDialog(this, "Select Difficulty", true);
        dialog.setSize(350, 250);
        dialog.setLayout(new GridLayout(5, 1, 10, 10));
        dialog.setLocationRelativeTo(this);

        JButton easyBtn = new JButton("Easy (28 blanks)");
        JButton mediumBtn = new JButton("Medium (36 blanks)");
        JButton hardBtn = new JButton("Hard (48 blanks)");
        JButton customBtn = new JButton("Custom blanks (max 64)");
        JButton cancelBtn = new JButton("Back to Menu");

        easyBtn.addActionListener(e -> {
            emptyCells = 28;
            dialog.dispose();
            startGame();
        });
        mediumBtn.addActionListener(e -> {
            emptyCells = 36;
            dialog.dispose();
            startGame();
        });
        hardBtn.addActionListener(e -> {
            emptyCells = 48;
            dialog.dispose();
            startGame();
        });
        customBtn.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(dialog, "Enter number of blanks (1~64):", "36");
            if (input != null) {
                try {
                    int val = Integer.parseInt(input);
                    if (val < 1 || val > 64) throw new NumberFormatException();
                    emptyCells = val;
                    dialog.dispose();
                    startGame();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(dialog, "Please enter an integer between 1 and 64.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        cancelBtn.addActionListener(e -> {
            dialog.dispose();
            dispose();
            if (parentFrame != null) parentFrame.setVisible(true);
        });

        dialog.add(easyBtn);
        dialog.add(mediumBtn);
        dialog.add(hardBtn);
        dialog.add(customBtn);
        dialog.add(cancelBtn);

        dialog.setVisible(true);
    }

    // 遊戲主畫面
    private void startGame() {
        getContentPane().removeAll();
        setLayout(new BorderLayout());

        // 產生題目
        generateSudokuPuzzle();

        JPanel gridPanel = new JPanel(new GridLayout(SIZE, SIZE));
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                JTextField tf = new JTextField();
                tf.setHorizontalAlignment(JTextField.CENTER);
                tf.setFont(new Font("Arial", Font.BOLD, 22));
                if (puzzle[i][j] != 0) {
                    tf.setText(String.valueOf(puzzle[i][j]));
                    tf.setEditable(false);
                    tf.setBackground(new Color(220, 220, 220));
                } else {
                    tf.setText("");
                    tf.setEditable(true);
                    tf.setBackground(Color.WHITE);
                }
                // 粗框線
                if (i % 3 == 0) tf.setBorder(BorderFactory.createMatteBorder(3, tf.getBorder().getBorderInsets(tf).left, tf.getBorder().getBorderInsets(tf).bottom, tf.getBorder().getBorderInsets(tf).right, Color.BLACK));
                if (j % 3 == 0) tf.setBorder(BorderFactory.createMatteBorder(tf.getBorder().getBorderInsets(tf).top, 3, tf.getBorder().getBorderInsets(tf).bottom, tf.getBorder().getBorderInsets(tf).right, Color.BLACK));
                if (i == SIZE - 1) tf.setBorder(BorderFactory.createMatteBorder(tf.getBorder().getBorderInsets(tf).top, tf.getBorder().getBorderInsets(tf).left, 3, tf.getBorder().getBorderInsets(tf).right, Color.BLACK));
                if (j == SIZE - 1) tf.setBorder(BorderFactory.createMatteBorder(tf.getBorder().getBorderInsets(tf).top, tf.getBorder().getBorderInsets(tf).left, tf.getBorder().getBorderInsets(tf).bottom, 3, Color.BLACK));
                cells[i][j] = tf;
                gridPanel.add(tf);
            }
        }

        JPanel btnPanel = new JPanel();
        JButton checkBtn = new JButton("Check");
        JButton hintBtn = new JButton("Hint");
        JButton showAnsBtn = new JButton("Show Answer");
        JButton backBtn = new JButton("Back");

        checkBtn.addActionListener(e -> checkSolution());
        hintBtn.addActionListener(e -> showHint());
        showAnsBtn.addActionListener(e -> showAnswer());
        backBtn.addActionListener(e -> {
            dispose();
            if (parentFrame != null) parentFrame.setVisible(true);
        });

        btnPanel.add(checkBtn);
        btnPanel.add(hintBtn);
        btnPanel.add(showAnsBtn);
        btnPanel.add(backBtn);

        add(gridPanel, BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);

        revalidate();
        repaint();
        setVisible(true);
    }

    // 產生完整解答與題目
    private void generateSudokuPuzzle() {
        solution = new int[SIZE][SIZE];
        puzzle = new int[SIZE][SIZE];
        generateFullBoard(solution);
        // 複製解答
        for (int i = 0; i < SIZE; i++)
            puzzle[i] = Arrays.copyOf(solution[i], SIZE);

        // 隨機挖空
        List<int[]> positions = new ArrayList<>();
        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++)
                positions.add(new int[]{i, j});
        Collections.shuffle(positions);

        int removed = 0;
        for (int[] pos : positions) {
            if (removed >= emptyCells) break;
            int backup = puzzle[pos[0]][pos[1]];
            puzzle[pos[0]][pos[1]] = 0;
            // 確保唯一解
            if (!hasUniqueSolution(puzzle)) {
                puzzle[pos[0]][pos[1]] = backup;
            } else {
                removed++;
            }
        }
    }

    // 產生完整數獨盤
    private boolean generateFullBoard(int[][] board) {
        for (int i = 0; i < SIZE; i++)
            Arrays.fill(board[i], 0);
        return fillBoard(board, 0, 0);
    }

    // 回溯法填滿盤面
    private boolean fillBoard(int[][] board, int row, int col) {
        if (row == SIZE) return true;
        int nextRow = (col == SIZE - 1) ? row + 1 : row;
        int nextCol = (col + 1) % SIZE;
        List<Integer> nums = new ArrayList<>();
        for (int i = 1; i <= SIZE; i++) nums.add(i);
        Collections.shuffle(nums);
        for (int num : nums) {
            if (isSafe(board, row, col, num)) {
                board[row][col] = num;
                if (fillBoard(board, nextRow, nextCol)) return true;
                board[row][col] = 0;
            }
        }
        return false;
    }

    // 判斷填入是否合法
    private boolean isSafe(int[][] board, int row, int col, int num) {
        for (int i = 0; i < SIZE; i++)
            if (board[row][i] == num || board[i][col] == num)
                return false;
        int boxRow = row / 3 * 3, boxCol = col / 3 * 3;
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (board[boxRow + i][boxCol + j] == num)
                    return false;
        return true;
    }

    // 檢查唯一解
    private boolean hasUniqueSolution(int[][] board) {
        int[][] copy = new int[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++)
            copy[i] = Arrays.copyOf(board[i], SIZE);
        return countSolutions(copy, 0, 0, 0) == 1;
    }

    // 計算解答數
    private int countSolutions(int[][] board, int row, int col, int count) {
        if (row == SIZE) return count + 1;
        if (count > 1) return count; // 超過一個解就不用繼續
        int nextRow = (col == SIZE - 1) ? row + 1 : row;
        int nextCol = (col + 1) % SIZE;
        if (board[row][col] != 0)
            return countSolutions(board, nextRow, nextCol, count);
        for (int num = 1; num <= SIZE; num++) {
            if (isSafe(board, row, col, num)) {
                board[row][col] = num;
                count = countSolutions(board, nextRow, nextCol, count);
                board[row][col] = 0;
                if (count > 1) break;
            }
        }
        return count;
    }

    // 檢查答案
    private void checkSolution() {
        int[][] user = new int[SIZE][SIZE];
        try {
            for (int i = 0; i < SIZE; i++)
                for (int j = 0; j < SIZE; j++) {
                    String text = cells[i][j].getText().trim();
                    if (text.isEmpty()) user[i][j] = 0;
                    else {
                        int val = Integer.parseInt(text);
                        if (val < 1 || val > 9) throw new NumberFormatException();
                        user[i][j] = val;
                    }
                }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter numbers 1~9.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (Arrays.deepEquals(user, solution)) {
            JOptionPane.showMessageDialog(this, "Congratulations! You solved it!", "Success", JOptionPane.INFORMATION_MESSAGE);
            dispose();
            if (parentFrame != null) parentFrame.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Incorrect solution. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // 隨機顯示一格正確答案
    private void showHint() {
        List<int[]> emptyList = new ArrayList<>();
        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++)
                if (cells[i][j].isEditable() && (cells[i][j].getText().trim().isEmpty() || !cells[i][j].getText().trim().equals(String.valueOf(solution[i][j]))))
                    emptyList.add(new int[]{i, j});
        if (emptyList.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No available cells for hint.", "Hint", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        int[] pos = emptyList.get(new Random().nextInt(emptyList.size()));
        cells[pos[0]][pos[1]].setText(String.valueOf(solution[pos[0]][pos[1]]));
        cells[pos[0]][pos[1]].setForeground(Color.BLUE);
    }
    // 顯示全部答案
    private void showAnswer() {
        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++) {
                cells[i][j].setText(String.valueOf(solution[i][j]));
                cells[i][j].setForeground(Color.RED);
            }
    }
}