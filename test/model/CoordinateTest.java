package model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


/**
 * represents the class of coordinate test.
 */
public class CoordinateTest {

  Coordinate c1;
  Coordinate c2;
  Coordinate c3;
  Coordinate c4;

  @Before
  public void setUp() throws Exception {
    c1 = new CubeCoordinate(2, 1, -3);
    c2 = new CubeCoordinate(2, 0, -2);
    c3 = new CubeCoordinate(3, 2, -5);
    c4 = new CubeCoordinate(0, 0, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidCubeCoordinates() {
    new CubeCoordinate(1, 1, 1);
    new CubeCoordinate(1, 1, 0);
    new CubeCoordinate(-1, -1, 0);
  }

  @Test
  public void addTest() {
    assertEquals(new CubeCoordinate(4, 1, -5), c1.add(c2));
    assertEquals(new CubeCoordinate(2, 1, -3), c1.add(c4));
  }

  @Test
  public void subtractTest() {
    assertEquals(new CubeCoordinate(2, 1, -3), c1.subtract(c4));
    assertEquals(new CubeCoordinate(0, 1, -1), c1.subtract(c2));
  }

  @Test
  public void getNeighborsTest() {
    CubeCoordinate[] expectedNeighbors = {
      new CubeCoordinate(3, 1, -4),
      new CubeCoordinate(2, 0, -2),
      new CubeCoordinate(3, 0, -3),
      new CubeCoordinate(2, 1, -3),
      new CubeCoordinate(2, 2, -4),
      new CubeCoordinate(1, 1, -2)
    };
    for (int i = 0; i < 6; i++) {
      assertEquals(expectedNeighbors[i], c1.getNeighbors().get(i));
    }
  }
}