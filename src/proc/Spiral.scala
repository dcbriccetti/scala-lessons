package proc

import processing.core.PApplet
import scala.math.{cos, sin, Pi}

/** Draws spirals, point by point. */
class Spiral extends PApplet {
  val ScreenDimension = 1080
  val PointsToAddPerFrame = 15
  val StartingRadius = ScreenDimension * 0.48 // Small margin
  val RadiusDecrementPixelsMinMax = (0.01, 0.5)
  val Δθ = 0.005

  var radius = StartingRadius
  var radiusDecrement = randomRadiusDecrement
  var θ = 0.0

  override def settings(): Unit = {
    size(ScreenDimension, ScreenDimension)
  }

  override def setup(): Unit = {
    frameRate(120)
    background(0)
    textFont(createFont("Helvetica", 14))
  }

  override def draw(): Unit = {
    for (_ <- 1 to PointsToAddPerFrame) {
      if (radius < 0) {
        background(0)
        radius = StartingRadius
        radiusDecrement = randomRadiusDecrement
        θ = 0.0
      } else {
        pushMatrix()
        translate(width / 2, height / 2)
        val (x, y) = (cos(θ) * radius, sin(θ) * radius)
        val green = ((radius / StartingRadius) * 255).toInt
        fill(0, green, 255 - green)
        stroke(0, green, 255 - green)
        ellipse(x.toInt, -y.toInt, 7, 7)
        radius -= radiusDecrement
        θ = (θ + Δθ) % (2 * Pi)
        popMatrix()
      }
    }
  }

  private def randomRadiusDecrement = {
    val range = RadiusDecrementPixelsMinMax._2 - RadiusDecrementPixelsMinMax._1
    RadiusDecrementPixelsMinMax._1 + math.random * range
  }
}

object Spiral {
  def main(args: Array[String]): Unit = PApplet.main("proc.Spiral")
}
