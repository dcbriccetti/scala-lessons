// Simulates throwing two dice, and draws a histogram of
// the results.

def simulate() = {
    val counts = new Array[Int](13)
    def roll = random(6) + 1
    repeat(1000) {
        val t = roll + roll
        counts(t) += 1
    }
    2 to 12 foreach(n => println(counts(n)))
    counts
}

def histogram(counts: Array[Int]) {
    val width = 20
    val spacing = 5
    val startX = - (width + spacing) * 6

    def rect(height: Int) {
        repeat(2) {
            forward(height)
            right(90)
            forward(width)
            right(90)
        }
    }

    2 to 12 foreach(i => {
        val x = startX + i * (width + spacing)
        val y = -100
        jumpTo(x, y)
        setPenColor(blue)
        rect(counts(i))
        jumpTo(x + width / 2 - 5, y - 5)
        setPenColor(black)
        write(i)
    })
}

clear()
clearOutput()
setAnimationDelay(50)
val counts = simulate()
histogram(counts)