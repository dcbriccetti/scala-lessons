import scala.xml._
val xml = XML.load("http://api.openweathermap.org/data/2.5/weather?q=Dublin,CA&mode=xml")
println(xml \ "wind" \ "speed" \ "@value")
val temps = xml \ "temperature"
val min = temps \ "@min"
val value = temps \ "@value"
val max = temps \ "@max"
def kToC(kelvinDegrees: Double) = kelvinDegrees - 273.15
val allTemps = Seq(min, value, max).map(t => math.round(kToC(t.text.toDouble)))
