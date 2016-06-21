package proc

import processing.event.KeyEvent

import processing.core.PApplet
import scala.util.Random.nextInt

class MultiColoredLines extends PApplet {

  override def settings() = {
    size(displayWidth, displayHeight)
  }

  override def draw() = {
    def cv = nextInt(256)
    stroke(cv, cv, cv)
    0 until width by 100 foreach(x => {
      line(x, 0, mouseX, mouseY)
    })
  }

  override def keyPressed(e: KeyEvent) = {
    super.keyPressed(e)
    if (e.getKey == ' ') {
      background(255)
    }
  }
}

object MultiColoredLines {
  def main(args: Array[String]) = PApplet.main("proc.MultiColoredLines")
}
