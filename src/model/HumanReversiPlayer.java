package model;

import controller.PlayerActionListener;
import controller.ReversiGUIController;

public class HumanReversiPlayer implements ReversiPlayer {
  private ReversiModel model;
  private CellState playerType;


  public HumanReversiPlayer(ReversiModel model) {
    this.model = model;
    // Human default player is black
    this.playerType = CellState.BLACK;
  }

  @Override
  public void makeMove() {
    // Handled by GUI events and controller
  }

  @Override
  public void passTurn() {
    // Handled by GUI events and controller
  }

  @Override
  public CellState getPlayerType() {
    return playerType;
  }

  @Override
  public void setActionListener(PlayerActionListener listener) {
    // No action needed as events are emitted by the view
  }
}