package strategy;

import java.util.ArrayList;
import java.util.List;

import model.CellState;
import model.Coordinate;
import model.ReversiGame;
import model.ReversiModel;

public class MinimaxStrategy implements ReversiStrategy {
  @Override
  public List<MoveOption> evaluateMoves(ReversiModel model, CellState player) {
    List<MoveOption> moveOptions = new ArrayList<>();
    for (Coordinate move : model.getLegalMoves(player)) {
      // Create a hypothetical model for simulating the move
      ReversiModel hypotheticalModel = new ReversiGame(model);
      hypotheticalModel.makeMove(move, player);
      // Assume opponent will play optimally and calculate their best response
      Coordinate opponentBestMove = hypotheticalModel.findBestMove(model.opposite(player));
      int opponentCaptures = hypotheticalModel.calculateCaptures(opponentBestMove, model.opposite(player));
      // The score is the negative of the opponent's best captures, as we want to minimize their gain
      moveOptions.add(new MoveOption(move, -opponentCaptures));
    }
    return moveOptions;
  }
}

