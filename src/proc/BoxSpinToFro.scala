package proc

import processing.core.{PConstants, PApplet}

class BoxSpinToFro extends PApplet {

  var z = 0f
  var zChg = 3
  val ZLimit = 400
  var rot = 0f

  override def settings(): Unit = {
    size(500, 500, PConstants.P3D)
    smooth(8)
  }

  override def setup(): Unit = {
    frameRate(100)
    stroke(255)
    noFill()
  }

  override def draw() = {
    background(0)
    lights()
    translate(width / 2, height / 2, z)
    rotateY(rot)
    rot += 0.01f
    z += zChg
    if (z > ZLimit || z < -ZLimit)
      zChg = -zChg
    box(50, 50, 50)
  }
}

object BoxSpinToFro {
  def main(args: Array[String]) = PApplet.main("proc.BoxSpinToFro")
}