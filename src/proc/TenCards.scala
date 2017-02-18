package proc

import processing.core.{PApplet, PFont}

class TenCards extends ScalaProcessingApplet {
  var smallFont: PFont = _
  var largeFont: PFont = _
  val CardWidth = 30
  val CardHeight = (CardWidth * 1.4).toInt
  val BetweenCardMargin = 5
  val RequestedFramerate = 30

  case class Card(id: Char, var opValue: Option[Int]) {
    def draw(x: Int, y: Int): Unit = {
      stroke(170)
      fill(255)
      rect(x, y, CardWidth, CardHeight)
      textFont(smallFont)
      fill(0)
      text(id.toString, x + 2, y + 11)
      opValue.foreach { value =>
        fill(46, 139, 87)
        textFont(largeFont)
        text(value.toString, x + 2, y + CardHeight - 5)
      }
    }
  }

  case class Deck(deckNumber: Int, var cards: Seq[Card]) {
    val created = System.currentTimeMillis
    var topDealt = false
    var movedToBottom = false

    def draw(): Unit = {
      mutate()
      cards.zipWithIndex.foreach {
        case (card, vertIndex) =>
          val deckIndex = deckNumber - 1
          val cardsVertDisplacement = deckIndex * 2 - (
            if (deckNumber == 1) 0
            else if (movedToBottom) 0
            else if (topDealt) 1
            else 2
            )
          val x = BetweenCardMargin + deckIndex * (CardWidth + BetweenCardMargin)
          val y = BetweenCardMargin + (cardsVertDisplacement + vertIndex) * (CardHeight + BetweenCardMargin)
          card.draw(x, y)
      }
    }

    private def mutate(): Unit = {
      if (deckNumber > 1) {
        if (timeFor(topDealt, Deck.DealTopAt)) {
          cards = cards.drop(1)
          topDealt = true
        }
        if (timeFor(movedToBottom, Deck.MoveToBottomAt)) {
          val movingToBottom = cards.head
          cards = cards.drop(1) :+ movingToBottom
          val newTop = cards.head
          if (newTop.opValue.isEmpty)
            newTop.opValue = Some(deckNumber)
          movedToBottom = true
        }
      }
    }

    def next: Deck = {
      Deck(deckNumber + 1, cards)
    }

    private def timeFor(done: Boolean, time: Long) =
      System.currentTimeMillis - created > time && ! done
  }

  object Deck {
    val DealTopAt = 1000
    val MoveToBottomAt = 2000

    def initial: Deck = {
      var cardValue = 0
      Deck(1, ('a' to 'j' zipWithIndex) map { case (id, index) =>
        Card(id, if (index % 2 == 0) {
            cardValue += 1
            Some(cardValue)
          } else None
        )
      })
    }
  }

  case class Decks(var initial: Deck) {
    var decks = Vector(initial)

    def draw(): Unit = { decks.foreach(_.draw()) }

    def animate(): Unit = {
      every(3000 / RequestedFramerate) {
        add()
      }
    }

    def add(): Unit = {
      if (decks.size < 10) {
        val lastDeck = decks(decks.size - 1)
        val newDeck = lastDeck.next
        decks :+= newDeck
      }
    }
  }

  val decks = Decks(Deck.initial)

  override def settings(): Unit = {
    size(CardWidth * 10 + BetweenCardMargin * 11, CardHeight * 19 + BetweenCardMargin * 20)
  }

  override def setup(): Unit = {
    frameRate(RequestedFramerate)
    smallFont = createFont("Helvetica", 12)
    largeFont = createFont("Helvetica", 24)
  }

  override def draw(): Unit = {
    decks.animate()
    background(235)
    decks.draw()
  }
}

object TenCards extends App {
  PApplet.main("proc.TenCards")
}
