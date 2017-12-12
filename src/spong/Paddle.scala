package spong

import proc.ScalaProcessingApplet

case class Paddle(
  position: SphericalCoords,
  color: (Int, Int, Int)
) {
  def draw(pa: ScalaProcessingApplet, fill: Boolean): Unit = {
    pa.beginShape()
    pa.noStroke()
    if (fill) {
      pa.fill(color._1, color._2, color._3)
    } else {
      pa.stroke(color._1, color._2, color._3)
    }
    1 to 9 foreach { circleIndex =>
      val φVertex = circleIndex / 40d
      val pointsAroundCircle = 10 * circleIndex
      0 to pointsAroundCircle foreach { pointIndex =>
        val θVertex = pointIndex * (math.Pi.toFloat * 2 / pointsAroundCircle)
        pa.sphericalVertex(position.r, θVertex, φVertex)
      }
    }
    pa.endShape()
  }
}
