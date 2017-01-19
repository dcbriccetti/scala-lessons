package proc

import scala.util.Random.{nextDouble, nextInt}
import math.{cos, sin}
import processing.core.{PApplet, PConstants}
import processing.event.KeyEvent

class DiminishingDimensions extends ScalaProcessingApplet with Common3dKeys {
  val PrismHeight = 600
  val PrismWidth = (PrismHeight * 1.8).toInt
  val NumPoints = 5000
  var colors = randomColors
  var phase = 0

  def randomCoords(max: Int = PrismHeight) = (0 until NumPoints).toArray.map(_ => nextInt(max) - max / 2)
  def zeroes(max: Int = PrismHeight) = (0 until NumPoints).toArray.map(_ => 0)
  var xs = zeroes(PrismWidth)
  var ys = zeroes()
  var zs = zeroes()
  var explodeMultipliers: Array[Array[Double]] = _
  val ExplodeSteps = 200
  var explodeStep = 0
  var rot = 0f

  override def settings() = {
    size(1920, 1080, PConstants.P3D)
  }

  override def setup() = {
    frameRate(60)
  }

  override def draw() = if (! suspend) {
    background(15)

    withPushedMatrix {
      translate(width / 2, height / 2, 0)
      if (rotX) rotateX(rot)
      if (rotY) rotateY(rot)
      if (rotZ) rotateZ(rot)
      0 until NumPoints foreach { n =>
        val c = colors(n)
        stroke(c._1, c._2, c._3)
        fill(c._1, c._2, c._3)
        withPushedMatrix {
          translate(xs(n), ys(n), zs(n))
          box(3)
        }
      }
    }
    rot += 0.01f

    val changed = move()

    if (phase >= 0 && ! changed)
      phase = (phase + 1) % 4
  }

  private def move() = {
    phase match {
      case 0 =>
        val changed = coalesceToZero(xs)
        if (!changed) {
          // Restart
          colors = randomColors
          explodeStep = 0
          explodeMultipliers = (0 until NumPoints).toArray.map { _ =>
            def a = nextDouble * math.Pi * 2
            val r = nextDouble * PrismWidth
            val (x, y, z) = sphericalToCartesian(r, a, a)
            Seq(x, y, z).toArray.map(_ / ExplodeSteps)
          }
        }
        changed
      case 1 =>
        explode(Seq((0, xs), (1, ys), (2, zs)))
      case 2 =>
        coalesceToZero(ys)
      case 3 =>
        coalesceToZero(zs)
    }
  }

  private def sphericalToCartesian(r: Double, θ: Double, φ: Double) = {
    val x = r * sin(φ) * cos(θ)
    val y = r * sin(φ) * sin(θ)
    val z = r * cos(φ)
    (x, y, z)
  }

  override def keyPressed(event: KeyEvent): Unit = {
    commonKeyPressed(event)
    super.keyPressed(event)
  }

  private def randomColors = {
    (0 until NumPoints).toArray.map { _ =>
      def rc = 200 + nextInt(56)
      nextInt(3) match {
        case 0 => (rc, rc, 0)
        case 1 => (0, 0, rc)
        case 2 => (rc, rc, rc)
      }
    }
  }

  private def coalesceToZero(coords: Array[Int]) = {
    var changed = false
    0 until NumPoints foreach { i =>
      val remaining = math.abs(coords(i))
      val chg = if (coords(i) == 0) 0 else coords(i) / remaining
      if (chg != 0) {
        coords(i) -= chg * math.min(3, remaining)
        changed = true
      }
    }
    changed
  }

  private def explode(axes: Iterable[(Int, Array[Int])]): Boolean = {
    explodeStep += 1
    0 until NumPoints foreach { i =>
      axes.foreach {
        case (axisIndex, coords) =>
          coords(i) = math.round(explodeStep * explodeMultipliers(i)(axisIndex)).toInt
      }
    }
    explodeStep < ExplodeSteps
  }

}

object DiminishingDimensions {
  def main(args: Array[String]) = PApplet.main("proc.DiminishingDimensions")
}
