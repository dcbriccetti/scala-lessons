import java.awt.Dimension
import java.awt.event.{ActionEvent, ActionListener}
import javax.swing.Timer
import scala.swing.{Label, MainFrame, SimpleSwingApplication}

object FastReader extends SimpleSwingApplication {
  private val label = new Label("") {
    preferredSize = new Dimension(400, 50)
  }
  private var wordIndex = 0
  private val words =
    """
      |There were a king with a large jaw and a queen with a plain face, on the
      |throne of England; there were a king with a large jaw and a queen with
      |a fair face, on the throne of France. In both countries it was clearer
      |than crystal to the lords of the State preserves of loaves and fishes,
      |that things in general were settled for ever.
      |
    """.stripMargin.split("[ \\n]")

  def top = new MainFrame {
    title = "Fast Reader"
    contents = label
    peer.setLocationRelativeTo(null)

    val timer = new Timer(200, new ActionListener() {
      def actionPerformed(event: ActionEvent) {
        label.text = words(wordIndex)
        wordIndex = (wordIndex + 1) % words.length
      }
    })
    timer.start()
  }
}
