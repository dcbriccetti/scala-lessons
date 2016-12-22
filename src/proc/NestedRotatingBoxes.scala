package proc

import java.text.NumberFormat
import math.{log, pow, Pi}
import processing.core.{PApplet, PConstants, PFont}
import processing.event.KeyEvent

class NestedRotatingBoxes extends PApplet {
  val WindowSize            = 1080
  val SmallestBoxEdgeLength = 32      // A power of 2
  val RotationPerFrame      = 0.005F  // All angles are ㎭
  val MinEdgeColorAmount    = 50

  val power2Largest  = log2Int(WindowSize * .9)
  val power2Smallest = log2Int(SmallestBoxEdgeLength)
  val numBoxes = power2Largest - power2Smallest + 1
  var font: PFont = _
  var suspend = false
  var rotX = false
  var rotY = false
  var rotZ = false
  var drawBoxNumbers = false
  val fmt = NumberFormat.getInstance()
  fmt.setMinimumFractionDigits(2)
  fmt.setMaximumFractionDigits(2)
  var frameNum = 0

  override def settings(): Unit = {
    size(WindowSize, WindowSize, PConstants.P3D)
    smooth(8)
  }

  override def setup(): Unit = {
    font = createFont("Helvetica", 12)
  }

  override def draw() = {
    background(0)
    translate(WindowSize / 2, WindowSize / 2, 0)

    val moving = ! suspend && (rotX || rotY || rotZ)
    if (moving)
      frameNum += 1

    0 to numBoxes foreach { boxIndex =>
      pushMatrix()

      val boxRotationPerFrame =
        RotationPerFrame * (boxIndex + 1)
      val θ = ((frameNum * boxRotationPerFrame) %
        (2 * Pi)).toFloat
      if (rotX) rotateX(θ)
      if (rotY) rotateY(θ)
      if (rotZ) rotateZ(θ)

      val colorChangeBetweenBoxes =
        (256.0 - MinEdgeColorAmount) / numBoxes
      val colorAmount = (MinEdgeColorAmount +
        boxIndex * colorChangeBetweenBoxes).toInt
      stroke(0, colorAmount, 0)
      noFill()

      val edge2Exponent = power2Largest - boxIndex
      val edgeLength = pow(2, edge2Exponent).toInt
      box(edgeLength)

      if (drawBoxNumbers) {
        textFont(font)
        val halfEdge = edgeLength / 2
        val rotFmt = if (rotX || rotY || rotZ)
          ": " + fmt.format(θ) + "㎭" else ""
        fill(255, 255, 0)
        text(s"${boxIndex + 1}$rotFmt",
          -halfEdge + 2, -halfEdge + 10, halfEdge)
      }

      popMatrix()
    }
  }

  override def keyPressed(event: KeyEvent): Unit = {
    event.getKey match {
      case 'n' => drawBoxNumbers = !drawBoxNumbers
      case 'x' => rotX = ! rotX
      case 'y' => rotY = ! rotY
      case 'z' => rotZ = ! rotZ
      case ' ' => suspend = ! suspend
      case _   =>
    }
    super.keyPressed(event)
  }

  def log2Int(num: Double) = (log(num) / log(2)).toInt
}

object NestedRotatingBoxes {
  def main(args: Array[String]): Unit =
    PApplet.main("proc.NestedRotatingBoxes")
}
