import javax.swing.*;
import javax.swing.plaf.FontUIResource;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class MazeProgram extends JPanel implements KeyListener {
    JFrame frame;
    String[][] maze;
    Hero hero;
    int endR, endC;
    ArrayList<Wall> walls;
    boolean in2D = true;
    boolean miniMap = false;

    public MazeProgram() {
        frame = new JFrame("program");
        frame.add(this);
        frame.setSize(1200, 800);

        setMaze();
        frame.addKeyListener(this);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(new Color(100, 0, 100));
        g2.fillRect(0, 0, frame.getWidth(), frame.getHeight());

        if (in2D) {
            miniMap = false;
            for (int i = 0; i < maze.length; i++) {
                for (int j = 0; j < maze[i].length; j++) {
                    if (maze[i][j].equals("*")) {
                        g2.setColor(Color.BLACK);
                        g2.fillRect(15 * j + 20, 15 * i + 20, 15, 15);

                    } else {
                        g2.setColor(new Color(100, 125, 125));
                        g2.drawRect(15 * j + 20, 15 * i + 20, 15, 15);
                    }
                }
            }
            g2.setColor(Color.WHITE);
            g2.fillOval(hero.getC() * 15 + 20, hero.getR() * 15 + 20, 15, 15);

        } else {
            g2.setColor(Color.WHITE);
            for (Wall wall : walls) {
                if (wall.getBreadCrumb() != null) {
                    g2.setColor(wall.getBreadCrumb());
                } else
                    g2.setPaint(wall.getPaint());

                g2.fillPolygon(wall.getPoly());
                g2.setColor(Color.BLACK);
                g2.drawPolygon(wall.getPoly());
            }

            if (miniMap) {
                int r = 0;
                int c = 0;
                int endR = hero.getR() + 5;
                int endC = hero.getC() + 5;
                try {
                    
                    for (int i = hero.getR(); i < hero.getR() + 5; i++) {   
                        c = 0;
                        for (int j = hero.getC(); j < hero.getC() + 5; j++) {
                            if (maze[i][j].equals("*")) {
                                g2.setColor(Color.BLACK);
                                g2.fillRect(15 * c + 825, 15 * r + 50, 15, 15);

                            } else {
                                g2.setColor(new Color(100, 125, 125));
                                g2.drawRect(15 * c + 826, 15 * r + 50, 15, 15);
                            }
                            c++;
                        }
                        r++;
                    }
                } catch (Exception e) {
                    for(int i = r; i < endR%5; i++){
                        for(int j = c; j < endC; j++){
                            g2.setColor(Color.BLACK);
                            g2.fillRect(15 * j + 825, 15 * i + 50, 15, 15);
                        }
                    }
                }
                g2.setColor(Color.PINK);
                g2.fillOval(826, 50, 15, 15);
            }

            g2.setFont(Font.getFont(Font.SANS_SERIF));
            g2.setColor(Color.GREEN);
            g2.scale(4, 4);
            g2.drawString(hero.getDir() + "", 250, 40);

        }

    }

    public void setMaze() {
        maze = new String[12][0];
        try {
            String text;
            BufferedReader reader = new BufferedReader(new FileReader("MazeProgram.txt"));
            boolean first = true;
            int r = 0;
            while ((text = reader.readLine()) != null) {
                if (first) {
                    String[] pieces = text.split(" ");
                    int row = Integer.parseInt(pieces[0]);
                    int col = Integer.parseInt(pieces[1]);
                    char dir = pieces[2].charAt(0);
                    endR = Integer.parseInt(pieces[3]);
                    endC = Integer.parseInt(pieces[4]);
                    hero = new Hero(row, col, dir);
                    first = false;
                } else {
                    String[] pieces = text.split("");
                    maze[r] = pieces;
                    r++;
                }
            }
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
                System.out.print(maze[i][j]);
            }
            System.out.println();
        }
    }

    public class Hero {
        int r;
        int c;
        char dir;

        public Hero(int r, int c, char dir) {
            this.r = r;
            this.c = c;
            this.dir = dir;
        }

        public int getR() {
            return r;
        }

        public int getC() {
            return c;
        }

        public char getDir() {
            return dir;
        }

        public void move(int key) {
            switch (key) {
                // right
                case 39:
                    switch (dir) {
                        case 'E':
                            dir = 'N';
                            break;
                        case 'N':
                            dir = 'W';
                            break;
                        case 'W':
                            dir = 'S';
                            break;
                        case 'S':
                            dir = 'E';
                            break;
                    }
                    break;

                // left
                case 37:
                    switch (dir) {
                        case 'E':
                            dir = 'S';
                            break;
                        case 'S':
                            dir = 'W';
                            break;
                        case 'W':
                            dir = 'N';
                            break;
                        case 'N':
                            dir = 'E';
                            break;
                    }
                    break;

                // forward
                case 38:
                    switch (dir) {
                        case 'E':
                            try {
                                if (!maze[r][c + 1].equals("*")) {
                                    c++;
                                }
                            } catch (ArrayIndexOutOfBoundsException e) {
                                e.printStackTrace();
                            }
                            break;

                        case 'W':
                            try {
                                if (!maze[r][c - 1].equals("*")) {
                                    c--;
                                }
                            } catch (ArrayIndexOutOfBoundsException e) {
                                e.printStackTrace();
                            }
                            break;

                        case 'S':
                            try {
                                if (!maze[r - 1][c].equals("*")) {
                                    r--;
                                }
                            } catch (ArrayIndexOutOfBoundsException e) {
                                e.printStackTrace();
                            }
                            break;

                        case 'N':
                            try {
                                if (!maze[r + 1][c].equals("*")) {
                                    r++;
                                }
                            } catch (ArrayIndexOutOfBoundsException e) {
                                e.printStackTrace();
                            }
                            break;
                    }
                    break;

                // backwards
                case 40:
                    switch (dir) {
                        case 'E':
                            try {
                                if (!maze[r][c - 1].equals("*")) {
                                    c--;
                                }
                            } catch (ArrayIndexOutOfBoundsException e) {
                                e.printStackTrace();
                            }
                            break;

                        case 'W':
                            try {
                                if (!maze[r][c + 1].equals("*")) {
                                    c++;
                                }
                            } catch (ArrayIndexOutOfBoundsException e) {
                                e.printStackTrace();
                            }
                            break;

                        case 'S':
                            try {
                                if (!maze[r + 1][c].equals("*")) {
                                    r++;
                                }
                            } catch (ArrayIndexOutOfBoundsException e) {
                                e.printStackTrace();
                            }
                            break;

                        case 'N':
                            try {
                                if (!maze[r - 1][c].equals("*")) {
                                    r--;
                                }
                            } catch (ArrayIndexOutOfBoundsException e) {
                                e.printStackTrace();
                            }
                            break;
                    }

                    break;
            }
        }
    }

    public void keyTyped(KeyEvent e) {

    }

    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == 32) {
            in2D = !in2D;
        }

        hero.move(e.getKeyCode());

        if (!in2D) {
            set3D();
        }

        if (!in2D && e.getKeyCode() == 77) {
            miniMap = true;
        }

        repaint();
    }

    public void keyReleased(KeyEvent e) {
        if (!in2D && e.getKeyCode() == 77) {
            miniMap = false;
        }
        repaint();
    }

    public void set3D() {
        walls = new ArrayList<Wall>();

        for (int i = 0; i < 5; i++) {

            // og trap
            leftWall(i);
            rightWall(i);

            // og rects
            int[] x = { 100 + i * 50, 150 + i * 50, 150 + i * 50, 100 + i * 50 };
            int[] y = { 100 + i * 50, 100 + i * 50, 500 - i * 50, 500 - i * 50 };
            walls.add(new Wall(x, y, 255 - 50 * i, 255 - 50 * i, 255 - 50 * i, "leftWall", i, null));

            int[] xR = { 900 - i * 50, 850 - i * 50, 850 - i * 50, 900 - i * 50 };
            int[] yR = { 100 + i * 50, 100 + i * 50, 500 - i * 50, 500 - i * 50 };
            walls.add(new Wall(xR, yR, 255 - 50 * i, 255 - 50 * i, 255 - 50 * i, "rightWall", i, null));

        }

        for (int i = 4; i >= 0; i--) {
            int r = hero.getR();
            int c = hero.getC();

            switch (hero.getDir()) {
                case 'E':
                    try {
                        if (maze[r + 1][c + i].equals("*"))
                            rightWall(i);

                        if (maze[r - 1][c + i].equals("*"))
                            leftWall(i);

                    } catch (ArrayIndexOutOfBoundsException e) {
                        e.printStackTrace();
                    }
                    break;

                case 'W':
                    try {
                        if (maze[r + 1][c - i].equals("*"))
                            leftWall(i);

                        if (maze[r - 1][c - i].equals("*"))
                            rightWall(i);

                    } catch (ArrayIndexOutOfBoundsException e) {
                        e.printStackTrace();

                    }
                    break;

                case 'S':
                    try {
                        if (maze[r - i][c - 1].equals("*"))
                            leftWall(i);

                        if (maze[r - i][c + 1].equals("*"))
                            rightWall(i);

                    } catch (ArrayIndexOutOfBoundsException e) {
                        e.printStackTrace();

                    }
                    break;

                case 'N':
                    try {
                        if (maze[r + i][c + 1].equals("*"))
                            leftWall(i);

                        if (maze[r + i][c - 1].equals("*"))
                            rightWall(i);

                    } catch (ArrayIndexOutOfBoundsException e) {
                        e.printStackTrace();
                    }
                    break;
            }

            for (int j = 4; j >= 0; j--) {
                ceiling(j);
                floor(j);
                switch (hero.getDir()) {
                    case 'E':
                        try {
                            if (maze[r][c + j].equals("*")) {
                                frontWall(j - 1);
                            }

                        } catch (ArrayIndexOutOfBoundsException e) {
                            e.printStackTrace();
                            if (c + j >= maze[0].length) {
                                frontWall(j - 1);
                            }
                        }
                        break;

                    case 'W':
                        try {
                            if (maze[r][c - j].equals("*"))
                                frontWall(j - 1);

                        } catch (ArrayIndexOutOfBoundsException e) {
                            e.printStackTrace();
                            if (c - j < 0) {
                                frontWall(j - 1);
                            }

                        }
                        break;

                    case 'S':
                        try {
                            if (maze[r - j][c].equals("*"))
                                frontWall(j - 1);

                        } catch (ArrayIndexOutOfBoundsException e) {
                            e.printStackTrace();
                            if (r - j < 0) {
                                frontWall(j - 1);
                            }

                        }
                        break;

                    case 'N':
                        try {
                            if (maze[r + j][c].equals("*"))
                                frontWall(j - 1);

                        } catch (ArrayIndexOutOfBoundsException e) {
                            e.printStackTrace();
                            if (r + j >= maze.length) {
                                frontWall(j - 1);
                            }
                        }
                        break;
                }
            }
        }
    }

    public void leftWall(int i) {
        int[] x = { 100 + i * 50, 150 + i * 50, 150 + i * 50, 100 + i * 50 };
        int[] y = { 50 + i * 50, 100 + i * 50, 500 - i * 50, 550 - i * 50 };
        walls.add(new Wall(x, y, 255 - 50 * i, 255 - 50 * i, 255 - 50 * i, "leftWall", i, null));
    }

    public void rightWall(int i) {
        int[] x = { 900 - i * 50, 850 - i * 50, 850 - i * 50, 900 - i * 50 };
        int[] y = { 50 + i * 50, 100 + i * 50, 500 - i * 50, 550 - i * 50 };
        walls.add(new Wall(x, y, 255 - 50 * i, 255 - 50 * i, 255 - 50 * i, "rightWall", i, null));
    }

    public void frontWall(int i) {
        int[] x = { 150 + i * 50, 850 - i * 50, 850 - i * 50, 150 + i * 50 };
        int[] y = { 100 + i * 50, 100 + i * 50, 500 - i * 50, 500 - i * 50 };
        walls.add(new Wall(x, y, 255 - 50 * i, 255 - 50 * i, 255 - 50 * i, "frontWall", i, null));
    }

    public void ceiling(int i) {
        int[] x = { 100 + i * 50, 900 - i * 50, 850 - i * 50, 150 + i * 50 };
        int[] y = { 50 + i * 50, 50 + i * 50, 100 + i * 50, 100 + i * 50 };
        walls.add(new Wall(x, y, 255 - 50 * i, 255 - 50 * i, 255 - 50 * i, "ceiling", i, null));
    }

    public void floor(int i) {
        int[] x = { 100 + i * 50, 900 - i * 50, 850 - i * 50, 150 + i * 50 };
        int[] y = { 550 - i * 50, 550 - i * 50, 500 - i * 50, 500 - i * 50 };
        walls.add(new Wall(x, y, 255 - 50 * i, 255 - 50 * i, 255 - 50 * i, "floor", i, null));
    }

    public class Wall {
        int[] x, y;
        int r, g, b;
        String type;
        boolean[][] visited;
        int dist;
        Color color;

        public Wall(int[] x, int[] y, int r, int g, int b, String type, int dist, Color color) {
            this.x = x;
            this.y = y;
            this.r = r;
            this.g = g;
            this.b = b;
            this.type = type;
            this.dist = dist;
            this.color = color;
        }

        public Color getBreadCrumb() {
            return color;
        }

        public void setColor(Color color) {
            this.color = color;
        }

        public Polygon getPoly() {
            return new Polygon(x, y, 4);
        }

        public GradientPaint getPaint() {
            int endR = r - 50;
            int endG = g - 50;
            int endB = b - 50;

            if (r < 0)
                r = 0;
            if (g < 0)
                g = 0;
            if (b < 0)
                b = 0;
            if (endR < 0)
                endR = 0;
            if (endG < 0)
                endG = 0;
            if (endB < 0)
                endB = 0;

            Color startColor = new Color(r, g, b);
            Color endColor = new Color(endR, endG, endB);

            switch (type) {
                case "rightWall":
                case "leftWall":
                    return new GradientPaint(x[0], y[0], startColor, x[1], y[0], endColor);
            }

            return new GradientPaint(x[0], y[0], startColor, x[0], y[2], endColor);
        }

        public Color getColor() {
            return new Color(r, g, b);
        }
    }

    public static void main(String[] args) {
        MazeProgram app = new MazeProgram();
    }
}
