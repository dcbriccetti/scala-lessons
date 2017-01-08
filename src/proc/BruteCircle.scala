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
    frameRate(10000)
    background(0)
  }

  override def draw(): Unit = {
    val x = (math.random * ScreenWidth).toInt
    val y = (math.random * ScreenHeight).toInt
    val xp = x - ScreenWidth / 2
    val yp = y - ScreenHeight / 2
    val distanceFromCenter = math.sqrt(xp * xp + yp * yp)
    if (distanceFromCenter < radius && distanceFromCenter > radius - 50)
      stroke(255, 165, 0)
    else
      stroke(64)
    point(x, y)
  }
}

object BruteCircle {
  def main(args: Array[String]): Unit = PApplet.main("proc.BruteCircle")
}
