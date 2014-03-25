package advent

object Adventure {

  def main(args: Array[String]) {

    class InventoryItem(val name: String)
    object BusPass extends InventoryItem("a bus pass")
    case class Money(dollars: Int) extends InventoryItem("$" + dollars)
    object UtilityBelt extends InventoryItem("a utility belt")
    object CodebreakingBook extends InventoryItem("a book on codebreaking")

    var money = 0
    var health = 100
    var inventory = Seq[InventoryItem]()

    case class Place(title: String, prep: String = "at", opAction: Option[() => Unit] = None, goal: Boolean = false)
    val livingRoom  = Place("your living room", prep = "in")
    val closet      = Place("a closet", prep = "in")
    val road        = Place("Main Street, in front of your house", prep = "on")
    val bus         = Place("a bus", prep = "on", opAction = Some(() => {
      inventory = inventory.filterNot(_ == BusPass)
    }))
    val library     = Place("the library")
    val kiosk       = Place("an information kiosk with a strange code written on it")
    val ladder      = Place("a ladder hidden inside the kiosk", prep = "on")
    val treasureRoom= Place("a room full of treasure", prep = "in", goal = true)

    case class Tr(place: Place, mustHave: Seq[InventoryItem] = Nil)
    implicit def placeToTr(place: Place) = Tr(place)
    val transitionsByPlace = Map[Place, Seq[Tr]](
      livingRoom    -> Seq(road, closet),
      road    -> Seq(livingRoom, Tr(bus, mustHave = Seq(BusPass))),
      closet  -> Seq(livingRoom),
      bus     -> Seq(kiosk, library, road),
      library -> Seq(bus),
      kiosk   -> Seq(bus, Tr(ladder, mustHave = Seq(CodebreakingBook))),
      ladder  -> Seq(treasureRoom, kiosk)
    )

    var availInventoryByPlace: Map[Place, Seq[InventoryItem]] = Map(
      closet  -> Seq(new Money(100), UtilityBelt, BusPass),
      library -> Seq(CodebreakingBook)
    )

    var place = livingRoom
    var keepRunning = true

    while (keepRunning) {
      println(s"\nYou are ${place.prep} ${place.title}.")
      if (inventory.size > 0) {
        println(s"You are carrying: ${inventory.map(_.name).mkString(", ")}.")
      }
      val displayMoney = if (money > 0) s"$$$money cash" else "no money"
      println(s"You have $displayMoney and your health is $health%.")
      transitionsByPlace.get(place) match {
        case None =>
          if (place.goal) {
            println("You win!")
          } else {
            println("There’s nowhere to go from here.")
          }
          keepRunning = false
        case Some(transitions) =>
          val fromHere = transitions.filter(_.mustHave.forall(c => inventory.contains(c))).map(_.place)
          println(s"You can go to: ${fromHere.map(_.title).mkString(", ")}.")
          println("Where would you like to go (type part of the name)? ")
          val response = io.ReadStdin.readLine()
          val newPlaces = fromHere.filter(_.title.toLowerCase.contains(response.toLowerCase))
          newPlaces match {
            case newPlace :: Nil =>
              place = newPlace
              place.opAction.foreach(_())
              availInventoryByPlace.get(place).foreach(items => {
                items.foreach {
                  case Money(dollars) =>
                    println(s"You found $$$dollars.")
                    money += dollars
                  case item: InventoryItem =>
                    println(s"You found ${item.name}.")
                    inventory :+= item
                }
                availInventoryByPlace -= place
              })
            case Nil =>
              println("You can't go there")
            case _ =>
              println("What you entered matches more than one place.")
          }
      }
    }
  }
}
