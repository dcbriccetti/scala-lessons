package proc

import scala.math.{Pi, cos, sin}
import scala.util.Random
import processing.core.{PApplet, PConstants}
import processing.event.KeyEvent

/** Draws 3D spirals */
class Spiral3D extends ScalaProcessingApplet with Common3dKeys {
  val random = new Random()
  val ScreenDimension = 1080
  val StartingRadius = ScreenDimension * 0.4
  val ΔImageXyzRotation = 0.01F

  var portionToDraw = 0D
  var imageXyzRotation = 0F
  var options = new RandomDrawingOptions()

  override def settings(): Unit = size(ScreenDimension, ScreenDimension, PConstants.P3D)

  override def setup(): Unit = {
    frameRate(60)
    background(0)
    colorMode(PConstants.RGB, 1F)
  }

  override def draw(): Unit = {
    if (! suspend) {
      noStroke()
      drawPortion(portionToDraw, imageXyzRotation)
      portionToDraw += options.ΔportionToDraw
      if (portionToDraw > 1) {
        portionToDraw = 0
        options = new RandomDrawingOptions()
      }
      imageXyzRotation += ΔImageXyzRotation
    }
  }

  private def drawPortion(portion: Double, imageθ: Float) = {
    val radiusRange = StartingRadius - 0
    val minRadiusThisPortion = StartingRadius - radiusRange * portion

    withPushedMatrix {
      translate(width / 2, height / 2, 0)
      if (rotX) rotateX(imageθ)
      if (rotY) rotateY(imageθ)
      if (rotZ) rotateZ(imageθ)
      background(0)

      var radius = StartingRadius
      var pointθ = 0.0
      var z = 0F

      while (radius > minRadiusThisPortion) {
        val x = cos(pointθ) * radius
        val y = sin(pointθ) * radius

        val proportionComplete = 1 - (radius / StartingRadius).toFloat
        val parts = options.gradient.colorsFor(proportionComplete)
        fill(parts._1, parts._2, parts._3)

        withPushedMatrix {
          translate(x.toInt, -y.toInt, z)
          box(options.boxSize)
        }

        radius += options.Δradius
        pointθ = (pointθ + options.Δpointθ) % (2 * Pi)
        z += options.ΔzPerPoint
      }
    }
  }

  override def keyPressed(event: KeyEvent): Unit = {
    commonKeyPressed(event)
    super.keyPressed(event)
  }

  class RandomDrawingOptions {
    val Δpointθ         = randBetween(0.02, 0.08)
    val Δradius         = randBetween(-0.5, 0.01)
    val ΔzPerPoint      = randBetween(-0.02, 0.02).toFloat
    val ΔportionToDraw  = randBetween(0.005, 0.010)
    val gradient = new Gradient()
    val boxSize = 1 + random.nextInt(12)

    private def randBetween(min: Double, max: Double) = {
      val range = max - min
      min + random.nextDouble * range
    }
  }
}

object Spiral3D {
  def main(args: Array[String]): Unit = PApplet.main("proc.Spiral3D")
}
