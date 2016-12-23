class MultiDraw(hhgs: Seq[(Turtle, Boolean)], sideLength: Int) {

    val skipSidesByHexagonIndex = Map(
        0 -> Set(2, 3),
        2 -> Set(2, 3),
        4 -> Set(2, 3)
    ).withDefaultValue(Set())
    
    def hexagon(iHexagon: Int) {
        val skipSides = skipSidesByHexagonIndex(iHexagon)
        for (iSide <- 0 until 6) {
            hhgs.foreach(hhg => {
                val t = hhg._1
                val outside = hhg._2
                if (outside && skipSides.contains(iSide)) t.penUp() else t.penDown()
                t.forward(sideLength)
                t.right(360 / 6)
            })
        }
    }
    
    def hexaHexagon() {
        val colors = Seq(red, blue, green, yellow, brown, gray)
        for (iHexagon <- 0 until 6) {
            hhgs.foreach(_._1.setPenColor(red))
            hexagon(iHexagon)
            hhgs.foreach(_._1.right(60))
        }
    }
} //> defined class MultiDraw
    
def makeTurtle(x: Int, y: Int) = {
    val t = newTurtle(x, y)
    t.invisible()
    t.setAnimationDelay(10)
    t
} //> makeTurtle: (x: Int, y: Int)net.kogics.kojo.core.Turtle

val sideLength = 50 //> sideLength: Int = 50
val apothem = sideLength / (2 * Math.tan(Math.toRadians(180) / 6)) //> apothem: Double = 43.30127018922194
val hspace = Math.round(2 * apothem).toInt //> hspace: Int = 87
val vspace = 3 * sideLength //> vspace: Int = 150
val centers = Seq(
                          (0, vspace, true),
                   (-hspace, 0, true), (hspace, 0, true),
    (-hspace * 2, -vspace, true), (0, -vspace, false), (hspace * 2, -vspace, true)
) //> centers: Seq[(Int, Int, Boolean)] = List((0,150,true), (-87,0,true), (87,0,true), (-174,-150,true), (0,-150,false), (174,-150,true))

clear()
invisible()
val hhgs = centers.map(center => (makeTurtle(center._1, center._2), center._3)) //> hhgs: Seq[(net.kogics.kojo.core.Turtle, Boolean)] = List((net.kogics.kojo.turtle.Turtle@472cb01d,true), (net.kogics.kojo.turtle.Turtle@46de646c,true), (net.kogics.kojo.turtle.Turtle@329312cd,true), (net.kogics.kojo.turtle.Turtle@55b76aab,true), (net.kogics.kojo.turtle.Turtle@7cbc2c83,false), (net.kogics.kojo.turtle.Turtle@260e8c6f,true))
val multiDraw = new MultiDraw(hhgs, sideLength) //> multiDraw: MultiDraw = MultiDraw@5e4294b2
multiDraw.hexaHexagon()
