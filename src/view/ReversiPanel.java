package view;

import javax.swing.*;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;
import java.util.HashMap;

import controller.PlayerActionListener;
import controller.ReversiListener;
import model.CellState;
import model.Coordinate;
import model.ReadonlyReversiModel;

public class ReversiPanel extends JPanel implements IView {
  public static final int INSTRUCTION_TEXT_X = 10;
  public static final int INSTRUCTION_TEXT_Y = 20;
  private final ReadonlyReversiModel model;
  private Coordinate selectedCell = null;
  private final Map<Hexagon, Coordinate> hexToCoord;
  private final int HEX_SIZE = 40;
  private final int CELL_SIZE = 40;
  private final Point hexOrigin;
  private final Font font = new Font("Arial", Font.BOLD, 18);
  FontMetrics metrics;
  private PlayerActionListener listener;

  public ReversiPanel(ReadonlyReversiModel model) {
    this.model = model;
    this.hexToCoord = new HashMap<>();
    int height = HEX_SIZE * (model.getSize() * 2 + 1) * 2;
    int width = (int) (HEX_SIZE * (Math.sqrt(3) * model.getSize() + Math.sqrt(3) / 2)) * 2 + 100;
    this.hexOrigin = new Point(width / 2, height / 2);

    this.setBackground(Color.BLACK);
    // Set preferred size for the component
    this.setPreferredSize(new Dimension(width, height));
    this.setFocusable(true);
  }

  /**
   * Gets the selected cell.
   *
   * @return The selected cell.
   */
  public Coordinate getSelectedCell() {
    return selectedCell;
  }

  public void showGameOverDialog() {
    String message = "Game over! ";
    Map<CellState, Integer> scores = model.getScores();
    if (scores.get(CellState.BLACK) > scores.get(CellState.WHITE)) {
      message += "Black wins!";
    } else if (scores.get(CellState.BLACK) < scores.get(CellState.WHITE)) {
      message += "White wins!";
    } else {
      message += "It's a tie!";
    }
    JOptionPane.showMessageDialog(this, message);
  }

  /**
   * Sets the selected cell.
   *
   * @param selectedCell The selected cell.
   */
  public void setSelectedCell(Coordinate selectedCell) {
    this.selectedCell = selectedCell;
  }

  /**
   * Show a dialog with the given message.
   *
   * @param message The message to display.
   */
  public void showDialog(String message) {
    JOptionPane.showMessageDialog(this, message);
  }

  /**
   * Show an error dialog with the given message.
   *
   * @param message The message to display.
   */
  public void showErrorDialog(String message) {
    JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
  }

  /**
   * Get the map of hexagons to coordinates.
   *
   * @return The map of hexagons to coordinates.
   */
  public Map<Hexagon, Coordinate> getHexToCoord() {
    return new HashMap<>(hexToCoord);
  }

  public void setListener(ReversiListener listener) {
    this.addKeyListener(new KeyAdapter() {
      @Override
      public void keyPressed(KeyEvent e) {
        listener.onKeyPressed(e);
      }
    });

    this.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        listener.onMouseClicked(e);
      }
    });
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g); // Clears the panel
    Graphics2D g2d = (Graphics2D) g;

    g2d.setStroke(new BasicStroke(4.0f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER));
    g2d.setFont(font);
    metrics = g.getFontMetrics();
    drawHexGrid(g2d);

    if (selectedCell != null) {
      drawHexByCoord(g2d, selectedCell, 0x68fAFD, 0, true);
    }

    // Clear the area where the text will be drawn
    g2d.setColor(getBackground());
    int textHeight = metrics.getHeight();
    g2d.fillRect(INSTRUCTION_TEXT_X, INSTRUCTION_TEXT_Y - textHeight, getWidth(), textHeight * 2);

    // Draw the instructions
    g2d.setColor(Color.WHITE);
    String instructions = "Press 'Enter' to make a move, 'P' to pass.";
    g2d.drawString(instructions, INSTRUCTION_TEXT_X, INSTRUCTION_TEXT_Y);

    // Draw the current player
    String instructions2 = "Now playing: " +
            model.getCurrentPlayer() +
            " Score: " + model.getScores().get(CellState.BLACK) +
            " - " + model.getScores().get(CellState.WHITE) +
            " (Black - White)";
    g2d.drawString(instructions2, INSTRUCTION_TEXT_X, INSTRUCTION_TEXT_Y + textHeight);
  }


  private Hexagon drawHexByCoord(Graphics2D g2d, Coordinate coord, int color, int thick, boolean fill) {
    Point pixel = hexToPixel(coord);
    int x = pixel.x + HEX_SIZE / 2;
    int y = pixel.y + HEX_SIZE / 2;
    Hexagon hex = new Hexagon(x, y, HEX_SIZE);
    hex.draw(g2d, thick, color, fill);
    return hex;
  }

  private void drawHexGrid(Graphics2D g2d) {
    Map<Coordinate, CellState> board = model.getBoard();

    for (Map.Entry<Coordinate, CellState> entry : board.entrySet()) {
      Coordinate coord = entry.getKey();
      CellState state = entry.getValue();

      Point pixel = hexToPixel(coord);
      Hexagon hex = drawHexByCoord(g2d, coord, 0xc0c0c0, 0, true);
      drawHexByCoord(g2d, coord, 0x000000, 2, false);
      hexToCoord.put(hex, coord);
      if (state != CellState.EMPTY) {
        // Set the color based on the cell state
        g2d.setColor(state == CellState.BLACK ? Color.BLACK : Color.WHITE);
        // Draw the oval at the calculated position with the CELL_SIZE
        g2d.fillOval(pixel.x, pixel.y, CELL_SIZE, CELL_SIZE);
      }
    }
  }

  private Point hexToPixel(Coordinate coord) {
    int x = (int) (HEX_SIZE * (Math.sqrt(3) * (coord.getQ() + coord.getR() / 2.0))) + hexOrigin.x - CELL_SIZE / 2;

    // Calculate the vertical position (y) and adjust it to the correct screen position
    int y = hexOrigin.y - (int) (HEX_SIZE * (3.0 / 2 * coord.getR())) + CELL_SIZE / 2;
    y = hexOrigin.y + (hexOrigin.y - y);
    return new Point(x, y);
  }

  @Override
  public void display() {
    JFrame frame = new JFrame("Reversi");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.add(this);
    frame.pack();
    frame.setVisible(true);
    this.requestFocusInWindow();
  }

  @Override
  public void addPlayerActionListener(PlayerActionListener listener) {
    this.listener = listener;
  }
}
