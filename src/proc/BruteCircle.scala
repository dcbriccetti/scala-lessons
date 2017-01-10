package proc

import java.text.NumberFormat
import processing.core.PApplet

class BruteCircle extends PApplet {
  val ScreenDimension = 1080
  val radius = ScreenDimension * 0.4
  val RingThickness = 1
  val PointsToAddPerFrame = 200
  var numPointsGenerated = 0
  var numPointsInRing = 0

  override def settings(): Unit = {
    size(ScreenDimension, ScreenDimension)
  }

  override def setup(): Unit = {
    frameRate(60)
    background(0)
    textFont(createFont("Helvetica", 14))
  }

  override def draw(): Unit = {
    def randCoord = (math.random * ScreenDimension).toInt - width / 2

    pushMatrix()
    translate(width / 2, height / 2)
    for (_ <- 1 to PointsToAddPerFrame) {
      val (x, y) = (randCoord, randCoord)
      val distanceFromCenter = math.sqrt(x * x + y * y)
      if (distanceFromCenter < radius && distanceFromCenter > radius - RingThickness) {
        stroke(255, 165, 0)
        numPointsInRing += 1
      } else
        stroke(64)
      numPointsGenerated += 1
      point(x, y)
    }
    popMatrix()

    displayInfo()
  }

  private def displayInfo() = {
    val fmt = NumberFormat.getIntegerInstance
    val textY = height - 10
    fill(0)
    noStroke()
    rect(0, height - 30, width, 30)
    fill(255)
    text(s"Points generated: ${fmt.format(numPointsGenerated)}", 10, textY)
    text(s"Points in ring: ${fmt.format(numPointsInRing)}", 220, textY)
  }
}

object BruteCircle {
  def main(args: Array[String]): Unit = PApplet.main("proc.BruteCircle")
}
