import java.awt.Font
import scala.util.Random
import scala.swing.BorderPanel.Position.{Center, South}
import scala.swing.event.ButtonClicked
import scala.swing.{BorderPanel, Button, Dimension, FlowPanel, Font, Frame, Label, MainFrame, Reactor, SimpleSwingApplication}

object FortuneTeller extends SimpleSwingApplication with Reactor {
  private val fortunes = Seq(
    "You will soon have a new job.",
    "You will make a difference in someone’s life.",
    "Things will go badly for you for awhile.",
    "Tonight’s dinner will be great!",
    "The best schools are looking for you."
  )
  private var shuffledFortunes = Random.shuffle(fortunes).toList
  private val msg = new Label("") {
    preferredSize = new Dimension(600, 50)
    peer.setFont(new Font("Serif", Font.BOLD, 30))
  }

  override def top: Frame = new MainFrame {
    title = "Fortune Teller"
    contents = new BorderPanel {
      add(msg, Center)
      val button = new Button("Receive Your Fortune")
      listenTo(button)
      reactions += {
        case ButtonClicked(_) =>
          msg.text = shuffledFortunes(0)
          shuffledFortunes = shuffledFortunes.drop(1)
          if (shuffledFortunes.nonEmpty)
            button.text = "Receive Another Fortune"
          else {
            button.text = "Sorry, No More"
            button.enabled = false
          }
      }
      val buttonPanel = new FlowPanel() {
        contents += button
      }
      add(buttonPanel, South)
    }
  }
}
