import java.awt.Color
import scala.swing._

object Gradients extends SimpleSwingApplication {

  def top = new MainFrame {
    title = "Gradients Demo"
    contents = new GradientsDisplay
    peer.setLocationRelativeTo(null)
  }
}

class GradientsDisplay extends Component {
  def constantZero(i: Int) = 0
  def increment   (i: Int) = i
  def decrement   (i: Int) = 255 - i

  val gradFuncs = Seq(constantZero _, increment _, decrement _)
  val BarHeight = 20
  val BarSpacing = 3
  val GradationWidth = 1
  val Margin = BarSpacing

  preferredSize = new Dimension(GradationWidth * 256 + 2 * Margin, 27 * BarHeight + 26 * BarSpacing + 2 * Margin)

  override protected def paintComponent(g2d: Graphics2D) = {
    super.paintComponent(g2d)
    var y = Margin

    val idxCombos = for {
        r <- gradFuncs
        g <- gradFuncs
        b <- gradFuncs
    } {
        0 to 255 foreach(gradation => {
          val x = Margin + GradationWidth * gradation
          g2d.setColor(new Color(r(gradation), g(gradation), b(gradation)))
          g2d.fillRect(x, y, GradationWidth, BarHeight)
        })
        y += BarHeight + BarSpacing
    }
  }
}
