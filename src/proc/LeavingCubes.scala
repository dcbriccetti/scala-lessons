package proc

import processing.core.{PApplet, PConstants}

import scala.util.Random

class LeavingCubes extends PApplet {

  var cubes = Vector[LeavingCube]()

  case class LeavingCube() {
    var x = 0f
    var y = 0f
    var z = 0f

    def rd = Random.nextFloat * 20 - 10
    def rc = Random.nextInt(256)

    val dx = rd
    val dy = rd
    val dz = rd

    val red   = rc
    val green = rc
    val blue  = rc

    def draw(): Unit = {
      pushMatrix()
      translate(width / 2 + x, height / 2 + y, z)
      stroke(255)
      fill(red, green, blue)
      box(50)
      popMatrix()
    }

    def move(): Unit = {
      x += dx
      y += dy
      z += dz
    }
  }

  override def settings(): Unit = {
    size(1920, 1080, PConstants.P3D)
    smooth(8)
  }

  override def setup(): Unit = {
    frameRate(60)
  }

  override def draw() = {
    background(0)

    if (frameCount % 30 == 0) {
      cubes :+= LeavingCube()
    }

    cubes.foreach(_.draw())
    cubes.foreach(_.move())
  }
}

object LeavingCubes {
  def main(args: Array[String]): Unit =
    PApplet.main("proc.LeavingCubes")
}
