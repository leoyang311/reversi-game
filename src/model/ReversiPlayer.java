package model;

import controller.PlayerActionListener;

/**
 * Represents a player in a game.
 */
public interface ReversiPlayer {
  /**
   * Makes a move.
   */
  void makeMove();

  /**
   * Passes the turn.
   */
  void passTurn();

  /**
   * Gets the player type.
   *
   * @return The player type.
   */
  CellState getPlayerType();

  /**
   * Sets the player action listener.
   *
   * @param listener The player action listener.
   */
  void setActionListener(PlayerActionListener listener);
}