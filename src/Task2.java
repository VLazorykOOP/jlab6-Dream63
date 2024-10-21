import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.util.Scanner;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

// Власне виключення
class InvalidMatrixException extends ArithmeticException {
    public InvalidMatrixException(String message) {
        super(message);
    }
}

public class Task2 extends JFrame {
    private JTextField filePathField; // Поле для введення шляху до файлу
    private final JTable matrixTable;       // Таблиця для відображення матриці
    private final JLabel resultLabel;       // Мітка для відображення результату

    public Task2() {
        // Base frame
        setTitle("SENCOND BEST PROGRAM EVER WRITTEN");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Base panel
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());

        JLabel filePathLabel = new JLabel("File path:");
        filePathField = new JTextField(20);
        JButton loadButton = new JButton("Load Matrix");

        inputPanel.add(filePathLabel);
        inputPanel.add(filePathField);
        inputPanel.add(loadButton);

        // Result panel
        JPanel resultPanel = new JPanel();
        resultLabel = new JLabel("Result: ");
        resultPanel.add(resultLabel);

        matrixTable = new JTable(new DefaultTableModel(0, 0));
        JScrollPane tableScrollPane = new JScrollPane(matrixTable);

        add(inputPanel, BorderLayout.NORTH);
        add(tableScrollPane, BorderLayout.CENTER);
        add(resultPanel, BorderLayout.SOUTH);

        // Event listener
        loadButton.addActionListener((ActionEvent e) -> {
            try {
                loadMatrixFromFile(filePathField.getText());
            } catch (FileNotFoundException ex) {
                JOptionPane.showMessageDialog(null, "File not found. Please check the file path.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Invalid matrix format. Please check the data.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (InvalidMatrixException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        setVisible(true);
    }

    private void loadMatrixFromFile(String filePath) throws FileNotFoundException, InvalidMatrixException {
        File file = new File(filePath);

        try (Scanner scanner = new Scanner(file)) {
            int n;
            try {
                n = Integer.parseInt(scanner.nextLine());
                if (n >= 20 || n <= 0) {
                    throw new InvalidMatrixException("Matrix size must be between 1 and 19.");
                }
            } 
            catch (NumberFormatException ex) {
                throw new NumberFormatException("Invalid matrix size format.");
            }

            double[][] matrix = new double[n][n];
            for (int i = 0; i < n; i++) {
                String[] row = scanner.nextLine().split("\\s+");
                
                if (row.length != n) {
                    throw new InvalidMatrixException("Invalid matrix format. All rows must have the same number of elements.");
                }
                for (int j = 0; j < n; j++) {
                    try {
                        matrix[i][j] = Double.parseDouble(row[j]);
                    } catch (NumberFormatException ex) {
                        throw new NumberFormatException("Matrix contains invalid numeric data.");
                    }
                }
            }

            updateTable(matrix, n);

            double result = calculateExpression(matrix, n);
            resultLabel.setText("Result: " + result);
        }
    }

    private void updateTable(double[][] matrix, int n) {
        DefaultTableModel model = new DefaultTableModel(n, n);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                model.setValueAt(matrix[i][j], i, j);
            }
        }
        matrixTable.setModel(model);
    }

    private double calculateExpression(double[][] matrix, int n) {
        double sum = 0;

        for (int i = 0; i < n; i++) {
            double minInRow = matrix[i][0];
            double maxInRow = matrix[i][0];

            for (int j = 1; j < n; j++) {
                if (matrix[i][j] < minInRow) {
                    minInRow = matrix[i][j];
                }
                if (matrix[i][j] > maxInRow) {
                    maxInRow = matrix[i][j];
                }
            }

            sum += minInRow * maxInRow;
        }

        return sum;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Task2());
    }
}
