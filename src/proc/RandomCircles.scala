package proc

import processing.event.KeyEvent
import scala.util.Random.nextInt
import processing.core.PApplet

class RandomCircles extends PApplet {

  override def settings() = {
    size(displayWidth, displayHeight)
  }

  override def draw() = {
    def rc = nextInt(256)
    fill(rc, rc, rc, 128)
    val MinDiameter = 20
    val diameter = MinDiameter + nextInt(width / 4)
    ellipse(mouseX, mouseY, diameter, diameter)
  }

  override def keyPressed(e: KeyEvent) = {
    super.keyPressed(e)
    if (e.getKey == ' ') {
      background(255)
    }
  }
}

object RandomCircles {
  def main(args: Array[String]) = PApplet.main("proc.RandomCircles")
}
