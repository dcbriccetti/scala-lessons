package proc

import processing.core.{PConstants, PApplet}

class PushIntoSpheres extends PApplet {

  override def settings(): Unit = {
    size(700, 697, PConstants.OPENGL)
  }

  override def setup(): Unit = {
    smooth()
    fill(0, 0, 255)
    background(255)
    frameRate(150)
  }

  var z = 1
  var dz = -1
  var rot = 0f

  override def draw() = {
    background(255)
    val numCols = 5
    val hnc = numCols / 2
    for {
      xi <- -hnc to hnc
      yi <- -hnc to hnc
    } {
      pushMatrix()
      translate(width / 2, height / 2, z)
      rotate(rot)
      translate(width / numCols * xi, height / numCols * yi, z)
      rotate(-rot * 2)
      sphereDetail(10)
      sphere(50)
      popMatrix()
    }
    if (z > 200 || z < 0) dz = -dz
    z += dz
    rot += .001f
  }

}

object PushIntoSpheres {
  def main(args: Array[String]) = PApplet.main("proc.PushIntoSpheres")
}