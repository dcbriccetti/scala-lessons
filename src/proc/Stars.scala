package proc

import processing.event.KeyEvent
import scala.util.Random.nextInt
import processing.core.{PConstants, PApplet}

class Stars extends PApplet {

  var rot = 0f
  val s = 200
  val NumPlanes = 5
  val NumStars = 4000
  var organizing = false

  def c = 1 to NumStars map(n => nextInt(s) - s / 2)
  def p1 = 0 until NumStars map(n => (n * s / NumPlanes) % s - s / 2)
  val xs1 = c.map(_.toFloat).toArray
  val xs2 = p1
  val ys = c
  val zs = c
  val steps = 300
  val dxs = xs1.zip(xs2).map(t => (t._2 - t._1) / steps.toFloat)
  var z = 100f

  override def settings() = {
    size(800, 800, PConstants.OPENGL)
  }

  override def setup() = {
    frameRate(60)
  }

  override def draw() = {
    lights()
    background(0)
    stroke(255)
    pushMatrix()
    translate(width / 2, height / 2, z)
    rotateY(rot)
    0 until NumStars foreach(n => {
      point(xs1(n), ys(n), zs(n))
    })
    popMatrix()
    if (organizing) {
      0 until NumStars foreach (i => {
        if (xs1(i) + dxs(i) < xs2(i)) {
          xs1(i) += dxs(i)
        }
      })
    }
    rot += .03f
    z += .02f
  }

  override def keyPressed(e: KeyEvent) = {
    super.keyPressed(e)
    if (e.getKey == ' ') organizing = true
  }
}

object Stars {
  def main(args: Array[String]) = PApplet.main("proc.Stars")
}
