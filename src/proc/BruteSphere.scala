package proc

import java.text.NumberFormat
import processing.core.{PApplet, PConstants, PFont}

/** Graphically demonstrates how to find points which lie near the surface of a sphere,
  * using randomly chosen x, y, and z coordinates and the Pythagorean Theorem.
  */
class BruteSphere extends PApplet {
  val ScreenDimension = 720
  val SphereRadius = ScreenDimension * 0.4
  val SurfaceThickness = 32
  val YRotateRadiansPerMillisecond = 0.0001f
  val PointsToAddEachFrame = 1000
  var font: PFont = _
  val fmt = NumberFormat.getIntegerInstance

  case class Point(x: Int, y: Int, z: Int,
    surfaceCloseness: Double /* 1 = at surface, 0 = SurfaceThickness pixels inside the surface */)

  var points = Vector[Point]()
  var numPointsGenerated = 0
  val startTimeMillis = System.currentTimeMillis

  override def settings(): Unit = {
    size(ScreenDimension, ScreenDimension, PConstants.P3D)
  }

  override def setup(): Unit = {
    frameRate(60)
    background(0)
    font = createFont("Helvetica", 14)
    textFont(font)
  }

  override def draw(): Unit = {
    addBatchOfPoints()
    background(0)

    pushMatrix()
    translate(width / 2, height / 2, 0)
    rotateY(YRotateRadiansPerMillisecond * (System.currentTimeMillis - startTimeMillis))
    points foreach { p =>
      val greenAmt = math.round(p.surfaceCloseness * 255) // Greener nearer the surface
      val blueAmt = 255 - greenAmt // Bluer farther from the surface
      stroke(0, greenAmt, blueAmt)
      point(p.x, p.y, p.z)
    }
    popMatrix()

    val textY = height - 10
    text(s"Points generated: ${fmt.format(numPointsGenerated)}", 10, textY)
    text(s"Points on “surface”: ${fmt.format(points.size)}", 220, textY)
    text(s"Frames/second: ${frameRate.toInt}", 420, textY)
  }

  private def addBatchOfPoints() = {
    def randCoord = (math.random * ScreenDimension).toInt - ScreenDimension / 2

    1 to PointsToAddEachFrame foreach { _ =>
      numPointsGenerated += 1
      val (x, y, z) = (randCoord, randCoord, randCoord)
      val radiusThisPoint = math.sqrt(x * x + y * y + z * z)
      val pointWithinSurfaceThickness =
        radiusThisPoint >= SphereRadius - SurfaceThickness && radiusThisPoint <= SphereRadius
      if (pointWithinSurfaceThickness) {
        val surfaceCloseness = 1.0 - (SphereRadius - radiusThisPoint) / SurfaceThickness
        points :+= Point(x, y, z, surfaceCloseness)
      }
    }
  }
}

object BruteSphere {
  def main(args: Array[String]): Unit = PApplet.main("proc.BruteSphere")
}
