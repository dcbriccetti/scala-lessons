package proc

import scala.util.Random.nextInt
import processing.core.{PConstants, PApplet}

class DiminishingDimensions extends PApplet {
  private val cubeSize = 900
  private val NumPlanes = 6
  private val PlaneThickness = cubeSize / NumPlanes
  private val NumStars = 10000
  private var phase = 0
  private val phaseColors = Seq((255, 255, 255), (255, 255, 0), (0, 255, 0), (255, 255, 0))

  private def randomCoords = (0 until NumStars).toArray.map(_ => (nextInt(cubeSize) - cubeSize / 2).toFloat)
  private var xs = randomCoords
  private var ys = randomCoords
  private var zs = randomCoords

  override def settings() = {
    size(1920, 1080, PConstants.P3D)
  }

  override def setup() = {
    frameRate(60)
  }

  override def draw() = {
    background(0)
    val c = phaseColors(phase)
    stroke(c._1, c._2, c._3)

    pushMatrix()
    translate(width / 2, height / 2, 0)
    val invertedMouseY = height - mouseY
    val halfPi = math.Pi / 2
    rotateX((invertedMouseY.toFloat / height * math.Pi - halfPi).toFloat)
    rotateY((mouseX        .toFloat / width  * math.Pi - halfPi).toFloat)
    0 until NumStars foreach(n => point(xs(n), ys(n), zs(n)))
    popMatrix()

    var changed = false

    phase match {
      case 0 =>
        changed = coalesceAxis(xs)
      case 1 =>
        changed = coalesceAxis(ys)
      case 2 =>
        changed = coalesceAxis(zs)
      case 3 =>
        changed = coalesceAllToPoint()
        if (! changed) {
          // Restart
          xs = randomCoords
          ys = randomCoords
          zs = randomCoords
        }
    }

    if (phase >= 0 && ! changed)
      phase = (phase + 1) % 4
  }

  private def coalesceAxis(coords: Array[Float]) = {
    var changed = false
    0 until NumStars foreach { i =>
      if (coords(i).toInt % PlaneThickness != 0) {
        coords(i) -= 0.5f
        changed = true
      }
    }
    changed
  }

  private def coalesceAllToPoint() = {
    var changed = false
    0 until NumStars foreach { i =>
      Seq(xs, ys, zs).foreach { coords =>
        val coordInt = coords(i).toInt
        val chg = if (coordInt > 0) -1 else if (coordInt < 0) 1 else 0
        if (chg != 0) {
          coords(i) = coordInt + chg
          changed = true
        }
      }
    }
    changed
  }

}

object DiminishingDimensions {
  def main(args: Array[String]) = PApplet.main("proc.DiminishingDimensions")
}
