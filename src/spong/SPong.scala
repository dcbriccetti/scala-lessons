package spong

import java.awt.event.KeyEvent._
import proc.ScalaProcessingApplet
import processing.core.{PApplet, PConstants, PVector}
import processing.event.KeyEvent

class SPong extends ScalaProcessingApplet {
  val FieldRadius = 200
  val BallRadius = FieldRadius / 25
  val MoveAmt = 0.05f
  var fillPaddles = true

  val playerPaddle = Paddle(SphericalCoords(FieldRadius, 0, 0), (0, 255, 0))
  val paddles = Seq(
    Paddle(SphericalCoords(FieldRadius, 0.8f, 0.3f), (255, 0, 0)),
    playerPaddle
  )
  val ball = Ball(new PVector(0, 0, 0), new PVector(2, 0.5f, 4f))

  override def settings() = {
    size(1280, 1000, PConstants.P3D)
  }

  override def setup() = {
    frameRate(60)
  }

  override def keyPressed(event: KeyEvent) = {
    event.getKey match {
      case 'f'  => fillPaddles = !fillPaddles
      case _    =>
    }
    super.keyPressed(event)
  }

  override def draw(): Unit = {
    background(0)
    noLights()

    translate(width / 2, height / 2, 400)

    paddles.foreach { paddle =>
      withPushedMatrix {
        rotateY(paddle.position.φ)
        rotateX(paddle.position.θ)
        paddle.draw(this, fillPaddles)
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
    if (ball.position.mag() >= FieldRadius) {
      ball.bounce()
    }
  }
}

object SPong {
  def main(args: Array[String]) = PApplet.main("spong.SPong")
}

case class SphericalCoords(
  var r: Float,
  var φ: Float,
  var θ: Float
)
