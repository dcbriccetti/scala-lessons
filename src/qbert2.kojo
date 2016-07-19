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
                //if (outside && skipSides.contains(iSide)) t.penUp() else t.penDown()
                t.forward(sideLength)
                t.right(360 / numSides)
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
} //> defined class MultiDraw //> warning: previously defined object MultiDraw is not a companion to class MultiDraw. //> Companions must be defined together; you may wish to use :paste mode for this.
    
def makeTurtle(x: Int, y: Int) = {
    val t = newTurtle(x, y)
    t.invisible()
    t.setAnimationDelay(10)
    t
} //> makeTurtle: (x: Int, y: Int)net.kogics.kojo.core.Turtle

val sideLength = 50 //> sideLength: Int = 50
val apothem = sideLength / (2 * Math.tan(Math.toRadians(180) / numSides)) //> apothem: Double = 43.30127018922194
val hspace = Math.round(2 * apothem).toInt //> hspace: Int = 87
val vspace = 3 * sideLength //> vspace: Int = 150
val centers = Seq(
                          (0, vspace, true),
                   (-hspace, 0, true), (hspace, 0, true),
    (-hspace * 2, -vspace, true), (0, -vspace, false), (hspace * 2, -vspace, true)
) //> centers: Seq[(Int, Int, Boolean)] = List((0,150,true), (-87,0,true), (87,0,true), (-174,-150,true), (0,-150,false), (174,-150,true))

clear()
invisible()
val hhgs = centers.map(center => (makeTurtle(center._1, center._2), center._3)) //> hhgs: Seq[(net.kogics.kojo.core.Turtle, Boolean)] = List((net.kogics.kojo.turtle.Turtle@77f7e4c7,true), (net.kogics.kojo.turtle.Turtle@33cd0259,true), (net.kogics.kojo.turtle.Turtle@58553ba0,true), (net.kogics.kojo.turtle.Turtle@43bdb2ae,true), (net.kogics.kojo.turtle.Turtle@5b1de040,false), (net.kogics.kojo.turtle.Turtle@66db87c,true))
val multiDraw = new MultiDraw(hhgs, sideLength) //> multiDraw: MultiDraw = MultiDraw@53998a38
multiDraw.hexaHexagon()
