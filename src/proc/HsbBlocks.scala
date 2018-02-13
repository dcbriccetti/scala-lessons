package proc

import processing.core.{PApplet, PFont}
import PApplet.{map => pmap, max}
import processing.core.PConstants.HSB
import processing.event.KeyEvent

class HsbBlocks extends PApplet {

  var font: PFont = _
  var mouseHasMoved = false

  override def settings() = {
    size(displayWidth, displayHeight)
  }

  override def setup(): Unit = {
    colorMode(HSB, 100)
    font = createFont("Helvetica", 24)
    textFont(font)
    fill(0)
    text(s"mouse x: brightness, y: saturation, space: hue auto advance.", 20, 20)
  }

  var hue = 0
  var autoAdvanceHue = true

  override def draw() = {
    if (mouseHasMoved) {
      val saturation = pmap(mouseY, 0, height - 1, 100,   0)
      val brightness = pmap(mouseX, 0, width  - 1,   0, 100)
      fill(hue, saturation, brightness)
      rect(mouseX, max(mouseY, 20), 20, 20)
      if (autoAdvanceHue)
        hue = (hue + 1) % 101
    }
  }

  override def keyPressed(event: KeyEvent): Unit = {
    event.getKey match {
      case ' ' => autoAdvanceHue = !autoAdvanceHue
      case _   =>
    }
    super.keyPressed(event)
  }

  override def mouseMoved(): Unit = mouseHasMoved = true
}

object HsbBlocks {
  def main(args: Array[String]) = PApplet.main("proc.HsbBlocks")
}
