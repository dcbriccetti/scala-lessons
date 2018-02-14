package proc

import processing.core.PApplet.radians
import processing.core.PConstants.HSB
import processing.core.{PApplet, PConstants}

class SpiralStairs extends ScalaProcessingApplet {

  var rotation = 0f
  var rotating = false

  override def settings(): Unit = fullScreen(PConstants.P3D)

  override def setup(): Unit = {
    colorMode(HSB, 100)
  }

  override def draw() = {
    background(0)
    translate(width / 2, 20)
    if (rotating)
      rotation += 0.01f
    rotateY(rotation)
    fill(0, 100, 100)

    for (degrees <- 0 to 360 * 4 by 12) {
      withPushedMatrix {
        rotateY(radians(degrees))
        translate(200, degrees, 0)
        box(50, 10, 30)
      }
    }
  }


  override def keyPressed(): Unit = {
    rotating = ! rotating
  }

}

object SpiralStairs extends App {
  PApplet.main("proc.SpiralStairs")
}
