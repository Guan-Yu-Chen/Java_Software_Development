package gamehub;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.util.*;

public class MillionaireGame extends JFrame {
    private static JFrame gameSelectionFrame;
    private JLabel questionLabel;
    private JButton option1, option2, option3, option4;
    private JButton fiftyFiftyButton, hintButton;
    private Timer timer;
    private int timeLeft;
    private int currentQuestion = 0;
    private int score = 0;
    private int fiftyFiftyUsed = 0;
    private int hintUsed = 0;
    private final int maxFiftyFiftyUses = 2;
    private final int maxHintUses = 2;
    private String correctAnswer;

    
    private final String[][] allQuestionSets = {
        {
            "1. What video game characters specialize in plumbing?",
            "2. What network is Lightning McQueen from?",
            "3. What year was Thomas The Tank Engine created?",
            "4. Who is the creator of Pokémon?",
            "5. What year was Fortnite created?",
            "6. How much does a Lamborghini aventador cost?",
            "7. What car is the fastest car in the world? (as of 2023)",
            "8. Where traditionally do birds fly in winter?",
            "9. Which year was python language developed?",
            "10. What city hosted the 2002 Olympic Games?",
            "11. The fear of insects is known as what?",
            "12. The Earth is approximately how many miles away from the sun?"
        },
        {
            "1. In A Bug's Life, What type of insect is Hopper?",
            "2. Who invented the lightbulb?",
            "3. When was Rosa Parks born?",
            "4. What year was Pluto declared 'not a planet'?",
            "5. How many characters were vaporized in Super Smash Bros. World Of Light?",
            "6. What are the names of Thomas The Tank Engine's faithful coaches?",
            "7. What is Yellowcake Uranium?",
            "8. Where was the first example of paper money used?",
            "9. Which of the following was considered one of the Seven Ancient Wonders?",
            "10. Who is generally considered the inventor of the motor car?",
            "11. Which of the following men does not have a chemical element named for him?",
            "12. According to the Population Reference Bureau, what is the approximate number of people who have ever lived on earth?"
        },
        {
            "1. What color are oranges?",
            "2. Are spiders insects?",
            "3. Raleigh is the capital of which State?",
            "4. What pet is the most popular pet in the USA?",
            "5. How fast can a falcon fly?",
            "6. What snake is the most venomous in the world?",
            "7. How much pressure does a Nile Crocodile bite down with?",
            "8. Which of the following languages has the longest alphabet?",
            "9. Which flies a green, white, and orange (in that order) tricolor flag?",
            "10. How many days make up a non-leap year in the Islamic calendar?",
            "11. Neurologists believe that the brain's medial ventral prefrontal cortex is activated when you do what?",
            "12. Who is credited with inventing the first mass-produced helicopter?"
        }
    };

    private final String[][][] allOptionSets = {
        {
            {"Mario and Luigi", "Sonic and Tails", "Mega Man and Samus", "Sans and Papyrus"},
            {"PBS Kids", "Nicktoons", "Cartoon Network", "Disney Pixar"},
            {"2003", "1929", "1942", "1887"},
            {"Hiroyuki Sanada", "Akira Kurosawa", "Satoshi Tajiri", "Takashi Miike"},
            {"2007", "2019", "1998", "2017"},
            {"$417,650", "$317,550", "$255,300", "$550,250"},
            {"Koenigsegg Jesko Absolut", "Hennessey Venom", "Lamborghini Aventador", "Bugatti Chiron"},
            {"North", "East", "South", "West"},
            {"1992", "1989", "2001", "1991"},
            {"Tokyo", "Beijing", "Sydney", "Qatar"},
            {"Arachnophobia", "Entomophobia", "Ailurophobia", "Lilapsophobia"},
            {"9.3 million", "39 million", "93 million", "193 million"}
        },
        {
            {"Ladybug", "Grasshopper", "Tick", "Yellow Jacket"},
            {"Thomas Edison", "George Washington Carver", "Willy Wonka", "Julius Caesar"},
            {"2006", "1903", "1927", "1913"},
            {"2006", "1936", "1942", "1929"},
            {"23", "67", "73", "12"},
            {"Donald and Douglas", "Mario and Luigi", "Annie and Clarabel", "Sonic and Tails"},
            {"A Uranium Concentrate Powder", "A Piece of a Jigsaw Puzzle", "An Ingredient for Chocolate", "None of The Above"},
            {"China", "Turkey", "Greece", "Germany"},
            {"Colosseum", "Great Wall of China", "Colossus of Rhodes", "Wadi Rum"},
            {"Henry Ford", "Henry M. Leland", "Karl Benz", "Karl Rapp"},
            {"Albert Einstein", "Niels Bohr", "Isaac Newton", "Enrico Fermi"},
            {"50 billion", "1 trillion", "100 billion", "5 trillion"}
        },
        {
            {"Blue", "Black", "Orange", "Green"},
            {"Yes", "No", "Sometimes", "Only in movies"},
            {"South Dakota", "North Carolina", "Mississippi", "Tennessee"},
            {"Dogs", "Cats", "Fish", "Hamsters"},
            {"240 mph", "88 mph", "165 mph", "36 mph"},
            {"The Western Diamondback Rattlesnake", "The King Cobra", "The Black Mamba", "The Inland Taipan"},
            {"25,000 lbs per sq inch", "6,000 lbs per sq inch", "245,400 lbs per sq inch", "3,500 lbs per sq inch"},
            {"Greek", "Arabic", "French", "Russian"},
            {"Ireland", "Ivory Coast", "Italy", "India"},
            {"365", "354", "376", "400"},
            {"Have a panic attack", "Remember a name", "Get a joke", "Listen to music"},
            {"Igor Sikorsky", "Elmer Sperry", "Ferdinand von Zeppelin", "Gottlieb Daimler"}
        }
    };

    private final String[][] allAnswerSets = {
        {
            "Mario and Luigi",
            "Disney Pixar",
            "1942",
            "Satoshi Tajiri",
            "2017",
            "$417,650",
            "Koenigsegg Jesko Absolut",
            "South",
            "1991",
            "Sydney",
            "Entomophobia",
            "93 million"
        },
        {
            "Grasshopper",
            "Thomas Edison",
            "1913",
            "2006",
            "73",
            "Annie and Clarabel",
            "A Uranium Concentrate Powder",
            "China",
            "Colossus of Rhodes",
            "Karl Benz",
            "Isaac Newton",
            "100 billion"
        },
        {
            "Orange",
            "No",
            "North Carolina",
            "Fish",
            "240 mph",
            "The Inland Taipan",
            "6,000 lbs per sq inch",
            "Russian",
            "Ireland",
            "354",
            "Get a joke",
            "Igor Sikorsky"
        }
    };

    private final String[][] allHints = {
        {"Red and Green", "Toy Story", "Inbetween the years that World-War2 happened", "S", "Trump's inauguration", "50x8353", "made in Sweden", "antarctica", "20", "Oceania", "Enter", "Quatre vingt treize"},
        {"Green", "T", "23", "8", "21", "abc", "U3O8", "Panda", "Greek name", "Car+l", "Apple", "11 0s"},
        {"red and yellow", "are planes birds?", "Nice car!", "Gill", "How many hours is there in a day", "Somehow the answer reminds me of this city....", "2 times 3", "This country was in a very cold war", "Leprechauns", "eh? why are there 11 days off in the calendar?", "Hate the ones dads make", "got scores from the sky!"}
    };

    private final int[] prizeAmounts = {
        100, 500, 1000, 2500, 5000, 10000, 50000, 100000, 250000, 500000, 750000, 1000000
    };

    private String[] questionSet;
    private String[][] optionSet;
    private String[] answerSet;
    private String[] hints;

    public MillionaireGame(JFrame gameSelectionFrame) {
        super("Who Wants to Be a Millionaire?");
        MillionaireGame.gameSelectionFrame = gameSelectionFrame;

        Random random = new Random();
        int selectedSet = random.nextInt(allQuestionSets.length);
        questionSet = allQuestionSets[selectedSet];
        optionSet = allOptionSets[selectedSet];
        answerSet = allAnswerSets[selectedSet];
        hints = allHints[selectedSet];

        setTitle("Who Wants to Be a Millionaire?");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(750, 550);
        setLocationRelativeTo(null);
        setResizable(false);

        JLabel rulesLabel = new JLabel("<html><body style='width: 500px; text-align: left;'>Rules of the Game:<br><br>" +
                "The Who Wants to Be a Millionaire questions are structured according to five different Levels with each level increasing in difficulty. Each level contains three questions.<br>" +
                "Questions that are grouped into the same level will all be of similar difficulty.<br><br>" +
                "Question 1 $100<br>" +
                "Question 2 $500<br>" +
                "Question 3 $1,000<br>" +
                "Question 4 $2,500<br>" +
                "Question 5 $5,000<br><br>" +
                "Question 6 $10,000<br>" +
                "Question 7 $50,000<br>" +
                "Question 8 $100,000<br>" +
                "Question 9 $250,000<br>" +
                "Question 10 $500,000<br><br>" +
                "Question 11 $750,000<br>" +
                "Question 12 $1,000,000<br><br>" +
                "50/50 : removes two wrong answers from the multiple-choice selection, leaving the contestant with only one correct and one incorrect option. This means they have a 50/50 chance.<br><br>" +
                "Ask for a hint : Contestants can ask for a hint to the correct answer that they can use at any point during the game. They can use this option twice.<br><br>" +
                "</body></html>");
        rulesLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        JPanel rulesPanel = new JPanel(new BorderLayout());
        rulesPanel.add(rulesLabel, BorderLayout.CENTER);
        rulesLabel.setForeground(Color.BLACK);

        JOptionPane.showMessageDialog(this, rulesPanel, "Rules of the Game", JOptionPane.PLAIN_MESSAGE, new ImageIcon("resources/images/capsule_616x353.jpg"));

        createUI();
    }

    private void createUI() {
        startTimer();
        BackgroundPanel backgroundPanel = new BackgroundPanel("resources/images/maxresdefault.jpg");
        backgroundPanel.setLayout(new BorderLayout());
        setContentPane(backgroundPanel);

        questionLabel = new JLabel();
        questionLabel.setForeground(new Color(169, 169, 169));
        questionLabel.setFont(new Font("Serif", Font.BOLD, 18));
        backgroundPanel.add(questionLabel, BorderLayout.NORTH);

        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new GridLayout(2, 2, 10, 10));
        optionsPanel.setOpaque(false);

        option1 = new JButton();
        option2 = new JButton();
        option3 = new JButton();
        option4 = new JButton();

        Color purple = new Color(186, 85, 211);
        Color grayText = new Color(255, 255, 255);

        JButton[] optionButtons = {option1, option2, option3, option4};
        for (JButton btn : optionButtons) {
            btn.setBackground(purple);
            btn.setForeground(grayText);
            btn.setOpaque(true);
            btn.setBorderPainted(false);
        }

        optionsPanel.add(option1);
        optionsPanel.add(option2);
        optionsPanel.add(option3);
        optionsPanel.add(option4);
        backgroundPanel.add(optionsPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 2, 10, 10));
        buttonPanel.setOpaque(false);
        fiftyFiftyButton = new JButton("50/50");
        hintButton = new JButton("Ask for a Hint");
        buttonPanel.add(fiftyFiftyButton);
        buttonPanel.add(hintButton);
        backgroundPanel.add(buttonPanel, BorderLayout.SOUTH);

        fiftyFiftyButton.addActionListener(e -> useFiftyFifty());
        hintButton.addActionListener(e -> useHint());

        setQuestion();

        option1.addActionListener(e -> checkAnswer(option1.getText()));
        option2.addActionListener(e -> checkAnswer(option2.getText()));
        option3.addActionListener(e -> checkAnswer(option3.getText()));
        option4.addActionListener(e -> checkAnswer(option4.getText()));
    }

    private void setQuestion() {
        if (timer != null) {
            timer.stop();
        }
        timeLeft = 20;
        updateQuestionLabel();
        option1.setText(optionSet[currentQuestion][0]);
        option2.setText(optionSet[currentQuestion][1]);
        option3.setText(optionSet[currentQuestion][2]);
        option4.setText(optionSet[currentQuestion][3]);
        correctAnswer = answerSet[currentQuestion];
        enableOptions();
        startTimer();
    }

    private void startTimer() {
        timer = new Timer(1000, e -> {
            timeLeft--;
            questionLabel.setText(questionSet[currentQuestion] + " (Time left: " + timeLeft + "s)");
            if (timeLeft <= 0) {
                timer.stop();
                handleTimeOut();
            }
        });
        timer.start();
    }

    private void updateQuestionLabel() {
        questionLabel.setText(questionSet[currentQuestion] + " (Time left: " + timeLeft + "s)");
    }

    private void handleTimeOut() {
        JOptionPane.showMessageDialog(this, "Time's up! You didn't choose an answer in time. You only got: $"+ score, "Time's Up", JOptionPane.WARNING_MESSAGE);
        this.dispose();
        gameSelectionFrame.setVisible(true);
    }

    private void enableOptions() {
        option1.setEnabled(true);
        option2.setEnabled(true);
        option3.setEnabled(true);
        option4.setEnabled(true);
    }

    private void disableOptions(String... options) {
        for (String option : options) {
            if (option1.getText().equals(option)) {
                option1.setEnabled(false);
            } else if (option2.getText().equals(option)) {
                option2.setEnabled(false);
            } else if (option3.getText().equals(option)) {
                option3.setEnabled(false);
            } else if (option4.getText().equals(option)) {
                option4.setEnabled(false);
            }
        }
    }

    private void useFiftyFifty() {
        if (fiftyFiftyUsed < maxFiftyFiftyUses) {
            fiftyFiftyUsed++;
            String[] options = {
                option1.getText(),
                option2.getText(),
                option3.getText(),
                option4.getText()
            };
            int correctIndex = -1;
            for (int i = 0; i < options.length; i++) {
                if (options[i].equals(correctAnswer)) {
                    correctIndex = i;
                    break;
                }
            }

            // Create an array to hold the incorrect options
            String[] incorrectOptions = new String[3];
            int incorrectCount = 0;

            for (int i = 0; i < options.length; i++) {
                if (i != correctIndex) {
                    incorrectOptions[incorrectCount++] = options[i];
                }
            }

            // Randomly select two incorrect options to disable
            Random rand = new Random();
            int firstToRemove = rand.nextInt(3);
            int secondToRemove;
            do {
                secondToRemove = rand.nextInt(3);
            } while (secondToRemove == firstToRemove);

            disableOptions(
                incorrectOptions[firstToRemove],
                incorrectOptions[secondToRemove]
            );

        } else {
            JOptionPane.showMessageDialog(this, "You have already used 50/50 twice.", "50/50 Used Up", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void useHint() {
        if (hintUsed < maxHintUses) {
            hintUsed++;
            JOptionPane.showMessageDialog(this, hints[currentQuestion], "Hint", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "You have already used hints twice.", "Hints Used Up", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void checkAnswer(String selectedOption) {
        if (timer != null) {
            timer.stop();
        }
        if (selectedOption.equals(correctAnswer)) {
            score += prizeAmounts[currentQuestion];
            currentQuestion++;
            if (currentQuestion < questionSet.length) {
                setQuestion();
            } else {
                JOptionPane.showMessageDialog(this, "Congratulations! You won: $" + score, "Game Over", JOptionPane.PLAIN_MESSAGE);
                this.dispose();
                gameSelectionFrame.setVisible(true);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Wrong answer! You only got: $" + score, "Game Over", JOptionPane.PLAIN_MESSAGE);
            this.dispose();
            gameSelectionFrame.setVisible(true);
        }
    }
}