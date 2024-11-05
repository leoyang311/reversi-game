package model;

import java.util.List;

/**
 * This interface defines the operations that can be performed on coordinates within a grid-based game.
 * It provides the basic arithmetic operations necessary to navigate and manipulate positions on the grid.
 * Implementations of this interface will represent specific coordinate systems, such as Cartesian or hexagonal grid coordinates,
 * and will define how coordinates are added, subtracted, and how neighbors are determined within that system.
 */
public interface Coordinate {
  /**
   * Adds two coordinates together.
   *
   * @param other The coordinate to add to this one.
   * @return The sum of the two coordinates.
   */
  Coordinate add(Coordinate other);

  /**
   * Subtracts two coordinates from each other.
   *
   * @param other The coordinate to subtract from this one.
   * @return The difference of the two coordinates.
   */
  Coordinate subtract(Coordinate other);

  /**
   * Gets the list of neighboring coordinates.
   *
   * @return The list of neighboring coordinates.
   */
  List<Coordinate> getNeighbors();

  //   getQ

  /**
   * Gets the q-axis value of the coordinate.
   *
   * @return The q-axis value of the coordinate.
   */
  int getQ();

  /**
   * Gets the r-axis value of the coordinate.
   *
   * @return The r-axis value of the coordinate.
   */
  int getR();

  /**
   * Gets the s-axis value of the coordinate.
   *
   * @return The s-axis value of the coordinate.
   */
  int getS();

  /**
   * Determines whether this coordinate is adjacent to the given coordinate.
   *
   * @param corner The coordinate to check.
   * @return True if this coordinate is adjacent to the given coordinate, false otherwise.
   */
  boolean isAdjacentTo(Coordinate corner);
}
