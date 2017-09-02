package proc

import processing.core.{PApplet, PConstants}
import PConstants.PI

class Chess extends ScalaProcessingApplet {
  override def settings(): Unit = {
    size(400, 400, PConstants.P3D)
  }

  override def draw() = {
    background(128)
    val edge = 40
    val halfEdge = edge / 2
    translate(width / 2, height / 2, -100)
    rotateX(PI / 4)
    val range = 0 to 7
    for (x <- range; y <- range) {
      val blackOrWhite = if ((x + y) % 2 == 0) 255 else 32
      fill(blackOrWhite)
      withPushedMatrix {
        translate((x - 4) * edge + halfEdge, (y - 4) * edge + halfEdge)
        noStroke()
        box(edge, edge, 4)
      }
    }
  }
}

object Chess extends App {
  PApplet.main("proc.Chess")
}
