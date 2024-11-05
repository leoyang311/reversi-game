package model;

import java.util.List;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Represents a coordinate in a hexagonal grid using the cube coordinate system.
 * Cube coordinates allow each hexagon in a grid to be identified with a unique set of three integers (q, r, s),
 * which sum to zero, enabling straightforward arithmetic for hex grids.
 * The origin (0,0,0) is typically at the center of the grid, with the axes running diagonally:
 * 'q' increases towards the east and decreases towards the west,
 * 'r' increases towards the southeast and decreases towards the northwest,
 * 's' increases towards the northeast and decreases towards the southwest.
 * This system facilitates efficient distance calculation and neighbor identification in a hexagonal layout.
 */
public class CubeCoordinate implements Coordinate {
  private final int q;
  private final int r;
  private final int s;

  protected static final CubeCoordinate[] DIRECTIONS = {
          new CubeCoordinate(+1, -1, 0), new CubeCoordinate(+1, 0, -1),
          new CubeCoordinate(0, +1, -1), new CubeCoordinate(-1, +1, 0),
          new CubeCoordinate(-1, 0, +1), new CubeCoordinate(0, -1, +1)
  };

  @Override
  public List<Coordinate> getNeighbors() {
    return Arrays.stream(DIRECTIONS)
            .map(this::add)
            .collect(Collectors.toList());
  }

  @Override
  public int getQ() {
    return q;
  }

  @Override
  public int getR() {
    return r;
  }

  @Override
  public int getS() {
    return s;
  }

  @Override
  public boolean isAdjacentTo(Coordinate corner) {
    if (!(corner instanceof CubeCoordinate)) {
      throw new IllegalArgumentException("Cannot compare cube coordinate to non-cube coordinate");
    }
    CubeCoordinate otherCube = (CubeCoordinate) corner;
    return this.getNeighbors().contains(otherCube);
  }

  public CubeCoordinate(int q, int r, int s) {
    // class invariant: q + r + s = 0
    // For every Coordinate instance inside the board map of ReversiGame,
    // the sum of its q, r, and s values must always be 0.
    // This is an essential property of cube coordinates in a hexagonal grid.
    // To enforce this invariant, we need to ensure that:
    // enforce it with an exception
    if (q + r + s != 0) {
      throw new IllegalArgumentException("Invalid cube coordinates");
    }
    this.q = q;
    this.r = r;
    this.s = s;
  }

  @Override
  public CubeCoordinate add(Coordinate other) {
    if (!(other instanceof CubeCoordinate)) {
      throw new IllegalArgumentException("Cannot add cube coordinate to non-cube coordinate");
    }
    CubeCoordinate otherCube = (CubeCoordinate) other;
    return new CubeCoordinate(this.q + otherCube.q, this.r + otherCube.r, this.s + otherCube.s);
  }

  @Override
  public CubeCoordinate subtract(Coordinate other) {
    if (!(other instanceof CubeCoordinate)) {
      throw new IllegalArgumentException("Cannot subtract cube coordinate from non-cube coordinate");
    }
    CubeCoordinate otherCube = (CubeCoordinate) other;
    return new CubeCoordinate(this.q - otherCube.q, this.r - otherCube.r, this.s - otherCube.s);
  }

  @Override
  public boolean equals(Object other) {
    if (!(other instanceof CubeCoordinate)) {
      return false;
    }
    CubeCoordinate otherCube = (CubeCoordinate) other;
    return this.q == otherCube.q && this.r == otherCube.r && this.s == otherCube.s;
  }

  @Override
  public int hashCode() {
    return Objects.hash(q, r, s);
  }

  @Override
  public String toString() {
    return String.format("(%d, %d, %d)", q, r, s);
  }
}