package proc
import processing.core.{PApplet, PConstants}

class Draw extends PApplet {
  override def draw() = {
    colorMode(PConstants.HSB)
    strokeWeight(4)
    stroke(frameCount % 360, 256, 256)
    line(pmouseX, pmouseY, mouseX, mouseY)
  }
}

object Draw {
  def main(args: Array[String]) = PApplet.main("proc.Draw")
}
