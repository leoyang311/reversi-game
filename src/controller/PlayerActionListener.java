package controller;

import model.Coordinate;

/**
 * Responds to a player action event.
 */
public interface PlayerActionListener {
  /**
   * Responds to a move selected event.
   *
   * @param move The move.
   */
  void onMoveSelected(Coordinate move);

  /**
   * Responds to a pass selected event.
   */
  void onPassSelected();
}
