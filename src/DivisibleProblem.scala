/* Display the numbers from 1 to 100 that are not divisible by both
4 and 7, but for those numbers divisible by 4, show ÷4 instead, and
for those divisible by 7, show ÷7 instead. */

object Solution1 extends App {
  println(
    (1 to 100 map(n => {
      def divBy(div: Int) = n % div == 0
      (divBy(4), divBy(7)) match {
        case (false, false) => Some(n)
        case (false, true ) => Some("÷7")
        case (true,  false) => Some("÷4")
        case (true,  true ) => None
      }
    })).flatten.mkString("\n")
  )
}

object Solution2 extends App {
  println(
    (1 to 100 map(n => {
      def divBy(div: Int) = n % div == 0
      if      (divBy(4) && divBy(7))  None
      else if (divBy(4))              Some("÷4")
      else if (divBy(7))              Some("÷7")
      else                            Some(n)
    })).flatten.mkString("\n")
  )
}
