import java.text.NumberFormat
import scala.util.Random
import processing.core.{PConstants, PApplet}
import PConstants.CENTER

class DiceHistogram extends PApplet {
  val counts = new Array[Int](13)
  val barWidth = 60 // Best if a multiple of 1–6, the range of numbers of ways to roll 2–12 (e.g., 6 ways to roll 7)
  val spacing = 5
  val numBars = 11
  val rg = new Random()
  var rolls = 0
  var displayedFrameRate = 0
  val font1 = createFont("Helvetica", 14)
  val font2 = createFont("Helvetica", 10)
  val nf = NumberFormat.getInstance
  nf.setMaximumFractionDigits(0)
  def fmt[A](num: A) = nf.format(num)
  val outcomes = (for(a <- 1 to 6; b <- 1 to 6) yield a -> b).groupBy(t => t._1 + t._2)
  
  override def setup() {
    size(barWidth * numBars + spacing * (numBars + 1), 600)
    textAlign(CENTER, CENTER)
    smooth()
    noStroke()
    frameRate(2000)
    drawStatic()
  }

  override def draw() = {
    def roll = rg.nextInt(6) + 1
    val roll1 = roll
    val roll2 = roll
    val sum = roll1 + roll2

    counts(sum) += 1
    rolls += 1
    displayStatus()

    val x = spacing + (sum - 2) * (barWidth + spacing)
    val y = height - 20
    val countThisBar = counts(sum)
    val barHeight = countThisBar
    val barPieceY = y - barHeight

    // Draw a line in a light color
    stroke(220, 220, 255)
    line(x, barPieceY, x + barWidth, barPieceY)
    
    // Draw, in a darker color, the segment of the line corresponding to the combination of dice values
    val rollsForThisSum = outcomes(sum)
    val segmentWidth = barWidth / rollsForThisSum.size
    val indexOfThisRollForThisSum = rollsForThisSum.indexWhere(t => t._1 == roll1 && t._2 == roll2)
    val lineStartX = x + segmentWidth * indexOfThisRollForThisSum
    stroke(0, 0, 128)
    line(lineStartX, barPieceY, lineStartX + segmentWidth, barPieceY)
    noStroke()

    // Clear any previously-drawn count atop the bar
    fill(255)
    rect(x, barPieceY - 10, barWidth, 10)

    // Draw the current count atop the bar
    fill(0)
    textFont(font2)
    text(fmt(countThisBar), x + barWidth / 2, barPieceY - 6)

    // Start over once a bar nears the top of the window
    if (barHeight > height - 60) {
      reset()
    }
  }

  private def reset(): Unit = {
    rolls = 0
    for (i <- 0 until counts.length) {
      counts(i) = 0
    }
    drawStatic()
  }

  var frameRateLastDisplayedTime = 0L
  
  private def displayStatus(): Unit = {
    fill(255)
    rect(0, 0, width, 30)
    textFont(font1)
    fill(0)
    val now = System.currentTimeMillis
    if (now > frameRateLastDisplayedTime + 500) { // Don’t show frame rate every frame
      displayedFrameRate = frameRate.toInt
      frameRateLastDisplayedTime = now
    }
    text(s"Rolls: ${fmt(rolls)}, Frame rate: ${fmt(displayedFrameRate)}", width / 2, 10)
  }

  private def drawStatic() {
    background(255)
    0 to 10 foreach(i => {
      val barNum = i + 2
      val x = spacing + i * (barWidth + spacing)
      val y = height - 20
      fill(0)
      textFont(font1)
      text(barNum.toString, x + barWidth / 2, y + 10)
    })
  }
}

object DiceHistogram {
  def main(args: Array[String]) = PApplet.main("DiceHistogram")
}
