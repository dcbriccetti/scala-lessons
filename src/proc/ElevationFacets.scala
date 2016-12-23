package proc

import processing.core.{PConstants, PApplet}

class ElevationFacets extends PApplet {

  private val elevations1 = Array(
    Array(4, 5, 2),
    Array(5, 2, 1),
    Array(1, 3, 0)
  )
  private val elevations = Array(
    Array(4, 4, 2),
    Array(4, 4, 1),
    Array(4, 4, 4)
  )
  private val elevations0 = Array(
    Array(310.1728515625,    310.1378479003906, 310.1368103027344),
    Array(309.7149353027344, 309.69921875,      309.4768371582031),
    Array(309.1067199707031, 309.2408142089844, 308.9651794433594)
  )
      
  private val pi = Math.PI.toFloat

  override def settings(): Unit = {
    size(800, 400, PConstants.P3D)
    smooth(8)
  }

  override def setup(): Unit = {
    frameRate(30)
    stroke(255)
  }

  override def draw(): Unit = {
    val scaleVal = 160

    def facet(c2: (Int, Int), c3: (Int, Int)): Unit = {
      val facetsScale = 100
      def vert(c: (Int, Int)): Unit = vertex((c._2 - 1).toFloat * facetsScale, (c._1 - 1).toFloat * facetsScale,
        ((elevations(c._1)(c._2)) * 10).toFloat - 10F)
      beginShape()
      vert((1, 1))
      vert(c2)
      vert(c3)
      vert((1, 1))
      endShape()
    }
    background(0)
    lights()
    translate(width / 2, height * 2 / 3, 0)
    rotateX(pi / 2 - mouseY / 100F)
    rotateZ(mouseX / 100F)
    noFill()
    stroke(64)

    pushMatrix()
    translate(0, 0, scaleVal / 2)
    box(scaleVal * 2, scaleVal * 2, scaleVal)
    popMatrix()

    fill(255)
    stroke(0)
    facet((1, 2), (0, 2))
    facet((0, 2), (0, 1))
    facet((0, 1), (0, 0))
    facet((0, 0), (1, 0))
    facet((1, 0), (2, 0))
    facet((2, 0), (2, 1))
    facet((2, 1), (2, 2))
    facet((2, 2), (1, 2))
  }
}

object ElevationFacets {
  def main(args: Array[String]) = PApplet.main("proc.ElevationFacets")
}