package proc

import java.text.NumberFormat

import processing.core.{PApplet, PConstants, PFont}

class BruteSphere extends PApplet {
  val ScreenWidth = 1440
  val ScreenHeight = ScreenWidth
  val radius = ScreenWidth * 0.4
  val SurfaceThickness = 50
  var font: PFont = _
  val fmt = NumberFormat.getIntegerInstance

  case class Point(x: Int, y: Int, z: Int, surfaceCloseness: Double)
  var points = Vector[Point]()
  var numPointsGenerated = 0
  var yRot = 0f

  override def settings(): Unit = {
    size(ScreenWidth, ScreenHeight, PConstants.P3D)
  }

  override def setup(): Unit = {
    frameRate(60)
    background(0)
    font = createFont("Helvetica", 14)
  }

  override def draw(): Unit = {
    addBatchOfPoints()
    background(0)
    pushMatrix()
    translate(width / 2, height / 2, 0)
    rotateY(yRot)
    yRot += 0.004f
    points foreach { p =>
      val greenAmt = (p.surfaceCloseness * 256).toInt
      stroke(0, greenAmt, 256 - greenAmt)
      point(p.x, p.y, p.z)
    }
    popMatrix()
    textFont(font)
    text(s"Points generated: ${fmt.format(numPointsGenerated)}", 10, height - 20)
    text(s"Points on “surface”: ${fmt.format(points.size)}", 220, height - 20)
    text(s"Frames/second: ${frameRate.toInt}", 420, height - 20)
  }

  private def addBatchOfPoints() = {
    def rp = (math.random * ScreenWidth).toInt - ScreenWidth / 2

    1 to 1000 foreach { i =>
      numPointsGenerated += 1
      val x = rp
      val y = rp
      val z = rp
      val distanceFromCenter = math.sqrt(x * x + y * y + z * z)
      if (distanceFromCenter < radius && distanceFromCenter > radius - SurfaceThickness)
        points :+= Point(x, y, z, 1.0 - (radius - distanceFromCenter) / SurfaceThickness)
    }
  }
}

object BruteSphere {
  def main(args: Array[String]): Unit = PApplet.main("proc.BruteSphere")
}
