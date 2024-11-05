package controller;

import model.CellState;

/**
 * Listens for model status events.
 */
public interface ModelStatusListener {
  /**
   * Responds to a player turn event.
   *
   * @param currentPlayer The current player.
   */
  void onPlayerTurn(CellState currentPlayer);

  /**
   * Responds to a game over event.
   *
   * @param winner The winner.
   */
  void onGameOver(CellState winner);
}
