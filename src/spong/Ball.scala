package spong

import processing.core.PVector
import scala.util.Random

case class Ball(
  position: PVector,
  velocity: PVector
) {
  private val random = new Random()

  def move(): Unit = {
    position.x += velocity.x
    position.y += velocity.y
    position.z += velocity.z
  }

  /** Naïvely bounce, with a bit of randomness */
  def bounce(): Unit = {
    def bounceCoord(coord: Float) = {
      val randomness = 1f
      -coord + random.nextFloat * randomness - randomness / 2
    }

    velocity.x = bounceCoord(velocity.x)
    velocity.y = bounceCoord(velocity.y)
    velocity.z = bounceCoord(velocity.z)
  }
}