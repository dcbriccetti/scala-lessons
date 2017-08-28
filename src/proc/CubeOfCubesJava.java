package proc;

import processing.core.PApplet;

public class CubeOfCubesJava extends PApplet {
    private int OFF_MAX = 300;

    public static void main(String[] args) {
        PApplet.main("proc.CubeOfCubesJava");
    }

    @Override
    public void settings() {
      size(2560, 1420, P3D);
    }

    @Override
    public void draw() {
      background(0);
      translate(width / 2, height / 2, -OFF_MAX);
      rotateX(frameCount * .01F);
      rotateY(frameCount * .01F);
      rotateZ(frameCount * .01F);

      for (int xo = -OFF_MAX; xo <= OFF_MAX; xo += 50) {
        for (int yo = -OFF_MAX; yo <= OFF_MAX; yo += 50) {
          for (int zo = -OFF_MAX; zo <= OFF_MAX; zo += 50) {
            pushMatrix();
            translate(xo, yo, zo);
            rotateX(frameCount * .02F);
            rotateY(frameCount * .02F);
            rotateZ(frameCount * .02F);
            fill(colorFromOffset(xo), colorFromOffset(yo),
              colorFromOffset(zo));
            box((float) (20 + (Math.sin(frameCount / 20.0)) * 15));
            popMatrix();
          }
        }
      }
    }

    private int colorFromOffset(int offset) {
      return (int) ((offset + OFF_MAX) / (2.0 * OFF_MAX) * 255);
    }
}
