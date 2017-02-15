package proc

import processing.core.{PApplet, PConstants}

import scala.util.Random

class LeavingShapes extends ScalaProcessingApplet {

  var cubes = Vector[LeavingShape]()

  case class LeavingShape() {
    var x = 0f
    var y = 0f
    var z = 0f

    def rd = Random.nextFloat * 10 - 5
    def rc = Random.nextInt(256)

    val dx = rd
    val dy = rd
    val dz = rd

    val red   = rc
    val green = rc
    val blue  = rc
    case class C3D(x: Float, y: Float, z: Float)
    val vs = Seq(
      C3D(-1, 0, 0),
      C3D(0, 2, -1),
      C3D(1, 0, 0),
      C3D(0, 1, 0)
    )
    val faces = Seq(
      Seq(1, 3, 4),
      Seq(1, 2, 4),
      Seq(2, 3, 4),
      Seq(1, 2, 3)
    )

    def draw(): Unit = {
      val Scale = 50
      withPushedMatrix {
        translate(width / 2 + x, height / 2 + y, z)
        rotateY(frameCount * 0.03f)
        stroke(255)
        fill(red, green, blue)
        faces foreach { face   =>
          beginShape()
          for {
            faceVertexNumber <- face
            v = vs(faceVertexNumber - 1)
          } vertex(v.x * Scale, v.y * Scale, v.z * Scale)
          endShape()
        }
      }
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
    frameRate(30)
    lights()
  }

  override def draw() = {
    background(0)

    if (frameCount % 30 == 0) {
      cubes :+= LeavingShape()
    }

    cubes.foreach(_.draw())
    cubes.foreach(_.move())
  }
}

object LeavingShapes {
  def main(args: Array[String]): Unit =
    PApplet.main("proc.LeavingCubes")
}
