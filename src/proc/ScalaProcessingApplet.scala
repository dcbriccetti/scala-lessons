package proc

import processing.core.PApplet

import scala.math.{cos, sin}

/** A Processing applet with some enhancements for Scala programmers */
abstract class ScalaProcessingApplet extends PApplet {

  /** Runs the given function within a pair of push/popMatrix calls */
  protected def withPushedMatrix(fn: => Unit): Unit = {
    pushMatrix()
    fn
    popMatrix()
  }

  /** Runs the given function every “frames” frames */
  protected def every(frames: Int)(fn: => Unit): Unit = {
    if (frameCount % frames == 0)
      fn
  }

  def sphericalToCartesian(radius: Double, θ: Double, φ: Double): (Float, Float, Float) = {
    val x = radius * sin(φ) * cos(θ)
    val y = radius * sin(φ) * sin(θ)
    val z = radius * cos(φ)
    (x.toFloat, y.toFloat, z.toFloat)
  }

  def sphericalVertex(radius: Double, θ: Double, φ: Double) = {
    val (x, y, z) = sphericalToCartesian(radius, θ, φ)
    vertex(x, y, z)
  }
}
