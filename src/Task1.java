import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;

public class Task1 extends JFrame {
    private int pointX = 50; // Початкова позиція точки по X
    private int pointY; // Позиція точки по Y
    private int speed = 2;    // Початкова швидкість
    private final Timer timer;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Task1());
    }

    private final JPanel drawingPanel = new JPanel() {
        @Override
        protected void paintComponent(Graphics g) {
            pointY = (getHeight() / 2) - 10;
            super.paintComponent(g);
            g.setColor(Color.RED);
            g.fillOval(pointX, pointY, 20, 20); // Малюємо точку
        }
    };
    
    public Task1() {
        // Base frame
        setTitle("THE BEST PROGRAM EVER WRITTEN");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel
        JPanel controlPanel = new JPanel();
        JButton increaseSpeedButton = new JButton("Збільшити швидкість");
        JButton decreaseSpeedButton = new JButton("Зменшити швидкість");

        controlPanel.add(increaseSpeedButton);
        controlPanel.add(decreaseSpeedButton);
        add(controlPanel, BorderLayout.SOUTH);
        add(drawingPanel, BorderLayout.CENTER);

        // Timer
        timer = new Timer(30, (ActionEvent e) -> movePoint());
        timer.start();

        // Listeners
        increaseSpeedButton.addActionListener((ActionEvent e) -> speed += 1);
        decreaseSpeedButton.addActionListener((ActionEvent e) -> speed = Math.max(0, speed - 1));

        setVisible(true);
    }

    private void movePoint() {
        pointX = pointX + speed > getWidth() ? 0 : pointX + speed;
        repaint(); // Перемальовуємо фрейм
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.RED);
        g.fillOval(pointX, pointY, 20, 20); // Малюємо точку
    }
}
