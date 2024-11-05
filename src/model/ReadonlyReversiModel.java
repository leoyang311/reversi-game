package model;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This interface defines the operations that can be performed on a game of reversi.Reversi
 * only contains methods that do not modify the game state.
 */
public interface ReadonlyReversiModel {


  /**
   * Gets the coordinates of the corners of the hexagonal grid.
   *
   * @return A set containing the corner coordinates.
   */
  Set<Coordinate> getCorners();

  /**
   * Finds the best move for the given player.
   *
   * @param player The current player's cell state.
   * @return The best move for the given player.
   */
  Coordinate findBestMove(CellState player);

  /**
   * Retrieves the opposite state of the provided player's state.
   *
   * @param player The current player's cell state.
   * @return The opposing player's cell state.
   */
  CellState opposite(CellState player);


  /**
   * Calculates the number of pieces that would be captured by a given move.
   *
   * @param move   The coordinate where the move is made.
   * @param player The current player's cell state.
   * @return The number of pieces that would be captured by the given move.
   */
  int calculateCaptures(Coordinate move, CellState player);

  /**
   * Retrieves the current state of the game board.
   *
   * @return A map of coordinates to their respective cell states.
   */
  Map<Coordinate, CellState> getBoard();


  /**
   * Retrieves the current state of the game board, excluding empty cells.
   *
   * @return A map of coordinates to their respective cell states.
   */
  Map<Coordinate, CellState> getNonEmptyBoard();

  /**
   * Retrieves the state of a cell at a given coordinate.
   *
   * @param coord The coordinate of the cell.
   * @return The current state of the cell.
   */
  CellState getCellState(Coordinate coord);

  /**
   * Retrieves the state of a cell at the given cube coordinates.
   *
   * @param q Cube coordinate q-axis value.
   * @param r Cube coordinate r-axis value.
   * @return The current state of the cell.
   */
  CellState getCellState(int q, int r);

  /**
   * Computes the list of coordinates where the given player can make a legal move.
   *
   * @param player The current player's cell state.
   * @return List of legal moves.
   */
  List<Coordinate> getLegalMoves(CellState player);


  /**
   * Checks if the current player has any legal moves.
   *
   * @return true if the current player has legal moves, false otherwise.
   */
  boolean hasLegalMoves();

  /**
   * Retrieves the current score of the game.
   *
   * @return A map of cell states to their respective scores.
   */
  Map<CellState, Integer> getScores();

  /**
   * Retrieves the current player's cell state.
   *
   * @return The current player's cell state.
   */
  CellState getCurrentPlayer();

  /**
   * Retrieves the size of the game board.
   *
   * @return The size of the game board.
   */
  int getSize();

  /**
   * Determines if the game is over.
   *
   * @return true if the game is over, false otherwise.
   */
  boolean isGameOver();
}
