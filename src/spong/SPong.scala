package spong

import proc.ScalaProcessingApplet
import processing.core.{PApplet, PConstants}

class SPong extends ScalaProcessingApplet {
  override def settings() = {
    size(1000, 1000, PConstants.P3D)
  }

  override def setup() = {
    frameRate(60)
  }

  val twoPi = math.Pi.toFloat * 2
  val radius = 300

  override def draw(): Unit = {
    background(0)
    translate(width / 2, height / 2, 0)
    lights()
    rotateY(mouseX.toFloat / width  * twoPi)
    rotateX(mouseY.toFloat / height * twoPi)

    drawPaddle(0)
    drawPaddle(twoPi / 2)

    noFill()
    stroke(90)
    sphere(radius)
  }

  private def drawPaddle(pos: Float) = {
    beginShape()
    stroke(255)
    fill(255, 0, 0)
    0 to 10 foreach { a =>
      val φ = pos + a / 60d
      var θ = 0f
      val pointsAroundCircle = 10 * a + 2
      1 to pointsAroundCircle + 1 foreach { _ =>
        sphericalVertex(radius, θ, φ)
        θ += twoPi / pointsAroundCircle
      }
    }
    endShape()
    radius
  }
}

object SPong {
  def main(args: Array[String]) = PApplet.main("spong.SPong")
}
