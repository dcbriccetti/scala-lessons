package proc

/** Randomly provides a variety of color part gradients (values 0 to 1) */
class Gradient {
  private var rgbTypes = randomColorTypes
  private val constantVal = math.random.toFloat

  /** Returns red, green, and blue color values (0 to 1), given the proportion through the gradient (0 to 1) */
  def colorsFor(proportionComplete: Float): (Float, Float, Float) = {
    val maxToMinColor = proportionComplete
    val minToMaxColor = 1 - proportionComplete
    val constantColor = constantVal
    val colors = Seq(maxToMinColor, minToMaxColor, constantColor)
    (colors(rgbTypes(0)), colors(rgbTypes(1)), colors(rgbTypes(2)))
  }

  /** Randomly chooses color types for each of read, green, and blue */
  private def randomColorTypes: IndexedSeq[Int] = {
    val result = (0 to 2) map(_ => (math.random * 3) toInt)
    if (result.forall(_ == 2))
      randomColorTypes // Can’t all be constant color. Try again.
    else
      result
  }
}
