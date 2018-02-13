package proc

import processing.core.{PApplet, PConstants, PVector}
import processing.core.PApplet.{cos, sin, map => pmap}
import processing.core.PConstants.{HSB, TWO_PI}

class ParticlesTowardCenter extends ScalaProcessingApplet {

  var particles = Seq[Particle]()
  var nextParticleAngle = 0F // Radians

  override def settings(): Unit = fullScreen(PConstants.P3D)

  override def setup(): Unit = {
    colorMode(HSB, 100)
  }

  override def draw() = {
    background(0)
    translate(width / 2, height / 2)
    // X controls speed towards center. Y controls density.
    if (frameCount % 5 == 0) {
      val stepsToCenter = pmap(mouseX, 0, width - 1, 1000, 10).toInt
      particles :+= Particle(nextParticleAngle, stepsToCenter)
    }
    val angleChange = pmap(mouseY, 0, height, 0.02F, 0.2F)
    nextParticleAngle = (nextParticleAngle + angleChange) % TWO_PI
    for (p <- particles) {
      p.draw()
      p.move()
    }
    particles = particles.filter(_.pos.mag > 1)
  }


  override def keyPressed(): Unit = {
    particles = Seq()
    nextParticleAngle = 0
  }

  case class Particle(angle: Float, numStepsToCenter: Int) {
    var pos = new PVector(cos(angle) * width / 3, sin(angle) * height / 3)
    val color = pmap(angle, 0, TWO_PI, 0, 100)
    val stepToCenter = PVector.div(pos, numStepsToCenter)
    var size = 30.0
    val sizeChg = {
      val smallestSize = 5
      (size - smallestSize) / numStepsToCenter
    }

    def draw(): Unit = {
      noStroke()
      fill(color, 100, 100)
      ellipse(pos.x, pos.y, size.toInt, size.toInt)
    }

    def move(): Unit = {
      pos.sub(stepToCenter)
      size -= sizeChg
    }
  }

}

object ParticlesTowardCenter extends App {
  PApplet.main("proc.ParticlesTowardCenter")
}
