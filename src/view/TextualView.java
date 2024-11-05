package view;

import java.io.IOException;

/**
 * This interface defines the contract for a textual view component in a game application.
 * It encapsulates the behavior for rendering game states in a textual representation to the user.
 * Implementing classes will provide specific mechanisms to output the game's textual view,
 * which may include displaying the game state on the console, a file, or another text-based medium.
 */
public interface TextualView extends IView {
  /**
   * Renders a model in some manner (e.g. as text, or as graphics, etc.).
   *
   * @throws IOException if the rendering fails for some reason
   */
  void render() throws IOException;
}
