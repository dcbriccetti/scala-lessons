package proc

import scala.util.Random.{nextDouble, nextInt}
import processing.core.{PApplet, PConstants}
import PConstants.HSB
import processing.event.KeyEvent

class DiminishingDimensions extends ScalaProcessingApplet with Common3dKeys {
  val Height = 600
  val Width = (Height * 1.8).toInt
  val NumPoints = 3000
  var colors = randomColors
  var phase = 0

  def randomCoords(max: Int = Height) = forAllPointIndexes(_ => nextInt(max) - max / 2)
  def zeroes = Array.fill(NumPoints)(0)
  var xs = zeroes
  var ys = zeroes
  var zs = zeroes
  val ExplodeSteps = 200
  var explodeStep = 0
  var explodeMultipliers = calculateExplodeMultipliers
  var rot = 0f

  override def settings(): Unit = fullScreen(PConstants.P3D)

  override def setup() = {
    frameRate(60)
    colorMode(HSB, 100)
  }

  override def draw() = if (! suspend) {
    background(15)

    withPushedMatrix {
      translate(width / 2, height / 2, 0)
      if (rotX) rotateX(rot)
      if (rotY) rotateY(rot)
      if (rotZ) rotateZ(rot)
      forAllPointIndexes { i =>
        val c = colors(i)
        stroke(c._1, c._2, c._3)
        fill  (c._1, c._2, c._3)
        withPushedMatrix {
          translate(xs(i), ys(i), zs(i))
          box(3)
        }
      }
    }
    rot += 0.005f

    val changed = move()

    if (phase >= 0 && ! changed)
      phase = (phase + 1) % 4
  }

  private def move() = {
    phase match {
      case 0 => explode(Seq((0, xs), (1, ys), (2, zs)))
      case 1 => coalesceToZero(ys)
      case 2 => coalesceToZero(zs)
      case 3 =>
        val changed = coalesceToZero(xs)
        if (!changed) {
          // Restart
          colors = randomColors
          explodeStep = 0
          explodeMultipliers = calculateExplodeMultipliers
        }
        changed
    }
  }

  private def calculateExplodeMultipliers =
    forAllPointIndexes { _ =>
      def a = nextDouble * math.Pi * 2

      val r = nextDouble * Width
      val (x, y, z) = sphericalToCartesian(r, a, a)
      Seq(x, y, z).toArray.map(_ / ExplodeSteps)
    }

  override def keyPressed(event: KeyEvent): Unit = {
    commonKeyPressed(event)
    super.keyPressed(event)
  }

  private def forAllPointIndexes[A](fn: (Int) => A) = (0 until NumPoints).toArray.map(fn)

  private def randomColors = {
    forAllPointIndexes { _ => (nextInt(101), 100, 80 + nextInt(21)) }
  }

  private def coalesceToZero(coords: Array[Int]) = {
    var changed = false
    forAllPointIndexes { i =>
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
          coords(i) = math.round(explodeStep * explodeMultipliers(i)(axisIndex))
      }
    }
    explodeStep < ExplodeSteps
  }
}

object DiminishingDimensions {
  def main(args: Array[String]) = PApplet.main("proc.DiminishingDimensions")
}
