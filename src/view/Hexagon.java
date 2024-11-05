package view;

import java.awt.*;
import java.io.Serial;

public class Hexagon extends Polygon {

  @Serial
  private static final long serialVersionUID = 1L;
  private Point[] pointArr = new Point[SIDE];
  public static final int SIDE = 6;
  private final int radius;
  private Point center = new Point(0, 0);

  public Hexagon(Point center, int radius) {
    npoints = SIDE;
    xpoints = new int[SIDE];
    ypoints = new int[SIDE];
    this.center = center;
    this.radius = radius;
    updatePoints();
  }

  public Hexagon(int x, int y, int radius) {
    this(new Point(x, y), radius);
  }

  public void setCenter(Point center) {
    this.center = center;
    updatePoints();
  }
  private double getAngle(double fraction) {
    int rotation = 90;
    return fraction * Math.PI * 2 + Math.toRadians((rotation + 180) % 360);
  }
  private Point getPoint(double angle) {
    int x = (int) (center.x + Math.cos(angle) * radius);
    int y = (int) (center.y + Math.sin(angle) * radius);
    return new Point(x, y);
  }

  protected void updatePoints() {
    for (int p = 0; p < SIDE; p++) {
      double angle = getAngle((double) p / SIDE);
      Point point = getPoint(angle);
      xpoints[p] = point.x;
      ypoints[p] = point.y;
      pointArr[p] = point;
    }
  }
  public void setCenter(int x, int y) {
    setCenter(new Point(x, y));
  }

  public void draw(Graphics2D g, int thick, int colorHex, boolean fill) {
    Stroke stroke = g.getStroke();
    Color color = g.getColor();

    g.setColor(new Color(colorHex));
    g.setStroke(new BasicStroke(thick, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER));

    if (fill) {
      g.fillPolygon(xpoints, ypoints, npoints);
    } else {
      g.drawPolygon(xpoints, ypoints, npoints);
    }

    g.setColor(color);
    g.setStroke(stroke);
  }
}
