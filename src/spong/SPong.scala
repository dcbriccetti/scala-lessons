package spong

import java.awt.event.KeyEvent._
import scala.util.Random
import proc.ScalaProcessingApplet
import processing.core.{PApplet, PConstants}

class SPong extends ScalaProcessingApplet {
  val TwoPi = math.Pi.toFloat * 2
  val FieldRadius = 600
  val BallRadius = FieldRadius / 25
  val MoveAmt = 0.05f
  val random = new Random()

  val playerPaddle = Paddle(SphericalCoords(FieldRadius, 0, 0), (0, 255, 0))
  val paddles = Seq(
    Paddle(SphericalCoords(FieldRadius, TwoPi / 8, TwoPi / 8), (255, 0, 0)),
    playerPaddle
  )
  val ball = Ball(Vector3D(0, 0, 0), Vector3D(2, 0.5f, 4f))

  override def settings() = {
    size(2560, 1440, PConstants.P3D)
  }

  override def setup() = {
    frameRate(60)
  }

  override def draw(): Unit = {
    background(0)
    noLights()

    translate(width / 2, height / 2, 0)

    paddles.foreach { paddle =>
      withPushedMatrix {
        rotateY(paddle.position.φ)
        rotateX(paddle.position.θ)
        paddle.draw(this)
      }
    }

    if (keyPressed) {
      val pos = playerPaddle.position
      keyCode match {
        case VK_LEFT  => pos.φ -= MoveAmt
        case VK_RIGHT => pos.φ += MoveAmt
        case VK_UP    => pos.θ += MoveAmt
        case VK_DOWN  => pos.θ -= MoveAmt
        case _        =>
      }
    }

    noFill()
    stroke(80)
    sphere(FieldRadius - 5)

    stroke(255, 255, 0)
    withPushedMatrix {
      translate(ball.position.x, ball.position.y, ball.position.z)
      sphere(BallRadius)
    }
    ball.move()
    if (ball.radius >= FieldRadius) {
      ball.bounce()
    }
  }
}

object SPong {
  def main(args: Array[String]) = PApplet.main("spong.SPong")
}

case class Vector3D(var x: Float, var y: Float, var z: Float)

case class SphericalCoords(
  var r: Float,
  var φ: Float,
  var θ: Float
)

case class Paddle(
  position: SphericalCoords,
  color: (Int, Int, Int)
) {
  def draw(pa: ScalaProcessingApplet): Unit = {
    pa.beginShape()
    pa.noStroke()
    pa.fill(color._1, color._2, color._3)
    1 to 9 foreach { circleIndex =>
      val φVertex = circleIndex / 40d
      val pointsAroundCircle = 10 * circleIndex
      0 to pointsAroundCircle foreach { pointIndex =>
        val θVertex = pointIndex * (math.Pi.toFloat * 2 / pointsAroundCircle)
        pa.sphericalVertex(position.r, θVertex, φVertex)
      }
    }
    pa.endShape()
  }
}

case class Ball(
  position: Vector3D,
  velocity: Vector3D
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

  def radius: Double = math.sqrt(
    position.x * position.x + position.y * position.y + position.z * position.z)
}
