package controller;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import model.Coordinate;

public interface ReversiListener {
  /**
   * Responds to a key press event.
   *
   * @param e The key event.
   */
  void onKeyPressed(KeyEvent e);

  /**
   * Responds to a mouse click event.
   *
   * @param e The mouse event.
   */
  void onMouseClicked(MouseEvent e);

  /**
   * Responds to a player move event.
   *
   * @param move The move.
   */
  void onMoveComputed(Coordinate move);

  /**
   * Responds to a turn passed event.
   */
  void onTurnPassed();
}
