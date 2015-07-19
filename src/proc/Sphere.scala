package proc

import java.awt.event.KeyEvent
import java.awt.event.KeyEvent._
import processing.core.{PConstants, PApplet}

class Sphere extends PApplet {
  var detail = 20
  var lightsOn = true
  var strokeOn = true

  override def setup(): Unit = {
    size(500, 500, PConstants.OPENGL)
    smooth()
    fill(255, 255, 0)
  }

  override def draw() = {
    background(0)
    if (lightsOn) lights() else noLights()
    if (strokeOn) stroke(0) else noStroke()
    translate(width / 2, height / 2, 0)
    sphereDetail(detail)
    sphere(200)
  }

  override def keyPressed(e: KeyEvent) = {
    e.getKeyCode match {
      case VK_UP   => detail += 1
      case VK_DOWN => if (detail > 1) detail -= 1
      case VK_L    => lightsOn = ! lightsOn
      case VK_S    => strokeOn = ! strokeOn
      case _       => super.keyPressed(e)
    }
  }
}

object Sphere {
  def main(args: Array[String]) = PApplet.main("proc.Sphere")
}