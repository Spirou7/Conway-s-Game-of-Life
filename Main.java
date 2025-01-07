import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

class Main extends JPanel
    implements ActionListener, MouseListener, MouseMotionListener {

  public static Simulation sim;
  public static JFrame frame;
  public static JButton startButton;

  public static int scaleX = 300;
  public static int scaleY = 300;
  public static int offsetX = 20;
  public static int offsetY = 20;

  public static Timer timer;
  public static boolean isRunning = false;

  public Main() {
    addMouseListener(this);
    addMouseMotionListener(this);
  }

  public static void main(String[] args) {
    // Configuring the JFrame
    frame = new JFrame();
    frame.setSize(400, 400);
    frame.setVisible(true);

    // Instantiating the JPanels
    Main panel = new Main();
    JPanel south = new JPanel(new FlowLayout());

    // Populating the JPanels
    startButton = new JButton("start");
    south.add(startButton);

    // Adding ActionListeners
    startButton.addActionListener(panel);

    // Adding JPanels to the JFrames
    frame.add(panel, BorderLayout.CENTER);
    frame.add(south, BorderLayout.SOUTH);

    sim = new Simulation(50, 50);
    //sim.randomPopulate(0.1);
    frame.repaint();

    timer = new Timer(300, panel);
  }

  public void actionPerformed(ActionEvent e) {
    frame.repaint();

    // Do button checking
    if (e.getSource() instanceof JButton) {
      JButton button = (JButton) e.getSource();

      if (button == startButton) {
        if(!isRunning){
          timer.start();
          startButton.setText("Stop");
          isRunning = true;
        }
        else
        {
          timer.stop();
          startButton.setText("Start");
          isRunning = false;
        }
      }
    }

    sim.Iterate();
  }

  public void paintComponent(Graphics g) {
    super.paintComponent(g);

    // Display the grid lines
    ShowLines(g);

    // Fill in the grid lines
    ShowGrid(g);
  }

  public void ShowGrid(Graphics g) {
    boolean[][] grid = sim.grid;

    int tileWidth = (int) ((double) scaleX / (grid.length));
    int tileHeight = (int) ((double) scaleY / (grid[0].length));

    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid[i].length; j++) {

        if (grid[i][j]) {
          int startX = offsetX + tileWidth * i;
          int startY = offsetY + tileHeight * j;
          g.setColor(Color.BLACK);
          g.fillRect(startX, startY, tileWidth, tileHeight);
        }
      }
    }
  }

  public void ShowLines(Graphics g) {
    boolean[][] grid = sim.grid;

    int tileWidth = (int) ((double) scaleX / (grid.length));
    int tileHeight = (int) ((double) scaleY / (grid[0].length));

    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid[i].length; j++) {
        int startX = offsetX + tileWidth * i;
        int startY = offsetY + tileHeight * j;
        g.setColor(Color.GRAY);
        g.fillRect(startX, startY, tileWidth, tileHeight);
        g.setColor(Color.WHITE);
        g.fillRect(startX + 1, startY + 1, tileWidth - 1, tileHeight - 1);
      }
    }
  }

  boolean isPressed = false;
  boolean fillAlive = false;

  public void fillIn(MouseEvent arg0)
  {
    int relativeX = arg0.getX();
      int relativeY = arg0.getY();

      boolean[][] grid = sim.grid;
      int tileWidth = (int) ((double) scaleX / (grid.length));
      int tileHeight = (int) ((double) scaleY / (grid[0].length));

      int xTile = (relativeX - offsetX) / tileWidth;
      int yTile = (relativeY - offsetY) / tileHeight;

      sim.grid[xTile][yTile] = !sim.grid[xTile][yTile];
      frame.repaint();
  }

  public boolean GetTile(MouseEvent arg0){
    int relativeX = arg0.getX();
      int relativeY = arg0.getY();

      boolean[][] grid = sim.grid;
      int tileWidth = (int) ((double) scaleX / (grid.length));
      int tileHeight = (int) ((double) scaleY / (grid[0].length));

      int xTile = (relativeX - offsetX) / tileWidth;
      int yTile = (relativeY - offsetY) / tileHeight;
    return grid[xTile][yTile];
  }

  public void mouseMoved(MouseEvent arg0) {
  }

  public void mouseDragged(MouseEvent arg0) {
    if (isPressed) {
      int x, y;
      System.out.println("pressing");
      if(GetTile(arg0) == fillAlive)
      {
      fillIn(arg0);
      }
    }
  }

  public void mouseEntered(MouseEvent arg0) {
  }

  public void mouseExited(MouseEvent arg0) {
  }

  public void mousePressed(MouseEvent arg0) {
    isPressed = true;
    fillAlive = !fillAlive;
    fillIn(arg0);
  }

  public void mouseReleased(MouseEvent arg0) {
    isPressed = false;
  }

  public void mouseClicked(MouseEvent arg0) {
    
  }
}