package proc

import processing.core.{PConstants, PApplet}

class Pong3D extends PApplet {

  override def settings(): Unit = {
    size((400 * 1.8).toInt, 400, PConstants.P3D)
    smooth(8)
  }

  override def setup(): Unit = {
    frameRate(30)
    stroke(255)
    fill(255)
  }

  override def draw() = {
    background(0)
    lights()
    pushMatrix()
    translate(50, height / 2, -50)
    box(30, 200, 100)
    popMatrix()
    translate(width - 50, height / 2, -50)
    box(30, 200, 100)
  }
}

object Pong3D {
  def main(args: Array[String]) = PApplet.main("proc.Pong3D")
}