import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class FindTheButtonGUI extends JFrame {
    private int currentLevel;
    private int gridSize;
    private int buttonRow;
    private int buttonCol;
    private JButton[][] buttons;
    private JPanel gamePanel;

    public FindTheButtonGUI() {
        setTitle("Find the Button Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        JButton playButton = new JButton("Play");
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame(5);
            }
        });

        JButton sandboxButton = new JButton("Sandbox");
        sandboxButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startSandbox();
            }
        });

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        add(playButton);
        add(sandboxButton);
        add(exitButton);

        setSize(300, 100);
        setVisible(true);
    }

    private void startGame(int levels) {
        currentLevel = 1;
        gridSize = currentLevel + 1;
        generateButtonLocation();

        setTitle("Find the Button Game - Level " + currentLevel);
        setupGamePanel(false);

        getContentPane().removeAll();
        setContentPane(gamePanel);
        setSize(300, 300);
        setVisible(true);
    }

    private void startSandbox() {
        currentLevel = 5;
        gridSize = currentLevel + 1;
        generateButtonLocation();

        setTitle("Find the Button Game - Sandbox");
        setupGamePanel(true);

        getContentPane().removeAll();
        setContentPane(gamePanel);
        setSize(300, 300);
        setVisible(true);
    }

    private void setupGamePanel(boolean showAllButtons) {
        gamePanel = new JPanel(new GridLayout(gridSize, gridSize));
        buttons = new JButton[gridSize][gridSize];

        generateButtonLocation();

        for (int i = 0; i < gridSize * gridSize; i++) {
            int row = i / gridSize;
            int col = i % gridSize;

            JButton button = new JButton();
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JButton clickedButton = (JButton) e.getSource();
                    int clickedRow = -1, clickedCol = -1;
                    for (int r = 0; r < gridSize; r++) {
                        for (int c = 0; c < gridSize; c++) {
                            if (buttons[r][c] == clickedButton) {
                                clickedRow = r;
                                clickedCol = c;
                                break;
                            }
                        }
                    }
                    if (clickedRow == buttonRow && clickedCol == buttonCol) {
                        JOptionPane.showMessageDialog(null, "Congratulations! You found the button!");
                        if (!showAllButtons) {
                            if (currentLevel < 5) {
                                currentLevel++;
                                gridSize++;
                                setTitle("Find the Button Game - Level " + currentLevel);
                                generateButtonLocation();
                                getContentPane().removeAll();
                                setupGamePanel(showAllButtons);
                                setContentPane(gamePanel);
                                setSize(300, 300);
                                setVisible(true);
                            } else {
                                JOptionPane.showMessageDialog(null, "You have completed all levels!");
                                dispose();
                            }
                        } else {
                            generateButtonLocation();
                            getContentPane().removeAll();
                            setupGamePanel(showAllButtons);
                            setContentPane(gamePanel);
                            setSize(300, 300);
                            setVisible(true);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Oops! Try again.");
                    }
                }
            });

            buttons[row][col] = button;
            gamePanel.add(button);
            if (showAllButtons || (row == buttonRow && col == buttonCol)) {
                button.setText(" ");
            } else {
                button.setText(" ");
            }
        }
    }

    private void generateButtonLocation() {
        Random random = new Random();
        buttonRow = random.nextInt(gridSize);
        buttonCol = random.nextInt(gridSize);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new FindTheButtonGUI();
            }
        });
    }
}
