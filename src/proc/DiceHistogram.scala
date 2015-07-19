package proc

import java.text.NumberFormat

import processing.core.PApplet
import processing.core.PConstants.CENTER

import scala.util.Random

class DiceHistogram extends PApplet {
  val barWidth = 60  // Best if a multiple of 1–6, the range of numbers of ways to roll 2–12 (e.g., 6 ways to roll 7)
  val spacing = 7
  val numDice = 2
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
  val combinations = generateCombinations(numDice)
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
    val rolls = 1 to numDice map (n => roll)
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
    val combinationsForThisSum = combinationsBySum(sum)
    val probabilityOfThisRoll = combinationsForThisSum.size / math.pow(numSides, numDice)
    val expectedCountsOfThisRoll = (probabilityOfThisRoll * numRolls).toInt

    // Draw a bar background line whose color shows whether this bar is behind, even, or ahead of the expectation
    val ahead = countThisBar - expectedCountsOfThisRoll
    if (math.abs(ahead) < 3)
      stroke(255, 255, 255)
    else if (ahead > 0)
      stroke(200, 255, 200)
    else
      stroke(255, 200, 200)
    line(x, barPieceY, x + barWidth, barPieceY)

    // Draw, in a darker color, the segment of the line corresponding to the combination of dice values
    val segmentWidth = barWidth / combinationsForThisSum.size
    val indexOfThisRollForThisSum = combinationsForThisSum.indexWhere(_ == rolls)
    val lineStartX = x + segmentWidth * indexOfThisRollForThisSum
    stroke(0, 0, 128)
    line(lineStartX, barPieceY, lineStartX + segmentWidth, barPieceY)
    noStroke()

    // Draw a vertical thin bar, showing the expected counts for this sum, given the number of rolls so far
    stroke(0, 0, 0)
    line(x - 1, y, x - 1, y - expectedCountsOfThisRoll)
    line(x + barWidth + 1, y, x + barWidth + 1, y - expectedCountsOfThisRoll)
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
    for (i <- counts.indices) counts(i) = 0
    drawStatic()
  }

  private var frameRateLastDisplayedTime = 0L

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

  private def generateCombinations(numDice: Int): List[List[Int]] =
    if (numDice > 0)
      for {
        h <- (1 to numSides).toList
        t <- generateCombinations(numDice - 1)
      } yield h :: t
    else 
      List(Nil)

  private def fmt[A](num: A) = nf.format(num)
}

object DiceHistogram {
  def main(args: Array[String]) = PApplet.main("proc.DiceHistogram")
}
