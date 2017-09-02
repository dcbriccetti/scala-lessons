package proc

import processing.core.{PApplet, PConstants}
import PConstants.{PI, TRIANGLE_STRIP}

/**
  * Inspired by:
  *   Coding Challenge #11: 3D Terrain Generation with Perlin Noise in Processing
  *   https://www.youtube.com/watch?v=IKB1hWWedMk
  */
class Terrain extends ScalaProcessingApplet {
  override def settings(): Unit = {
    size(1920, 1060, PConstants.P3D)
  }

  override def draw() = {
    val spacing = 20
    lights()
    background(135, 206, 250) // Sky
    translate(width / 2, height / 2, 0)
    rotateX(PI / 3)
    translate(-width, -height, 0)
    val range = 0 to width / spacing * 2
    fill(128, 200, 128) // Terrain
    noStroke()

    for (y <- range) {
      beginShape(TRIANGLE_STRIP)
      for (x <- range) {
        vertex(spacing * x, spacing * y,       scaledNoise(x, y))
        vertex(spacing * x, spacing * (y + 1), scaledNoise(x, y + 1))
      }
      endShape()
    }
  }

  private def scaledNoise(x: Int, y: Int) = {
    val gradation = 10f
    PApplet.map(noise(x / gradation, (y - frameCount) / gradation), 0, 1, -100, 100)
  }
}

object Terrain extends App {
  PApplet.main("proc.Terrain")
}
