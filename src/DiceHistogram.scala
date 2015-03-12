import java.text.NumberFormat
import scala.util.Random
import processing.core.{PConstants, PApplet}
import PConstants.CENTER

class DiceHistogram extends PApplet {
  val barWidth = 60 // Best if a multiple of 1–6, the range of numbers of ways to roll 2–12 (e.g., 6 ways to roll 7)
  val spacing = 7
  val numDice = 3
  val numSides = 6
  val minSum = numDice
  val maxSum = numDice * numSides
  val numBars = maxSum - minSum + 1
  val counts = new Array[Int](numBars)
  val rg = new Random()
  var numRolls = 0
  var displayedFrameRate = 0
  val font1 = createFont("Helvetica", 14)
  val font2 = createFont("Helvetica", 10)
  val nf = NumberFormat.getInstance
  nf.setMaximumFractionDigits(0)
  def fmt[A](num: A) = nf.format(num)

  val combinations: Seq[Seq[Int]] = numDice match {
    case 1 => for (a <- 1 to numSides) yield Seq(a)
    case 2 => for (a <- 1 to numSides; b <- 1 to numSides) yield Seq(a, b)
    case 3 => for (a <- 1 to numSides; b <- 1 to numSides; c <- 1 to numSides) yield Seq(a, b, c)
  }
  val combinationsBySum = combinations.groupBy(_.sum)
  
  override def setup() {
    size(barWidth * numBars + spacing * (numBars + 1), 600)
    textAlign(CENTER, CENTER)
    smooth()
    noStroke()
    frameRate(2000)
    drawStatic()
  }

  override def draw() = {
    def roll = rg.nextInt(numSides) + 1
    val rolls = 1 to numDice map(n => roll)
    val sum = rolls.sum

    val i = sum - minSum
    counts(i) += 1
    numRolls += 1
    displayStatus()

    val x = spacing + i * (barWidth + spacing)
    val y = height - 20
    val countThisBar = counts(i)
    val barHeight = countThisBar
    val barPieceY = y - barHeight

    // Draw a line in a light color
    stroke(220, 220, 255)
    line(x, barPieceY, x + barWidth, barPieceY)
    
    // Draw, in a darker color, the segment of the line corresponding to the combination of dice values
    val combinationsForThisSum = combinationsBySum(sum)
    val segmentWidth = barWidth / combinationsForThisSum.size
    val indexOfThisRollForThisSum = combinationsForThisSum.indexWhere(_ == rolls)
    val lineStartX = x + segmentWidth * indexOfThisRollForThisSum
    stroke(0, 0, 128)
    line(lineStartX, barPieceY, lineStartX + segmentWidth, barPieceY)
    noStroke()
    
    // Draw a vertical thin bar, showing the expected counts for this sum, given the number of rolls so far
    val probabilityOfThisRoll = combinationsForThisSum.size / math.pow(numSides, numDice)
    val expectedCountsOfThisRoll = probabilityOfThisRoll * numRolls
    stroke(0, 0, 0)
    line(x - 1, y, x - 1, y - expectedCountsOfThisRoll.toInt)
    line(x + barWidth + 1, y, x + barWidth + 1, y - expectedCountsOfThisRoll.toInt)
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
  
  private var pausing = false
  
  override def keyPressed() {
    if (key == ' ') {
      pausing = ! pausing
      if (pausing) noLoop() else loop()
    }
  }

  private def reset(): Unit = {
    numRolls = 0
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
    text(s"Rolls: ${fmt(numRolls)}, Frame rate: ${fmt(displayedFrameRate)}", width / 2, 10)
  }

  private def drawStatic() {
    background(255)
    0 until numBars foreach(i => {
      val barNum = i + minSum
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
