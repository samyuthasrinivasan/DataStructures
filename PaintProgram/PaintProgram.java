import java.awt.event.*;
import java.awt.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.geom.*;
import java.awt.image.*;

public class PaintProgram extends JPanel implements MouseListener,MouseMotionListener, ActionListener,AdjustmentListener,ChangeListener
{
	JFrame frame;
	ArrayList<Point> points;
	
	JMenuBar bar;
	JMenu colorMenu,file;
	JMenuItem[] colorOptions;
	Color[] colors;
	JColorChooser colorChooser;
	int penWidth=2;
	Color currColor = Color.MAGENTA, backgroundColor = Color.WHITE;
	JScrollBar penWidthBar;
	JMenuItem clear, exit, load, save;
	ImageIcon saveImg, loadImg, rectImg, ovalImg, triImg, eraserImg, freeLineImg, lineImg, undoImg, redoImg;
	JFileChooser fileChooser;
	BufferedImage loadedImage;

	Stack<Object> shapes, undoShapes;
	Shape currShape;
	boolean drawingFreeLine = true, drawingRectangle = false, drawingStraightLine = false, drawingTriangle = false, drawingOval = false, erasing = false, firstClick = true;

	int initX, initY;
	JButton lineButton, rectButton, ovalButton, triButton, eraserButton, freeLineButton, undoButton, redoButton;

	public PaintProgram() {
		try {
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
            // UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
        } catch (Exception e) {
        }

		UIManager.put("JMenuItem.opaque", true);
		bar = new JMenuBar();

		file = new JMenu("File");
		clear = new JMenuItem("Clear");
		exit = new JMenuItem("Exit");
		load = new JMenuItem("Load", KeyEvent.VK_L);
		save = new JMenuItem("Save", KeyEvent.VK_S);

		load.addActionListener(this);
		clear.addActionListener(this);
		exit.addActionListener(this);
		save.addActionListener(this);

		save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		load.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.CTRL_MASK));
		saveImg = new ImageIcon("saveImg.png");
		saveImg = new ImageIcon(saveImg.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
		loadImg = new ImageIcon("loadImg.png");
		loadImg = new ImageIcon(loadImg.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
		save.setIcon(saveImg);
		load.setIcon(loadImg);

		file.add(clear);
		file.add(load);
		file.add(save);
		file.add(exit);

		bar.add(file);
		
		colorMenu = new JMenu("Color Options");
		colors = new Color[]{Color.RED,Color.ORANGE,Color.YELLOW,
							Color.GREEN,Color.BLUE,
							new Color(75, 0, 130),
							new Color(143, 0, 255)};
		colorOptions = new JMenuItem[colors.length];
		colorMenu.setLayout(new GridLayout(colors.length,1));
		for(int w = 0; w < colors.length; w++){
			colorOptions[w]=new JMenuItem();
			colorOptions[w].putClientProperty("colorIndex",w);
			colorOptions[w].setOpaque(true);
			colorOptions[w].setBackground(colors[w]);
			colorOptions[w].setPreferredSize(new Dimension(50,30));
			colorOptions[w].addActionListener(this);
			colorMenu.add(colorOptions[w]);
		}

		bar.add(colorMenu);
		currColor = colors[0];
		frame = new JFrame("The Best Paint Program Ever Constructed - by me....");
		frame.add(this);

		undoButton = new JButton();
		undoImg = new ImageIcon("undoImg.png");
		undoImg = new ImageIcon(undoImg.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
		undoButton.setIcon(undoImg);
		undoButton.addActionListener(this);
		undoButton.setFocusable(false);
		bar.add(undoButton);

		redoButton = new JButton();
		redoImg = new ImageIcon("redoImg.png");
		redoImg = new ImageIcon(redoImg.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
		redoButton.setIcon(redoImg);
		redoButton.addActionListener(this);
		redoButton.setFocusable(false);
		bar.add(redoButton);

		freeLineButton = new JButton();
		freeLineImg = new ImageIcon("freeLineImg.png");
		freeLineImg = new ImageIcon(freeLineImg.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
		freeLineButton.setIcon(freeLineImg);
		freeLineButton.addActionListener(this);
		freeLineButton.setFocusable(false);
		bar.add(freeLineButton);

		lineButton = new JButton();
		lineImg = new ImageIcon("lineImg.png");
		lineImg = new ImageIcon(lineImg.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
		lineButton.setIcon(lineImg);
		lineButton.addActionListener(this);
		lineButton.setFocusable(false);
		bar.add(lineButton);

		rectButton = new JButton();
		rectImg = new ImageIcon("rectImg.png");
		rectImg = new ImageIcon(rectImg.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
		rectButton.setIcon(rectImg);
		rectButton.addActionListener(this);
		rectButton.setFocusable(false);
		bar.add(rectButton);

		ovalButton = new JButton();
		ovalImg = new ImageIcon("ovalImg.png");
		ovalImg = new ImageIcon(ovalImg.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
		ovalButton.setIcon(ovalImg);
		ovalButton.addActionListener(this);
		ovalButton.setFocusable(false);
		bar.add(ovalButton);

		triButton = new JButton();
		triImg = new ImageIcon("triImg.png");
		triImg = new ImageIcon(triImg.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
		triButton.setIcon(triImg);
		triButton.addActionListener(this);
		triButton.setFocusable(false);
		bar.add(triButton);

		eraserButton = new JButton();
		eraserImg = new ImageIcon("eraserImg.png");
		eraserImg = new ImageIcon(eraserImg.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
		eraserButton.setIcon(eraserImg);
		eraserButton.addActionListener(this);
		eraserButton.setFocusable(false);
		bar.add(eraserButton);

		penWidthBar = new JScrollBar(JScrollBar.HORIZONTAL, 1, 0 , 1, 100);
		penWidthBar.addAdjustmentListener(this);
		penWidth = penWidthBar.getValue();
		bar.add(penWidthBar);

		colorChooser=new JColorChooser();
		colorChooser.getSelectionModel().addChangeListener(this);
		colorMenu.add(colorChooser);
		currColor=Color.MAGENTA;

		frame.add(bar,BorderLayout.NORTH);
		frame.setSize(1400,800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		points = new ArrayList<Point>();
		shapes = new Stack<Object>();
		undoShapes = new Stack<Object>();

		this.addMouseListener(this);
		this.addMouseMotionListener(this);

		String currDir = System.getProperty("user.dir");
		fileChooser = new JFileChooser(currDir);
		frame.setVisible(true);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.WHITE);
		g.fillRect(0,0,frame.getWidth(),frame.getHeight());
		Graphics2D g2 = (Graphics2D)g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		if(shapes.size()>0) {
			Iterator it = shapes.iterator();
			while(it.hasNext()){
				Object shape = it.next();
				if(shape instanceof BufferedImage) {
					g.drawImage(loadedImage, 0, 0, this);
				} else if(shape instanceof ArrayList<?>){
					ArrayList<Point> p = (ArrayList<Point>)shape;
					Stroke stroke=new BasicStroke(p.get(0).getPenWidth(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
					g2.setStroke(stroke);
					g2.setColor(p.get(0).getColor());
					for(int a = 0; a < p.size() - 1; a++) {
						Point p1 = p.get(a);
						Point p2 = p.get(a+1);
						g2.drawLine(p1.getX(), p1.getY(), p2.getX(), p2.getY());
					}
				} else if (shape instanceof Rectangle) {
					Rectangle rect = (Rectangle)shape;
					g2.setStroke(new BasicStroke(rect.getPenWidth(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
					//g2.setStroke(new BasicStroke(rect.getPenWidth(), BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER));
					g.setColor(rect.getColor());
					g2.draw(rect.getShape());
				} else if(shape instanceof Oval) {
					Oval oval = (Oval) shape;
					g2.setStroke(new BasicStroke(oval.getPenWidth(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
					g.setColor(oval.getColor());
					g2.draw(oval.getShape());
				} else if(shape instanceof Triangle) {
					Triangle triangle = (Triangle) shape;
					g2.setStroke(new BasicStroke(triangle.getPenWidth(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
					g.setColor(triangle.getColor());
					g2.draw(triangle.getShape());
				} else if(shape instanceof StraightLine) {
					StraightLine line = (StraightLine) shape;
					g2.setStroke(new BasicStroke(line.getPenWidth(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
					g.setColor(line.getColor());
					g2.draw(line.getShape());
				}
			}
		}	
		if(drawingFreeLine && points.size() > 0){
			g2.setStroke(
				new BasicStroke(points.get(0).getPenWidth(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
			g.setColor(points.get(0).getColor());
			for(int a = 0; a < points.size() - 1; a++) {
				Point p1 = points.get(a);
				Point p2 = points.get(a+1);
				g2.drawLine(p1.getX(),p1.getY(),p2.getX(),p2.getY());
			}
		}
	}

	public void actionPerformed(ActionEvent e){
		if(e.getSource() == eraserButton) {
			erasing = true;
			drawingFreeLine = true;
			drawingOval = false;
			drawingRectangle = false;
			drawingTriangle = false;
			drawingStraightLine = false;
		} else if(e.getSource() == rectButton) {
			erasing = false;
			drawingFreeLine = false;
			drawingOval = false;
			drawingRectangle = true;
			drawingTriangle = false;
			drawingStraightLine = false;
		} else if(e.getSource() == ovalButton) {
			erasing = false;
			drawingFreeLine = false;
			drawingOval = true;
			drawingRectangle = false;
			drawingTriangle = false;
			drawingStraightLine = false;
		} else if(e.getSource() == freeLineButton) {
			erasing = false;
			drawingFreeLine = true;
			drawingOval = false;
			drawingRectangle = false;
			drawingTriangle = false;
			drawingStraightLine = false;
		} else if(e.getSource() == triButton) {
			erasing = false;
			drawingFreeLine = false;
			drawingOval = false;
			drawingRectangle = false;
			drawingTriangle = true;
			drawingStraightLine = false;
		} else if(e.getSource() == lineButton) {
			erasing = false;
			drawingFreeLine = false;
			drawingOval = false;
			drawingRectangle = false;
			drawingTriangle = false;
			drawingStraightLine = true;
		} else if(e.getSource() == undoButton){
			Object s = shapes.pop();
			undoShapes.add(s);
			shapes.remove(s);
			repaint();
		} else if(e.getSource() == redoButton){
			Object s = undoShapes.pop();
			shapes.add(s);
			undoShapes.remove(s);
			repaint();
		} else if(e.getSource() == clear) {
			shapes = new Stack<Object>();
			repaint();
		} else if(e.getSource() == exit) {
			System.exit(0);
		} else if(e.getSource() == load) {
			fileChooser.showOpenDialog(null);
			File imgFile = fileChooser.getSelectedFile();
			if(imgFile != null && imgFile.toString().indexOf(".png") >= 0) {
				try{
					loadedImage = ImageIO.read(imgFile);
					
				} catch(IOException ex) {
					System.out.println("check");
					shapes = new Stack<Object>();
					repaint();
				}
			} else {
				if(imgFile != null) {
					JOptionPane.showMessageDialog(null, "Wrong file type. Please select a PNG file.");
				}
			}

		} else if(e.getSource() == save) {
			FileFilter filter = new FileNameExtensionFilter("*.png", "png");
			fileChooser.setFileFilter(filter);
			if(fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION){
				File file = fileChooser.getSelectedFile();
				try{
					String st = file.getAbsolutePath();
					if(st.indexOf(".png") >= 0) {
						st = st.substring(0, st.length() - 4);
					}
					ImageIO.write(createImage(), "png", new File(st+".png"));
				} catch(IOException ex){}
			}
		} else {
			int index=(int)((JMenuItem)e.getSource()).getClientProperty("colorIndex");
			currColor=colors[index];
		}
	}

	public BufferedImage createImage() {
		int width = this.getWidth();
		int height = this.getHeight();
		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = img.createGraphics();
		this.paint(g2);
		g2.dispose();
		return img;
	}

	public void mouseReleased(MouseEvent e){
		firstClick = true;
		if(drawingFreeLine) {
			if(points.size()>0) {
				shapes.push(points);
				points = new ArrayList<Point>();
			}
		}
		repaint();
	}

	public void mouseDragged(MouseEvent e){

		if(drawingFreeLine) {
			Color c = currColor;
			if(erasing) {
				c = backgroundColor;
			}
			points.add(new Point(e.getX(),e.getY(),c,penWidth));
		} else if(drawingRectangle) {
			if(firstClick) {
				initX = e.getX();
				initY = e.getY();
				shapes.push(new Rectangle(initX, initY, currColor, penWidth, 0, 0));
				firstClick = false;
			} else {
				int width = Math.abs(e.getX() - initX);
				int height = Math.abs(e.getY() - initY);
				Rectangle rect = (Rectangle) shapes.peek();
				rect.setHeight(height);
				rect.setWidth(width);
				if(e.getX() < initX) {
					rect.setX(e.getX());
				}
				if(e.getY() < initY) {
					rect.setY(e.getY());
				}
			}
		} else if(drawingOval) {
			if(firstClick) {
				initX = e.getX();
				initY = e.getY();
				shapes.push(new Oval(initX, initY, currColor, penWidth, 0, 0));
				firstClick = false;
			} else {
				int width = Math.abs(e.getX() - initX);
				int height = Math.abs(e.getY() - initY);
				Oval oval = (Oval) shapes.peek();
				oval.setHeight(height);
				oval.setWidth(width);
				if(e.getX() < initX) {
					oval.setX(e.getX());
				}
				if(e.getY() < initY) {
					oval.setY(e.getY());
				}
			}
		} else if(drawingTriangle) {
			if(firstClick) {
				initX = e.getX();
				initY = e.getY();
				shapes.push(new Triangle(initX, initY, currColor, penWidth, 0, 0));
				firstClick = false;
			} else {
				int width = Math.abs(e.getX() - initX);
				int height = Math.abs(e.getY() - initY);
				Triangle triangle = (Triangle) shapes.peek();
				triangle.setHeight(height);
				triangle.setWidth(width);
				if(e.getX() < initX) {
					triangle.setWidth(-width);
				}
				if(e.getY() < initY) {
					triangle.setHeight(-height);
				}
				
			}
		} else if(drawingStraightLine) {
			if(firstClick) {
				initX = e.getX();
				initY = e.getY();
				shapes.push(new StraightLine(initX, initY, currColor, penWidth, initX, initY));
				firstClick = false;
			} else {
				int endX = e.getX();
				int endY = e.getY();
				StraightLine line = (StraightLine) shapes.peek();
				line.setHeight(endY);
				line.setWidth(endX);
			}
		}
		repaint();
	}

	public void adjustmentValueChanged(AdjustmentEvent e){
		penWidth = penWidthBar.getValue();
	}

	public void stateChanged(ChangeEvent e){
		currColor = colorChooser.getColor();
	}

	public class Point{
		int x,y,penWidth;
		Color color;
		public Point(int x, int y, Color color, int penWidth){
			this.x=x;
			this.y=y;
			this.color=color;
			this.penWidth=penWidth;
		}
		public int getX(){return x;}
		public int getY(){return y;}
		public int getPenWidth(){return penWidth;}
		public Color getColor(){return color;}
	}

	public class Shape{
		int x, y, penWidth, width, height;
		Color color;
		public Shape(int x, int y, Color color, int penWidth, int width, int height) {
			this.x = x;
			this.y = y;
			this.color = color;
			this.penWidth = penWidth;
			this.width = width;
			this.height = height;
		}
		public int getX() {return x;}
		public int getY() {return y;}
		public Color getColor() {return color;}
		public int getWidth() {return width;}
		public int getHeight() {return height;}
		public void setX(int x) {this.x = x;}
		public void setY(int y) {this.y = y;}
		public void setHeight(int height) {this.height = height;}
		public void setWidth(int width) {this.width = width;}
		public int getPenWidth() {return penWidth;}
	}

	public class Rectangle extends Shape {
		public Rectangle(int x, int y, Color color, int penWidth, int width, int height){
			super(x, y, color, penWidth, width, height);
		}
		public Rectangle2D.Double getShape(){return new Rectangle2D.Double(getX(), getY(), getWidth(), getHeight());}
	}

	public class Oval extends Shape {
		public Oval(int x, int y, Color color, int penWidth, int width, int height){
			super(x, y, color, penWidth, width, height);
		}
		public Ellipse2D.Double getShape(){return new Ellipse2D.Double(getX(), getY(), getWidth(), getHeight());}
	}

	public class Triangle extends Shape {
		public Triangle(int x, int y, Color color, int penWidth, int width, int height) {
			super(x, y, color, penWidth, width, height);
		}
		public Polygon getShape(){
			int [] x = {getX(), getX() - getWidth()/2, getX() + getWidth()/2};
			int [] y = {getY(), getY() + getHeight(), getY() + getHeight()};
			return new Polygon(x, y, 3);
		}
	}

	public class StraightLine extends Shape {
		public StraightLine(int x, int y, Color color, int penWidth, int width, int height){
			super(x, y, color, penWidth, width, height);
		}
		public Line2D.Double getShape(){return new Line2D.Double(x, y, width, height);}
	}

	public static void main(String[] args)
	{
		PaintProgram app=new PaintProgram();
	}
	public void mousePressed(MouseEvent e){}
	public void mouseMoved(MouseEvent e){}
	public void mouseClicked(MouseEvent e){}
	public void mouseEntered(MouseEvent e){}
	public void mouseExited(MouseEvent e){}


}