package model;

import java.util.List;

import controller.PlayerActionListener;
import strategy.MoveOption;
import strategy.ReversiStrategy;
import strategy.CompositeStrategy;
import strategy.CaptureMaxPiecesStrategy;
import strategy.AvoidGivingCornersStrategy;
import strategy.TakeCornersStrategy;
import strategy.MinimaxStrategy;

public class ComputerReversiPlayer implements ReversiPlayer {
  private ReversiStrategy strategy;
  private ReversiModel model;
  private CellState playerType;
  private PlayerActionListener actionListener;


  public ComputerReversiPlayer(ReversiModel model, String strategyName) {
    switch (strategyName) {
      case "CaptureMaxPiecesStrategy":
        strategy = new CaptureMaxPiecesStrategy();
        break;
      case "AvoidGivingCornersStrategy":
        strategy = new AvoidGivingCornersStrategy();
        break;
      case "TakeCornersStrategy":
        strategy = new TakeCornersStrategy();
        break;
      case "MinimaxStrategy":
        strategy = new MinimaxStrategy();
        break;
      default:
        strategy = new CompositeStrategy(
                new CaptureMaxPiecesStrategy(),
                new AvoidGivingCornersStrategy(),
                new TakeCornersStrategy(),
                new MinimaxStrategy()
        );
    }
    this.model = model;
    this.playerType = CellState.WHITE;
  }

  @Override
  public void makeMove() {
    List<MoveOption> moves = strategy.evaluateMoves(model, model.getCurrentPlayer());
    if (!moves.isEmpty()) {
      MoveOption bestMove = moves.get(0);
      actionListener.onMoveSelected(bestMove.getMove());
    } else {
      actionListener.onPassSelected();
    }
  }

  @Override
  public void passTurn() {
    actionListener.onPassSelected();
  }

  @Override
  public CellState getPlayerType() {
    return playerType;
  }

  @Override
  public void setActionListener(PlayerActionListener listener) {
    actionListener = listener;
  }
}
