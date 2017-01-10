package proc

import processing.core.PApplet

class BruteCircle extends PApplet {
  val ScreenWidth = 1080
  val ScreenHeight = ScreenWidth
  val radius = ScreenWidth * 0.4

  override def settings(): Unit = {
    size(ScreenWidth, ScreenHeight)
  }

  override def setup(): Unit = {
    frameRate(30)
    background(0)
  }

  override def draw(): Unit = {
    translate(width / 2, height / 2)
    1 to 100 foreach { _ =>
      val x = (math.random * width).toInt - width / 2
      val y = (math.random * height).toInt - height / 2
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
