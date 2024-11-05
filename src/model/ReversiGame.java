package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import controller.ModelStatusListener;

/**
 * Represents the game board for a hexagonal board game.
 * The board uses cube coordinates to manage cell positions.
 */
public class ReversiGame implements ReversiModel {
  private final int size;
  private final Map<Coordinate, CellState> board;
  private CellState currentPlayer;
  private int currentPlayerIndex;
  private List<ReversiPlayer> players;
  private ModelStatusListener listener;

  @Override
  public void addModelStatusListener(ModelStatusListener listener) {
    this.listener = listener;
  }

  /**
   * Constructs a new GameBoard of the given size.
   *
   * @param size The size of the game board. Must be a positive value.
   * @throws IllegalArgumentException If size is not positive.
   */
  public ReversiGame(int size) {
    if (size < 1) {
      throw new IllegalArgumentException("Size must be positive");
    }
    this.currentPlayerIndex = -1;
    this.players = new ArrayList<>();
    this.currentPlayer = CellState.BLACK;
    this.size = size;
    this.board = new HashMap<>();

    // Initialize with default state
    for (int i = -size; i <= size; i++) {
      for (int j = -size; j <= size; j++) {
        int z = -i - j;
        if (Math.abs(i) <= size && Math.abs(j) <= size && Math.abs(z) <= size) {
          board.put(new CubeCoordinate(i, j, z), CellState.EMPTY);
        }
      }
    }

    // Initialize the center cells
    board.put(new CubeCoordinate(0, -1, 1), CellState.BLACK);
    board.put(new CubeCoordinate(1, -1, 0), CellState.WHITE);
    board.put(new CubeCoordinate(-1, 0, 1), CellState.WHITE);
    board.put(new CubeCoordinate(-1, 1, 0), CellState.BLACK);
    board.put(new CubeCoordinate(0, 1, -1), CellState.WHITE);
    board.put(new CubeCoordinate(1, 0, -1), CellState.BLACK);
  }


  private void checkPlayers() {
    if (players == null || players.size() != 2) {
      throw new IllegalArgumentException("The game must have exactly 2 players");
    }
  }

  @Override
  public void setPlayers(List<ReversiPlayer> players) {
    checkPlayers();
    this.players = players;
  }

  @Override
  public void startGame() {
    currentPlayer = CellState.BLACK;
    currentPlayerIndex = 0;
    notifyPlayerTurn();
  }

  private void notifyPlayerTurn() {
    if (currentPlayerIndex >= 0 && currentPlayerIndex < players.size()) {
      ReversiPlayer currentPlayer = players.get(currentPlayerIndex);
      currentPlayer.makeMove();
    }
  }

  public void nextTurn() {
    currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
    notifyPlayerTurn();
  }

  @Override
  public Map<Coordinate, CellState> getBoard() {
    return new HashMap<>(board);
  }

  @Override
  public Map<Coordinate, CellState> getNonEmptyBoard() {
    Map<Coordinate, CellState> nonEmptyBoard = new HashMap<>();
    for (Map.Entry<Coordinate, CellState> entry : board.entrySet()) {
      if (entry.getValue() != CellState.EMPTY) {
        nonEmptyBoard.put(entry.getKey(), entry.getValue());
      }
    }
    return nonEmptyBoard;
  }

  /**
   * Copy constructor that creates a new GameBoard as a deep copy of the given board.
   *
   * @param other The GameBoard to copy.
   */
  public ReversiGame(ReversiModel other) {
    this.size = other.getSize();
    this.board = new HashMap<>();
    this.currentPlayer = other.getCurrentPlayer();
    if (other instanceof ReversiGame) {
      this.board.putAll(((ReversiGame) other).board);
    } else {
      for (int i = -size; i <= size; i++) {
        for (int j = -size; j <= size; j++) {
          int z = -i - j;
          if (Math.abs(i) <= size && Math.abs(j) <= size && Math.abs(z) <= size) {
            this.board.put(new CubeCoordinate(i, j, z), other.getCellState(i, j));
          }
        }
      }
    }
  }


  private void checkQAndR(int q, int r) {
    if (Math.abs(q) > size || Math.abs(r) > size || Math.abs(-q - r) > size) {
      throw new IllegalArgumentException("Coordinate out of bounds");
    }
  }

  private void checkCoord(Coordinate coord) {
    if (coord == null) {
      throw new IllegalArgumentException("Coordinate cannot be null");
    }
    if (!board.containsKey(coord)) {
      throw new IllegalArgumentException("Coordinate out of bounds");
    }
  }

  private void checkState(CellState state) {
    if (state == null) {
      throw new IllegalArgumentException("State cannot be null");
    }
  }


  @Override
  public Set<Coordinate> getCorners() {
    Set<Coordinate> corners = new HashSet<>();

    corners.add(new CubeCoordinate(-size, size, 0));
    corners.add(new CubeCoordinate(0, size, -size));
    corners.add(new CubeCoordinate(size, 0, -size));
    corners.add(new CubeCoordinate(size, -size, 0));
    corners.add(new CubeCoordinate(0, -size, size));
    corners.add(new CubeCoordinate(-size, 0, size));

    return corners;
  }


  /**
   * Flips the captured pieces after a player makes a move.
   *
   * @param start     The starting coordinate of the move.
   * @param direction The direction to check for capturing.
   * @param player    The current player's cell state.
   */
  private void flipCaptured(Coordinate start, Coordinate direction, CellState player) {
    checkCoord(start);
    checkCoord(direction);
    checkState(player);
    List<Coordinate> toFlip = new ArrayList<>();
    Coordinate coord = start.add(direction);
    while (board.containsKey(coord)) {
      if (board.get(coord) == opposite(player)) {
        toFlip.add(coord);
      } else if (board.get(coord) == player) {
        for (Coordinate flipCoord : toFlip) {
          board.put(flipCoord, player);
        }
        return;
      } else {
        break;
      }
      coord = coord.add(direction);
    }
  }

  @Override
  public CellState getCurrentPlayer() {
    return currentPlayer;
  }

  @Override
  public CellState getCellState(Coordinate coord) {
    checkCoord(coord);
    return board.get(coord);
  }

  @Override
  public CellState getCellState(int q, int r) {
    checkQAndR(q, r);
    Coordinate coord = new CubeCoordinate(q, r, -q - r);
    return getCellState(coord);
  }

  private void setCellState(Coordinate coord, CellState state) {
    checkCoord(coord);
    checkState(state);
    board.put(coord, state);
  }

  private void nextPlayer() {
    currentPlayer = opposite(currentPlayer);
  }

  @Override
  public List<Coordinate> getLegalMoves(CellState player) {
    checkState(player);
    List<Coordinate> legalMoves = new ArrayList<>();
    for (Map.Entry<Coordinate, CellState> entry : board.entrySet()) {
      if (entry.getValue() == CellState.EMPTY && canCapture(entry.getKey(), player)) {
        legalMoves.add(entry.getKey());
      }
    }
    return legalMoves;
  }

  /**
   * Checks if a move at a given coordinate can capture any opposing pieces.
   *
   * @param coord  The coordinate of the cell.
   * @param player The current player's cell state.
   * @return true if the move can capture, false otherwise.
   */
  private boolean canCapture(Coordinate coord, CellState player) {
    checkCoord(coord);
    checkState(player);
    for (Coordinate neighbor : coord.getNeighbors()) {
      if (board.get(neighbor) == opposite(player)) {
        Coordinate direction = neighbor.subtract(coord);
        Coordinate checkCoord = neighbor.add(direction);
        while (board.containsKey(checkCoord)) {
          if (board.get(checkCoord) == player) {
            return true;
          }
          if (board.get(checkCoord) == CellState.EMPTY) {
            break;
          }
          checkCoord = checkCoord.add(direction);
        }
      }
    }
    return false;
  }

  @Override
  public Map<CellState, Integer> getScores() {
    Map<CellState, Integer> scores = new HashMap<>();
    scores.put(CellState.BLACK, 0);
    scores.put(CellState.WHITE, 0);

    for (CellState state : board.values()) {
      if (state != CellState.EMPTY) {
        scores.put(state, scores.get(state) + 1);
      }
    }
    return scores;
  }

  @Override
  public Coordinate findBestMove(CellState player) {
    List<Coordinate> legalMoves = getLegalMoves(player);
    Coordinate bestMove = null;
    int maxCaptures = 0;

    for (Coordinate move : legalMoves) {
      int captures = calculateCaptures(move, player);
      if (captures > maxCaptures || (captures == maxCaptures && isUpperLeft(move, bestMove))) {
        bestMove = move;
        maxCaptures = captures;
      }
    }

    return bestMove;
  }

  @Override
  public int calculateCaptures(Coordinate move, CellState player) {
    int totalCaptures = 0;

    // Directions in a cube coordinate system
    Coordinate[] directions = {
            new CubeCoordinate(1, -1, 0),
            new CubeCoordinate(1, 0, -1),
            new CubeCoordinate(0, 1, -1),
            new CubeCoordinate(-1, 1, 0),
            new CubeCoordinate(-1, 0, 1),
            new CubeCoordinate(0, -1, 1)
    };

    for (Coordinate direction : directions) {
      totalCaptures += capturesInDirection(move, direction, player);
    }

    return totalCaptures;
  }

  private int capturesInDirection(Coordinate start, Coordinate direction, CellState player) {
    int captures = 0;
    Coordinate current = start;
    boolean hasOpponentPieces = false;

    while (true) {
      current = new CubeCoordinate(
              current.getQ() + direction.getQ(),
              current.getR() + direction.getR(),
              current.getS() + direction.getS()
      );
      // if current is out of bounds, continue to next direction
      if (!board.containsKey(current)) {
        return 0;
      }

      CellState currentState = getCellState(current);

      if (currentState == CellState.EMPTY || currentState == null) {
        return 0; // Empty cell or out of bounds
      } else if (currentState != player) {
        hasOpponentPieces = true;
        captures++;
      } else {
        // We reached a cell of the current player
        return hasOpponentPieces ? captures : 0;
      }
    }
  }

  private boolean isUpperLeft(Coordinate move1, Coordinate move2) {
    return move1.getS() > move2.getS()
            || (move1.getQ() == move2.getQ()
            && move1.getR() < move2.getR());
  }


  @Override
  public boolean hasLegalMoves() {
    return !getLegalMoves(currentPlayer).isEmpty();
  }

  @Override
  public CellState opposite(CellState player) {
    checkState(player);
    return player == CellState.BLACK ? CellState.WHITE : CellState.BLACK;
  }

  @Override
  public void makeMove(Coordinate coord, CellState player) {
    checkCoord(coord);
    checkState(player);
    if (isGameOver()) {
      throw new IllegalStateException("Game is over");
    }
    if (currentPlayer != player) {
      throw new IllegalArgumentException("It's not the current player's turn");
    }
    if (board.get(coord) != CellState.EMPTY || !canCapture(coord, player)) {
      throw new IllegalArgumentException("Illegal move");
    }
    setCellState(coord, player);

    for (Coordinate direction : CubeCoordinate.DIRECTIONS) {
      flipCaptured(coord, direction, player);
    }

    // After a successful move, switch players and check for legal moves
    pass();
  }

  @Override
  public void pass() {
    nextPlayer();
    if (!hasLegalMoves()) {
      // If the next player has no moves, pass the turn back to the current player
      nextPlayer();
    }
  }


  @Override
  public boolean isGameOver() {
    boolean blackHasMoves = !getLegalMoves(CellState.BLACK).isEmpty();
    boolean whiteHasMoves = !getLegalMoves(CellState.WHITE).isEmpty();
    boolean allCellsFilled = board.values().stream().noneMatch(state -> state == CellState.EMPTY);

    return !blackHasMoves && !whiteHasMoves || allCellsFilled;
  }

  @Override
  public int getSize() {
    return size;
  }
}
