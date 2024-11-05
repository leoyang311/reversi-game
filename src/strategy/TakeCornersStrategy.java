package strategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import model.CellState;
import model.Coordinate;
import model.ReversiModel;

public class TakeCornersStrategy implements ReversiStrategy {

  @Override
  public List<MoveOption> evaluateMoves(ReversiModel model, CellState player) {
    Set<Coordinate> corners = model.getCorners();
    List<MoveOption> moveOptions = new ArrayList<>();
    for (Coordinate move : model.getLegalMoves(player)) {
      int score = corners.contains(move) ? Integer.MAX_VALUE : model.calculateCaptures(move, player);
      moveOptions.add(new MoveOption(move, score));
    }
    return moveOptions;
  }
}

