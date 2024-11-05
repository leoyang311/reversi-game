package strategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import model.CellState;
import model.Coordinate;
import model.ReversiModel;

public class AvoidGivingCornersStrategy implements ReversiStrategy {
  @Override
  public List<MoveOption> evaluateMoves(ReversiModel model, CellState player) {
    Set<Coordinate> corners = model.getCorners();
    List<MoveOption> moveOptions = new ArrayList<>();
    for (Coordinate move : model.getLegalMoves(player)) {
      if (!isNextToCorner(move, corners)) {
        int captures = model.calculateCaptures(move, player);
        moveOptions.add(new MoveOption(move, captures));
      }
    }
    return moveOptions;
  }

  private boolean isNextToCorner(Coordinate move, Set<Coordinate> corners) {
    for (Coordinate corner : corners) {
      if (move.isAdjacentTo(corner)) {
        return true;
      }
    }
    return false;
  }
}
