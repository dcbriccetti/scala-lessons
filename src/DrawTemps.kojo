import scala.xml._

val xml = XML.load("http://api.openweathermap.org/data/2.5/weather?q=Dublin,CA&mode=xml")
val temps = xml \ "temperature"
val min = temps \ "@min"
val value = temps \ "@value"
val max = temps \ "@max"
def kToC(kelvinDegrees: Double) = kelvinDegrees - 273.15
val allTemps = Seq(min, value, max).map(t => math.round(kToC(t.text.toDouble)))

clear()
setAnimationDelay(200)

val PelsPerDeg = 10
val DegsOnScale = 40

setPenColor(black)
setPenThickness(1)
jumpTo(-20, 0)
forward(DegsOnScale * PelsPerDeg)

0 to DegsOnScale by 5 foreach (temp => {
  jumpTo(-50, temp * PelsPerDeg + 10)
  setHeading(90)
  write(temp)
  jumpTo(-25, temp * PelsPerDeg)
  setHeading(0)
  forward(10)
})

setHeading(90)
jumpTo(0, 0)

setPenColor(red)
setPenThickness(10)

allTemps.foreach(temp => {
  forward(temp * PelsPerDeg)
  jumpTo(position.x + 30, 0)
})
