package controller;

import model.CellState;
import model.ReversiModel;
import view.IView;

public interface ReversiController {
  /**
   * Responds to a player turn event.
   *
   * @param currentPlayer The current player.
   */
  void onPlayerTurn(CellState currentPlayer);
}
