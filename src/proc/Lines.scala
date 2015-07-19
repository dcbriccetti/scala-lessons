package proc
import processing.core.PApplet

class Lines extends PApplet {
  override def draw() = {
    line(0, 0, mouseX, mouseY)
  }
}

object Lines {
  def main(args: Array[String]) = PApplet.main("proc.Lines")
}
