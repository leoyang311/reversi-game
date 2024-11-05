package view;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import model.CellState;
import model.CubeCoordinate;
import model.ReversiGame;
import model.ReversiModel;

import static org.junit.Assert.assertEquals;


/**
 * represents the additional test of textualview.
 */
public class TextualViewTest {
  ReversiModel model;
  TextualView view;
  StringBuilder output;

  @Before
  public void setUp() throws Exception {
    model = new ReversiGame(3);
    output = new StringBuilder();
    view = new ReversiTextualView(model, output);
  }

  @Test
  public void initialStateTest() throws IOException {
    view.render();
    assertEquals("    _ _ _ _\n"
                    + "   _ _ _ _ _\n"
                    + "  _ _ X O _ _\n"
                    + " _ _ O _ X _ _\n"
                    + "  _ _ X O _ _\n"
                    + "   _ _ _ _ _\n"
                    + "    _ _ _ _",
            output.toString());
  }

  @Test
  public void makeMoveStateTest() throws IOException {
    model.makeMove(new CubeCoordinate(1, -2, 1), CellState.WHITE);
    view.render();
    assertEquals("    _ _ _ _\n"
                    + "   _ _ O _ _\n"
                    + "  _ _ O O _ _\n"
                    + " _ _ O _ X _ _\n"
                    + "  _ _ X O _ _\n"
                    + "   _ _ _ _ _\n"
                    + "    _ _ _ _",
            output.toString());
  }
}