package proc

import processing.core.PApplet
import scala.util.Random

class ColoredLines extends PApplet {

  val r = new Random()

  override def setup() = {
    size(displayWidth, displayHeight)
  }

  override def draw() = {
    def cv = r.nextInt(256)
    stroke(cv, cv, cv)
    line(width / 2, height / 2, mouseX, mouseY)
  }
}

object ColoredLines {
  def main(args: Array[String]) = PApplet.main("proc.ColoredLines")
}
