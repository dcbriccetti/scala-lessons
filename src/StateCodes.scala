
object StateCodes extends App {
  val states = Map(
    "AZ" -> "Arizona",
    "CA" -> "California"
  )
  val code = readLine("Enter a state code ")
  val opState = states.get(code.toUpperCase)
  opState match {
    case Some(state) => println(state)
    case None        => println("There is no state with that code")
  }
}
