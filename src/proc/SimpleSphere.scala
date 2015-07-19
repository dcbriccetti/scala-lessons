package proc

import processing.core.{PConstants, PApplet}

class SimpleSphere extends PApplet {

  override def setup(): Unit = {
    size(500, 500, PConstants.OPENGL)
    fill(255, 255, 0)
  }

  override def draw() = {
    background(0)
    translate(width / 2, height / 2, 0)
    sphereDetail(20)
    sphere(200)
  }
}

object SimpleSphere {
  def main(args: Array[String]) = PApplet.main("proc.SimpleSphere")
}