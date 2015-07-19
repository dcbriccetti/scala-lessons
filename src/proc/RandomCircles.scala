package proc

import java.awt.event.KeyEvent
import scala.util.Random
import processing.core.PApplet

class RandomCircles extends PApplet {

  val r = new Random()

  override def setup() = {
    size(displayWidth, displayHeight)
  }

  override def draw() = {
    def rc = r.nextInt(256)
    fill(rc, rc, rc, 128)
    val w = 20 + r.nextInt(width / 4)
    ellipse(mouseX, mouseY, w, w)
  }

  override def keyPressed(e: KeyEvent) = {
    super.keyPressed(e)
    if (e.getKeyChar == ' ') {
      background(255)
    }
  }
}

object RandomCircles {
  def main(args: Array[String]) = PApplet.main("proc.RandomCircles")
}
