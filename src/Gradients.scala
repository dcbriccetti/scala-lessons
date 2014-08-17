import java.awt.Color
import scala.swing._

object Gradients extends SimpleSwingApplication {

  def top = new MainFrame { // top is a required method
    title = "Gradients Demo"
    contents = new GradientsDisplay
  }
}

class GradientsDisplay extends Component {
  def constantZero(i: Int) = 0
  def increment(i: Int) = i
  def decrement(i: Int) = 255 - i
  val allFns = Seq(constantZero _, increment _, decrement _)
  val BarHeight = 20
  val BarSpacing = 3
  val Margin = 10

  preferredSize = new Dimension(3 * 256 + 2 * Margin, 27 * (BarHeight + BarSpacing) + 2 * Margin)

  override protected def paintComponent(g2d: Graphics2D) = {
    super.paintComponent(g2d)
    var y = Margin

    val idxCombos = for {
        i <- 0 to 2
        j <- 0 to 2
        k <- 0 to 2
    } {
        0 to 255 foreach(g => {
          val x = Margin + 3 * g
          g2d.setColor(new Color(allFns(i)(g), allFns(j)(g), allFns(k)(g)))
          g2d.fillRect(x, y, 3, BarHeight)
        })
        y += BarHeight + BarSpacing
    }
  }
}
