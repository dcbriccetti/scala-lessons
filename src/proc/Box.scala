package proc

import java.awt.event.KeyEvent._
import processing.event.KeyEvent
import processing.core.{PConstants, PApplet}

class Box extends PApplet {

  var zRot = 0f
  var zRotChg = 0f

  override def settings(): Unit = {
    size(500, 500, PConstants.P3D)
    smooth(8)
  }

  override def setup(): Unit = {
    frameRate(100)
    stroke(255)
  }

  override def draw() = {
    background(0)
    lights()
    translate(width / 2, height / 2, 0)
    rotateY(zRot)
    zRot += zRotChg
    fill(255, 255, 0)
    box(100, 100, 100)
    if (frameCount % 100 == 0) {
      println(frameRate)
    }
  }

  override def keyPressed(event: KeyEvent) = {
    super.keyPressed(event)
    event.getKeyCode match {
      case VK_UP    => zRotChg += .01f
      case VK_DOWN  => zRotChg -= .01f
      case _        => super.keyPressed(event)
    }
  }
}

object Box {
  def main(args: Array[String]) = PApplet.main("proc.Box")
}