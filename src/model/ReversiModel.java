package model;

import java.util.List;

import controller.ModelStatusListener;

/**
 * This interface defines the operations that can be performed on a game of reversi.Reversi contains methods that modify the game state.
 */
public interface ReversiModel extends ReadonlyReversiModel {

  /**
   * Adds a model status listener.
   *
   * @param listener The listener.
   */
  void addModelStatusListener(ModelStatusListener listener);

  /**
   * Sets the players for the game.
   *
   * @param players The players for the game.
   */
  void setPlayers(List<ReversiPlayer> players);

  /**
   * Starts the game.
   */
  void startGame();

  /**
   * Makes a move for a player at a given coordinate and captures any valid opposing pieces.
   *
   * @param coord  The coordinate where the move is made.
   * @param player The current player's cell state.
   * @throws IllegalArgumentException If the move is illegal.
   */
  void makeMove(Coordinate coord, CellState player);

  /**
   * Passes the turn to the next player.
   */
  void pass();
}
