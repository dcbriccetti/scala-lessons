import java.text.NumberFormat
import scala.util.Random
import processing.core.{PConstants, PApplet}
import PConstants.CENTER

class DiceHistogram extends PApplet {
  val counts = new Array[Int](13)
  val barWidth = 20
  val spacing = 5
  val numBars = 11
  val rg = new Random()
  var rolls = 0
  val font1 = createFont("Helvetica", 14)
  val font2 = createFont("Helvetica", 10)
  val nf = NumberFormat.getInstance
  def fmt(num: Int) = nf.format(num)
  
  override def setup() {
    size(barWidth * numBars + spacing * (numBars + 1), 800)
    textAlign(CENTER, CENTER)
    smooth()
    frameRate(60 * 10)
  }

  override def draw() = {
    background(255)

    def roll = rg.nextInt(6) + 1
    val outcome = roll + roll
    counts(outcome) += 1
    rolls += 1

    textFont(font1)
    text(s"Rolls: ${fmt(rolls)}", width / 2, 10)

    var reset = false
    
    0 to 10 foreach(i => {
      val barNum = i + 2
      val x = spacing + i * (barWidth + spacing)
      val y = height - 20
      val countThisBar = counts(barNum)
      val barHeight = countThisBar
      if (barHeight > height - 60) {
        reset = true
      }
      fill(0, 0, 128)
      rect(x, y - barHeight, 20, barHeight)
      fill(0)
      textFont(font1)
      text(barNum.toString, x + barWidth / 2, y + 10)
      textFont(font2)
      text(fmt(countThisBar), x + barWidth / 2, y - barHeight - 5)
    })
    
    if (reset) {
      rolls = 0
      for (i <- 0 until counts.length) {
        counts(i) = 0
      }
      println(frameRate)
    }
  }
}

object DiceHistogram {
  def main(args: Array[String]) = PApplet.main("DiceHistogram")
}
