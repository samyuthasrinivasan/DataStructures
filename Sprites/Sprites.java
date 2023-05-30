import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

public class Sprites extends JPanel implements Runnable {
    BufferedImage[] images;
    JFrame frame;
    BufferedImage imageSheet;
    int index = 0;
    Thread timing;

    public Sprites() {
        frame = new JFrame();
        images = new BufferedImage[6];

        try {
            imageSheet = ImageIO.read(new File("spritesheet.jpg"));
            imageSheet = resize(imageSheet, 1200, 800);

            int x1 = 50;
            int y1 = 100;
            int width = 350;
            int height = 300;
            int i = 0;
            int j = 0;
            int k = 0;
            images[0] = imageSheet.getSubimage(50, 100, 350, 300);

            for (int y = 100; y < imageSheet.getHeight(); y += height + 50) {
                if (k == 0) {
                    x1 = 50;
                    y1 = 20;
                    k++;
                } else {
                    x1 = 45;
                    y1 = 25;
                }

                for (int x = x1; x < imageSheet.getWidth(); x += width + y1) {
                    images[i] = imageSheet.getSubimage(x, y, width, height);
                    i++;
                    j++;
                    if (i == 6) {
                        y = imageSheet.getHeight();
                        x = imageSheet.getWidth();

                    }

                    if (j == 3) {
                        x = imageSheet.getWidth();
                        j = 0;
                    }

                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        frame.add(this);
        frame.setSize(350, 330);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        timing = new Thread(this);
        timing.start();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, frame.getWidth(), frame.getHeight());
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // g2.drawImage(imageSheet, imageSheet.getMinX(), imageSheet.getMinY(),
        // imageSheet.getWidth(), imageSheet.getHeight(), frame);
        g2.drawImage(images[index], images[index].getMinX(), images[index].getMinY(), images[index].getWidth(),
                images[index].getHeight(), frame);
    }

    @Override
    public void run() {
        while (true) {
            try {
                for(int i = 0; i < images.length; i ++) {
                    play(i);
                    frame.repaint();
                    timing.sleep(100);   
                }
                timing.sleep(0);
            } catch (InterruptedException e) {
                System.out.println("well thats not good");
            }
        }

    }

    public void play(int num) {
        index = num;
    }

    public BufferedImage resize(BufferedImage image, int width, int height) {
        // Images can be scaled but BufferedImages cannot
        Image temp = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        // Turn the BufferedImage into Image so that it can be scaled
        BufferedImage scaledVersion = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        // Scaled the Image and then casted it into a BufferedImage
        Graphics2D g2 = scaledVersion.createGraphics();
        // render it and then return the image
        g2.drawImage(temp, 0, 0, null);
        g2.dispose();
        return scaledVersion;
    }

    public static void main(String[] args) {
        Sprites s = new Sprites();
    }
}