package geo

import java.io.File

import org.geotools.gce.geotiff.GeoTiffReader

object Elevation extends App {
  val r = new GeoTiffReader(new File("/Users/daveb/devel/elevation/laf-elev-4326.tiff"))
}
