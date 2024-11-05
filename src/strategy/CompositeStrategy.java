package strategy;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

import model.CellState;
import model.ReversiModel;

public class CompositeStrategy implements ReversiStrategy {
  private List<ReversiStrategy> strategies;

  public CompositeStrategy(ReversiStrategy... strategies) {
    this.strategies = Arrays.asList(strategies);
  }

  @Override
  public List<MoveOption> evaluateMoves(ReversiModel model, CellState player) {
    List<MoveOption> options = new ArrayList<>();
    for (ReversiStrategy strategy : strategies) {
      options.addAll(strategy.evaluateMoves(model, player));
    }
    // Remove duplicates and sort by score
    return options.stream()
            .distinct()
            .sorted(Comparator.comparingInt(MoveOption::getScore))
            .collect(Collectors.toList());
  }
}
