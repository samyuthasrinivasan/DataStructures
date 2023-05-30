import java.util.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.plaf.FontUIResource;
import java.awt.event.*;
import java.awt.*;

public class GUITask extends JFrame implements ActionListener {
    JPanel panel;
    JButton north, south, east, west, reset;
    JMenuBar menuBar, directionsBar;
    JMenu font, size, textColor, textAreaBackgroundColor, buttonOutlineColor;
    JTextArea textArea;
    JMenuItem font1, font2, font3, size1, size2, size3, textColor1, textColor2, textColor3, textBackColor1, textBackColor2, textBackColor3, borderColor1, borderColor2, borderColor3;
    String currentFont;
    int currentSize;
    Color currentTextColor, currentBackColor, currentBorderColor;

    public GUITask() {
        panel = new JPanel();
        panel.setLayout(new GridLayout(1, 2));
        directionsBar = new JMenuBar();
        directionsBar.setLayout(new GridLayout(1, 4));

        currentFont = "Arial";
        currentSize = 30;
        currentTextColor = Color.BLACK;
        currentBackColor = Color.WHITE;
        currentBorderColor = Color.WHITE;

        north = new JButton("North");
        north.addActionListener(this);
        south = new JButton("South");
        south.addActionListener(this);
        east = new JButton("East");
        east.addActionListener(this);
        west = new JButton("West");
        west.addActionListener(this);

        north.setBorder(new LineBorder(currentBorderColor, 5));
        west.setBorder(new LineBorder(currentBorderColor, 5));
        south.setBorder(new LineBorder(currentBorderColor, 5));
        east.setBorder(new LineBorder(currentBorderColor, 5));

        directionsBar.add(north);
        directionsBar.add(east);
        directionsBar.add(west);
        directionsBar.add(south);

        menuBar = new JMenuBar();

        font = new JMenu("Font");
        font1 = new JMenuItem("Chalkboard");
        font1.setFont(new Font("Chalkboard", Font.BOLD, 30));
        font1.addActionListener(this);
        font2 = new JMenuItem("Sana");
        font2.setFont(new Font("Sana", Font.BOLD, 30));
        font2.addActionListener(this);
        font3 = new JMenuItem("Rockwell");
        font3.setFont(new Font("Rockwell", Font.BOLD, 30));
        font3.addActionListener(this);

        font.add(font1);
        font.add(font2);
        font.add(font3);

        size = new JMenu("Text Size");
        size1 = new JMenuItem("30");
        size1.setFont(new Font("Arial", Font.BOLD, 30));
        size1.addActionListener(this);
        size2 = new JMenuItem("60");
        size2.setFont(new Font("Arial", Font.BOLD, 60));
        size2.addActionListener(this);
        size3 = new JMenuItem("90");
        size3.setFont(new Font("Arial", Font.BOLD, 90));
        size3.addActionListener(this);

        size.add(size1);
        size.add(size2);
        size.add(size3);

        textColor = new JMenu("Text Color");
        textColor1 = new JMenuItem("MAGENTA");
        textColor1.setForeground(Color.MAGENTA);
        textColor1.setFont(new Font("Arial", Font.BOLD, 30));
        textColor1.addActionListener(this);
        textColor2 = new JMenuItem("RED");
        textColor2.setForeground(Color.RED);
        textColor2.setFont(new Font("Arial", Font.BOLD, 30));
        textColor2.addActionListener(this);
        textColor3 = new JMenuItem("RANDOM");
        textColor3.setForeground(Color.BLACK);
        textColor3.setFont(new Font("Arial", Font.BOLD, 30));
        textColor3.addActionListener(this);

        textColor.add(textColor1);
        textColor.add(textColor2);
        textColor.add(textColor3);

        textAreaBackgroundColor = new JMenu("BG Color");
        textBackColor1 = new JMenuItem("CYAN");
        textBackColor1.setBackground(Color.CYAN);
        textBackColor1.setFont(new Font("Arial", Font.BOLD, 30));
        textBackColor1.addActionListener(this);
        textBackColor2 = new JMenuItem("ORANGE");
        textBackColor2.setBackground(Color.ORANGE);
        textBackColor2.setFont(new Font("Arial", Font.BOLD, 30));
        textBackColor2.addActionListener(this);
        textBackColor3 = new JMenuItem("RANDOM");
        textBackColor3.setBackground(Color.WHITE);
        textBackColor3.setFont(new Font("Arial", Font.BOLD, 30));
        textBackColor3.addActionListener(this);

        textAreaBackgroundColor.add(textBackColor1);
        textAreaBackgroundColor.add(textBackColor2);
        textAreaBackgroundColor.add(textBackColor3);


        buttonOutlineColor = new JMenu("Bord Color");
        borderColor1 = new JMenuItem("GREEN");
        borderColor1.setBorder(new LineBorder(Color.GREEN));
        borderColor1.setFont(new Font("Arial", Font.BOLD, 30));
        borderColor1.addActionListener(this);
        borderColor2 = new JMenuItem("YELLOW");
        borderColor2.setBorder(new LineBorder(Color.yellow));
        borderColor2.setFont(new Font("Arial", Font.BOLD, 30));
        borderColor2.addActionListener(this);
        borderColor3 = new JMenuItem("RANDOM");
        borderColor3.setBorder(new LineBorder(Color.WHITE));
        borderColor3.setFont(new Font("Arial", Font.BOLD, 30));
        borderColor3.addActionListener(this);

        buttonOutlineColor.add(borderColor1);
        buttonOutlineColor.add(borderColor2);
        buttonOutlineColor.add(borderColor3);

        textArea = new JTextArea("Type Stuff Here!");
        textArea.setFont(new Font(currentFont, Font.BOLD, currentSize));

        reset = new JButton("Reset");
        reset.addActionListener(this);
        reset.setBorder(new LineBorder(currentBorderColor, 5));

        menuBar.setLayout(new GridLayout(1, 6));
        menuBar.add(font);
        menuBar.add(size);
        menuBar.add(textColor);
        menuBar.add(textAreaBackgroundColor);
        menuBar.add(buttonOutlineColor);
        menuBar.add(reset);

        panel.add(directionsBar);
        panel.add(menuBar);

        this.add(panel, BorderLayout.NORTH);
        this.add(textArea);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setSize(1000, 1000);

    }

    public static void main(String[] args) {
        GUITask app = new GUITask();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == north) {
            panel.setLayout(new GridLayout(1, 2));
            directionsBar.setLayout(new GridLayout(1, 4));
            menuBar.setLayout(new GridLayout(1, 6));
            this.add(panel, BorderLayout.NORTH);

        }

        if (e.getSource() == south) {
            panel.setLayout(new GridLayout(1, 2));
            directionsBar.setLayout(new GridLayout(1, 4));
            menuBar.setLayout(new GridLayout(1, 6));
            this.add(panel, BorderLayout.SOUTH);

        }

        if (e.getSource() == east) {
            panel.setLayout(new GridLayout(2, 1));
            directionsBar.setLayout(new GridLayout(4, 1));
            menuBar.setLayout(new GridLayout(6, 1));
            this.add(panel, BorderLayout.EAST);

        }

        if (e.getSource() == west) {
            panel.setLayout(new GridLayout(2, 1));
            directionsBar.setLayout(new GridLayout(4, 1));
            menuBar.setLayout(new GridLayout(6, 1));
            this.add(panel, BorderLayout.WEST);
        }

        if(e.getSource() == reset) {
            currentSize = 30;
            currentFont = "Arial";
            currentTextColor = Color.BLACK;
            currentBackColor = Color.WHITE;
            currentBorderColor = Color.WHITE;
            panel.setLayout(new GridLayout(1, 2));
            directionsBar.setLayout(new GridLayout(1, 4));
            menuBar.setLayout(new GridLayout(1, 6));
            this.add(panel, BorderLayout.NORTH);
        }

        if (e.getSource() == font1) {
            currentFont = "Chalkboard";
        }
        if (e.getSource() == font2) {
            currentFont = "Sana";
        }
        if (e.getSource() == font3) {
            currentFont = "Rockwell";
        }

        if (e.getSource() == size1) {
            currentSize = 30;
        }
        if (e.getSource() == size2) {
            currentSize = 60;
        }
        if (e.getSource() == size3) {
            currentSize = 90;
        }

        if(e.getSource() == textColor1) {
            currentTextColor = Color.MAGENTA;
        }

        if(e.getSource() == textColor2) {
            currentTextColor = Color.RED;
        }

        if(e.getSource() == textColor3) {
            currentTextColor = new Color((int)(Math.random()*256), (int)(Math.random()*256), (int)(Math.random()*256));
        }

        if(e.getSource() == textBackColor1) {
            currentBackColor = Color.CYAN;
        }

        if(e.getSource() == textBackColor2) {
            currentBackColor = Color.ORANGE;
        }

        if(e.getSource() == textBackColor3) {
            currentBackColor = new Color((int)(Math.random()*256), (int)(Math.random()*256), (int)(Math.random()*256));
        }

        if(e.getSource() == borderColor1) {
            currentBorderColor = Color.GREEN;
        }

        if(e.getSource() == borderColor2) {
            currentBorderColor = Color.YELLOW;
        }
        
        if(e.getSource() == borderColor3) {
            currentBorderColor = new Color((int)(Math.random()*256), (int)(Math.random()*256), (int)(Math.random()*256));
        }

        textArea.setFont(new Font(currentFont, Font.BOLD, currentSize));
        textArea.setForeground(currentTextColor);
        textArea.setBackground(currentBackColor);
        north.setBorder(new LineBorder(currentBorderColor, 5));
        west.setBorder(new LineBorder(currentBorderColor, 5));
        south.setBorder(new LineBorder(currentBorderColor, 5));
        east.setBorder(new LineBorder(currentBorderColor, 5));
        reset.setBorder(new LineBorder(currentBorderColor, 5));
        
        this.revalidate();
    }
}