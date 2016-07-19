clear()

setAnimationDelay(5)
def qbert() {
    val turns = 6
    repeat(turns) {
        repeat(6) {
            forward(50)
            right(360 / 6)
        }
        right(360 / turns)
    }
}

jumpTo(-173, 0)
qbert()
jumpTo(0, 0)
qbert()
jumpTo(-173 / 2, 150)
qbert()
jumpTo(-173 / 2, -150)
qbert()