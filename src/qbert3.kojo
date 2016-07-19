val sl = 50 //> sl: Int = 50

def cube(x: Int, y: Int) {
    jumpTo(x, y)

    def side(left: Boolean) {
        val (color, sign) = if (left) (blue, 1) else (green, -1)
        setPenColor(color)
        setFillColor(color)
        forward(sl)
        turn(sign * 45)
        forward(sl)
        turn(sign * 135)
        forward(sl)
        turn(sign * 45)
        forward(sl)
        turn(sign * 135)
    }

    def top() {
        forward(sl)
        setPenColor(red)
        setFillColor(red)
        turn(45)
        forward(sl)
        turn(-90)
        forward(sl)
        turn(-90)
        forward(sl)
        turn(-90)
        forward(sl)
        turn(-135)
    }

    side(left = true)
    side(left = false)
    top()
} //> cube: (x: Int, y: Int)Unit

clear()
setAnimationDelay(5)
val hspace = Math.round(Math.sqrt(sl * sl + sl * sl)).toInt //> hspace: Int = 71
-250 to 250 by hspace foreach(x => cube(x, -200))
(-250 + hspace / 2) to (250 - hspace / 2) by hspace foreach(x => cube(x, -200 + hspace))
