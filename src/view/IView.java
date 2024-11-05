package view;

import controller.PlayerActionListener;
import controller.ReversiGUIController;

/**
 * Represents a view of a game.
 */
public interface IView {
  /**
   * Displays the view.
   */
  void display();

  /**
   * Adds a player action listener.
   *
   * @param listener The model status listener.
   */
  void addPlayerActionListener(PlayerActionListener listener);
}
