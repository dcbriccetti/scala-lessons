clear()

def c(r: Int, g: Int, b: Int) = Color(r, g, b) //> c: (r: Int, g: Int, b: Int)java.awt.Color

val colors = Seq(
    c(255, 128, 0),
    c(128, 128, 0),
    c(0, 128, 255)
    ) //> colors: Seq[java.awt.Color] = List(java.awt.Color[r=255,g=128,b=0], java.awt.Color[r=128,g=128,b=0], java.awt.Color[r=0,g=128,b=255])

setAnimationDelay(25)
repeat(100) {
    setPenColor(colors((Math.random() * colors.length).toInt))
    jumpTo(0, 0)

    repeat(100) {
        forward(20)
        right(Math.random() * 360)
    }
}