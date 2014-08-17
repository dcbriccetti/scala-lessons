clear()
setAnimationDelay(10)
setPenThickness(15)
right(90)

def incr(i: Int) = i
def decr(i: Int) = 255 - i
def zero(i: Int) = 0
val gradFuncs = Seq(incr _, decr _, zero _)

var y = 225

for (r <- gradFuncs; g <- gradFuncs; b <- gradFuncs) {
    setPosition(-380, y)
    0 to 255 foreach(grad => {
        setPenColor(Color(r(grad), g(grad), b(grad)))
        forward(3)
    })
    y -= 17
}