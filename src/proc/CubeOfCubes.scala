package proc

import processing.core.{PApplet, PConstants}

class CubeOfCubes extends ScalaProcessingApplet {
  private val BOX_SPACING = 50
  private lazy val maxOffset = height / 2 - 200

  override def settings(): Unit = {
    size(2560, 1420, PConstants.P3D)
  }

  override def draw() = {
    background(0)
    translate(width / 2, height / 2, -maxOffset)
    rotateAllAxes(angleOverTime(0.1F))
    val range = -maxOffset to maxOffset by BOX_SPACING
    for (x <- range; y <- range; z <- range) {
      withPushedMatrix {
        translate(x, y, z)
        rotateY(angleOverTime(.5))
        fill(colorPart(x), colorPart(y), colorPart(z))
        box(varyOverTime(5, 30, 2))
      }
    }
  }

  private def rotateAllAxes(θ: Float) = {
    rotateX(θ); rotateY(θ); rotateZ(θ)
  }

  private def colorPart(offset: Int) = PApplet.map(offset, -maxOffset, maxOffset, 0, 255)
}

object CubeOfCubes extends App {
  PApplet.main("proc.CubeOfCubes")
}
