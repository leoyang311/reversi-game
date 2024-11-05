package controller;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Map;

import javax.swing.*;

import model.CellState;
import model.ComputerReversiPlayer;
import model.Coordinate;
import model.ReversiModel;
import model.ReversiPlayer;
import view.Hexagon;
import view.IView;
import view.ReversiPanel;

public class ReversiGUIController implements ReversiController, PlayerActionListener, ModelStatusListener, ReversiListener {
  private final ReversiModel model;
  private final ReversiPanel view;
  private final ReversiPlayer player;

  public ReversiGUIController(ReversiModel model, ReversiPlayer player, IView view) {
    this.model = model;
    this.player = player;
    this.view = (ReversiPanel) view;

    model.addModelStatusListener(this);
    view.addPlayerActionListener(this);
    player.setActionListener(this);
    ((ReversiPanel) view).setListener(this);

    this.view.display();
  }

  @Override
  public void onPlayerTurn(CellState currentPlayer) {
    if (currentPlayer == player.getPlayerType()) {
      if (player instanceof ComputerReversiPlayer) {
        player.makeMove();
      }
      // For human players, we wait for GUI interaction
    }
  }

  @Override
  public void onMoveSelected(Coordinate move) {
    try {
      model.makeMove(move, model.getCurrentPlayer());
    } catch (IllegalArgumentException e) {
      JOptionPane.showMessageDialog(view, e.getMessage(), "Invalid Move", JOptionPane.ERROR_MESSAGE);
    }
    view.repaint();
  }

  @Override
  public void onPassSelected() {
    model.pass();
    view.repaint();
  }

  @Override
  public void onGameOver(CellState winner) {
    view.showGameOverDialog();
  }

  private void turns() {
    if (model.isGameOver()) {
      view.showDialog("Game over!");
      return;
    }

    if (isComputerTurn(model.getCurrentPlayer())) {
      // Run computer turn with a slight delay for better UX
      Timer timer = new Timer(500, e -> computerTurn());
      timer.setRepeats(false);
      timer.start();
    }
  }

  private void computerTurn() {
    Coordinate move = model.findBestMove(model.getCurrentPlayer());
    System.out.println("Computer move: " + move);
    if (move != null) {
      model.makeMove(move, model.getCurrentPlayer());
    } else {
      model.pass();
    }
    view.repaint();
    // After a computer move, if no more moves are available, pass the turn
    if (!model.hasLegalMoves()) {
      model.pass();
    }
    // Prepare for the next turn, which could be human or another computer turn
    turns();
  }

  private boolean isComputerTurn(CellState currentPlayer) {
    return currentPlayer == CellState.WHITE; // Assuming WHITE is always a computer
  }


  @Override
  public void onKeyPressed(KeyEvent e) {
    // Only process key presses if it's a human player's turn
    if (!isComputerTurn(model.getCurrentPlayer())) {
      Coordinate selectedCell = view.getSelectedCell();
      if (selectedCell != null && e.getKeyCode() == KeyEvent.VK_ENTER) {
        if (model.getBoard().get(selectedCell) == CellState.EMPTY) {
          try {
            if (model.getLegalMoves(model.getCurrentPlayer()).contains(selectedCell)) {
              model.makeMove(selectedCell, model.getCurrentPlayer());
              view.setSelectedCell(null); // Deselect cell after move
            } else {
              view.showErrorDialog("Invalid move: " + selectedCell);
              return;
            }
          } catch (IllegalArgumentException exception) {
            view.showErrorDialog("Invalid move: " + exception.getMessage());
          }
          view.repaint();
          // After handling the key event, proceed to the next turn
          turns();
        }
      } else if (e.getKeyCode() == KeyEvent.VK_P) {
        model.pass();
        view.repaint();
        // After handling the pass, proceed to the next turn
        turns();
      }
    }
  }

  @Override
  public void onMouseClicked(MouseEvent e) {
    // Only process mouse clicks if it's a human player's turn
    if (!isComputerTurn(model.getCurrentPlayer())) {
      Point mousePoint = e.getPoint();
      Map<Hexagon, Coordinate> hexToCoord = view.getHexToCoord();
      for (Map.Entry<Hexagon, Coordinate> entry : hexToCoord.entrySet()) {
        if (entry.getKey().contains(mousePoint)) {
          Coordinate move = entry.getValue();
          // Highlight the cell instead of making a move
          if (model.getBoard().get(move) == CellState.EMPTY) {
            view.setSelectedCell(move);
          } else {
            view.setSelectedCell(null);
          }
          view.repaint();
          return;
        }
      }
    }
  }

  @Override
  public void onMoveComputed(Coordinate move) {
    model.makeMove(move, model.getCurrentPlayer());
    view.repaint();
    // Continue to the next turn.
  }

  @Override
  public void onTurnPassed() {
    model.pass();
    view.repaint();
    // Continue to the next turn.
  }
}
