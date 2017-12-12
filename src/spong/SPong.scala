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
  var arenaSphereDetail = 20

  val playerPaddle = Paddle(SphericalCoords(FieldRadius, 0   ,    0), (0, 255, 0))
  val npcPaddle    = Paddle(SphericalCoords(FieldRadius, 0.8f, 0.3f), (255, 0, 0))
  val paddles = Seq(npcPaddle, playerPaddle)
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
    translate(width / 2, height / 2, 400)

    drawPaddles()
    drawArenaSphere()
    drawBall()

    movePlayerPaddle()
    moveBall()
  }

  private def drawPaddles() = {
    paddles.foreach { paddle =>
      withPushedMatrix {
        rotateY(paddle.position.φ)
        rotateX(paddle.position.θ)
        paddle.draw(this, fillPaddles)
      }
    }
  }

  private def drawArenaSphere() = {
    noFill()
    stroke(80)
    sphereDetail(arenaSphereDetail)
    sphere(FieldRadius - 5)
  }

  private def drawBall() = {
    stroke(255, 255, 0)
    withPushedMatrix {
      translate(ball.position.x, ball.position.y, ball.position.z)
      sphere(BallRadius)
    }
  }

  private def movePlayerPaddle() = {
    if (keyPressed) {
      val pos = playerPaddle.position
      keyCode match {
        case VK_LEFT => pos.φ -= MoveAmt
        case VK_RIGHT => pos.φ += MoveAmt
        case VK_UP => pos.θ += MoveAmt
        case VK_DOWN => pos.θ -= MoveAmt
        case _ =>
          key match { // Using key instead of keyCode to support layouts like Dvorak
            case 'D'  => arenaSphereDetail += 1
            case 'd'  => if (arenaSphereDetail > 3) arenaSphereDetail -= 1
            case _    =>
          }
      }
    }
  }

  private def moveBall() = {
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
