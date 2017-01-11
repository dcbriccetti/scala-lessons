package proc

import processing.core.PApplet

/** A Processing applet with some enhancements for Scala programmers */
abstract class ScalaProcessingApplet extends PApplet {

  /** Runs the given function within a pair of push/popMatrix calls */
  protected def withPushedMatrix(fn: => Unit): Unit = {
    pushMatrix()
    fn
    popMatrix()
  }
}
