package proc
import processing.core.{PApplet, PConstants}

class Draw extends PApplet {
  override def settings(): Unit = fullScreen(PConstants.P3D)

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
