package proc

import processing.core.PApplet

import scala.math.{cos, sin, Pi}

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

  /** Returns a rotation angle (in radians) given a number of rotations per second, using the elapsed time */
  def angleOverTime(rotationsPerSecond: Double) = (rotationsPerSecond / 1000 * millis * Pi * 2).toFloat

  /**
    * Returns a value in the range min to max, varied sinusoidally over time with period length periodSecs.
    * @param min the minimum value to be returned
    * @param max the maximum value to be returned
    * @param periodLengthSecs the length, in seconds, of one cycle of the sine curve
    * @return a value between min and max
    */
  def varyOverTime(min: Double, max: Double, periodLengthSecs: Double): Float = {
    val halfRange = (max - min) / 2
    val center = min + halfRange
    val periodLengthMs = (periodLengthSecs * 1000).toLong
    val timeInPeriodMs = millis % periodLengthMs
    val periodFraction = timeInPeriodMs.toFloat / periodLengthMs
    val angle = Pi * 2 * periodFraction
    val rangeOff = sin(angle) * halfRange
    (center + rangeOff).toFloat
  }
}
