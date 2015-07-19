package proc
import processing.core.PApplet

class Draw extends PApplet {
  override def draw() = {
    line(pmouseX, pmouseY, mouseX, mouseY)
  }
}

object Draw {
  def main(args: Array[String]) = PApplet.main("proc.Draw")
}
