package reversi;

import controller.ReversiController;
import controller.ReversiGUIController;
import model.CellState;
import model.ComputerReversiPlayer;
import model.HumanReversiPlayer;
import model.ReversiGame;
import model.ReversiModel;
import model.ReversiPlayer;
import strategy.ReversiStrategy;
import view.ReversiPanel;

public final class Reversi {
  public static void main(String[] args) {
    String defaultStrategy = "CompositeStrategy";
    String defaultPlayer1Type = "computer";
    if (args.length < 2) {
      args = new String[] {defaultPlayer1Type, defaultStrategy};
    } else if (args.length < 4) {
      args = new String[] {args[0], args[1], defaultPlayer1Type, defaultStrategy};
    } else if (args.length > 4) {
      throw new IllegalArgumentException("Usage: Reversi <player1Type> <player1Strategy> <player2Type> <player2Strategy>");
    }

    ReversiModel model = new ReversiGame(3);
    ReversiPanel viewPlayer1 = new ReversiPanel(model);
    ReversiPanel viewPlayer2 = new ReversiPanel(model);
    ReversiPlayer player1 = createPlayer(args[0], args.length > 1 ? args[1] : null, model);
    ReversiPlayer player2 = createPlayer(args.length > 2 ? args[2] : "human", args.length > 3 ? args[3] : null, model);
    ReversiController controller1 = new ReversiGUIController(model, player1, viewPlayer1);
    ReversiController controller2 = new ReversiGUIController(model, player2, viewPlayer2);
    model.startGame();
  }

  private static ReversiPlayer createPlayer(String playerType, String strategyName, ReversiModel model) {
    if (playerType.equalsIgnoreCase("human")) {
      return new HumanReversiPlayer(model);
    } else if (playerType.equalsIgnoreCase("computer")) {
      return new ComputerReversiPlayer(model, strategyName);
    } else {
      throw new IllegalArgumentException("Invalid player type: " + playerType);
    }
  }
}
