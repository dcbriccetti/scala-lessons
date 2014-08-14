object CoinToss1 extends App {
  var numHeads = 0
  var numTails = 0

  1 to 100 foreach(c => {
    if (math.random > .5) {
      numHeads += 1
    } else {
      numTails += 1
    }
  })
  println(s"$numHeads heads and $numTails tails")
}
