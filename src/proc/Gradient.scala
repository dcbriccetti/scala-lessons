package proc

class Gradient {
  private var rgbTypes = randomColorTypes
  private val constantVal = math.random.toFloat

  def colorsFor(proportionComplete: Float) = {
    val maxToMinColor = proportionComplete
    val minToMaxColor = 1 - proportionComplete
    val constantColor = constantVal
    val colors = Seq(maxToMinColor, minToMaxColor, constantColor)
    (colors(rgbTypes(0)), colors(rgbTypes(1)), colors(rgbTypes(2)))
  }

  private def randomColorTypes: Array[Int] = {
    val result = (0 to 2).toArray map(_ => (math.random * 3) toInt)
    if (result.forall(_ == 2))
      randomColorTypes // Can’t all be constant color. Try again.
    else
      result
  }
}
