import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Sierpinski extends JPanel implements KeyListener {
    JFrame frame;
    ArrayList<Point> points;
    int exitCond = 1;

    public Sierpinski() {
        frame = new JFrame();
        frame.add(this);
        frame.addKeyListener(this);
        frame.setSize(1000, 1000);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        points = new ArrayList<Point>();
        setup();
    }

    public void setup() {

        // creating six main points

        //center
        //points.add(new Point(500, 410, Color.black));

        //triangle 
        points.add(new Point(500, 300, Color.black));
        points.add(new Point(250, 600, Color.black));
        points.add(new Point(750, 600, Color.black));

        // r = 200

        int[] listX = { points.get(0).getX(), points.get(1).getX(), points.get(2).getX() };
        int[] listY = { points.get(0).getY(), points.get(1).getY(), points.get(2).getY() };
        // Polygon shape = new Polygon(listX, listY, 3);
        Polygon shape = new Polygon(listX, listY, 3);
        int ranX = 1;
        int ranY = 1;

        while (!shape.contains(ranX, ranY)) {
            ranX = (int) (Math.random() * frame.getWidth()) + 1;
            ranY = (int) (Math.random() * frame.getHeight()) + 1;

            if (shape.contains(ranX, ranY)) {
                points.add(new Point(ranX, ranY, Color.black));
            }
        }

    }

    public void sierpinski() {

        for (int i = 0; i < 5; i++) {
            Point point = points.get(points.size() - 1);

            int ran = (int) (Math.random() * 3);
            Point randomPoint = points.get(ran);

            int newX = (point.getX() + randomPoint.getX()) / 2;
            int newY = (point.getY() + randomPoint.getY()) / 2;

            points.add(new Point(newX, newY, Color.black));
            this.paintComponent(getGraphics());
        }
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        g.setColor(Color.cyan);
        g.fillRect(0, 0, frame.getWidth(), frame.getHeight());

        for (int i = 0; i < points.size(); i++) {
            g.setColor(Color.BLACK);
            g.fillOval(points.get(i).getX(), points.get(i).getY(), 10, 10);
        }

    }

    public class Point {
        int x, y;
        Color color;

        public Point(int x, int y, Color color) {
            this.x = x;
            this.y = y;
            this.color = color;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public Color getColor() {
            return color;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println(e.getKeyCode());

        if (e.getKeyCode() == 32) {
            sierpinski();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public static void main(String[] args) {
        Sierpinski app = new Sierpinski();
    }
}