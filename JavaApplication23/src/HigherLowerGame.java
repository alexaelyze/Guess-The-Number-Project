import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.Random;

public class HigherLowerGame extends JFrame {
    private int targetNumber;
    private int guessCount;
    private long startTime;
    private final int TIME_LIMIT_SECONDS = 60; // Time limit for guessing in seconds
    private final Timer timer;

    private JLabel statusLabel;
    private JLabel guessLabel;
    private JLabel timeLabel;
    private JTextField guessField;
    private JButton guessButton;
    private JButton retryButton;

    public HigherLowerGame() {
        setTitle("Lowest to Highest Guessing Game");
        setSize(350, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Title label
        JLabel titleLabel = new JLabel("<html><div style='font-size:16pt;text-align:center'>Guess the Random Between 1 & 100</div></html>");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());

        // Instructions Panel
        JPanel instructionsPanel = new JPanel(new BorderLayout());
        Border border = BorderFactory.createLineBorder(Color.BLACK, 2);
        instructionsPanel.setBorder(BorderFactory.createCompoundBorder(border,
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        JLabel instructionsLabel = new JLabel("<html><div style='font-size:14pt;text-align:center'>Blue means <font color='blue'>Higher</font><br>Red means <font color='red'>Lower</font></div></html>");
        instructionsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        instructionsPanel.add(instructionsLabel, BorderLayout.CENTER);
        centerPanel.add(instructionsPanel, BorderLayout.NORTH);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        guessLabel = new JLabel("Guess: ");
        guessLabel.setFont(new Font("Arial", Font.BOLD, 14));
        guessField = new JTextField(10);
        guessField.setFont(new Font("Arial", Font.PLAIN, 14));
        guessButton = new JButton("Guess");
        guessButton.setFont(new Font("Arial", Font.BOLD, 14));
        retryButton = new JButton("Retry");
        retryButton.setFont(new Font("Arial", Font.BOLD, 14));
        retryButton.setVisible(false); // Initially hidden
        inputPanel.add(guessLabel);
        inputPanel.add(guessField);
        inputPanel.add(guessButton);
        inputPanel.add(retryButton);

        centerPanel.add(inputPanel, BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);

        statusLabel = new JLabel("");
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(statusLabel, BorderLayout.SOUTH);

        targetNumber = new Random().nextInt(100) + 1; // Generating random number between 1 and 100
        guessCount = 0;
        startTime = System.currentTimeMillis();

        timeLabel = new JLabel("<html><div style='font-size:14pt;text-align:center'><b>Time left: " + TIME_LIMIT_SECONDS + " seconds</b></div></html>");
        add(timeLabel, BorderLayout.PAGE_END);

        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                long elapsedTime = (System.currentTimeMillis() - startTime) / 1000; // Convert to seconds
                long remainingTime = TIME_LIMIT_SECONDS - elapsedTime;
                if (remainingTime <= 0) {
                    timer.stop();
                    guessField.setEditable(false);
                    guessButton.setEnabled(false);
                    retryButton.setVisible(true);
                    timeLabel.setText("<html><div style='font-size:14pt;text-align:center;color:red'><b>Time's up!</b></div></html>");
                } else {
                    timeLabel.setText("<html><div style='font-size:14pt;text-align:center'><b>Time left: " + remainingTime + " seconds</b></div></html>");
                }
            }
        });

        guessButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = guessField.getText();
                if (!input.matches("\\d+")) {
                    statusLabel.setText("<html><div style='font-size:14pt;color:red'>Please input a valid number between 1 and 100.</div></html>");
                    return;
                }
                int guess = Integer.parseInt(input);
                if (guess < 1 || guess > 100) {
                    statusLabel.setText("<html><div style='font-size:14pt;color:red'>Please input a number between 1 and 100.</div></html>");
                    return;
                }
                if (guessCount == 0) {
                    startTime = System.currentTimeMillis();
                    timer.start();
                    guessLabel.setText("Guess: " + ++guessCount);
                } else {
                    guessLabel.setText("Guess: " + ++guessCount);
                }
                if (guess == targetNumber) {
                    timer.stop();
                    JOptionPane.showMessageDialog(null, "Congratulations! You guessed the number in " + guessCount + " guesses.", "Congratulations", JOptionPane.INFORMATION_MESSAGE);
                    retryButton.setVisible(true);
                } else {
                    if (guess < targetNumber) {
                        statusLabel.setText("<html><div style='font-size:14pt;color:blue'>Higher!</div></html>");
                        getContentPane().setBackground(new Color(173, 216, 230)); // Light Blue background
                    } else {
                        statusLabel.setText("<html><div style='font-size:14pt;color:red'>Lower!</div></html>");
                        getContentPane().setBackground(new Color(255, 192, 203)); // Light Pink background
                    }
                    guessField.setText("");
                }
            }
        });

        retryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                targetNumber = new Random().nextInt(100) + 1;
                guessCount = 0;
                startTime = System.currentTimeMillis();
                timer.start();
                retryButton.setVisible(false);
                guessField.setEditable(true);
                guessButton.setEnabled(true);
                statusLabel.setText("");
                getContentPane().setBackground(null); // Reset background color
                guessLabel.setText("Guess: ");
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new HigherLowerGame().setVisible(true);
            }
        });
    }
}