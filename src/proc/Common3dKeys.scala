package proc

import processing.event.KeyEvent

/** Adds a keyboard interface for common features */
trait Common3dKeys {
  protected var suspend = false
  protected var rotX = true
  protected var rotY = true
  protected var rotZ = true

  /** Processes a KeyEvent */
  def commonKeyPressed(event: KeyEvent): Unit = {
    event.getKey match {
      case 'x' => rotX = ! rotX
      case 'y' => rotY = ! rotY
      case 'z' => rotZ = ! rotZ
      case ' ' => suspend = ! suspend
      case _   =>
    }
  }
}
