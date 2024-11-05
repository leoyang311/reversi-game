package view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import controller.PlayerActionListener;
import model.Coordinate;
import model.CubeCoordinate;
import model.ReversiModel;

/**
 * Provides a textual representation of the reversi.Reversi game state, suitable for rendering in a console
 * or a text-based user interface. This class implements the TextualView interface and is responsible
 * for converting the current state of a reversi.Reversi game, as represented by a GameModel, into a string
 * representation that visually depicts the board and the placement of pieces on a hexagonal grid.
 * The output can be directed to any Appendable object, allowing for flexible display options such
 * as console output, file writing, or UI components.
 */
public class ReversiTextualView implements TextualView {
  private ReversiModel reversiGame;
  private final Appendable ap;

  public ReversiTextualView(ReversiModel reversiGame, Appendable ap) {
    if (reversiGame == null || ap == null) {
      throw new IllegalArgumentException("Neither model nor appendable can be null.");
    }
    this.reversiGame = reversiGame;
    this.ap = ap;
  }

  @Override
  public String toString() {
    List<String> rows = new ArrayList<>();
    int size = reversiGame.getSize();

    for (int r = -size; r <= size; r++) {
      int r1 = Math.max(-size, -r - size);
      int r2 = Math.min(size, -r + size);

      // Add spaces for hexagonal appearance
      List<String> rowContent = new ArrayList<>();
      rowContent.add(" ".repeat(Math.abs(r)));

      for (int q = r1; q <= r2; q++) {
        Coordinate coord = new CubeCoordinate(q, r, -r - q);
        switch (reversiGame.getCellState(coord)) {
          case EMPTY:
            rowContent.add("_");
            break;
          case BLACK:
            rowContent.add("X");
            break;
          case WHITE:
            rowContent.add("O");
            break;
        }
      }
      rows.add(String.join(" ", rowContent));
    }
    return String.join("\n", rows);
  }

  @Override
  public void render() throws IOException {
    ap.append(this.toString());
  }

  @Override
  public void display() {
    try {
      this.render();
    } catch (IOException e) {
      throw new IllegalStateException("Appendable error", e);
    }
  }

  @Override
  public void addPlayerActionListener(PlayerActionListener listener) {
    // pass
    return;
  }
}
