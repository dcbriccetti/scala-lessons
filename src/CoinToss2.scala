object CoinToss2 extends App {
  val numTosses = 100
  val numHeads = Range(0, numTosses).map(n => math.random).count(_ > .5)
  val numTails = numTosses - numHeads
  println(s"$numHeads heads and $numTails tails")
}
