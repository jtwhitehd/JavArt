import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.util.Random;

class ArtFrame extends JFrame {
    Random random;
    Shape[] shapes;
    float[] dx, dy;

    ArtFrame() {
        super("Art Project");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        random = new Random();
        int numShapes = random.nextInt(3) + 1;  // 1-3 shapes
        shapes = new Shape[numShapes];
        dx = new float[numShapes];
        dy = new float[numShapes];

        for (int i = 0; i < numShapes; i++) {
            int x = random.nextInt(getWidth());
            int y = random.nextInt(getHeight());
            int w = random.nextInt(100) + 50;  // Width: 50-150
            int h = random.nextInt(100) + 50;  // Height: 50-150
            shapes[i] = new Ellipse2D.Float(x, y, w, h);
            dx[i] = random.nextFloat() * 4 - 2;  // Speed: -2 to 2
            dy[i] = random.nextFloat() * 4 - 2;  // Speed: -2 to 2
        }

        new Timer(20, e -> {
            for (int i = 0; i < numShapes; i++) {
                Rectangle bounds = shapes[i].getBounds();
                if (bounds.x + dx[i] < 0 || bounds.x + bounds.width + dx[i] > getWidth()) dx[i] *= -1;
                if (bounds.y + dy[i] < 0 || bounds.y + bounds.height + dy[i] > getHeight()) dy[i] *= -1;
                AffineTransform at = new AffineTransform();
                at.translate(dx[i], dy[i]);
                shapes[i] = at.createTransformedShape(shapes[i]);
            }
            repaint();
        }).start();
    }

    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.BLACK);
        for (Shape shape : shapes) g2.draw(shape);
    }
}

public class ArtProject {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ArtFrame frame = new ArtFrame();
            frame.setVisible(true);
        });
    }
}
