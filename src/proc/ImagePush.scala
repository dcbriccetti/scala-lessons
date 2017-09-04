package proc

import processing.core.{PApplet, PConstants, PImage}
import PConstants.HALF_PI
import processing.event.KeyEvent

/** Displays a photo, pushing individual pixels on the z axis according to their intensity */
class ImagePush extends ScalaProcessingApplet {
  var img: PImage = _
  var pushing = false

  override def settings(): Unit = {
    size(640, 750, PConstants.P3D)
  }

  override def setup(): Unit = {
    img = loadImage("proc/ImagePush.jpg")
    img.loadPixels()
  }

  override def draw() = {
    background(0)
    translate(width / 2, height / 2, -400)

    if (pushing) {
      rotateX(PApplet.map(mouseY, 0, height, HALF_PI, -HALF_PI))
      rotateY(PApplet.map(mouseX, 0, height, -HALF_PI, HALF_PI))
      translate(-img.width / 2, -img.height / 2)

      for (x <- 0 until img.width; y <- 0 until img.height) {
        val rgb = img.pixels(x + y * img.width)
        stroke(rgb >> 16 & 255, rgb >> 8 & 255, rgb & 255)
        point(x, y, brightness(rgb))
      }
    } else image(img, -img.width / 2, -img.height / 2)
  }

  /** Toggles “pushing” mode when space key is pressed */
  override def keyPressed(event: KeyEvent): Unit = {
    if (event.getKey == ' ')
      pushing = !pushing
    super.keyPressed(event)
  }
}

object ImagePush extends App {
  PApplet.main("proc.ImagePush")
}
