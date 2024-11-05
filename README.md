# Changes for part 3
Certainly! Here's a summary of the key changes made to your initial design, along with a brief explanation for each:

1. Refactored `ReversiGUIController`:
    - Change: Simplified the controller by streamlining event handling and player-turn logic.
    - Reason: To reduce complexity, enhance readability, and ensure a clear separation of concerns.

2. Implemented `PlayerActionListener` and `ModelStatusListener` Interfaces:
    - Change: Created interfaces for handling player actions and model status updates.
    - Reason: To establish a contract for communication between the model, view, and controller, facilitating a more organized event-driven architecture.

3. Separated Player Logic into `HumanReversiPlayer` and `ComputerReversiPlayer`:
    - Change: Created distinct classes for human and computer players with specific behaviors.
    - Reason: To encapsulate player-specific logic and enable different handling for human and computer players within the game.

4. Integrated Strategy Pattern for Computer Players:
    - Change: Applied the strategy pattern for computer player moves.
    - Reason: To allow flexible and interchangeable move strategies for computer players, enhancing the game's adaptability and complexity.

5. Enhanced Player Interface with Action Listener Registration:
    - Change: Extended the `ReversiPlayer` interface to allow registration of action listeners.
    - Reason: To enable both human and computer players to communicate their actions (like making a move) to the controller.

6. Updated Controller to Manage Player Turns and Exceptions:
    - Change: Modified the controller to handle turn management and exceptions thrown by invalid moves.
    - Reason: To ensure that the game adheres to turn-based rules and provides user feedback for invalid actions.

7. Refactored Main Method for Flexible Player Configuration:
    - Change: Adapted the `main` method to initialize the game based on command-line arguments.
    - Reason: To allow dynamic configuration of the game with different types of players and strategies, offering customizable gameplay.

8. Created View Components to Emit Player Actions:
    - Change: Updated `ReversiPanel` to emit high-level player action events.
    - Reason: To bridge the gap between user interactions (like mouse clicks) and the game logic, ensuring that player inputs are effectively captured and processed.

9. Implemented Turn-Based Gameplay Logic in Controller:
    - Change: Refined the controller to enforce turn-based gameplay.
    - Reason: To align the asynchronous nature of the GUI with the synchronous, turn-based rules of Reversi, maintaining the integrity of the game's mechanics.

Each of these changes was driven by the need to make the game architecture more robust, flexible, and maintainable, while also enhancing the user experience and the game's functionality.

# Changes for part 2

- Change javadoc for all classes to better describe the class.
- Add copy constructor for `ReversiGame` class, allow to copy the game board.
- Add `currentPlayer` field for `ReversiGame` class to set the current player.
- Add test for this copy constructor.
- Add `hasLegalMoves` to `ReadonlyReversiModel` interface to check if the current player has legal moves.
- add `getScores` for `ReadonlyReversiModel` interface to get the current scores.
- Add `getCurrentPlayer` for `ReadonlyReversiModel` interface to get the current player.
- Add `isGameOver` for `ReadonlyReversiModel` interface to check if the game is over.