package advent

object Adventure {

  class InventoryItem(val name: String)
  case class Place(title: String, prep: String = "at", opAction: Option[() => Unit] = None, goal: Boolean = false)
  case class GameState(var place: Place, var money: Int = 0, var health: Int = 100, var inventory: Seq[InventoryItem] = Nil)

  def main(args: Array[String]) {

    object BusPass extends InventoryItem("a bus pass")
    case class Money(dollars: Int) extends InventoryItem("$" + dollars)
    object CodebreakingBook extends InventoryItem("a book on codebreaking")

    val livingRoom  = Place("your living room", prep = "in")
    val gs = GameState(livingRoom)

    val closet      = Place("a closet", prep = "in")
    val road        = Place("Main Street, in front of your house", prep = "on")
    val bus         = Place("a bus", prep = "on", opAction = Some(() => {
      gs.inventory = gs.inventory.filterNot(_ == BusPass)
    }))
    val library     = Place("the library")
    val kiosk       = Place("an information kiosk with a strange code written on it")
    val ladder      = Place("a ladder hidden inside the kiosk", prep = "on")
    val treasureRoom= Place("a room full of treasure", prep = "in", goal = true)

    case class Trans(place: Place, mustHave: Seq[InventoryItem] = Nil)
    val transitionsByPlace = {
      implicit def placeToTr(place: Place) = Trans(place)
      Map[Place, Seq[Trans]](
        livingRoom  -> Seq(road, closet),
        road        -> Seq(livingRoom, Trans(bus, mustHave = Seq(BusPass))),
        closet      -> Seq(livingRoom),
        bus         -> Seq(kiosk, library, road),
        library     -> Seq(bus),
        kiosk       -> Seq(bus, Trans(ladder, mustHave = Seq(CodebreakingBook))),
        ladder      -> Seq(treasureRoom, kiosk)
      )
    }

    var availInventoryByPlace: Map[Place, Seq[InventoryItem]] = Map(
      closet  -> Seq(new Money(100), BusPass),
      library -> Seq(CodebreakingBook)
    )

    var keepRunning = true

    while (keepRunning) {
      println(s"\nYou are ${gs.place.prep} ${gs.place.title}.")
      if (gs.inventory.size > 0) {
        println(s"You are carrying: ${gs.inventory.map(_.name).mkString(", ")}.")
      }
      val displayMoney = if (gs.money > 0) s"$$${gs.money} cash" else "no money"
      println(s"You have $displayMoney and your health is ${gs.health}%.")
      transitionsByPlace.get(gs.place) match {
        case None =>
          if (gs.place.goal) {
            println("You win!")
          } else {
            println("There’s nowhere to go from here.")
          }
          keepRunning = false
        case Some(transitions) =>
          val fromHere = transitions.filter(_.mustHave.forall(c => gs.inventory.contains(c))).map(_.place)
          println(s"You can go to: ${fromHere.map(_.title).mkString(", ")}.")
          println("Where would you like to go (type part of the name)? ")
          val response = io.ReadStdin.readLine()
          val newPlaces = fromHere.filter(_.title.toLowerCase.contains(response.toLowerCase))
          newPlaces match {
            case newPlace :: Nil =>
              gs.place = newPlace
              newPlace.opAction.foreach(_())
              availInventoryByPlace.get(newPlace).foreach(items => {
                items.foreach {
                  case Money(dollars) =>
                    println(s"You found $$$dollars.")
                    gs.money += dollars
                  case item: InventoryItem =>
                    println(s"You found ${item.name}.")
                    gs.inventory :+= item
                }
                availInventoryByPlace -= newPlace
              })
            case Nil =>
              println("What you entered doesn't match a place.")
            case _ =>
              println("What you entered matches more than one place.")
          }
      }
    }
  }
}
