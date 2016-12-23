package geo

import java.io._
import java.net.{URL, URLEncoder}

import processing.core.{PApplet, PConstants}

import scala.xml.{NodeSeq, XML}

class Geo extends PApplet {
  val apiKey = Secrets.apiKey  // Get an API key from https://developers.google.com/places/
  val area = "94549"
  val startTime = System.currentTimeMillis

  val placeTypes = Seq("school", "park", "book_store").par

  val cacheFile = new File("/tmp/geo.cache")
  val places = cachedData getOrElse
    cache(for {
      (areaLat, areaLong) <- findArea(area).toSeq
      placeType           <- placeTypes
      place               <- findNearbyPlaces(areaLat, areaLong, placeType)
    } yield place)

  val minLat    = places.map(_.lat).min
  val maxLat    = places.map(_.lat).max
  val latRange  = maxLat - minLat

  val minLong   = places.map(_.long).min
  val maxLong   = places.map(_.long).max
  val longRange = maxLong - minLong

  val minElev   = places.map(_.elevation).min
  val maxElev   = places.map(_.elevation).max
  val elevRange = maxElev - minElev

  val ScreenWidth = 1440
  val ScreenHeight = 1440
  val ScaleFactor = Math.min(ScreenHeight, ScreenWidth) * 0.8
  def latToScreen (lat:  Double) = ScreenHeight - ((lat - minLat) / latRange * ScaleFactor).toFloat - ScreenHeight / 2
  def longToScreen(long: Double) = ((long - minLong) / longRange * ScaleFactor).toFloat - ScreenWidth  / 2
  def elevToScreen(elev: Double) = ((elev - minElev) / elevRange * ScreenHeight / 2).toFloat - ScreenHeight / 4

  override def settings(): Unit = {
    size(ScreenWidth, ScreenHeight, PConstants.P3D)
    smooth(8)
  }

  override def setup(): Unit = {
    textFont(createFont("Helvetica", 14))
  }

  override def draw() = {
    background(0)
    translate(ScreenWidth / 2, ScreenHeight / 2, 0)
    val globalXRot = mouseY.toFloat / ScreenHeight * math.Pi.toFloat
    rotateX(globalXRot)
    val globalZRot = mouseX.toFloat / ScreenWidth * math.Pi.toFloat
    rotateZ(globalZRot)
    noFill()
    stroke(128)
    box(ScreenWidth, ScreenHeight, ScreenHeight / 2)
    for {
      place <- places
    } {
      pushMatrix()
      translate(longToScreen(place.long), latToScreen(place.lat), elevToScreen(place.elevation))
      stroke(0, 255, 0)
      sphere(5)
      rotateZ(-globalZRot)
      rotateX(-globalXRot)
      text(place.name, 7, 3)
      popMatrix()
    }
  }

  private def findArea(area: String): Option[(String, String)] = {
    log("Looking for " + area)
    val response = XML.load(new URL(s"https://maps.googleapis.com/maps/api/geocode/xml?key=$apiKey&address=$area"))
    (response \ "status").text match {
      case "OK" => Some(latLong(response \ "result" \ "geometry" \ "location"))
      case _    => None
    }
  }

  private def findNearbyPlaces(lat: String, long: String, placeType: String) = {
    log("Looking for " + placeType)
    val placeTypeEncoded = URLEncoder.encode(placeType, "utf8")
    val urlString = s"https://maps.googleapis.com/maps/api/place/nearbysearch/xml?key=$apiKey&type=$placeTypeEncoded&location=$lat,$long&radius=10000"
    println(urlString)
    val response = XML.load(new URL(urlString))
    (response \ "status").text match {
      case "OK" =>
        val results = response \ "result"
        for {
          result <- results
          name = (result \ "name").text
          vicinity = (result \ "vicinity").text
          (lat, long) = latLong(result \ "geometry" \ "location")
          elevation = findElev(lat, long)
        } yield Place(name, vicinity, lat.toDouble, long.toDouble, elevation)
      case _ =>
        Seq()
    }
  }

  private def findElev(lat: String, long: String) = {
    log(s"Looking for elevation at $lat, $long")
    val xml = XML.load(new URL(s"https://nationalmap.gov/epqs/pqs.php?x=$long&y=$lat&units=Meters&output=xml"))
    (xml \\ "Elevation").text.toDouble
  }

  private def latLong(loc: NodeSeq) = {
    def text(elem: String) = (loc \ elem).text
    (text("lat"), text("lng"))
  }

  private def cachedData = {
    if (cacheFile.exists) {
      val ois = new ObjectInputStream(new FileInputStream(cacheFile))
      val places = ois.readObject.asInstanceOf[Seq[Place]]
      ois.close()
      Some(places)
    } else None
  }

  private def cache(places: Seq[Place]) = {
    val oos = new ObjectOutputStream(new FileOutputStream(cacheFile))
    oos.writeObject(places)
    oos.close()
    places
  }

  private def log(msg: String): Unit =
    println(Thread.currentThread.getId + s" ${System.currentTimeMillis - startTime} $msg")
}

object Geo {
  def main(args: Array[String]): Unit = PApplet.main("geo.Geo")
}

@SerialVersionUID(1L)
case class Place(name: String, vicinity: String, lat: Double, long: Double, elevation: Double)
