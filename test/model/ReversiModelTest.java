package model;

import org.junit.Before;
import org.junit.Test;

import view.ReversiTextualView;
import view.TextualView;

import static org.junit.Assert.assertEquals;


/**
 * represents the additional test for GameModel.
 */
public class ReversiModelTest {
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
  public void getCellStateTest() {
    assertEquals(CellState.EMPTY, model.getCellState(new CubeCoordinate(0, 0, 0)));
    assertEquals(CellState.EMPTY, model.getCellState(-1, 2));
  }

  @Test
  public void testCopyConstructor() {
    ReversiModel copy = new ReversiGame(model);
    assertEquals(model.getCellState(0, 0), copy.getCellState(0, 0));
    assertEquals(model.getCellState(1, 0), copy.getCellState(1, 0));
    assertEquals(model.getCellState(0, 1), copy.getCellState(0, 1));
    assertEquals(model.getCellState(-1, 1), copy.getCellState(-1, 1));
    assertEquals(model.getCellState(1, -1), copy.getCellState(1, -1));
    assertEquals(model.getCellState(-1, 0), copy.getCellState(-1, 0));
    assertEquals(model.getCellState(0, -1), copy.getCellState(0, -1));
  }

  @Test
  public void testGetCellStateTest() {
    assertEquals(CellState.EMPTY, model.getCellState(0, 0));
    assertEquals(CellState.EMPTY, model.getCellState(-1, 2));
    assertEquals(CellState.EMPTY, model.getCellState(2, 1));
    assertEquals(CellState.WHITE, model.getCellState(1, -1));
  }

  @Test
  public void getLegalMovesTest() {
    assertEquals(model.getLegalMoves(CellState.BLACK), model.getLegalMoves(
            CellState.WHITE));
    assertEquals(6, model.getLegalMoves(CellState.BLACK).size());
    assertEquals(6, model.getLegalMoves(CellState.WHITE).size());
  }

  @Test
  public void makeValidMoveTest() {
    model.makeMove(new CubeCoordinate(-1, 2, -1), CellState.BLACK);
    assertEquals(CellState.BLACK, model.getCellState(new CubeCoordinate(-1, 2, -1)));
    assertEquals(CellState.BLACK, model.getCellState(0, -1));
  }

  @Test(expected = IllegalArgumentException.class)
  public void makeInvalidMoveTest() {
    model.makeMove(new CubeCoordinate(0, 0, 0), CellState.WHITE);
  }


  @Test
  public void getSizeTest() {
    assertEquals(3, model.getSize());
  }
}