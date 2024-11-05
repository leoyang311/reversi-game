package strategy;

import java.util.List;

import model.CellState;
import model.ReversiModel;

public interface ReversiStrategy {
  List<MoveOption> evaluateMoves(ReversiModel model, CellState player);
}