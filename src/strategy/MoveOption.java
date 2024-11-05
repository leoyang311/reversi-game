package strategy;

import model.Coordinate;

public class MoveOption {
  Coordinate move;
  int score;

  public MoveOption(Coordinate move, int score) {
    this.move = move;
    this.score = score;
  }

  /**
   * @return the move
   */
  public Coordinate getMove() {
    return move;
  }

  /**
   * @return the score
   */
  public int getScore() {
    return score;
  }
}
