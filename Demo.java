
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class ColoredShape {
    private final Shape shape;
    private final Color color;

    public ColoredShape(Shape shape, Color color) {
        this.shape = shape;
        this.color = color;
    }

    public Shape getShape() {
        return shape;
    }

    public Color getColor() {
        return color;
    }
}

class DrawingPanel extends JPanel {
    private final List<ColoredShape> coloredShapes = new ArrayList<>();

    DrawingPanel() {
        setPreferredSize(new Dimension(800, 600));
        setBackground(Color.BLACK);
    }

    void addColoredShape(ColoredShape coloredShape) {
        coloredShapes.add(coloredShape);
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        for (ColoredShape coloredShape : coloredShapes) {
            g2d.setColor(coloredShape.getColor());
            g2d.fill(coloredShape.getShape());
        }
    }
}

public class Demo {
    public static void main(String[] args) {
        DrawingPanel panel = new DrawingPanel();
        JFrame frame = new JFrame();
        frame.add(panel);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        Random rand = new Random();

        new Thread(() -> {
        	final int SH_SIZE = 50;
        	final int MOVE = 7;
        	
        	int[] val = new int[2];
        	val[0] = 0;		// x coordinate
        	val[1] = 0;		// y coordinate
        	boolean towardsRight = true;
        	boolean towardsBottom = true;
        	
            while (true) {
                try {
                    Thread.sleep(75);					// thread sleep!
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                
                // Draw shape
                SwingUtilities.invokeLater(() -> {
                    // Random position within panel bounds
                    int x = val[0];
                    int y = val[1];
                    // Random color
                    float r = rand.nextFloat();
                    float g = rand.nextFloat();
                    float b = rand.nextFloat();
                    panel.addColoredShape(new ColoredShape(new Rectangle2D.Double(x, y, SH_SIZE, SH_SIZE), new Color(r, g, b)));
                });
     
                if (towardsRight) {
                	val[0] = val[0] + MOVE;
                }
                else {
                	val[0] = val[0] - MOVE;
                }
                if (towardsBottom) {
                	val[1] = val[1] + MOVE;
                }
                else {
                	val[1] = val[1] - MOVE;
                }
                
                if (val[0] + SH_SIZE >= 800) {	// left-right bounds check
                	towardsRight = false;
                	val[0] = val[0] - 10;
                }
                else if (val[0] <= 0) {
                	towardsRight = true;
                }
                if (val[1] + SH_SIZE >= 600) {	// top-bottom bounds check
                	towardsBottom = false;
                	val[1] = val[1] - 10;
                }
                else if (val[1] <= 0) {
                	towardsBottom = true;
                }
            }
        }).start();
    }
}
