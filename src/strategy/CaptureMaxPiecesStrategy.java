package strategy;

import java.util.ArrayList;
import java.util.List;

import model.CellState;
import model.Coordinate;
import model.ReversiModel;

public class CaptureMaxPiecesStrategy implements ReversiStrategy{
  @Override
  public List<MoveOption> evaluateMoves(ReversiModel model, CellState player) {
    List<MoveOption> options = new ArrayList<>();
    for (Coordinate move : model.getLegalMoves(player)) {
      int captures = model.calculateCaptures(move, player);
      options.add(new MoveOption(move, captures));
    }
    return options;
  }
}
