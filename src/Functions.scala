object Functions extends App {
  def cToF(celsiusDeg: Int) = {
    math.round(celsiusDeg * 1.8 + 32)
  }
  println("C.\tF")
  0 to 100 foreach(c => {
    val f = cToF(c)
    println(s"$c $f")
  })
}
