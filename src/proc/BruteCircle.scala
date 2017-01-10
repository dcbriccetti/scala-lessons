package proc

import processing.core.PApplet

class BruteCircle extends PApplet {
  val ScreenDimension = 1080
  val radius = ScreenDimension * 0.4

  override def settings(): Unit = {
    size(ScreenDimension, ScreenDimension)
  }

  override def setup(): Unit = {
    frameRate(60)
    background(0)
  }

  override def draw(): Unit = {
    translate(width / 2, height / 2)
    def rc = (math.random * ScreenDimension).toInt - width / 2
    1 to 100 foreach { _ =>
      val (x, y) = (rc, rc)
      val distanceFromCenter = math.sqrt(x * x + y * y)
      if (distanceFromCenter < radius && distanceFromCenter > radius - 50)
        stroke(255, 165, 0)
      else
        stroke(64)
      point(x, y)
    }
  }
}

object BruteCircle {
  def main(args: Array[String]): Unit = PApplet.main("proc.BruteCircle")
}
